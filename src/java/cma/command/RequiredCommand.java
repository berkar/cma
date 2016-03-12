/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:50:49
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import org.jdom.Element;
import cma.ConsoleInterface;

import java.awt.event.ActionEvent;

public class RequiredCommand extends CommandAdapter
{
    public boolean preProcess(ActionEvent e, ConsoleInterface parent)
    {
        if (ConsoleInterface.getCompetition() == null) {
            // No competition opened
            javax.swing.JOptionPane.showMessageDialog(parent, "Ingen tävling öppnad");
            return false;
        }
        Element root = ConsoleInterface.getCompetition().getRootElement();
        Element common = root.getChild("common");
        if (common == null) {
            javax.swing.JOptionPane.showMessageDialog(parent, "Ingen tävling definierad");
            return false;
        }
        // Competition opened and defined
        return true;
    }
}
