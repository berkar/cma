package cma.view;

import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;
import com.oculustech.layout.OculusBox;

import javax.swing.*;

import cma.common.FileChooserHelper;
import cma.vo.ReportVO;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ReportPanel extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private JTextField name = new JTextField(20);
    private JTextField postfix = new JTextField(20);
    private JTextField url = new JTextField(20);
    private JTextField note = new JTextField(20);
    private JButton select = new JButton("Välj ...");

    /**
     * This method is the default constructor.
     */
    public ReportPanel()
    {
        init();
    }

    /**
     * This method initialises the panel and layout manager.
     */
    private void init()
    {
        select.addActionListener(new ReportPanel.SelectAction(this));

        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.nestGrid(3,4);
                {
                    layout.add(new JLabel("Namn"));
                    layout.add(name);
                    layout.add(new JLabel());
                    layout.add(new JLabel("Postfix (.html)"));
                    layout.add(postfix);
                    layout.add(new JLabel());
                    layout.add(new JLabel("Anmärkning"));
                    layout.add(note);
                    layout.add(new JLabel());
                    layout.add(new JLabel("Stylesheet"));
                    layout.add(url);
                    layout.add(select);
                    layout.parent();
                }
                layout.parent();
            }
            layout.parent();
        }
    }

    public void setData(ReportVO vo)
    {
        name.setText(vo.getName());
        url.setText(vo.getUrl());
        note.setText(vo.getNote());
        postfix.setText(vo.getPostfix());
    }

    public ReportVO getData()
    {
        ReportVO vo = new ReportVO();
        vo.setName(name.getText());
        vo.setUrl(url.getText());
        vo.setNote(note.getText());
        vo.setPostfix(postfix.getText());
        if(postfix.getText() != null && postfix.getText().length() > 0) {
            vo.setPostfix(postfix.getText());
        }
        else {
            vo.setPostfix(".html");
        }
        return vo;
    }

    protected boolean isValidData()
    {
        if (name.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Rapport namn måste anges!", "Inget rapport namn angivet!",
                JOptionPane.WARNING_MESSAGE
            );
            name.requestFocus();
            return false;
        }
        if (url.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "URL till stylesheet måste anges!", "Ingen stylesheet angiven!",
                JOptionPane.WARNING_MESSAGE
            );
            select.requestFocus();
            return false;
        }

        try {
            URL tmp = ReportPanel.class.getResource(url.getText());
            if(tmp == null) {
                File file = new File(url.getText());
                tmp = file.toURL();
                if(tmp == null) {
                    throw new Exception("No URL could be resolved for selected path: " + url.getText() + "!");
                }
            }
            if(tmp != null) {
                InputStream is = tmp.openStream();
                is.close();
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Felaktig path till stylesheet angiven", "Path alt. stylesheet existerar inte",
                    JOptionPane.WARNING_MESSAGE
            );
            url.requestFocus();
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
            String fileName = FileChooserHelper.getOpenFileName(parent, new String[] {"xslt", "xsl"});
            if(fileName != null) {
                url.setText(fileName);
            }
        }
    }
}
