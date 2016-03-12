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
import cma.Constants;
import cma.common.ReportsManager;
import cma.view.ReportsDialog;

import java.awt.event.ActionEvent;

public class ReportsCmd extends CommandAdapter
{
    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        // Get all reports available
        Element reports = ReportsManager.getInstance(Constants.APPLICATION_NAME).getReports();
        if(ReportsDialog.showDialog(reports) == true) {
            // Dialog ended with Ok button, save reports
            ReportsManager.getInstance(Constants.APPLICATION_NAME).save();
        }
    }
}
