package cma.view;

import cma.vo.ImportVO;
import cma.components.StandardDialog;

public class ImportTimeDialog extends StandardDialog
{
    private ImportTimePanel myContentPane = new ImportTimePanel();

    public ImportTimeDialog()
    {
        init();
    }

    private void init()
    {
      setContentPane(myContentPane);
    }

    public ImportVO getData()
    {
        return myContentPane.getData();
    }

    protected boolean isValidData()
    {
       return myContentPane.isValidData();
    }

    public static ImportVO showDialog()
    {
      // Create dialog
      ImportTimeDialog cDlg = new ImportTimeDialog();
      // Set title
      cDlg.setTitle("Välj resultatfil");
      cDlg.pack();
      // Show dialog
      cDlg.show();
      if(cDlg.hasUserCancelled()) {
        return null;
      }
      return cDlg.getData();
    }

    /**
     *
     */
    public static void main(String[] args)
    {
      ImportVO vo = ImportTimeDialog.showDialog();
      System.out.println("ImportVO: " + vo);
      System.exit(0);
    }

}
