package cma;

/**
 *
 *  @author  Bertil Karlsson
 *  @version 1.0
 */

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.swing.*;
import java.io.InputStream;
import java.io.File;
import java.net.URL;

import cma.common.ApplicationPropertyManager;
import cma.common.ReportsManager;
import cma.menu.MenuManager;

/**
 *
 */
public class ApplicationController
{
    private final static ApplicationController singleton = new ApplicationController();
    private static Document properties = null;
    ConsoleInterface mInterface;
    ConsoleConnection mConnection;
    private String DEFAULT_TITLE = Constants.APPLICATION_NAME;
    private String DEFAULT_PROPERTIES_FILE = "/cma.xml";
    private String DEFAULT_ICON = "icon.gif";

    private ApplicationController()
    {
    }

    public static void main(String[] asApplicationArgs)
    {
        singleton.start();
    }

    public static ApplicationController getInstance()
    {
        return singleton;
    }

    public static Document getProperties()
    {
        return properties;
    }

    public static void setProperties(Document properties)
    {
        ApplicationController.properties = properties;
    }

    // by placing all startup code in its own method it makes
    // it easy to restart the console if necessary
    void start()
    {
        try {
            mInterface = ConsoleInterface.getInstance();
            mConnection = null;
            StringBuffer sbError = new StringBuffer(250);

            // Initialize properties file
            String property = System.getProperty("cma.properties.file", DEFAULT_PROPERTIES_FILE);
            InputStream is = ApplicationController.class.getResourceAsStream(property);
            SAXBuilder builder = new SAXBuilder(false);
            properties = builder.build(is);
            Element root = properties.getRootElement();

            // Check for presence of meny. REQUIRED
            if (root.getChildTextTrim("menubar") == null) {
                throw new IllegalArgumentException("No menu could be found");
            }
            JMenuBar menuBar = MenuManager.getInstance().createMenuBar(root.getChild("menubar"));

            // Check for presence of title element
            if (root.getChildTextTrim("title") != null) {
                DEFAULT_TITLE = root.getChildTextTrim("title");
            }
            // Check for presence of icon element
            if (root.getChildTextTrim("icon") != null) {
                DEFAULT_ICON = root.getChildTextTrim("icon");
            }
            if (mInterface.initialize(DEFAULT_TITLE, DEFAULT_ICON, menuBar, sbError)) {
                // Get reference to latest stored competition
                String filePath = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).getLatestCompetition();
                if (filePath != null && filePath.length() > 0) {
                    URL url = new URL(filePath);
                    File file = new File(url.getFile());
                    System.out.println("Found reference to latest competition: " + file.getPath());
                    // Open latest file, if it exists
                    if(file.exists()) {
                        System.out.println("Reference exists! Open latest competition!");
                        mInterface.openCompetition(file.getPath());
                        MenuManager.getInstance().executePostAction("OPEN");
                    }
                    else {
                        // Remove reference due to not existing
                        ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).setLatestCompetition("");
                    }
                }
                // Show user interface
                mInterface.setVisible(true);
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        mInterface.setFocus();
                    }
                });
            }
            else {
                javax.swing.JOptionPane.showMessageDialog(null, "Failed to initialize interface: " + sbError);
            }
        }
        catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Unexpected error during startup: " + ex);
        }
    }

    public static void exit()
    {
        // Shuts down VM
        System.out.println("Exit's on demand! Save properties & reports first ...");
        ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).save();
        ReportsManager.getInstance(Constants.APPLICATION_NAME).save();
        System.exit(0);
    }
}
