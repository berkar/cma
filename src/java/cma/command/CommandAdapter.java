/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:50:49
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import cma.ConsoleInterface;

import java.awt.event.ActionEvent;

public class CommandAdapter implements Command
{
    public boolean preProcess(ActionEvent e, ConsoleInterface parent)
    {
        return true;
    }

    public boolean preProcess(ActionEvent e) {
        return true;
    }

    public void execute(ActionEvent e, ConsoleInterface parent) {}

    public void execute(ActionEvent e) {
        execute(e, ConsoleInterface.getInstance());
    }

    public boolean postProcess(ActionEvent e) {
        return true;
    }
}
