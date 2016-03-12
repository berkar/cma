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
import cma.Constants;
import cma.common.ApplicationPropertyManager;

import java.awt.event.ActionEvent;

public class BrowserCommand extends RequiredCommand
{
    public boolean preProcess(ActionEvent e, ConsoleInterface parent)
    {
        boolean result = super.preProcess(e, parent);
        if(result == true) {
            // All's well! Check if there is a browser defined
            String browserPath = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).getBrowserPath();
            if (browserPath == null || browserPath.trim().length() <= 0) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Ingen 'browser-path' definierad!");
                return false;
            }
        }
        return result;
    }
}
