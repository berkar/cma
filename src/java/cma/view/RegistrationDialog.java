package cma.view;

import javax.swing.*;

import cma.vo.RegistrationVO;
import cma.components.StandardDialog;
import cma.components.RegistrationPanel;

public class RegistrationDialog extends StandardDialog
{
    private RegistrationPanel myContentPane = new RegistrationPanel();

    public RegistrationDialog(String className) {
        init(className);
    }

    /**
     * This method initialises the cma.components on the panel.
     */
    private void init(String className)
    {
        myContentPane.setBorder(BorderFactory.createEtchedBorder());
        myContentPane.setClassName(className);
        setContentPane(myContentPane);
    }

    public void setData(RegistrationVO vo)
    {
        myContentPane.setData(vo);
    }

    public RegistrationVO getData()
    {
        return myContentPane.getData();
    }

    protected boolean isValidData()
    {
        return myContentPane.isValidData();
    }

    public static RegistrationVO showDialog(String className)
    {
        RegistrationDialog rDlg = new RegistrationDialog(className);
        rDlg.setTitle("Ny Registrering");
        rDlg.pack();
        rDlg.show();
        if (rDlg.hasUserCancelled())
            return null;
        else
            return rDlg.getData();
    }

    public static RegistrationVO showDialog(String className, RegistrationVO vo)
    {
        RegistrationDialog rDlg = new RegistrationDialog(className);
        rDlg.setTitle("Ändra Registrering");
        rDlg.setData(vo);
        rDlg.pack();
        rDlg.show();
        if (rDlg.hasUserCancelled())
            return null;
        else
            return rDlg.getData();
    }


    public static void main(String[] args)
    {
        System.out.println("VO: " +  RegistrationDialog.showDialog("D21A"));
        System.exit(0);
    }
}
