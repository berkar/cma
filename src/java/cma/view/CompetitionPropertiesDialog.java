package cma.view;

import cma.components.LabelledItemPanel;
import cma.components.StandardDialog;

import javax.swing.*;

import cma.vo.CompetitionPropertiesVO;

import cma.util.DateSupport;
import cma.util.Time;

/**
 * This class demonstrates the usage of the cma.components.StandardDialog class.
 */
public class CompetitionPropertiesDialog extends StandardDialog
{
    // Constants
    private final String entityName = "Tävling";
    private final JTextField club = new JTextField(20);
    private final JTextField name = new JTextField();
    private final JTextField place = new JTextField();
    private final JTextField startdate = new JTextField();
    private final JTextField starttime = new JTextField();
    private final JTextField classinterval = new JTextField();
    private final JTextField startinterval = new JTextField();
    private final JTextField firstnumber = new JTextField();
    private LabelledItemPanel myContentPane = new LabelledItemPanel();

    // Methods

    /**
     * This method is the default constructor.
     */
    public CompetitionPropertiesDialog()
    {
        init();
    }

    /**
     * This method initialises the cma.components on the panel.
     */
    private void init()
    {
        setTitle(entityName);
        myContentPane.setBorder(BorderFactory.createEtchedBorder());
        myContentPane.addItem("Förening", club);
        myContentPane.addItem("Namn", name);
        myContentPane.addItem("Plats", place);
        myContentPane.addItem("Start datum (YYYY-MM-DD)", startdate);
        myContentPane.addItem("Starttid tävlingsklocka (HH:MM:SS)", starttime);
        myContentPane.addItem("Klass intervall (sek)", classinterval);
        myContentPane.addItem("Start intervall (sek)", startinterval);
        myContentPane.addItem("Start nummer", firstnumber);
        setContentPane(myContentPane);
    }

    public CompetitionPropertiesVO getData()
    {
        CompetitionPropertiesVO vo = new CompetitionPropertiesVO();
        // Take care of common attributes
        vo.setClub(club.getText());
        vo.setName(name.getText());
        vo.setPlace(place.getText());
        vo.setDate(DateSupport.parseDate(startdate.getText()));
        vo.setTime(Time.string2Interval(starttime.getText()));
        vo.setStartInterval(Long.parseLong(startinterval.getText()));
        vo.setClassInterval(Long.parseLong(classinterval.getText()));
        vo.setFirstNumber(new Integer(firstnumber.getText()).intValue());
        return vo;
    }

    public void setData(CompetitionPropertiesVO vo)
    {
        club.setText(vo.getClub());
        name.setText(vo.getName());
        place.setText(vo.getPlace());
        startdate.setText(DateSupport.formatDate(vo.getDate()));
        starttime.setText(Time.interval2String(vo.getTime()));
        startinterval.setText(Long.toString(vo.getStartInterval()));
        classinterval.setText(Long.toString(vo.getClassInterval()));
        firstnumber.setText(new Integer(vo.getFirstNumber()).toString());
    }

    /**
     * This method checks that the data entered is valid.
     * To be valid, the following must be met:
     * <LI>Customer Code field is not blank
     * <LI>Name field is not blank
     *
     * @return <code>true</code> if the data is valid, otherwise
     * <code>false</code>
     */
    protected boolean isValidData()
    {
        if (club.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Förening måste anges", "Blank förening", JOptionPane.WARNING_MESSAGE);
            club.requestFocus();
            return false;
        }
        if (name.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Namn måste anges", "Blankt namn", JOptionPane.WARNING_MESSAGE);
            name.requestFocus();
            return false;
        }

        if (starttime.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Starttid för första start måste anges!", "Blank starttid",
                JOptionPane.WARNING_MESSAGE
            );
            starttime.requestFocus();
            return false;
        }
        if (Time.validateTimeFormat(starttime.getText()) == false) {
            JOptionPane.showMessageDialog(
                this,
                "Starttid måste anges enligt HH:MM:SS!",
                "Felaktigt format på starttid",
                JOptionPane.WARNING_MESSAGE
            );
            starttime.setText("");
            starttime.requestFocus();
            return false;
        }
        if (firstnumber.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Första startnummer måste anges!", "Blankt startnummer",
                JOptionPane.WARNING_MESSAGE
            );
            firstnumber.requestFocus();
            return false;
        }
        try {
            if (Integer.parseInt(firstnumber.getText()) <= 0) {
                throw new NumberFormatException("Trigger error handling");
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Första startnummer måste vara ett heltal och större än 0(noll)!",
                "Felaktigt format på startnummer",
                JOptionPane.WARNING_MESSAGE
            );
            firstnumber.setText("");
            firstnumber.requestFocus();
            return false;
        }
        if (startinterval.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Start intervall måste anges!", "Blankt start intervall",
                JOptionPane.WARNING_MESSAGE
            );
            startinterval.requestFocus();
            return false;
        }
        try {
            if (Long.parseLong(startinterval.getText()) < 0) {
                throw new NumberFormatException("Trigger error handling");
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Start intervall måste vara ett positivt heltal, och i sekunder!",
                "Felaktigt format på start intervall",
                JOptionPane.WARNING_MESSAGE
            );
            startinterval.setText("");
            startinterval.requestFocus();
            return false;
        }
        if (classinterval.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Klass intervall måste anges!", "Blankt klass intervall",
                JOptionPane.WARNING_MESSAGE
            );
            classinterval.requestFocus();
            return false;
        }
        try {
            if (Long.parseLong(classinterval.getText()) < 0) {
                throw new NumberFormatException("Trigger error handling");
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Klass intervall måste vara ett postivt heltal, och i sekunder!",
                "Felaktigt format på klass intervall",
                JOptionPane.WARNING_MESSAGE
            );
            classinterval.setText("");
            classinterval.requestFocus();
            return false;
        }
        return true;
    }

    public static CompetitionPropertiesVO showDialog()
    {
        CompetitionPropertiesDialog pDlg = new CompetitionPropertiesDialog();
        pDlg.setTitle("Ny Tävling");
        pDlg.pack();
        pDlg.show();
        if (pDlg.hasUserCancelled())
            return null;
        else
            return pDlg.getData();
    }

    public static CompetitionPropertiesVO showDialog(CompetitionPropertiesVO properties)
    {
        CompetitionPropertiesDialog pDlg = new CompetitionPropertiesDialog();
        pDlg.setTitle("Ändra Tävling");
        pDlg.setData(properties);
        pDlg.pack();
        pDlg.show();
        if (pDlg.hasUserCancelled())
            return null;
        else
            return pDlg.getData();
    }

    /**
     *
     */
    public static void main(String[] args)
    {
        CompetitionPropertiesVO result = null;
        String cmd = args[0];
        if ("new".equalsIgnoreCase(cmd)) {
            result = CompetitionPropertiesDialog.showDialog();
        }
        if ("edit".equalsIgnoreCase(cmd)) {
            CompetitionPropertiesVO tmp = new CompetitionPropertiesVO();
            tmp.setClub("BDIF");
            tmp.setName("Kronandraget");
            tmp.setPlace("Ormberget");
            result = CompetitionPropertiesDialog.showDialog(tmp);
        }
        System.out.println("Result: " + result);
        System.exit(0);
    }
}