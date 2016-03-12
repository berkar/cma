/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.common;

import org.jdom.Element;
import org.jaxen.jdom.JDOMXPath;
import org.jaxen.JaxenException;
import cma.ConsoleInterface;
import cma.command.TimeType;
import cma.util.Time;
import cma.vo.ImportVO;
import cma.vo.RegistrationVO;
import cma.vo.CompetitionPropertiesVO;
import cma.view.ImportTimeDialog;

import java.io.*;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.text.ParseException;
import javax.swing.*;

public class ImportTimeHelper
{
    private final static String SEPARATOR = ",";

    public static void execute(ConsoleInterface parent)
    {
        // Check for competition definition
        Element root = ConsoleInterface.getCompetition().getRootElement();
        Element common = root.getChild("common");
        CompetitionPropertiesVO prop = CompetitionPropertiesVO.parse(common);

        // Check for modified competition
        if (parent.isModified()) {
            // Auto save competition first
            parent.saveCompetition();
        }

        // Get result file name and parameters
        ImportVO vo = ImportTimeDialog.showDialog();
        if (vo != null) {
            System.out.println("Importera tider enligt:");
            System.out.println("->" + vo);
            String timeElement = null;
            if ("Mellantid 1".equals(vo.getPosition())) {
                timeElement = "time1";
            }
            if ("Mellantid 2".equals(vo.getPosition())) {
                timeElement = "time2";
            }
            if ("Mellantid 3".equals(vo.getPosition())) {
                timeElement = "time3";
            }
            if ("Sluttid".equals(vo.getPosition())) {
                timeElement = "totaltime";
            }

            // read result file according to supplied paremeters and handle result
            InputStream is = null;
            try {
                is = new FileInputStream(vo.getFileName());
                System.out.println("Importfile selected: " + vo.getFileName());
                Map map = parseFile(is);
                Iterator i = map.keySet().iterator();
                while (i.hasNext()) {
                    Long key = (Long) i.next();
                    JDOMXPath xpath = new JDOMXPath("//startnumber[text()='" + key + "']/parent::*");
                    Element reg = (Element) xpath.selectSingleNode(root);
                    if (reg != null) {
                        RegistrationVO regVO = RegistrationVO.parse(reg);
                        System.out.println("Found key " + key + " and registration: " + regVO);
                        String sTime = (String) map.get(key);
                        // get time on long, milliseconds
                        long time = getTime(
                            prop.getTime(),
                            regVO.getStarttime(),
                            sTime,
                            TimeType.findById(vo.getTimeType())
                        );
                        // Setup time element
                        Element eFinishTime = reg.getChild(timeElement);
                        if (eFinishTime == null) {
                            eFinishTime = new Element(timeElement);
                            reg.addContent(eFinishTime);
                        }
                        eFinishTime.setText(Long.toString(time));
                    }
                    else {
                        System.err.println("Could not find any registration for key: " + key);
                    }
                }
                int option = JOptionPane.showConfirmDialog(
                    parent,
                    "Vill du beräkna resultat också?",
                    "Import av tider klart!",
                    JOptionPane.YES_NO_OPTION
                );
                if(option == JOptionPane.YES_OPTION) {
                    CalculateResultHelper.execute(parent);
                }
                parent.setModified(true);
            }
            catch (JaxenException e1) {
                JOptionPane.showMessageDialog(parent, "Problem med läsning av tävlingsfil!");
                e1.printStackTrace();
            }
            catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(parent, "Kunde inte öppna vald importfil: " + vo.getFileName());
                e1.printStackTrace();
            }
            catch (IOException e1) {
                JOptionPane.showMessageDialog(parent, "Fel vid läsning av vald importfil: " + vo.getFileName());
                e1.printStackTrace();
            }
            catch (ParseException e1) {
                JOptionPane.showMessageDialog(parent, "Formatfel i importfil: " + e1.getMessage() + " p� rad: " + e1.getErrorOffset());
            }
            finally {
                if(is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException ignore) {}
                }
            }
        }
    }

    private static Map parseFile(InputStream is) throws ParseException, IOException
    {
        Map result = new HashMap();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int count = 1;
        String row = null;

        while ((row = br.readLine()) != null) {
            if(row.trim().length() > 0) {
                if (row.indexOf(SEPARATOR) == -1) {
                    // No separator found
                    throw new ParseException("Ingen separator hittades!", count);
                }
                try {
                    System.out.print("Parsing: " + row + "=>");
                    long number = Long.parseLong(row.substring(0, row.indexOf(SEPARATOR)));
                    String time = row.substring(row.indexOf(SEPARATOR) + 1, row.length());
                    result.put(new Long(number), time);
                    System.out.print("Startnumber=" + number + "," + "Time=" + time);
                    System.out.println();
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ParseException("Felaktigt format på startnummer! Måste vara ett heltal", count);
                }
                count++;
            }
        }
        return result;
    }

    private static long getTime(long firstTime, long startTime, String time, TimeType timeType)
        //throws ParseException
    {
        long longTime = -1;

        if (TimeType.RELATIVE_TIME.equals(timeType)) {
            // Time is relative, i.e. time after first start
            longTime = Time.string2Interval(time) - Math.abs(startTime - firstTime);
        }
        else if (TimeType.ABSOLUTE_TIME.equals(timeType)) {
            // Time is absolute, e.g 19:21:00
            longTime = Time.string2Interval(time) - startTime;
        }
        else {
            // Time is corrected, that is it's already correct
            longTime = Time.string2Interval(time);
        }

        return longTime;
    }
}
