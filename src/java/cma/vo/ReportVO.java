/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReportVO
{
    private String name;
    private String url;
    private String postfix;
    private boolean system = false;
    private String note;

    public String getName()
    {
        return name;
    }

    public void setName(String aName)
    {
        name = aName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getPostfix()
    {
        return postfix;
    }

    public void setPostfix(String postfix)
    {
        this.postfix = postfix;
    }

    public String toString()
    {
        return name + "," + url;
    }

    public String describe()
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Element e = ReportVO.describe(this);
        XMLOutputter output = new XMLOutputter();
        try {
            output.output(e, os);
            return os.toString();
        }
        catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return super.toString();
        }
    }

    /**
     */
    public static Element describe(ReportVO report)
    {
        Element tmp = new Element("report");
        tmp.addContent(new Element("name").addContent(report.getName()));
        tmp.addContent(new Element("url").addContent(report.getUrl()));
        tmp.addContent(new Element("note").addContent(report.getNote()));
        tmp.addContent(new Element("postfix").addContent(report.getPostfix()));
        if (report.isSystem()) {
            tmp.addContent(new Element("system"));
        }
        return tmp;
    }

    public static ReportVO parse(String registration)
    {
        InputStream is = new ByteArrayInputStream(registration.getBytes());
        SAXBuilder builder = new SAXBuilder(false);
        try {
            Document doc = builder.build(is);
            return ReportVO.parse(doc.getRootElement());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ReportVO parse(Element report)
    {
        ReportVO vo = new ReportVO();
        vo.setName(report.getChildTextTrim("name"));
        vo.setUrl(report.getChildTextTrim("url"));
        vo.setNote(report.getChildTextTrim("note"));
        vo.setPostfix(report.getChildTextTrim("postfix"));
        if(vo.getPostfix() == null || vo.getPostfix().length() <= 0) {
            vo.setPostfix(".html");
        }
        if(report.getChild("system") != null) {
            vo.setSystem(true);
        }
        else {
            vo.setSystem(false);
        }
        return vo;
    }
}
