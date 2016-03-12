package cma.components;

import java.awt.*;
import javax.swing.*;

import com.oculustech.layout.*;
import cma.vo.RegistrationVO;
import cma.util.Time;

public class RegistrationPanel extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    protected JTextField clazz;
    protected JTextField did;
    protected JTextField name;
    protected JTextField address;
    protected JTextField club;
    protected JTextField dogs;
    protected JTextField phone;
    protected JTextField postAddress;
    protected JTextField mail;
    protected JTextField startNumber;
    protected JCheckBox starting;
    protected JTextField startTime;
    protected JCheckBox paymentReceived;
    protected JTextField time1;
    protected JTextField time2;
    protected JTextField time3;
    protected JTextField pos;
    protected JTextField totalTime;
    protected JTextField diff;
    protected JCheckBox warning;
    protected JCheckBox abandon;
    protected JCheckBox disqualified;
    protected JTextField note;
    protected JButton ok;
    protected JButton cancel;

    public RegistrationPanel() {
        initComponents();
    }

    private void initComponents() {
        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);

        // Set class to be used
        clazz = new JTextField();
        clazz.setEditable(false);
        clazz.setBackground(Color.decode("-2830136"));
        clazz.setEnabled(false);

        did = new JTextField(10);
        name = new JTextField(20);
        address = new JTextField(20);
        club = new JTextField(20);
        dogs = new JTextField();
        phone = new JTextField();
        postAddress = new JTextField();
        mail = new JTextField();
        startNumber = new JTextField();
        starting = new JCheckBox("Startar");
        starting.setSelected(true);
        startTime = new JTextField();
        paymentReceived = new JCheckBox("Betalning mottagen");
        time1 = new JTextField();
        time2 = new JTextField();
        time3 = new JTextField();
        pos = new JTextField();
        pos.setEditable(false);
        pos.setBackground(Color.decode("-2830136"));
        pos.setEnabled(false);
        pos.setToolTipText("Genereras automatiskt via 'Verktyg/Beräkna resultat'");
        totalTime = new JTextField();
        diff = new JTextField();
        diff.setEditable(false);
        diff.setBackground(Color.decode("-2830136"));
        diff.setEnabled(false);
        diff.setToolTipText("Genereras automatiskt via 'Verktyg/Beräkna resultat'");
        warning = new JCheckBox("Varning");
        abandon = new JCheckBox("Bryter");
        disqualified = new JCheckBox("Diskvalificerad");
        note = new JTextField();
        ok = new JButton("Ok");
        cancel = new JButton("Cancel");

        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.nestGrid(1, 1);
                {
                    layout.nestBox(OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.add(new JLabel("Tävlingsklass"));
                        layout.add(clazz);
                        layout.parent();
                    }
                    layout.parent();
                }
                layout.addSpace(10);
                layout.nestTabbedPane();
                {
                    layout.nestBoxAsTab("Ekipage", OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.nestGrid(4, 4);
                            {
                                layout.add(new JLabel("DID"));
                                layout.add(did);
                                layout.add(new JLabel("Förening"));
                                layout.add(club);
                                layout.add(new JLabel("Namn"));
                                layout.add(name);
                                layout.add(new JLabel("Hundnamn"));
                                layout.add(dogs);
                                layout.add(new JLabel("Adress"));
                                layout.add(address);
                                layout.add(new JLabel("Telefon"));
                                layout.add(phone);
                                layout.add(new JLabel("Postadress"));
                                layout.add(postAddress);
                                layout.add(new JLabel("Mail"));
                                layout.add(mail);
                                layout.parent();
                            }
                            layout.addSpace(10);
                            layout.nestGrid(3, 1);
                            {
                                layout.nestBox(OculusLayout.VERTICAL);
                                {
                                    layout.add(starting);
                                    layout.add(paymentReceived);
                                    layout.parent();
                                }
                                layout.nestBox(OculusLayout.VERTICAL);
                                {
                                    layout.add(warning);
                                    layout.add(abandon);
                                    layout.add(disqualified);
                                    layout.parent();
                                }
                                layout.nestBox(OculusLayout.VERTICAL);
                                {
                                    layout.add(new JLabel("Anmärkning"));
                                    layout.add(note);
                                    layout.parent();
                                }
                                layout.parent();
                            }
                            layout.parent();
                        }
                        layout.parent();
                    }
                    layout.nestBoxAsTab("Startdata", OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.nestGrid(2, 2);
                            {
                                layout.add(new JLabel("Startnummer"));
                                layout.add(startNumber);
                                layout.add(new JLabel("Starttid"));
                                layout.add(startTime);
                                layout.parent();
                            }
                            layout.parent();
                        }
                        layout.parent();
                    }
                    layout.nestBoxAsTab("Resultatdata", OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.nestGrid(4, 3);
                            {
                                layout.add(new JLabel("Mellantid 1"));
                                layout.add(time1);
                                layout.add(new JLabel("Position"));
                                layout.add(pos);
                                layout.add(new JLabel("Mellantid 2"));
                                layout.add(time2);
                                layout.add(new JLabel("Totaltid"));
                                layout.add(totalTime);
                                layout.add(new JLabel("Mellantid 3"));
                                layout.add(time3);
                                layout.add(new JLabel("Differans"));
                                layout.add(diff);
                                layout.parent();
                            }
                            layout.parent();
                        }
                        layout.parent();
                    }
                    layout.parent();
                }
                layout.addSpace(10);
                layout.parent();
            }
        }
    }

    public void setClassName(String className)
    {
        clazz.setText(className);
    }

    public void setData(RegistrationVO vo)
    {
        club.setText(vo.getClub());
        name.setText(vo.getName());
        address.setText(vo.getAddress());
        postAddress.setText(vo.getPostAddress());
        phone.setText(vo.getPhone());
        mail.setText(vo.getMail());
        dogs.setText(vo.getDogs());
        did.setText(vo.getDid());
        paymentReceived.setSelected(vo.isPaymentReceived());
        starting.setSelected(vo.isStarting());
        if (vo.getStartNumber() > 0) {
            startNumber.setText(Long.toString(vo.getStartNumber()));
        }
        startTime.setText(Time.interval2String(vo.getStarttime()));
        if(vo.getTime1() > 0) time1.setText(Time.interval2String(vo.getTime1()));
        if(vo.getTime2() > 0) time2.setText(Time.interval2String(vo.getTime2()));
        if(vo.getTime3() > 0) time3.setText(Time.interval2String(vo.getTime3()));
        if(vo.getTotaltime() > 0) totalTime.setText(Time.interval2String(vo.getTotaltime()));
        if(vo.getDiff() > 0) diff.setText(Time.interval2String(vo.getDiff()));
        if(vo.getPosition() > 0) pos.setText(Long.toString(vo.getPosition()));
        abandon.setSelected(vo.isAbandon());
        warning.setSelected(vo.isWarning());
        disqualified.setSelected(vo.isDisqualified());
        note.setText(vo.getNote());
    }

    public RegistrationVO getData()
    {
        RegistrationVO vo = new RegistrationVO();
        vo.setClub(club.getText());
        vo.setName(name.getText());
        vo.setAddress(address.getText());
        vo.setPostAddress(postAddress.getText());
        vo.setPhone(phone.getText());
        vo.setMail(mail.getText());
        vo.setDogs(dogs.getText());
        vo.setDid(did.getText());
        vo.setPaymentReceived(paymentReceived.isSelected());
        vo.setStarting(starting.isSelected());
        if (startNumber.getText() != null && startNumber.getText().trim().length() > 0) {
            vo.setStartNumber(Long.parseLong(startNumber.getText()));
        }
        vo.setStarttime(Time.string2Interval(startTime.getText()));
        vo.setTime1(Time.string2Interval(time1.getText()));
        vo.setTime2(Time.string2Interval(time2.getText()));
        vo.setTime3(Time.string2Interval(time3.getText()));
        vo.setTotaltime(Time.string2Interval(totalTime.getText()));
        vo.setDiff(Time.string2Interval(diff.getText()));
        if (pos.getText() != null && pos.getText().trim().length() > 0) {
            vo.setPosition(Long.parseLong(pos.getText()));
        }
        vo.setAbandon(abandon.isSelected());
        vo.setWarning(warning.isSelected());
        vo.setDisqualified(disqualified.isSelected());
        vo.setNote(note.getText());
        return vo;
    }

    public boolean isValidData()
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
        return true;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RegistrationPanel pane = new RegistrationPanel();
        pane.setClassName("D21A");
        frame.setContentPane(pane);
        frame.pack();
        frame.show();
    }
}
