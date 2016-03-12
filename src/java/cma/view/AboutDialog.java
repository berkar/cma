package cma.view;

// package declaration here

import com.oculustech.layout.OculusBox;
import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }
    private String version = "SNAPSHOT";

    protected JLabel console;
    protected JButton ok;

    public AboutDialog()
    {
        if(System.getProperty("app.version") != null) {
            version = System.getProperty("app.version");
        }
        initComponents();
    }

    private void initComponents()
    {
        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);

        console = new JLabel("Competition Management Application (CMA)");
        console.setFont(Font.decode("MS Sans Serif-BOLD-18"));
        console.setFocusTraversalKeysEnabled(false);
        console.setFocusable(false);

        ok = new JButton("Ok");

        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.nestGrid(1, 3);
                {
                    layout.nestBox(OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.addFiller();
                        layout.add(console);
                        layout.addFiller();
                        layout.parent();
                    }
                    layout.nestBox(OculusLayout.HORIZONTAL, OculusLayout.TOP_JUSTIFY);
                    {
                        layout.addFiller();
                        layout.nestBox(OculusLayout.VERTICAL);
                        {
                            layout.addSpace(10);
                            layout.add(new JLabel("Programming by Bertil Karlsson"));
                            layout.add(new JLabel("Version: " + version));
                            layout.addSpace(10);
                            layout.parent();
                        }
                        layout.addFiller();
                        layout.parent();
                    }
                    layout.parent();
                }
                layout.parent();
            }
            layout.parent();
        }
    }

    public static void showDialog()
    {
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AboutDialog pane = new AboutDialog();
        frame.setContentPane(pane);
        frame.pack();
        frame.show();
    }

    public static void main(String[] args) throws Exception
    {
        AboutDialog.showDialog();
    }
}
