package cma.view;

import cma.vo.ImportRegistrationVO;
import cma.vo.ReportVO;
import cma.components.StandardDialog;

public class ReportDialog extends StandardDialog
{
    private ReportPanel myContentPane = new ReportPanel();

    public ReportDialog()
    {
        init();
    }

    private void init()
    {
        setContentPane(myContentPane);
    }

    public void setData(ReportVO vo)
    {
        myContentPane.setData(vo);
    }

    public ReportVO getData()
    {
        return myContentPane.getData();
    }

    protected boolean isValidData()
    {
        return myContentPane.isValidData();
    }

    public static ReportVO showDialog()
    {
        // Create dialog
        ReportDialog cDlg = new ReportDialog();
        // Set title
        cDlg.setTitle("Ny Rapport");
        cDlg.pack();
        // Show dialog
        cDlg.show();
        if (cDlg.hasUserCancelled()) {
            return null;
        }
        return cDlg.getData();
    }

    public static ReportVO showDialog(ReportVO vo)
    {
        // Create dialog
        ReportDialog cDlg = new ReportDialog();
        // Set title
        cDlg.setTitle("Ändra Rapport");
        cDlg.setData(vo);
        cDlg.pack();
        // Show dialog
        cDlg.show();
        if (cDlg.hasUserCancelled()) {
            return null;
        }
        return cDlg.getData();
    }

    /**
     *
     */
    public static void main(String[] args)
    {
        ReportVO vo = ReportDialog.showDialog();
        System.out.println("ReportVO: " + vo);
        System.exit(0);
    }

}
