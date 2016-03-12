/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import org.jdom.Element;
import cma.ConsoleInterface;
import cma.view.RegistrationsDialog;

import java.awt.event.ActionEvent;

public class RegistrationsCmd extends RequiredCommand
{
    public boolean preProcess(ActionEvent e, ConsoleInterface parent)
    {
        boolean result = super.preProcess(e, parent);
        if (result == true) {
            Element root = parent.getCompetition().getRootElement();
            // Get all classes available
            Element classes = root.getChild("classes");
            if (classes == null || classes.getChild("class") == null) {
                // No competition opened
                javax.swing.JOptionPane.showMessageDialog(parent, "Inga tävlingsklasser finns definierade");
                return false;
            }
        }
        return result;
    }

    public void execute(ActionEvent e, cma.ConsoleInterface parent)
    {
        Element root = parent.getCompetition().getRootElement();
        // Get all classes available
        Element classes = root.getChild("classes");
        boolean modified = RegistrationsDialog.showDialog(classes);
        if (modified) {
            parent.setModified(true);
        }
    }
}
