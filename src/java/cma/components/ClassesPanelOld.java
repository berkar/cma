package cma.components;

import org.jdom.Element;
import cma.model.XMLClassesListModel;
import cma.model.XMLListModel;
import cma.view.ClassDialog;
import cma.vo.ClassVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.oculustech.layout.OculusBox;
import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;

/**
 */
public class ClassesPanelOld extends OculusBox
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
    public ClassesPanelOld()
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

        MouseListener mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2) {
                    int index = listView.locationToIndex(e.getPoint());
                    ClassVO vo = ClassVO.parse((Element) listModel.elementAt(index));
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
                    ClassVO vo = ClassDialog.showDialog((Element) listModel.elementAt(index));
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
                layout.add(new JButton(moveUpAction));
                layout.add(new JButton(moveDownAction));
                layout.parent();
            }
            layout.parent();
        }
        layout.addSpace(10);
    }

    public void setModelData(Element classes)
    {
        listModel = new XMLClassesListModel(classes);
        listView.setModel(listModel);
    }
}