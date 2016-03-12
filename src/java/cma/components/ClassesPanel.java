package cma.components;

import org.jdom.Element;
import cma.model.XMLClassesListModel;
import cma.model.XMLListModel;
import cma.view.ClassDialog;
import cma.vo.ClassVO;
import cma.vo.CompetitionPropertiesVO;
import cma.util.Time;
import cma.common.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;
import com.oculustech.layout.OculusBox;

/**
 */
public class ClassesPanel extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private final JTextField firstNumber = new JTextField(10);
    private final JTextField startTime = new JTextField(10);
    private final JTextField classInterval = new JTextField(10);
    private final JTextField startInterval = new JTextField(10);
    private final JButton startNumbers = new JButton("Lotta startnummer");
    private final JButton startTimes = new JButton("Beräkna starttider");

    private JList listView = new JList();
    private XMLListModel listModel;

    /**
     * This method is the default constructor.
     */
    public ClassesPanel()
    {
        init();
    }

    private void init() {
        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);

        startNumbers.addActionListener(new CalculateNumbersAction());
        startTimes.addActionListener(new CalculateTimesAction());
        MouseListener mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2) {
                    int index = listView.locationToIndex(e.getPoint());
                    ClassVO vo = ClassVO.parse(listModel.elementAt(index));
                    vo = ClassDialog.showDialog(vo);
                    if (vo != null) {
                        listModel.setElementAt(ClassVO.describe(vo), index);
                    }
                }
            }
        };
        listView.addMouseListener(mouseListener);

        Action addAction = new AbstractAction("Lägg till...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                ClassVO vo = ClassDialog.showDialog();
                if (vo != null) {
                    listModel.addElement(ClassVO.describe(vo));
                }
            }
        };

        Action editAction = new AbstractAction("Ändra...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int index = listView.getSelectedIndex();
                if(index != -1) {
                    ClassVO vo = ClassDialog.showDialog(listModel.elementAt(index));
                    if (vo != null) {
                        listModel.setElementAt(ClassVO.describe(vo), index);
                    }
                }
            }
        };

        Action delAction = new AbstractAction("Ta bort")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Är du säker på detta? Alla eventuella registreringar kommer också att tas bort för denna klass!",
                    "Varning",
                    JOptionPane.WARNING_MESSAGE
                );

                if (opt == JOptionPane.OK_OPTION) {
                    int index = listView.getSelectedIndex();
                    if(index != -1) {
                        listModel.removeElementAt(index);
                    }
                }
            }
        };

        Action moveUpAction = new AbstractAction("Flytta upp")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int index = listView.getSelectedIndex();
                if (index != -1) {
                    if (index > 0) {
                        listModel.moveElement(index, index - 1);
                        listView.setSelectedIndex(index - 1);
                    }
                }
            }
        };

        Action moveDownAction = new AbstractAction("Flytta ner")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int index = listView.getSelectedIndex();
                if (index != -1) {
                    if (index < (listView.getModel().getSize() - 1)) {
                        listModel.moveElement(index, index + 1);
                        listView.setSelectedIndex(index + 1);
                    }
                }
            }
        };

        layout.addSpace(10);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.addSpace(10);
                layout.add(new JScrollPane(listView));
                layout.parent();
            }
            layout.addSpace(10);
            layout.parent();
        }
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.addSpace(10);
                layout.nestTabbedPane();
                {
                    layout.nestBoxAsTab("Klasser",OculusLayout.HORIZONTAL,OculusLayout.TOP_JUSTIFY);
                    {
                        layout.addSpace(10);
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.add(new JButton(addAction));
                            layout.add(new JButton(editAction));
                            layout.add(new JButton(delAction));
                            layout.addFiller();
                            layout.add(new JLabel("Ändra startordning"));
                            layout.add(new JButton(moveUpAction));
                            layout.add(new JButton(moveDownAction));
                            layout.addSpace(10);
                            layout.parent();
                        }
                        layout.addSpace(10);
                        layout.parent();
                    }
                    layout.nestBoxAsTab("Start",OculusLayout.HORIZONTAL,OculusLayout.TOP_JUSTIFY);
                    {
                        layout.addSpace(10);
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.nestGrid(2,4);
                            {
                                layout.add(new JLabel("Första startnumret"));
                                layout.add(firstNumber);
                                layout.add(new JLabel("Första Starttiden"));
                                layout.add(startTime);
                                layout.add(new JLabel("Start intervall (sek)"));
                                layout.add(startInterval);
                                layout.add(new JLabel("Klass intervall (sek)"));
                                layout.add(classInterval);
                                layout.parent();
                            }
                            layout.addSpace(10);
                            layout.add(startNumbers);
                            layout.add(startTimes);
                            layout.addSpace(10);
                            layout.parent();
                        }
                        layout.addSpace(10);
                        layout.parent();
                    }
                    layout.parent();
                }
                layout.parent();
            }
            layout.addSpace(10);
            layout.parent();
        }
        layout.addSpace(10);
    }

    public void setModelData(Element competition)
    {
        if (competition != null) {
            Element common = competition.getChild("common");
            CompetitionPropertiesVO vo = CompetitionPropertiesVO.parse(common);
            startTime.setText(Time.interval2String(vo.getTime()));
            firstNumber.setText("" + vo.getFirstNumber());
            classInterval.setText("" + vo.getClassInterval());
            startInterval.setText("" + vo.getStartInterval());

            Element classes = competition.getChild("classes");
            listModel = new XMLClassesListModel(classes);
            listView.setModel(listModel);
        }
    }

    protected boolean isValidData()
    {
        if (startTime.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Starttid för första start måste anges!", "Blank starttid",
                JOptionPane.WARNING_MESSAGE
            );
            startTime.requestFocus();
            return false;
        }
        if (Time.validateTimeFormat(startTime.getText()) == false) {
            JOptionPane.showMessageDialog(
                this,
                "Starttid måste anges enligt HH:MM:SS!",
                "Felaktigt format på starttid",
                JOptionPane.WARNING_MESSAGE
            );
            startTime.setText("");
            startTime.requestFocus();
            return false;
        }
        if (firstNumber.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Första startnummer måste anges!", "Blankt startnummer",
                JOptionPane.WARNING_MESSAGE
            );
            firstNumber.requestFocus();
            return false;
        }
        try {
            if (Integer.parseInt(firstNumber.getText()) <= 0) {
                throw new NumberFormatException("Trigger error handling");
            }
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Första startnummer måste vara ett positivt heltal och större än 0(noll)!",
                "Felaktigt format på startnummer",
                JOptionPane.WARNING_MESSAGE
            );
            firstNumber.setText("");
            firstNumber.requestFocus();
            return false;
        }
        if (startInterval.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Start intervall måste anges!", "Blankt start intervall",
                JOptionPane.WARNING_MESSAGE
            );
            startInterval.requestFocus();
            return false;
        }
        try {
            if (Long.parseLong(startInterval.getText()) < 0) {
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
            startInterval.setText("");
            startInterval.requestFocus();
            return false;
        }
        if (classInterval.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Klass intervall måste anges!", "Blankt klass intervall",
                JOptionPane.WARNING_MESSAGE
            );
            classInterval.requestFocus();
            return false;
        }
        try {
            if (Long.parseLong(classInterval.getText()) < 0) {
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
            classInterval.setText("");
            classInterval.requestFocus();
            return false;
        }
        return true;
    }


    // *** Internal classes ***

    private class CalculateNumbersAction extends AbstractAction
    {
        public CalculateNumbersAction()
        {
        }

        public CalculateNumbersAction(String name)
        {
            super(name);
        }

        public void actionPerformed(ActionEvent actionEvent)
        {
            if (isValidData()) {
                // Get selected indexes
                int[] index = listView.getSelectedIndices();
                // Setup starting point
                long sNumber = Long.parseLong(firstNumber.getText());
                // Execute
                for (int i = 0; i < index.length; i++) {
                    Element tmp = listModel.elementAt(index[i]);
                    long count = StartOrderHelper.setStartNumber(tmp, sNumber);
                    sNumber += count;
                }
            }
        }
    }

    private class CalculateTimesAction extends AbstractAction
    {
        public CalculateTimesAction()
        {
        }

        public CalculateTimesAction(String name)
        {
            super(name);
        }

        public void actionPerformed(ActionEvent actionEvent)
        {
            if (isValidData()) {
                // Get selected indexes
                int[] index = listView.getSelectedIndices();
                // Setup starting point
                long sTime = Time.string2Interval(startTime.getText());
                long cInterval = (long) Double.parseDouble(classInterval.getText().replace(',', '.'));
                long sInterval = (long) Double.parseDouble(startInterval.getText().replace(',', '.'));
                // Execute
                for (int i = 0; i < index.length; i++) {
                    Element tmp = listModel.elementAt(index[i]);
                    long count = StartOrderHelper.setStartTime(tmp, sTime, (sInterval * 1000));
                    if (i < index.length) {
                        // Only add class interval if more classes available
                        sTime += ((count - 1) * (sInterval * 1000) + (cInterval * 1000));
                    }
                }
            }
        }
    }

}