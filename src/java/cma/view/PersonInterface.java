/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import cma.vo.PersonVO;

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

public class PersonInterface extends JDialog implements ActionListener
{
    private final String entityName = "Person";
    private PersonVO person;

    private boolean canceled = false;

    private final JPanel jpDetail = new JPanel(false);

    protected final JTextField firstName = new JTextField();
    protected final JTextField lastName = new JTextField();

    protected final JButton ok = new JButton("OK");
    protected final JButton cancel = new JButton("Avbryt");
    protected final JButton save = new JButton("Spara");

    public PersonInterface(PersonVO person)
    {
        this.person = person;
      // Setup data connection to interface cma.components
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
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
            namePanel.add(new JLabel("Förnamn", JLabel.LEFT));
            namePanel.add(new JLabel("Efteramn", JLabel.LEFT));
            //namePanel.add(new JLabel("", JLabel.LEFT));

            JPanel fieldPanel = new JPanel(false);
            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(firstName);
            fieldPanel.add(lastName);
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

    public PersonVO getPerson()
    {
        return person;
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            getPerson().setFirstName(firstName.getText());
            getPerson().setLastName(lastName.getText());
            this.setVisible(false);
        }
        if(e.getSource() == save) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            this.setTitle("Ändra " + entityName);
            getPerson().setFirstName(firstName.getText());
            getPerson().setLastName(lastName.getText());
        }
        if(e.getSource() == cancel) {
            //JOptionPane.showMessageDialog(this, "Cancel requested");
            canceled = true;
            this.setVisible(false);
        }
    }

    public static PersonVO showDialog()
    {
        PersonInterface pif = new PersonInterface(new PersonVO());
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ny " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getPerson();
    }

    public static PersonVO showDialog(PersonVO person)
    {
        PersonInterface pif = new PersonInterface(person);
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ändra " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getPerson();
    }

    public static void main(String[] args)
    {
        PersonVO result = null;
        String cmd = args[0];

        if("new".equalsIgnoreCase(cmd)) {
            result = PersonInterface.showDialog();
        }
        if("edit".equalsIgnoreCase(cmd)) {
            result = PersonInterface.showDialog(new PersonVO("Bertil","Karlsson"));
        }
        System.out.println("Result: " + result);
    }

}
