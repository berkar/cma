package cma.view;

import cma.components.*;
import org.jdom.Element;

import javax.swing.*;

/**
 * This class demonstrates the usage of the cma.components.StandardDialog class.
 */
public class RegistrationsDialog extends StandardDialog
{
  private RegistrationsPanel myContentPane = new RegistrationsPanel();
  private Element classes;
  // Constants

  // Methods

  /**
   * This method is the default constructor.
   */
  public RegistrationsDialog()
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

  /**
   */
  public Element getData(Element classes)
  {
    Element parent = (Element)classes.getParent();
    classes.detach();
    parent.addContent(this.classes);
    return this.classes;
  }

  public void setData(Element classes)
  {
    this.classes = (Element)classes.clone();
    myContentPane.setModelData(this.classes);
  }

  public static boolean showDialog(Element classes)
  {
    RegistrationsDialog rDlg = new RegistrationsDialog();
    rDlg.setTitle("Anmälningar");
    rDlg.setData(classes);
    rDlg.pack();
    rDlg.show();

    if (rDlg.hasUserCancelled()) {
      return false;
    }

    rDlg.getData(classes);
    return true;
  }

  /**
   *
   */
  public static void main(String[] args)
  {
    RegistrationsDialog.showDialog(null);
    System.exit(0);
  }
}