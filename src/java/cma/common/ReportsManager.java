package cma.common;

import java.io.*;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;
import org.jdom.input.SAXBuilder;
import cma.vo.ReportVO;

/**
 *
 */
public class ReportsManager
{
    private Document doc;
    private File rootDir = null;
    private static final String fileName = "reports.xml";

    /** Field ourInstance ... */
    private static ReportsManager ourInstance;

    /**
     * Singleton interface method
     * @param applicationName
     * @return ReportsManager
     */
    public static synchronized ReportsManager getInstance(String applicationName)
    {
        if (ourInstance == null) {
            ourInstance = new ReportsManager(applicationName);
        }
        return ourInstance;
    }

    /**
     * Private constructor. Only te be used by the singleton method <code>{@link #getInstance}</code>
     * @param applicationName
     */
    private ReportsManager(String applicationName)
    {
        // Make sure that path to property file exists
        rootDir = new File(
                System.getProperties().getProperty("user.home") +
                System.getProperties().getProperty("file.separator") +
                "." + applicationName
        );
        rootDir.mkdirs();

        try {
            InputStream is = new FileInputStream(
                    rootDir.getPath() +
                    System.getProperties().getProperty("file.separator") +
                    fileName
            );
            if (is != null) {
                System.out.println("Found property file!");
                SAXBuilder builder = new SAXBuilder(false);
                doc = builder.build(is);
                is.close();
            }
            else {
                System.out.println("Could not find property file!");
                // Create default reports
                doc = createDefaultReports();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Create new reports! No reports file could be found");
            // Create default reports
            doc = createDefaultReports();
        }
        catch (JDOMException e) {
            System.err.println("Creating default reports! Could not read reports file!");
            // Create default reports
            doc = createDefaultReports();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }
        //prop.list(System.out);
    }

    private Document createDefaultReports()
    {
        // Create default reports
        ReportVO vo;
        Document root = new Document(new Element("reports"));
        Element tmp = root.getRootElement();
        // Registrations report
        vo = new ReportVO();
        vo.setName("[Anmälningslista]");
        vo.setUrl(System.getProperty("cma.registrationlist.html.stylesheet", "/reglista_html.xslt"));
        vo.setSystem(true);
        vo.setNote("HTML rapport på alla anmälda till tävlingen");
        tmp.addContent(ReportVO.describe(vo));
        // Startlist report
        vo = new ReportVO();
        vo.setName("[Startlista]");
        vo.setUrl(System.getProperty("cma.startlist.html.stylesheet", "/startlista_html.xslt"));
        vo.setSystem(true);
        vo.setNote("HTML rapport på startlistan för tävlingen");
        tmp.addContent(ReportVO.describe(vo));
        // Resultlist report
        vo = new ReportVO();
        vo.setName("[Resultatlista]");
        vo.setUrl(System.getProperty("cma.resultlist.html.stylesheet", "/resultatlista_html.xslt"));
        vo.setSystem(true);
        vo.setNote("HTML rapport på resultatlistan för tävlingen");
        tmp.addContent(ReportVO.describe(vo));
        return root;
    }

    public synchronized void save()
    {
        // Save reports file persistent
        try {
            OutputStream os = new FileOutputStream(
                    rootDir.getPath() +
                    System.getProperties().getProperty("file.separator") +
                    fileName
            );
            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            output.output(doc, os);
            os.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Element getReports()
    {
        return doc.getRootElement();
    }

    public static void main(String[] args) throws Exception
    {
    }
}

