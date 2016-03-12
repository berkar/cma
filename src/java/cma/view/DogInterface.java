/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import cma.vo.DogVO;

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

public class DogInterface extends JDialog implements ActionListener
{
    private final String entityName = "Hund";
    private DogVO dog;

    private boolean canceled = false;

    private final JPanel jpDetail = new JPanel(false);

    protected final JTextField name = new JTextField();
    protected final JTextField breed = new JTextField();
    protected final JTextField gender = new JTextField();

    protected final JButton ok = new JButton("OK");
    protected final JButton cancel = new JButton("Avbryt");
    protected final JButton save = new JButton("Spara");

    public DogInterface(DogVO dog)
    {
        this.dog = dog;
      // Setup data connection to interface cma.components
        name.setText(dog.getName());
        breed.setText(dog.getBreed());
        gender.setText(dog.getGender());
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
            namePanel.add(new JLabel("Ras", JLabel.LEFT));
            namePanel.add(new JLabel("Kön", JLabel.LEFT));
            //namePanel.add(new JLabel("", JLabel.LEFT));

            JPanel fieldPanel = new JPanel(false);
            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(name);
            fieldPanel.add(breed);
            fieldPanel.add(gender);
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

    public DogVO getDog()
    {
        return dog;
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            getDog().setName(name.getText());
            getDog().setBreed(breed.getText());
            getDog().setGender(gender.getText());
            this.setVisible(false);
        }
        if(e.getSource() == save) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            this.setTitle("Ändra " + entityName);
            getDog().setName(name.getText());
            getDog().setBreed(breed.getText());
            getDog().setGender(gender.getText());
        }
        if(e.getSource() == cancel) {
            //JOptionPane.showMessageDialog(this, "Cancel requested");
            canceled = true;
            this.setVisible(false);
        }
    }

    public static DogVO showDialog()
    {
        DogInterface pif = new DogInterface(new DogVO());
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ny " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getDog();
    }

    public static DogVO showDialog(DogVO dog)
    {
        DogInterface pif = new DogInterface(dog);
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ändra " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getDog();
    }

    public static void main(String[] args)
    {
        DogVO result = null;
        String cmd = args[0];

        if("new".equalsIgnoreCase(cmd)) {
            result = DogInterface.showDialog();
        }
        if("edit".equalsIgnoreCase(cmd)) {
            result = DogInterface.showDialog(new DogVO("Maia","Korth. Vorsteh","Tik"));
        }
        System.out.println("Result: " + result);
    }

}
