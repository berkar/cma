package cma;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import cma.command.Command;
import cma.common.FileChooserHelper;
import cma.common.ApplicationPropertyManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ConsoleInterface extends JFrame
{
    public final static String MODIFIED = "modified";

    private String competitionFileName = "";
    private static Document competition = null;
    private static boolean modified = false;

    private String title = Constants.APPLICATION_NAME;

    // Singleton interface for this class
    private static ConsoleInterface instance = null;
    public static ConsoleInterface getInstance()
    {
        if(instance == null) {
            instance = new ConsoleInterface();
        }
        return instance;
    }

    /** Creates new form ConsoleInterface */
    private ConsoleInterface()
    {
    }

    /**
     * @param sTitle
     * @param sIcon
     * @param menubar
     * @param sbError
     * @return boolean True if all's well
     */
    public boolean initialize(String sTitle, String sIcon, JMenuBar menubar, StringBuffer sbError)
    {
        title = sTitle;
        try {
            // Set native look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // create icon
            try {
                byte[] abIcon;
                InputStream inputstreamIcon = this.getClass().getResourceAsStream(sIcon);
                int iIconSize = inputstreamIcon.available();
                abIcon = new byte[iIconSize];
                inputstreamIcon.read(abIcon);
                this.setIconImage(new ImageIcon(abIcon).getImage());
            }
            catch (Exception ex) {
                // the default icon will be used
            }

            this.setTitle(title);

            addWindowListener(new java.awt.event.WindowAdapter()
            {
                public void windowClosing(java.awt.event.WindowEvent evt)
                {
                    exitForm(evt);
                }
            });

            setJMenuBar(menubar);

            pack();
            this.resize();

            return true;
        }
        catch (Exception e) {
            sbError.append("Error initializing control panel: " + e);
            return false;
        }
    }

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt)
    {
        if(secureIfModified(javax.swing.JOptionPane.YES_NO_OPTION)) {
            ApplicationController.exit();
        }
    }

    void resize()
    {
        //Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimScreenSize = new Dimension();
        dimScreenSize.setSize(800, 800);
        this.setSize(dimScreenSize);
    }

    protected void setFocus()
    {
        //jtfCommand.requestFocus();
    }

    public void newCompetition()
    {
        System.out.println("You choose to create new competition");

        competitionFileName = "";
        this.setTitle(title + ": " + competitionFileName);
        setCompetition(new Document(new Element("competition")));
        setModified(true);
    }

    public void openCompetition(String selectedCompetition)
    {
        System.out.println("You choose to open this file: " + selectedCompetition);
        // Initialize properties file
        try {
            InputStream is = new FileInputStream(selectedCompetition);
            SAXBuilder builder = new SAXBuilder(false);

            competitionFileName = selectedCompetition;
            this.setTitle(title + ": " + competitionFileName);
            setCompetition(builder.build(is));
            setModified(false);
            // Store reference to opened competition
            File file = new File(competitionFileName);
            ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).setLatestCompetition(
                file.toURL().toString()
            );
        }
        catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error when open competition: " + e);
        }
    }

    public void closeCompetition()
    {
        System.out.println("You choose to close current competition");
        competitionFileName = "";
        this.setTitle(title);
        setCompetition(null);
        setModified(false);
        // Remove reference to latest stored competition
        ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).setLatestCompetition("");
    }

    public void saveCompetition()
    {
        System.out.println("You choose to save current competition");

        if (competitionFileName == null || competitionFileName.length() == 0) {
            competitionFileName = FileChooserHelper.getSaveFileName(this, new String[] {"dog"});
            if(competitionFileName == null) return;
        }
        saveAsCompetition(competitionFileName);
    }

    public void saveAsCompetition(String newName)
    {
        System.out.println("You choose to save current competition as: " + newName);

        competitionFileName = newName;
        if (competitionFileName.indexOf(".dog") == -1) {
            // Set default postfix
            competitionFileName += ".dog";
        }

        System.out.println("Saving current competition as: " + competitionFileName);
        try {
            OutputStream os = new FileOutputStream(competitionFileName);
            XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
            output.output(competition, os);
            os.close();

            this.setTitle(title + ": " + competitionFileName);
            setModified(false);

            // Store reference to latest stored competition
            File file = new File(competitionFileName);
            ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).setLatestCompetition(
                file.toURL().toString()
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setModified(false);
    }

    private void setCompetition(Document competition)
    {
        ConsoleInterface.competition = competition;
    }

    public static Document getCompetition()
    {
        return competition;
    }

    public String getCompetitionFileName()
    {
        return competitionFileName;
    }

    public boolean isModified()
    {
        return ConsoleInterface.modified;
    }

    public void setModified(boolean aModified)
    {
    	ConsoleInterface.modified = aModified;
        if(modified && this.getTitle().indexOf("(*)") == -1 ) {
            this.setTitle(this.getTitle() + " (*)");
        }
    }

    public boolean secureIfModified()
    {
        return secureIfModified(javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public boolean secureIfModified(int buttonOptions)
    {
        if(isModified()) {
            int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                new String[] { "Tävlingen är modifierad.", "Vill du spara tävlingen?" },
                "Spara Tävlingen",
                buttonOptions
            );
            switch(result) {
                case javax.swing.JOptionPane.YES_OPTION:
                    this.saveCompetition();
                    break;
                case javax.swing.JOptionPane.NO_OPTION:
                    break;
                case javax.swing.JOptionPane.CANCEL_OPTION:
                case javax.swing.JOptionPane.CLOSED_OPTION:
                    return false;
            }
        }
        return true;
    }
}


