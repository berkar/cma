/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Element;
import cma.ConsoleInterface;
import cma.common.FileChooserHelper;
import cma.util.Time;
import cma.vo.RegistrationVO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @deprecated Not used anymore, I hope ...
 */
public class ImportResultCmd extends RequiredCommand
{
    private final static String SEPARATOR = ",";
/*
  private final static String HTML_STYLESHEET = System.getProperty(
    "cma.resultlist.html.stylesheet", "/resultatlista_html.xslt"
  );
*/

    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        Element root = ConsoleInterface.getCompetition().getRootElement();
        // Get file name to store competition in
        String importFile = FileChooserHelper.getOpenFileName(parent, new String[]{"txt"});
        if (importFile == null) {
            return;
        }

        try {
            InputStream is = new FileInputStream(importFile);
            System.out.println("Importfile selected: " + importFile);
            Map map = parseFile(is);
            Iterator i = map.keySet().iterator();
            while (i.hasNext()) {
                Long key = (Long) i.next();
                //JDOMXPath xpath = new JDOMXPath("//startnumber[text()='" + key + "']");
                JDOMXPath xpath = new JDOMXPath("//startnumber[text()='" + key + "']/parent::*");
                //Element startNumber = (Element)xpath.selectSingleNode(root);
                Element reg = (Element) xpath.selectSingleNode(root);
                if (reg != null) {
                    //Element reg = startNumber.getParent();
                    RegistrationVO regVO = RegistrationVO.parse(reg);
                    System.out.println("Found key " + key + " and registration: " + regVO);
                    String sTime = (String) map.get(key);
                    // Test format of date
                    Time.string2Time(sTime);
                    // Setup finish time element
                    Element eFinishTime = reg.getChild("finishtime");
                    if (eFinishTime == null) {
                        eFinishTime = new Element("finishtime");
                        reg.addContent(eFinishTime);
                    }
                    eFinishTime.setText(sTime);
                }
                else {
                    System.err.println("Could not find any registration for key: " + key);
                }
            }
            parent.setModified(true);
        }
        catch (JaxenException e1) {
            JOptionPane.showMessageDialog(parent, "Problem med låsning av tävlingsfil!");
            e1.printStackTrace();
        }
        catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(parent, "Kunde inte öppna vald importfil: " + importFile);
            e1.printStackTrace();
        }
        catch (IOException e1) {
            JOptionPane.showMessageDialog(parent, "Fel vid låsning av vald importfil: " + importFile);
            e1.printStackTrace();
        }
        catch (ParseException e1) {
            JOptionPane.showMessageDialog(parent, "Formatfel i importfil: " + e1.getMessage() + " på rad: " + e1.getErrorOffset());
        }
    }

    private Map parseFile(InputStream is) throws ParseException, IOException
    {
        Map result = new HashMap();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int count = 1;
        String row = null;

        while ((row = br.readLine()) != null) {
            if (row.indexOf(SEPARATOR) == -1) {
                // No separator found
                throw new ParseException("Ingen separator hittades!", count);
            }
            System.out.println("Parsing: " + row);
            try {
                long number = Long.parseLong(row.substring(0, row.indexOf(SEPARATOR)));
                String time = row.substring(row.indexOf(SEPARATOR) + 1, row.length());
                result.put(new Long(number), time);
                System.out.println("  Startnumber: " + number);
                System.out.println("  Time: " + time);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                throw new ParseException("Felaktigt format på startnummer! Måste vara ett heltal", count);
            }
            count++;
        }
        return result;
    }
}
