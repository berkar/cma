package tools;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jaxen.jdom.JDOMXPath;
import org.jaxen.JaxenException;

import java.io.*;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 19, 2003
 * Time: 6:38:39 PM
 * To change this template use Options | File Templates.
 */
public class ClearCompetition
{
    public static void main(String[] args)
    {
        try {
            String source = args[0];
            String destination = source;
            if(args.length > 1) {
                destination = args[1];
            }
            FileInputStream is = new FileInputStream(source);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(is);
            is.close();

            clearCommon(doc);
            clearClass(doc);
            clearRegistration(doc);

            System.out.println("Saving competition to " + destination);
            OutputStream os = new FileOutputStream(destination);
            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            output.output(doc, os);
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private static void clearCommon(Document doc) throws JaxenException
    {
        String[] elementNames = new String[] { "measurement" };

        String[] attributeNames = new String[] {};

        JDOMXPath xpath = new JDOMXPath("//common");
        List list = xpath.selectNodes(doc);
        Iterator i = list.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            System.out.println("Common: " + e.getChildTextTrim("name"));
            for (int j = 0; j < elementNames.length; j++) {
                String elementName = elementNames[j];
                Element tmp = e.getChild(elementName);
                if(tmp != null) {
                    System.out.println("->Removing element <" + elementName + ">");
                    // Remove element from DOM tree
                    tmp.detach();
                }
            }
            for (int j = 0; j < attributeNames.length; j++) {
                String attributeName = attributeNames[j];
                Attribute tmp = e.getAttribute(attributeName);
                if(tmp != null) {
                    System.out.println("->Removing attribute <" + attributeName + ">");
                    // Remove attribute from DOM tree
                    tmp.detach();
                }
            }
        }
    }

    private static void clearClass(Document doc) throws JaxenException
    {
        String[] elementNames = new String[] {};

        String[] attributeNames = new String[] {
            "manual", "firstnumber", "startinterval", "firsttime"
        };

        JDOMXPath xpath = new JDOMXPath("//class");
        List list = xpath.selectNodes(doc);
        Iterator i = list.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            System.out.println("Class: " + e.getAttributeValue("name"));
            for (int j = 0; j < elementNames.length; j++) {
                String elementName = elementNames[j];
                Element tmp = e.getChild(elementName);
                if(tmp != null) {
                    System.out.println("->Removing element <" + elementName + ">");
                    // Remove element from DOM tree
                    tmp.detach();
                }
            }
            for (int j = 0; j < attributeNames.length; j++) {
                String attributeName = attributeNames[j];
                Attribute tmp = e.getAttribute(attributeName);
                if(tmp != null) {
                    System.out.println("->Removing attribute <" + attributeName + ">");
                    // Remove attribute from DOM tree
                    tmp.detach();
                }
            }
        }
    }

    private static void clearRegistration(Document doc) throws JaxenException
    {
        String[] elementNames = new String[] {
            "random", "startnumber", "starttime", "time1", "time2", "time3", "totaltime", "difftime", "position"
        };

        String[] attributeNames = new String[] {};

        JDOMXPath xpath = new JDOMXPath("//registration");
        List list = xpath.selectNodes(doc);
        Iterator i = list.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            System.out.println("Registration: " + e.getChildTextTrim("name"));
            for (int j = 0; j < elementNames.length; j++) {
                String elementName = elementNames[j];
                Element tmp = e.getChild(elementName);
                if(tmp != null) {
                    System.out.println("->Removing element <" + elementName + ">");
                    // Remove element from DOM tree
                    tmp.detach();
                }
            }
            for (int j = 0; j < attributeNames.length; j++) {
                String attributeName = attributeNames[j];
                Attribute tmp = e.getAttribute(attributeName);
                if(tmp != null) {
                    System.out.println("->Removing attribute <" + attributeName + ">");
                    // Remove attribute from DOM tree
                    tmp.detach();
                }
            }
        }
    }
}
