/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import cma.ConsoleInterface;
import cma.common.TransformationHelper;
import cma.common.FileChooserHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.output.DOMOutputter;

public class ExportStartListCmd extends RequiredCommand
{
    private final static String CSV_STYLESHEET = System.getProperty(
        "cma.startlist.csv.stylesheet", "/startlista_csv.xslt"
    );

    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        // Get file name to store start list in
        String startList = FileChooserHelper.getSaveFileName(parent, new String[]{"txt"});
        if (startList != null) {
            try {
                if (startList.indexOf(".") == -1) {
                    // Add postfix of file name
                    startList += ".txt";
                }
                // Convert JDOM Document to W3C Document
                Document doc = ConsoleInterface.getCompetition();
                DOMOutputter out = new DOMOutputter();
                org.w3c.dom.Document dom = out.output(doc);
                // Call for transformation
                TransformationHelper.createFile(
                    new FileOutputStream(startList),
                    dom,
                    ExportStartListCmd.class.getResource(CSV_STYLESHEET).openStream()
                );
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Kunde inte exportera startlista!");
                ex.printStackTrace();
                return;
            }
        }
    }
}
