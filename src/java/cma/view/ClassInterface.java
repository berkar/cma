/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import cma.vo.ClassVO;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Vector;
import java.util.ArrayList;

public class ClassInterface extends JDialog implements ActionListener
{
    private final String entityName = "Tävlingsklass";
    private ClassVO clazz;

    private boolean canceled = false;

    private final JPanel jpDetail = new JPanel(false);

    protected final JTextField name = new JTextField();
    protected final JTextField distance = new JTextField();

    protected final JButton ok = new JButton("OK");
    protected final JButton cancel = new JButton("Avbryt");
    protected final JButton save = new JButton("Spara");

    public ClassInterface(ClassVO clazz)
    {
        this.clazz = clazz;
      // Setup data connection to interface cma.components
        name.setText(clazz.getName());
        distance.setText(new Double(clazz.getDistance()).toString());
        // Setup event handling
        ok.addActionListener(this);
        ok.setMnemonic('O');
        cancel.addActionListener(this);
        cancel.setMnemonic('C');
        save.addActionListener(this);
        save.setMnemonic('S');
    }

    public boolean initialize(String sTitle, StringBuffer sbError)
    {
        try {
            this.setTitle(sTitle);

            // set up content pane
            Container content = this.getContentPane();
            content.setLayout(new BorderLayout());

            jpDetail.setLayout(new BoxLayout(jpDetail, BoxLayout.X_AXIS));

            //  set up detail info display area
            JPanel namePanel = new JPanel(false);
            namePanel.setLayout(new GridLayout(0, 1));
            namePanel.add(new JLabel("Namn", JLabel.LEFT));
            namePanel.add(new JLabel("Distans", JLabel.LEFT));
            //namePanel.add(new JLabel("", JLabel.LEFT));

            JPanel fieldPanel = new JPanel(false);
            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(name);
            fieldPanel.add(distance);
            //fieldPanel.add(new JLabel());

            jpDetail.add(namePanel);
            jpDetail.add(fieldPanel);

            content.add(jpDetail, BorderLayout.CENTER);

            JPanel jpButtons = new JPanel();
            jpButtons.setLayout(new GridLayout(1,4, 5, 5));
            jpButtons.add(ok);
            jpButtons.add(cancel);
            jpButtons.add(save);

            content.add(jpButtons, BorderLayout.SOUTH);

            this.setModal(true);
            //resize();
            pack();

            setVisible(true);

            return true;

        }
        catch (Exception e) {
            e.printStackTrace();
            sbError.append("Error initializing " + entityName + " properties panel: " + e);
            return false;
        }
    }

    void resize()
    {
        //Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimScreenSize = new Dimension(200, 200);
        this.setSize(dimScreenSize);
    }

    public ClassVO getClazz()
    {
        return clazz;
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            getClazz().setName(name.getText());
            getClazz().setDistance(Double.parseDouble(distance.getText()));
            this.setVisible(false);
        }
        if(e.getSource() == save) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            this.setTitle("Ändra " + entityName);
            getClazz().setName(name.getText());
            getClazz().setDistance(Double.parseDouble(distance.getText()));
        }
        if(e.getSource() == cancel) {
            //JOptionPane.showMessageDialog(this, "Cancel requested");
            canceled = true;
            this.setVisible(false);
        }
    }

    public static ClassVO showDialog()
    {
        ClassInterface pif = new ClassInterface(new ClassVO());
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ny " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getClazz();
    }

    public static ClassVO showDialog(ClassVO clazz)
    {
        ClassInterface pif = new ClassInterface(clazz);
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ändra " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getClazz();
    }

    public static void main(String[] args)
    {
        ClassVO result = null;
        String cmd = args[0];

        if("new".equalsIgnoreCase(cmd)) {
            result = ClassInterface.showDialog();
        }
        if("edit".equalsIgnoreCase(cmd)) {
            result = ClassInterface.showDialog(new ClassVO("H21",10.5));
        }
        System.out.println("Result: " + result);
    }

}
