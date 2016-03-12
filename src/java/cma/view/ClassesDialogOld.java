package cma.view;

import cma.components.*;
import org.jdom.Element;
import org.jdom.Parent;

import javax.swing.*;

/**
 * This class demonstrates the usage of the cma.components.StandardDialog class.
 */
public class ClassesDialogOld extends StandardDialog
{
  private ClassesPanelOld myContentPane = new ClassesPanelOld();
  private Element classes;

  // Constants

  // Methods

  /**
   * This method is the default constructor.
   */
  public ClassesDialogOld()
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
    ClassesDialogOld cDlg = new ClassesDialogOld();
    cDlg.setTitle("Tävlingsklasser");
    cDlg.setData(classes);
    cDlg.pack();
    cDlg.show();

    if (cDlg.hasUserCancelled()) {
      return false;
    }

    cDlg.getData(classes);
    return true;
  }

  /**
   *
   */
  public static void main(String[] args)
  {
    ClassesDialogOld.showDialog(null);
    System.exit(0);
  }
}