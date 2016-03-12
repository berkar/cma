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
import cma.view.ClassesDialog;

import java.awt.event.ActionEvent;


public class StartNumberCmd extends RequiredCommand
{
    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        Element root = ConsoleInterface.getCompetition().getRootElement();
        boolean modified = ClassesDialog.showDialog(root);
        if (modified) {
            parent.setModified(true);
        }
    }
}
