/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 00:54:13
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.command;

import cma.common.FileChooserHelper;

import java.awt.event.ActionEvent;

public class SaveAsCmd extends CommandAdapter
{
    public void execute(ActionEvent e, cma.ConsoleInterface parent)
    {
        String saveAs = FileChooserHelper.getSaveFileName(parent, new String[]{"dog"});
        if (saveAs != null) {
            parent.saveAsCompetition(saveAs);
        }
    }
}
