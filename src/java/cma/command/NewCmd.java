/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import java.awt.event.ActionEvent;

public class NewCmd extends CommandAdapter
{
    public void execute(ActionEvent e, cma.ConsoleInterface parent)
    {
        if (parent.secureIfModified()) {
            parent.newCompetition();
        }
    }
}
