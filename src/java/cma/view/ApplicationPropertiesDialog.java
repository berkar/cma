package cma.view;

import cma.components.LabelledItemPanel;
import cma.components.StandardDialog;

import javax.swing.*;

import cma.vo.ApplicationPropertiesVO;
import cma.common.FileChooserHelper;

import java.io.File;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This class demonstrates the usage of the cma.components.StandardDialog class.
 */
public class ApplicationPropertiesDialog extends StandardDialog
{
    // Constants
    private final String entityName = "Egenskaper";
    private final JTextField browserPath = new JTextField(40);
    private JButton select = new JButton("Välj ...");
    private LabelledItemPanel myContentPane = new LabelledItemPanel();

    // Methods

    /**
     * This method is the default constructor.
     */
    public ApplicationPropertiesDialog()
    {
        init();
    }

    /**
     * This method initialises the cma.components on the panel.
     */
    private void init()
    {
        setTitle(entityName);
        select.addActionListener(new ApplicationPropertiesDialog.SelectAction(this));
        myContentPane.setBorder(BorderFactory.createEtchedBorder());
        myContentPane.addItem("Browser path", browserPath);
        myContentPane.addItem("", select);
        setContentPane(myContentPane);
    }

    public ApplicationPropertiesVO getData()
    {
        ApplicationPropertiesVO vo = new ApplicationPropertiesVO();
        // Take care of common attributes
        vo.setBrowserPath(browserPath.getText());
        return vo;
    }

    public void setData(ApplicationPropertiesVO vo)
    {
        browserPath.setText(vo.getBrowserPath());
    }

    /**
     * This method checks that the data entered is valid.
     * To be valid, the following must be met:
     * <LI>Customer Code field is not blank
     * <LI>Name field is not blank
     *
     * @return <code>true</code> if the data is valid, otherwise
     * <code>false</code>
     */
    protected boolean isValidData()
    {
        if (browserPath.getText().equals("") == false) {
            File file = new File(browserPath.getText());
            if(file.exists() == false) {
                JOptionPane.showMessageDialog(this, "Felaktig browser path angiven", "Path existerar inte", JOptionPane.WARNING_MESSAGE);
                browserPath.requestFocus();
                return false;
            }
        }
        return true;
      }

    public static ApplicationPropertiesVO showDialog(ApplicationPropertiesVO properties)
    {
        ApplicationPropertiesDialog pDlg = new ApplicationPropertiesDialog();
        pDlg.setTitle("Ändra Egenskaper");
        pDlg.setData(properties);
        pDlg.pack();
        pDlg.show();
        if (pDlg.hasUserCancelled())
            return null;
        else
            return pDlg.getData();
    }

    private class SelectAction extends AbstractAction
    {
        private Component parent;
        public SelectAction(Component aParent)
        {
            super();
            parent = aParent;
        }

        public void actionPerformed(ActionEvent actionEvent)
        {
            String fileName = FileChooserHelper.getOpenFileName(parent, new String[] {"exe", "bat", "cmd", "sh"});
            if(fileName != null) {
                browserPath.setText(fileName);
            }
        }
    }

    /**
     *
     */
    public static void main(String[] args)
    {
        ApplicationPropertiesVO result = null;
        String cmd = args[0];
        if ("edit".equalsIgnoreCase(cmd)) {
            ApplicationPropertiesVO tmp = new ApplicationPropertiesVO();
            tmp.setBrowserPath("file:/C:/tmp");
            result = ApplicationPropertiesDialog.showDialog(tmp);
        }
        System.out.println("Result: " + result);
        System.exit(0);
    }
}