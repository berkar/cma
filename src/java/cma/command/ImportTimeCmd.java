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
import cma.common.ImportTimeHelper;

import java.awt.event.ActionEvent;

public class ImportTimeCmd extends RequiredCommand
{
    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        ImportTimeHelper.execute(parent);
    }
}
