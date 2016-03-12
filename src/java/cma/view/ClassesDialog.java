package cma.view;

import org.jdom.Element;
import cma.components.StandardDialog;
import cma.components.ClassesPanel;

import javax.swing.*;

/**
 */
public class ClassesDialog extends StandardDialog
{
    private ClassesPanel myContentPane = new ClassesPanel();
    private Element competition;

    public ClassesDialog()
    {
        init();
    }

    /**
     * This method initialises the cma.components on the panel.
     */
    private void init()
    {
        myContentPane.setBorder(BorderFactory.createEtchedBorder());
        setContentPane(myContentPane);
    }

    public Element getData(Element competition)
    {
        // Remove old reference to classes
        Element classes = competition.getChild("classes");
        classes.detach();
        // Disconnect new reference to classes
        classes = this.competition.getChild("classes");
        classes.detach();
        // Attach new reference to classes to supplied competition
        competition.addContent(classes);
        // Return supplied competition
        return competition;
    }

    public void setData(Element competition)
    {
        this.competition = (Element) competition.clone();
        myContentPane.setModelData(this.competition);
    }

    public static boolean showDialog(Element competition)
    {
        ClassesDialog cDlg = new ClassesDialog();
        cDlg.setTitle("Tävlingsklasser");
        cDlg.setData(competition);
        cDlg.pack();
        cDlg.show();

        if (cDlg.hasUserCancelled()) {
            return false;
        }
        cDlg.getData(competition);
        return true;
    }

    /**
     *
     */
    public static void main(String[] args)
    {
        ClassesDialog.showDialog(null);
        System.exit(0);
    }
}