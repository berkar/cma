/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

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

public class ConsoleProperties extends JDialog
{
    private final static int iDEFAULT_FontSize = 12;

    public ConsoleProperties()
    {
    }

    private final JTabbedPane jtpMain = new JTabbedPane();
    private final JPanel jpDetail = new JPanel(false);
    private final JPanel jpClasses = new JPanel(false);
    private final JTable jtClasses = new JTable();

    public boolean initialize(String sTitle, StringBuffer sbError)
    {
        try {
            this.setTitle(sTitle);

            // set up content pane
            Container content = this.getContentPane();
            content.setLayout(new BorderLayout());

            jpDetail.setLayout(new BoxLayout(jpDetail, BoxLayout.X_AXIS));

            jpClasses.setLayout(new BorderLayout(5,5));

            //set up tabbed pane
            content.add(jtpMain);
            jtpMain.addTab("Information", jpDetail);
            jtpMain.addTab("Klasser", jpClasses);

            //  set up detail info display area
            JPanel namePanel = new JPanel(false);
            namePanel.setLayout(new GridLayout(0, 1));
            namePanel.add(new JLabel("Förening", JLabel.LEFT));
            namePanel.add(new JLabel("Namn", JLabel.LEFT));
            namePanel.add(new JLabel("Plats", JLabel.LEFT));
            namePanel.add(new JLabel("Startdatum", JLabel.LEFT));
            namePanel.add(new JLabel("Starttid", JLabel.LEFT));
            namePanel.add(new JLabel("Relativ tidsmätning", JLabel.LEFT));
            namePanel.add(new JLabel("Start intervall", JLabel.LEFT));
            namePanel.add(new JLabel("Klass intervall", JLabel.LEFT));
            namePanel.add(new JLabel("Första startnr", JLabel.LEFT));

            JPanel fieldPanel = new JPanel(false);
            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JCheckBox());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());
            fieldPanel.add(new JTextField());

            jpDetail.add(namePanel);
            jpDetail.add(fieldPanel);

            //  set up classes display area
            //jpClasses.add(jtClasses, BorderLayout.CENTER);
            Vector row = new Vector();
            Vector rowData = new Vector();
            rowData.add("1");
            rowData.add("H21");
            rowData.add("15");
            row.add(rowData);
            Vector columnNames = new Vector();
            columnNames.add("Startordning");
            columnNames.add("Klassnamn");
            columnNames.add("Distans");
            jpClasses.add(new JTable(row, columnNames), BorderLayout.CENTER);
            jtClasses.setTableHeader(new JTableHeader());

            JPanel jpButtons = new JPanel();
            jpButtons.setLayout(new GridLayout(2,1,5,5));
            jpButtons.add(new JButton("Lägg till ..."));
            jpButtons.add(new JButton("Ta bort"));

            JPanel jpRight = new JPanel();
            jpRight.setLayout(new BorderLayout());
            jpRight.add(jpButtons, BorderLayout.NORTH);

            jpClasses.add(jpRight, BorderLayout.EAST);

            this.setModal(true);
            //resize();
            pack();

            setVisible(true);

            return true;

        }
        catch (Exception e) {
            e.printStackTrace();
            sbError.append("Error initializing console properties panel: " + e);
            return false;
        }
    }

    void resize()
    {
        //Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimScreenSize = new Dimension(200, 200);
        this.setSize(dimScreenSize);
    }
}
