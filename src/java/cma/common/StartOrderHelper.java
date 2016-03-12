/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 13, 2003
 * Time: 9:03:52 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.common;

import org.jdom.Element;
import cma.vo.ClassVO;
import cma.vo.RegistrationVO;

import java.util.*;

public class StartOrderHelper
{
    /**
     * For a competition class, take care of deciding start order, setting start numbers and
     * setting start times based on start numbers to registrations
     * @deprecated
     */
    public static long setStartOrder(Element eClass, long startNumber, long startTime, long startInterval)
    {
        // Save start time
        //long orgStartTime = startTime;

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
        // Setup starting point
        Random random = new Random();
        map.clear();
        ClassVO vo = ClassVO.parse(eClass);
        System.out.println("Class: " + vo);
        List regList = eClass.getChildren("registration");
        Iterator i2 = regList.iterator();
        // For each registration
        while (i2.hasNext()) {
            Element elem = (Element) i2.next();
            RegistrationVO regVO = RegistrationVO.parse(elem);
            long start = random.nextLong();
            System.out.println("->" + regVO + "=" + start);
            map.put(new Long(start), elem);
        }
        System.out.println("Size: " + map.size());
        // Use the sorted map to get things in order
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = (Long) iterator.next();
            Element reg = ((Element) map.get(key));
            Element start = reg.getChild("startnumber");
            // Check if element exists
            if (start != null) {
                // Update element found
                start.setText(Long.toString(startNumber++));
            }
            else {
                // Create new element
                reg.addContent(new Element("startnumber").setText(Long.toString(startNumber++)));
            }
            Element time = reg.getChild("starttime");
            // Check if element exists
            if (time != null) {
                // Update element found
                time.setText(Long.toString(startTime));
            }
            else {
                // Create new element
                reg.addContent(new Element("starttime").setText(Long.toString(startTime)));
            }
            if (iterator.hasNext()) {
                // Only add start interval if more registrations available
                startTime += startInterval;
            }
        }
        return map.size();
    }

    /**
     * For a competition class, take care of deciding start order and setting start numbers to registrations
     * @param eClass
     * @param startNumber
     * @return Count of found registrations
     */
    public static long setStartNumber(Element eClass, long startNumber)
    {
        // Save start time
        //long orgStartTime = startTime;

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
        // Setup starting point
        Random random = new Random();
        map.clear();
        ClassVO vo = ClassVO.parse(eClass);
        System.out.println("Class: " + vo);
        List regList = eClass.getChildren("registration");
        Iterator i2 = regList.iterator();
        // For each registration
        while (i2.hasNext()) {
            Element elem = (Element) i2.next();
            RegistrationVO regVO = RegistrationVO.parse(elem);
            long start = random.nextLong();
            System.out.println("->" + regVO + "=" + start);
            map.put(new Long(start), elem);
        }
        System.out.println("Size: " + map.size());
        // Use the sorted map to get things in order
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = (Long) iterator.next();
            Element reg = ((Element) map.get(key));
            Element start = reg.getChild("startnumber");
            // Check if element exists
            if (start != null) {
                // Update element found
                start.setText(Long.toString(startNumber++));
            }
            else {
                // Create new element
                reg.addContent(new Element("startnumber").setText(Long.toString(startNumber++)));
            }
        }
        return map.size();
    }

    /**
     * For a competition class, take care of setting start times based on start numbers
     * @param eClass
     * @param startTime Time of first start as the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * @param startInterval The interval in seconds between starts
     * @return Count of found valid registrations
     */
    public static long setStartTime(Element eClass, long startTime, long startInterval)
    {
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
        // Setup starting point
        map.clear();
        ClassVO vo = ClassVO.parse(eClass);
        System.out.println("Class: " + vo);
        List regList = eClass.getChildren("registration");
        Iterator i2 = regList.iterator();
        // For each registration
        while (i2.hasNext()) {
            Element elem = (Element) i2.next();
            RegistrationVO regVO = RegistrationVO.parse(elem);
            // Use start number for all included registrations as sorted key value
            long start = regVO.getStartNumber();
            if (start > 0) {
                // Only use those with startnumber
                System.out.println("->" + regVO + "=" + start);
                map.put(new Long(start), elem);
            }
            else {
                System.out.println("Warning! Registration found without startnumber: " + regVO);
            }
        }
        System.out.println("Size: " + map.size());
        // Use the sorted map to get things in order
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = (Long) iterator.next();
            Element reg = ((Element) map.get(key));
            Element time = reg.getChild("starttime");
            // Check if element exists
            if (time != null) {
                // Update element found
                time.setText(Long.toString(startTime));
            }
            else {
                // Create new element
                reg.addContent(new Element("starttime").setText(Long.toString(startTime)));
            }
            if (iterator.hasNext()) {
                // Only add start interval if more registrations available
                startTime += startInterval;
            }
        }
        return map.size();
    }
}
