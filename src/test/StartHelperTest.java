
import java.io.ByteArrayInputStream;

import org.jdom.input.SAXBuilder;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import cma.common.StartOrderHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Bertil
 * Date: Nov 29, 2003
 * Time: 11:43:51 PM
 * To change this template use Options | File Templates.
 */
public class StartHelperTest
{
    public static void main(String[] args)
    {
        StringBuffer sb = new StringBuffer("<class name=\"H21A\" distance=\"10.0\">");
        sb.append("<registration>");
        sb.append("<club>XXX</club>");
        sb.append("<name>Bertil</name>");
        sb.append("<address></address>");
        sb.append("<post-address></post-address>");
        sb.append("<phone></phone>");
        sb.append("<mail></mail>");
        sb.append("<dogs>Maia</dogs>");
        sb.append("<did></did>");
        sb.append("<payment>false</payment>");
        sb.append("<starting>true</starting>");
        sb.append("<abandon>false</abandon>");
        sb.append("<warning>false</warning>");
        sb.append("<disqualified>false</disqualified>");
        sb.append("<note></note>");
        sb.append("</registration>");

        sb.append("<registration>");
        sb.append("<club>XXX</club>");
        sb.append("<name>Lasse</name>");
        sb.append("<address></address>");
        sb.append("<post-address></post-address>");
        sb.append("<phone></phone>");
        sb.append("<mail></mail>");
        sb.append("<dogs>Maia</dogs>");
        sb.append("<did></did>");
        sb.append("<payment>false</payment>");
        sb.append("<starting>true</starting>");
        sb.append("<abandon>false</abandon>");
        sb.append("<warning>false</warning>");
        sb.append("<disqualified>false</disqualified>");
        sb.append("<note></note>");
        sb.append("</registration>");

        sb.append("<registration>");
        sb.append("<club>XXX</club>");
        sb.append("<name>Janne</name>");
        sb.append("<address></address>");
        sb.append("<post-address></post-address>");
        sb.append("<phone></phone>");
        sb.append("<mail></mail>");
        sb.append("<dogs>Maia</dogs>");
        sb.append("<did></did>");
        sb.append("<payment>false</payment>");
        sb.append("<starting>true</starting>");
        sb.append("<abandon>false</abandon>");
        sb.append("<warning>false</warning>");
        sb.append("<disqualified>false</disqualified>");
        sb.append("<note></note>");
        sb.append("</registration>");
        sb.append("</class>");

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(sb.toString().getBytes());
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = builder.build(is);
            Element clazz = doc.getRootElement();

            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());

            System.out.println("****************************");
            output.output(clazz, System.out);
            System.out.println("****************************");
            StartOrderHelper.setStartNumber(clazz, 501);
            output.output(clazz, System.out);
            System.out.println("****************************");
            StartOrderHelper.setStartTime(clazz, 1000, 10);
            output.output(clazz, System.out);
            System.out.println("****************************");
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}
