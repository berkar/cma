/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.DOMOutputter;
import cma.ConsoleInterface;
import cma.common.TransformationHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResultListCmd extends BrowserCommand
{
    private final static String HTML_STYLESHEET = System.getProperty(
        "cma.resultlist.html.stylesheet", "/resultatlista_html.xslt"
    );

    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        try {
            Document doc = ConsoleInterface.getCompetition();
            DOMOutputter out = new DOMOutputter();
            org.w3c.dom.Document dom = out.output(doc);
            TransformationHelper.show(dom, HTML_STYLESHEET, ".html");
        }
        catch (JDOMException e1) {
            JOptionPane.showMessageDialog(parent, "Problem med läsning av tävlingsfil!");
            e1.printStackTrace();
        }
    }
}
