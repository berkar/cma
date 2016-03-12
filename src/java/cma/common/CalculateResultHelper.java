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
import cma.ConsoleInterface;
import cma.vo.RegistrationVO;

import java.util.*;

public class CalculateResultHelper
{
    public static void execute(ConsoleInterface parent)
    {
        Element root = ConsoleInterface.getCompetition().getRootElement();
        // Set start number for all included registrations based on competition properties
        // Use a sorted map to make things easier
        TreeMap map = new TreeMap(new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                long long1 = ((Long) o1).longValue();
                long long2 = ((Long) o2).longValue();
                if (long1 < long2) return -1;
                if (long1 > long2) return 1;
                return 0;
            }
        });

        // Setup starting point and sort based on total time, for each class
        List clsList = root.getChild("classes").getChildren("class");
        Iterator i1 = clsList.iterator();
        // For each class
        while (i1.hasNext()) {
            map.clear();
            Element eClass = (Element) i1.next();
            List regList = eClass.getChildren("registration");
            Iterator i2 = regList.iterator();
            // For each registration
            while (i2.hasNext()) {
                Element eReg = (Element) i2.next();
                RegistrationVO regVO = RegistrationVO.parse(eReg);
                // Only use registrations that have started, is not abandoned and not disqualified and has a totaltime
                if (regVO.getTotaltime() > 0) {
                    if (regVO.isStarting()) {
                        if (regVO.isAbandon() == false && regVO.isDisqualified() == false) {
                            System.out.println("Sorting: " + regVO);
                            // Add registration to sorted map, based on firstTotal time
                            map.put(new Long(regVO.getTotaltime()), eReg);
                        }
                    }
                }
            }
            // Use the sorted map to get things in order
            boolean first = true;
            long firstTotal = 0;
            long position = 1;
            Iterator i3 = map.keySet().iterator();
            while (i3.hasNext()) {
                Long key = (Long) i3.next();
                Element eReg = ((Element) map.get(key));
                RegistrationVO regVO = RegistrationVO.parse(eReg);
                if (first) {
                    firstTotal = regVO.getTotaltime();
                    first = false;
                }
                // Take care of position in competition
                Element aStart = eReg.getChild("position");
                // Check if element exists
                if (aStart != null) {
                    // Update element found
                    aStart.setText(Long.toString(position++));
                }
                else {
                    // Create new element
                    eReg.addContent(new Element("position").setText(Long.toString(position++)));
                }
                // Take care of difference in time between positions
                long diff = regVO.getTotaltime() - firstTotal;
                Element eDiff = eReg.getChild("difftime");
                // Check if element exists
                if (eDiff != null) {
                    // Update element found
                    eDiff.setText(Long.toString(diff));
                }
                else {
                    // Create new element
                    eReg.addContent(new Element("difftime").setText(Long.toString(diff)));
                }
            }
        }
        parent.setModified(true);
    }
}
