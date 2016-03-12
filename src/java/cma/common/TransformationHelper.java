package cma.common;

import cma.ConsoleInterface;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.swing.*;
import java.io.*;
import java.net.URL;

import org.w3c.dom.Node;
import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 17, 2003
 * Time: 9:19:10 PM
 * To change this template use Options | File Templates.
 */
public class TransformationHelper
{

/* TODO Remove this section when sure not to use it anymore ...
    public static void saveAndShow(ConsoleInterface parent, String stylesheet, String extension)
    {
        // Check for modified competition
        if (parent.isModified()) {
            // Auto save competition first
            parent.saveCompetition();
        }
        String result = FileChooserHelper.getSaveFileName(parent, new String[]{ extension });
        if (result != null) {
            try {
                if (result.indexOf(".") == -1) {
                    // Set default postfix
                    result += ("." + extension);
                }
                createFile(result, parent.getCompetitionFileName(), stylesheet);
                // Show the result in a browser
                BrowserHelper.view(result);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Kunde inte skapa alt. visa rapport!");
                ex.printStackTrace();
                return;
            }
        }
    }

    public static void createFile(String resultFile, String competitionFileName, String stylesheet)
        throws IOException, TransformerException
    {
        File file = new File(resultFile);
        TransformationHelper.createFile(
            new FileOutputStream(file),
            new FileInputStream(competitionFileName),
            TransformationHelper.class.getResource(stylesheet).openStream()
        );
        System.out.println("File created successfully: " + file.getAbsolutePath());
    }

    public static void createFile(OutputStream destination,
                                  InputStream source,
                                  InputStream stylesheet)
        throws TransformerException
    {
        //Create html file
        PrintWriter pw = new PrintWriter(destination);

        // Transform XML content from competition file format to printable startlist format
        TransformerFactory tFactory = TransformerFactory.newInstance();

        // Use the TransformerFactory to process the stylesheet Source and  generate a Transformer.
        Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));

        // Use the Transformer to transform an XML Source and send the output to print writer object.
        transformer.transform(new StreamSource(source), new StreamResult(pw));

        // Close printwriter used
        pw.close();
    }





    public static void saveAndShow(Document doc, String stylesheet, String extension)
    {
        ConsoleInterface parent = ConsoleInterface.getInstance();
        String result = FileChooserHelper.getSaveFileName(parent, new String[]{ extension });
        if (result != null) {
            try {
                if (result.indexOf(".") == -1) {
                    // Set default postfix
                    result += ("." + extension);
                }
                createFile(result, doc, stylesheet);
                // Show the result in a browser
                BrowserHelper.view(result);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Kunde inte skapa alt. visa rapport!");
                ex.printStackTrace();
                return;
            }
        }
    }

    public static void createFile(String resultFile, Node source, String stylesheet)
        throws IOException, TransformerException
    {
        File file = new File(resultFile);
        TransformationHelper.createFile(
            new FileOutputStream(file),
            source,
            TransformationHelper.class.getResource(stylesheet).openStream()
        );
        System.out.println("File created successfully: " + file.getAbsolutePath());
    }
*/

    /**
     * Creates a temp file and tries to view the generated file with the browser path specified in ApplicationProperties
     * @param doc
     * @param stylesheet
     */
    public static void show(Document doc, String stylesheet, String suffix)
    {
        ConsoleInterface parent = ConsoleInterface.getInstance();
        try {
            File file = File.createTempFile("cma_", suffix);
            URL url = TransformationHelper.class.getResource(stylesheet);
            if(url == null) {
                File tmp = new File(stylesheet);
                url = tmp.toURL();
            }
            if(url != null) {
                TransformationHelper.createFile(
                    new FileOutputStream(file),
                    doc,
                    url.openStream()
                );
                // Show the result in a browser
                BrowserHelper.view(file.toURL().toString());
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Kunde inte visa rapport!");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Transforms and saves the result to the destination parameter
     * @param destination
     * @param source
     * @param stylesheet
     * @throws TransformerException
     */
    public static void createFile(OutputStream destination, Node source, InputStream stylesheet)
        throws TransformerException
    {
        //Create html file
        PrintWriter pw = new PrintWriter(destination);

        // Transform XML content from competition file format to printable startlist format
        TransformerFactory tFactory = TransformerFactory.newInstance();

        // Use the TransformerFactory to process the stylesheet Source and  generate a Transformer.
        Transformer transformer = tFactory.newTransformer(new StreamSource(stylesheet));

        // Use the Transformer to transform an XML Source and send the output to print writer object.
        transformer.transform(new DOMSource(source), new StreamResult(pw));

        // Close printwriter used
        pw.close();
    }
}
