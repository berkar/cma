/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import cma.vo.ClubVO;

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

public class ClubInterface extends JDialog implements ActionListener
{
    private final String entityName = "Förening";
    private ClubVO club;

    private boolean canceled = false;

    private final JPanel jpDetail = new JPanel(false);

    protected final JTextField shortName = new JTextField();
    protected final JTextField name = new JTextField();

    protected final JButton ok = new JButton("OK");
    protected final JButton cancel = new JButton("Avbryt");
    protected final JButton save = new JButton("Spara");

    public ClubInterface(ClubVO club)
    {
        this.club = club;
      // Setup data connection to interface cma.components
        name.setText(club.getName());
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
            namePanel.add(new JLabel("Förkortning", JLabel.LEFT));
            namePanel.add(new JLabel("Föreningsnamn", JLabel.LEFT));
            //namePanel.add(new JLabel("", JLabel.LEFT));

            JPanel fieldPanel = new JPanel(false);
            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(shortName);
            fieldPanel.add(name);
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

    public ClubVO getClub()
    {
        return club;
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            getClub().setShortName(shortName.getText());
            getClub().setName(name.getText());
            this.setVisible(false);
        }
        if(e.getSource() == save) {
            //JOptionPane.showMessageDialog(this, "Save requested");
            this.setTitle("Ändra " + entityName);
            getClub().setShortName(shortName.getText());
            getClub().setName(name.getText());
        }
        if(e.getSource() == cancel) {
            //JOptionPane.showMessageDialog(this, "Cancel requested");
            canceled = true;
            this.setVisible(false);
        }
    }

    public static ClubVO showDialog()
    {
        ClubInterface pif = new ClubInterface(new ClubVO());
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ny " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getClub();
    }

    public static ClubVO showDialog(ClubVO club)
    {
        ClubInterface pif = new ClubInterface(club);
        StringBuffer sbError = new StringBuffer(250);
        pif.initialize("Ändra " + pif.entityName, sbError);
        if(pif.isCanceled())
            return null;
        else
            return pif.getClub();
    }

    public static void main(String[] args)
    {
        ClubVO result = null;
        String cmd = args[0];

        if("new".equalsIgnoreCase(cmd)) {
            result = ClubInterface.showDialog();
        }
        if("edit".equalsIgnoreCase(cmd)) {
            result = ClubInterface.showDialog(new ClubVO("BDIF", "Bodens draghund"));
        }
        System.out.println("Result: " + result);
    }

}
