package cma.view;

import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;
import com.oculustech.layout.OculusBox;

import javax.swing.*;

import cma.common.FileChooserHelper;
import cma.vo.ImportRegistrationVO;

import java.awt.event.ActionEvent;
import java.awt.*;

public class ImportRegistrationPanel extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private JComboBox separator = new JComboBox(new String[]{" , ", " ; ", " : "});
    private JTextField resultFileName = new JTextField(20);
    private JButton select = new JButton("Välj ...");

    /**
     * This method is the default constructor.
     */
    public ImportRegistrationPanel()
    {
        init();
    }

    /**
     * This method initialises the panel and layout manager.
     */
    private void init()
    {
        select.addActionListener(new ImportRegistrationPanel.SelectAction(this));

        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);
        separator.setMaximumRowCount(4);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.nestGrid(3,2);
                {
                    layout.add(new JLabel("Separator"));
                    layout.nestBox(OculusLayout.HORIZONTAL,OculusLayout.TOP_JUSTIFY);
                    {
                        layout.add(separator);
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

    public ImportRegistrationVO getData()
    {
        ImportRegistrationVO vo = new ImportRegistrationVO();
        vo.setSeparator(((String)separator.getSelectedItem()).trim());
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
