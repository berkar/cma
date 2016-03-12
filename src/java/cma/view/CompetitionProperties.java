/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: May 6, 2002
 * Time: 10:42:51 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.view;

import org.jdom.Element;

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
import java.util.Iterator;

import cma.vo.CompetitionVO;

public class CompetitionProperties extends JDialog implements ActionListener
{
    private final String entityName = "Tävling";

    private boolean canceled = false;

    private Element source;
    private Element common;
    private Element classes;

    public CompetitionProperties(Element source)
    {
        this.source = source;
        this.common = (Element)source.getChild("common").clone();
        this.classes = (Element)source.getChild("classes").clone();
    }

    private final JTabbedPane jtpMain = new JTabbedPane();
    private final JPanel jpDetail = new JPanel(false);
    private final JPanel namePanel = new JPanel(false);
    private final JPanel fieldPanel = new JPanel(false);
    private final JPanel jpClasses = new JPanel(false);
    private JTable jtClasses = new JTable();

    private final JTextField club = new JTextField();
    private final JTextField name = new JTextField();
    private final JTextField place = new JTextField();
    private final JTextField startdate= new JTextField();
    private final JTextField starttime= new JTextField();
    private final JTextField classinterval = new JTextField();
    private final JTextField startinterval = new JTextField();
    private final JTextField firstnumber = new JTextField();
    private final JCheckBox measurement = new JCheckBox();

    protected final JButton ok = new JButton("OK");
    protected final JButton cancel = new JButton("Avbryt");
    protected final JButton save = new JButton("Spara");
    protected final JButton add = new JButton("Lägg till ...");
    protected final JButton remove = new JButton("Ta bort");

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

            club.setText(common.getChildTextTrim("club"));
            name.setText(common.getChildTextTrim("name"));
            place.setText(common.getChildTextTrim("place"));
            startdate.setText(common.getChildTextTrim("date"));
            starttime.setText(common.getChildTextTrim("time"));
            if("relative".equals(common.getChild("measurement").getAttributeValue("id"))) {
                measurement.setSelected(true);
            }
            else {
                measurement.setSelected(false);
            }
            startinterval.setText(common.getChildTextTrim("startinterval"));
            classinterval.setText(common.getChildTextTrim("classinterval"));
            firstnumber.setText(common.getChildTextTrim("firstnumber"));

            fieldPanel.setLayout(new GridLayout(0, 1));
            fieldPanel.add(club);
            fieldPanel.add(name);
            fieldPanel.add(place);
            fieldPanel.add(startdate);
            fieldPanel.add(starttime);
            fieldPanel.add(measurement);
            fieldPanel.add(startinterval);
            fieldPanel.add(classinterval);
            fieldPanel.add(firstnumber);

            jpDetail.add(namePanel);
            jpDetail.add(fieldPanel);

            //  set up classes display area
            //jpClasses.add(jtClasses, BorderLayout.CENTER);
            Vector row = new Vector();
            java.util.List list = classes.getChildren("class");
            if(list != null) {
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    Element e = (Element) iterator.next();
                    Vector rowData = new Vector();
                    rowData.add(e.getChildTextTrim("orderby"));
                    rowData.add(e.getChildTextTrim("name"));
                    rowData.add(e.getChildTextTrim("distance"));
                    row.add(rowData);
                }
            }
            Vector columnNames = new Vector();
            columnNames.add("Startordning");
            columnNames.add("Klassnamn");
            columnNames.add("Distans");
            jtClasses = new JTable(row, columnNames);
            jpClasses.add(jtClasses, BorderLayout.CENTER);
            jtClasses.setTableHeader(new JTableHeader());

            JPanel jpButtons = new JPanel();
            jpButtons.setLayout(new GridLayout(2,1,5,5));
            jpButtons.add(add);
            jpButtons.add(remove);
            // Setup event handling
            add.addActionListener(this);
            add.setMnemonic('L');
            remove.addActionListener(this);
            remove.setMnemonic('R');

            JPanel jpRight = new JPanel();
            jpRight.setLayout(new BorderLayout());
            jpRight.add(jpButtons, BorderLayout.NORTH);

            jpClasses.add(jpRight, BorderLayout.EAST);

            JPanel jpButtons2 = new JPanel();
            jpButtons2.setLayout(new GridLayout(1,4, 5, 5));
            jpButtons2.add(ok);
            jpButtons2.add(cancel);
            jpButtons2.add(save);
            // Setup event handling
            ok.addActionListener(this);
            ok.setMnemonic('O');
            cancel.addActionListener(this);
            cancel.setMnemonic('C');
            save.addActionListener(this);
            save.setMnemonic('S');

            content.add(jpButtons2, BorderLayout.SOUTH);

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

    public boolean isCanceled()
    {
        return canceled;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok || e.getSource() == save) {
            // Take care of common attributes
            common.getChild("club").setText(club.getText());
            common.getChild("name").setText(name.getText());
            common.getChild("place").setText(place.getText());
            common.getChild("date").setText(startdate.getText());
            common.getChild("time").setText(starttime.getText());
            if(measurement.isSelected()) {
                common.getChild("measurement").getAttribute("id").setValue("true");
            }
            else {
                common.getChild("measurement").getAttribute("id").setValue("false");
            }
            common.getChild("startinterval").setText(startinterval.getText());
            common.getChild("classinterval").setText(classinterval.getText());
            common.getChild("firstnumber").setText(firstnumber.getText());
            canceled = false;
            if(e.getSource() == ok) {
                source.getChild("common").detach();
                source.getChild("classes").detach();
                source.addContent(common);
                source.addContent(classes);
                this.setVisible(false);
            }
            if(e.getSource() == save) {
                this.setTitle("Ändra " + entityName);
            }
        }
        if(e.getSource() == cancel) {
            canceled = true;
            this.setVisible(false);
        }
        if(e.getSource() == add) {
            System.out.println("Add");
        }
        if(e.getSource() == remove) {
            int row = jtClasses.getSelectedRow();
            System.out.println("Remove: " + row);
            if(row != -1) {
                // Remove selected row
                jtClasses.remove(row);
                this.repaint();
            }
        }
    }

    public static boolean showDialog(Element source, boolean create)
    {
        // Secure that required tags exists
        secureStart(source.getChild("common"));
        CompetitionProperties cp = new CompetitionProperties(source);
        StringBuffer sbError = new StringBuffer(250);
        if(create) {
            cp.initialize("Ny " + cp.entityName, sbError);
        }
        else {
            cp.initialize("Ändra " + cp.entityName, sbError);
        }

        return (cp.isCanceled() == false);
    }

    private static void secureStart(Element common)
    {
        Element tmp = null;
        String[] tags = {
          "club",
          "name",
          "place",
          "date",
          "time",
          "measurement",
          "startinterval",
          "classinterval",
          "firstnumber"
        };
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i];
            if(common.getChild(tag) == null) {
                tmp = new Element(tag);
                common.addContent(tmp);
            }
        }
        common.getChild("measurement").setAttribute("id", "relative");
    }
}

