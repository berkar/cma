package cma.components;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.DOMOutputter;
import cma.model.XMLListModel;
import cma.model.XMLReportListModel;
import cma.view.ReportDialog;
import cma.vo.ReportVO;
import cma.ConsoleInterface;
import cma.Constants;
import cma.common.TransformationHelper;
import cma.common.ApplicationPropertyManager;

import javax.swing.*;
import java.awt.event.*;

import com.oculustech.layout.OculusBox;
import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;

/**
 */
public class ReportsPanel  extends OculusBox
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private JList listView;
    private XMLListModel listModel;

    /**
     * This method is the default constructor.
     */
    public ReportsPanel()
    {
        init();
    }

    /**
     * This method initialises the panel and layout manager.
     */
    private void init()
    {
        // Setup the internal content pane to hold the user content pane
        // and the standard button panel

        OculusLayoutHelper layout = new OculusLayoutHelper(this);
        layout.setJustification(OculusLayout.TOP_JUSTIFY);

        listView = new JList();
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );

        MouseListener mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2) {
                    int index = listView.locationToIndex(e.getPoint());
                    ReportVO vo = ReportVO.parse(listModel.elementAt(index));
                    if(vo.isSystem()) {
                        javax.swing.JOptionPane.showMessageDialog(
                                ConsoleInterface.getInstance(),
                                "Du får inte ändra i en grund rapport! Definiera en egen istället!"
                        );
                        return;
                    }
                    vo = ReportDialog.showDialog(vo);
                    if (vo != null) {
                        listModel.setElementAt(ReportVO.describe(vo), index);
                    }
                }
            }
        };
        listView.addMouseListener(mouseListener);

        Action addAction = new AbstractAction("Lägg till ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                ReportVO vo = ReportDialog.showDialog();
                if (vo != null) {
                    listModel.addElement(ReportVO.describe(vo));
                }
            }
        };

        Action editAction = new AbstractAction("Ändra ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int index = listView.getSelectedIndex();
                if(index != -1) {
                    ReportVO vo = ReportVO.parse(listModel.elementAt(index));
                    if(vo.isSystem()) {
                        javax.swing.JOptionPane.showMessageDialog(
                                ConsoleInterface.getInstance(),
                                "Du får inte ändra i en grund rapport! Definiera en egen istället!"
                        );
                        return;
                    }
                    vo = ReportDialog.showDialog(vo);
                    if (vo != null) {
                        listModel.setElementAt(ReportVO.describe(vo), index);
                    }
                }
            }
        };

        Action delAction = new AbstractAction("Ta bort")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                int index = listView.getSelectedIndex();
                if(index != -1) {
                    ReportVO vo = ReportVO.parse(listModel.elementAt(index));
                    if(vo.isSystem()) {
                        javax.swing.JOptionPane.showMessageDialog(
                                ConsoleInterface.getInstance(), "Du får inte ta bort en grund rapport!"
                        );
                        return;
                    }
                    listModel.removeElementAt(index);
                }
            }
        };

        Action transformAction = new AbstractAction("Visa ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                // Add transformations
                int index = listView.getSelectedIndex();
                if(index != -1) {
                    // All's well! Check if there is a browser defined
                    String browserPath = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).getBrowserPath();
                    if (browserPath == null || browserPath.trim().length() <= 0) {
                        javax.swing.JOptionPane.showMessageDialog(
                                ConsoleInterface.getInstance(),
                                "Ingen 'browser-path' definierad! Se menyval Fil->Egenskaper"
                        );
                        return;
                    }
                    ReportVO vo = ReportVO.parse(listModel.elementAt(index));
                    try {
                        Document doc = ConsoleInterface.getCompetition();
                        DOMOutputter out = new DOMOutputter();
                        org.w3c.dom.Document dom = out.output(doc);
                        TransformationHelper.show(dom, vo.getUrl(), vo.getPostfix());
                    }
                    catch (JDOMException e1) {
                        JOptionPane.showMessageDialog(
                                ConsoleInterface.getInstance(),
                                "Problem med läsning av tävlingsfil!"
                        );
                        e1.printStackTrace();
                    }
                }
            }
        };

        layout.addSpace(10);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.addSpace(10);
            layout.add(new JScrollPane(listView));
            layout.addSpace(10);
            layout.parent();
        }
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.addSpace(10);
            layout.nestBox(OculusLayout.VERTICAL);
            {
                layout.add(new JButton(addAction));
                layout.add(new JButton(editAction));
                layout.add(new JButton(delAction));
                layout.add(new JButton(transformAction));
                layout.parent();
            }
            layout.addSpace(10);
            layout.parent();
        }
        layout.addSpace(10);
    }

    public void setModelData(Element reports)
    {
        listModel = new XMLReportListModel(reports);
        listView.setModel(listModel);
    }
}