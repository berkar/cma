package cma.view;

import cma.vo.ImportRegistrationVO;
import cma.components.StandardDialog;

public class ImportRegistrationDialog extends StandardDialog
{
    private ImportRegistrationPanel myContentPane = new ImportRegistrationPanel();

    public ImportRegistrationDialog()
    {
        init();
    }

    private void init()
    {
      setContentPane(myContentPane);
    }

    public ImportRegistrationVO getData()
    {
        return myContentPane.getData();
    }

    protected boolean isValidData()
    {
       return myContentPane.isValidData();
    }

    public static ImportRegistrationVO showDialog()
    {
      // Create dialog
      ImportRegistrationDialog cDlg = new ImportRegistrationDialog();
      // Set title
      cDlg.setTitle("Välj importfil");
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
      ImportRegistrationVO vo = ImportRegistrationDialog.showDialog();
      System.out.println("ImportRegistrationVO: " + vo);
      System.exit(0);
    }

}
