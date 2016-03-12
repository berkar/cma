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

public interface Command
{
    @Deprecated
    public boolean preProcess(ActionEvent e, ConsoleInterface parent);
    public boolean preProcess(ActionEvent e);

    @Deprecated
    public void execute(ActionEvent e, cma.ConsoleInterface parent);
    public void execute(ActionEvent e);

    public boolean postProcess(ActionEvent e);
}
