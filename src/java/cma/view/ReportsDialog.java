package cma.view;

import cma.components.ReportsPanel;
import cma.components.StandardDialog;

import javax.swing.*;

import org.jdom.Element;
import org.jdom.Document;

/**
 * This class demonstrates the usage of the cma.components.StandardDialog class.
 */
public class ReportsDialog extends StandardDialog
{
    private ReportsPanel myContentPane = new ReportsPanel();
    private Element reports;

    /**
     * This method is the default constructor.
     */
    public ReportsDialog()
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

    public Element getData(Element reports)
    {
        if(reports != null) {
            Object parent = reports.getParent();
            if(parent instanceof Element && parent != null) {
                reports.detach();
                ((Element)parent).addContent(this.reports);
            }
            else {
                // Root element
                Document doc = reports.getDocument();
                reports.detach();
                doc.setRootElement(this.reports);
            }
        }
        return this.reports;
    }

    public void setData(Element reports)
    {
        if(reports != null) {
            this.reports = (Element) reports.clone();
            myContentPane.setModelData(this.reports);
        }
    }

    public static boolean showDialog(Element reports)
    {
        ReportsDialog rDlg = new ReportsDialog();
        rDlg.setTitle("Rapporter");
        rDlg.setData(reports);
        rDlg.pack();
        rDlg.show();

        if (rDlg.hasUserCancelled()) {
            return false;
        }

        rDlg.getData(reports);
        return true;
    }

    /**
     *
     */
    public static void main(String[] args)
    {
        ReportsDialog.showDialog(null);
        System.exit(0);
    }
}