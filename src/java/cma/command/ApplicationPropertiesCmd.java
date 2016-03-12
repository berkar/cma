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
import cma.Constants;
import cma.common.ApplicationPropertyManager;
import cma.view.ApplicationPropertiesDialog;
import cma.vo.ApplicationPropertiesVO;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ApplicationPropertiesCmd extends CommandAdapter
{
    public void execute(ActionEvent e, ConsoleInterface parent)
    {
        // Get application property manager instance
        ApplicationPropertyManager mgr = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME);
        // Create VO for properties to be used
        ApplicationPropertiesVO vo = new ApplicationPropertiesVO();
        // Copy values
        if (mgr.getBrowserPath() != null && mgr.getBrowserPath().length() > 0) {
            try {
                URL url = new URL(mgr.getBrowserPath());
                File file = new File(url.getFile());
                if (file.exists()) {
                    vo.setBrowserPath(new URL(mgr.getBrowserPath()).getFile());
                }
            }
            catch (MalformedURLException ignore) {
                System.err.println("Error when handling browser path!");
                ignore.printStackTrace(System.err);
            }
        }
        // Show dialog
        vo = ApplicationPropertiesDialog.showDialog(vo);
        // Check if canceled or not
        if (vo != null) {
            // Copy result values back to manager
            if (vo.getBrowserPath() != null && vo.getBrowserPath().length() > 0) {
                try {
                    File file = new File(vo.getBrowserPath());
                    if (file.exists()) {
                        mgr.setBrowserPath(file.toURL().toString());
                    }
                }
                catch (MalformedURLException ignore) {
                    System.err.println("Error when handling browser path!");
                    ignore.printStackTrace(System.err);
                }
            }
            else {
                mgr.setBrowserPath("");
            }
        }
    }
}
