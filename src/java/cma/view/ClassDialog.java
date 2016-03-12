/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import cma.components.LabelledItemPanel;
import cma.components.StandardDialog;
import cma.vo.ClassVO;

import javax.swing.*;

import org.jdom.Element;

public class ClassDialog extends StandardDialog
{
    private Element eClass;
    private final JTextField name = new JTextField(20);
    private final JTextField distance = new JTextField();

    private LabelledItemPanel myContentPane = new LabelledItemPanel();

    public ClassDialog()
    {
        init();
    }

    private void init()
    {
        myContentPane.setBorder(BorderFactory.createEtchedBorder());
        myContentPane.addItem("Namn", name);
        myContentPane.addItem("Distans (km)", distance);
        setContentPane(myContentPane);
    }

    public void setData(Element eClass)
    {
        this.eClass = eClass;
        ClassVO clazz = ClassVO.parse(this.eClass);
        setData(clazz);
    }

    public void setData(ClassVO clazz)
    {
        name.setText(clazz.getName());
        distance.setText(Double.toString(clazz.getDistance()));
    }

    public ClassVO getData()
    {
        ClassVO vo = new ClassVO();
        vo.setName(name.getText());
        vo.setDistance(Double.parseDouble(distance.getText().replace(',', '.')));
        return vo;
    }

    protected boolean isValidData()
    {
        if (name.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Namn måste anges", "Blankt namn", JOptionPane.WARNING_MESSAGE);
            name.requestFocus();
            return false;
        }
        if (distance.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Distans måste anges", "Blank distans", JOptionPane.WARNING_MESSAGE);
            distance.requestFocus();
            return false;
        }
        try {
            new Double(distance.getText().replace(',', '.'));
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Distans måste vara numeriskt!", "Felaktigt format på distans", JOptionPane.WARNING_MESSAGE);
            distance.setText("");
            distance.requestFocus();
            return false;
        }
        return true;
    }

    public static ClassVO showDialog()
    {
        ClassDialog pif = new ClassDialog();
        pif.setTitle("Ny Tävlingsklass");
        pif.pack();
        pif.show();
        if (pif.hasUserCancelled())
            return null;
        else
            return pif.getData();
    }

    public static ClassVO showDialog(ClassVO clazz)
    {
        ClassDialog pif = new ClassDialog();
        pif.setTitle("Ändra Tävlingsklass");
        pif.setData(clazz);
        pif.pack();
        pif.show();
        if (pif.hasUserCancelled())
            return null;
        else
            return pif.getData();
    }

    public static ClassVO showDialog(Element eClass)
    {
        ClassDialog pif = new ClassDialog();
        pif.setTitle("Ändra Tävlingsklass");
        pif.setData(eClass);
        pif.pack();
        pif.show();
        if (pif.hasUserCancelled())
            return null;
        else
            return pif.getData();
    }

    public static void main(String[] args)
    {
        ClassVO result = null;
        String cmd = args[0];
        if ("new".equalsIgnoreCase(cmd)) {
            result = ClassDialog.showDialog();
        }
        if ("edit".equalsIgnoreCase(cmd)) {
            result = ClassDialog.showDialog(new ClassVO("H21", 10.5));
        }
        System.out.println("Result: " + result);
        System.exit(0);
    }
}
