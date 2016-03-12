package cma.view;

import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;
import com.oculustech.layout.OculusBox;

import javax.swing.*;

import cma.common.FileChooserHelper;
import cma.vo.ImportVO;
import org.jdom.Element;

import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 18, 2003
 * Time: 11:25:45 PM
 * To change this template use Options | File Templates.
 */
public class ImportTimePanel extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private JComboBox timeType = new JComboBox(new String[]{"Korrigerad tid", "Absolut tid", "Relativ tid"});
    private JComboBox position = new JComboBox(new String[]{"Sluttid", "Mellantid 1", "Mellantid 2", "Mellantid 3"});
    private JTextField resultFileName = new JTextField();
    private JButton select = new JButton("Välj ...");

    /**
     * This method is the default constructor.
     */
    public ImportTimePanel()
    {
        init();
    }

    /**
     * This method initialises the panel and layout manager.
     */
    private void init()
    {
        select.addActionListener(new ImportTimePanel.SelectAction(this));

        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);
        timeType.setMaximumRowCount(3);
        position.setMaximumRowCount(4);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.nestGrid(3,2);
                {
                    layout.add(new JLabel("Tidstyp"));
                    layout.nestBox(OculusLayout.HORIZONTAL,OculusLayout.TOP_JUSTIFY);
                    {
                        layout.add(timeType);
                        layout.add(new JLabel("Position"));
                        layout.add(position);
                        layout.parent();
                    }
                    layout.add(new JLabel());
                    layout.add(new JLabel("Resultatfil"));
                    layout.add(resultFileName);
                    layout.add(select);
                    layout.parent();
                }
                layout.parent();
            }
            layout.parent();
        }
    }

    public ImportVO getData()
    {
        ImportVO vo = new ImportVO();
        vo.setTimeType((String)timeType.getSelectedItem());
        vo.setPosition((String)position.getSelectedItem());
        vo.setFileName(resultFileName.getText());
        return vo;
    }

    protected boolean isValidData()
    {
        if (resultFileName.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Resultatfil måste anges!", "Ingen resultatfil angiven",
                JOptionPane.WARNING_MESSAGE
            );
            select.requestFocus();
            return false;
        }
        return true;
    }

    private class SelectAction extends AbstractAction
    {
        private Component parent;
        public SelectAction(Component aParent)
        {
            super();
            parent = aParent;
        }

        public void actionPerformed(ActionEvent actionEvent)
        {
            String fileName = FileChooserHelper.getOpenFileName(parent, new String[] {"txt"});
            if(fileName != null) {
                resultFileName.setText(fileName);
            }
        }
    }
}
