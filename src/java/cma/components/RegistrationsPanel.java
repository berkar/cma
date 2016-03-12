package cma.components;

import cma.common.ImportRegistrationHelper;
import cma.model.XMLListModel;
import cma.model.XMLRegistrationListModel;
import cma.view.RegistrationDialog;
import cma.vo.ClassVO;
import cma.vo.RegistrationVO;
import com.oculustech.layout.OculusBox;
import com.oculustech.layout.OculusLayout;
import com.oculustech.layout.OculusLayoutHelper;
import org.jdom.Element;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class RegistrationsPanel  extends OculusBox implements ClipboardOwner
{
    static
    {
        OculusLayout.setLicenseNumber("JNADDN3BMPV");
    }

    private static final Clipboard clipboard = new Clipboard("Registration");

    private JList listView;
    private JComboBox classesCombo;
    private XMLListModel listModel;
    private Element classes;
    private Map classMap = new TreeMap();

    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
        System.out.println("Lost clipboard");
    }

    /**
     * This method is the default constructor.
     */
    public RegistrationsPanel()
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

        classesCombo = new JComboBox();
        classesCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JComboBox cb = (JComboBox) e.getSource();
                String className = (String) cb.getSelectedItem();
                listModel = new XMLRegistrationListModel((Element) classMap.get(className));
                listView.setModel(listModel);
            }
        });

        listView = new JList();
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        // Take care of double click's
        MouseListener mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (listModel.getSize() > 0 && e.getClickCount() == 2) {
                    int index = listView.locationToIndex(e.getPoint());
                    String className = (String) classesCombo.getSelectedItem();
                    RegistrationVO vo = RegistrationVO.parse(listModel.elementAt(index));
                    vo = RegistrationDialog.showDialog(className, vo);
                    if (vo != null) {
                        listModel.setElementAt(RegistrationVO.describe(vo), index);
                    }
                }
            }
        };
        listView.addMouseListener(mouseListener);
        // Take care of Ctrl-X
        Action cutAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = listView.getSelectedIndex();
                RegistrationVO vo = RegistrationVO.parse(listModel.elementAt(index));
                clipboard.setContents(new StringSelection(vo.describe()), RegistrationsPanel.this);
                listModel.removeElementAt(index);
                System.out.println("CUT: " + vo.toString());
            }
        };
        listView.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK), "cut");
        listView.getActionMap().put("cut", cutAction);
        // Take care of Ctrl-C
        Action copyAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = listView.getSelectedIndex();
                RegistrationVO vo = RegistrationVO.parse(listModel.elementAt(index));
                clipboard.setContents(new StringSelection(vo.describe()), RegistrationsPanel.this);
                System.out.println("COPY: " + vo.toString());
            }
        };
        listView.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK), "copy");
        listView.getActionMap().put("copy", copyAction);
        // Take care of Ctrl-V
        Action pasteAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("PASTE ...");
                Transferable content = clipboard.getContents(RegistrationsPanel.this);
                if(content != null) {
                    try {
                        String tmp = (String)content.getTransferData(DataFlavor.stringFlavor);
                        RegistrationVO vo = RegistrationVO.parse(tmp);
                        listModel.addElement(RegistrationVO.describe(vo));
                        System.out.println("PASTE: " + vo.toString());
                    }
                    catch (UnsupportedFlavorException e1) {
                        e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
                    }
                }
            }
        };
        listView.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK), "paste");
        listView.getActionMap().put("paste", pasteAction);

        Action addAction = new AbstractAction("Lägg till ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                // Get class name
                String className = (String) classesCombo.getSelectedItem();
                RegistrationVO vo = RegistrationDialog.showDialog(className);
                if (vo != null) {
                    listModel.addElement(RegistrationVO.describe(vo));
                }
            }
        };

        Action editAction = new AbstractAction("Ändra ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                String className = (String) classesCombo.getSelectedItem();
                int index = listView.getSelectedIndex();
                if(index != -1) {
                    RegistrationVO vo = RegistrationVO.parse(listModel.elementAt(index));
                    vo = RegistrationDialog.showDialog(className, vo);
                    if (vo != null) {
                        listModel.setElementAt(RegistrationVO.describe(vo), index);
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
                    listModel.removeElementAt(index);
                }
            }
        };

        Action importAction = new AbstractAction("Importera (CSV) ...")
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                // Take care of imports
                Collection result = ImportRegistrationHelper.execute();
                for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                    RegistrationVO tmp = (RegistrationVO) iterator.next();
                    listModel.addElement(RegistrationVO.describe(tmp));
                }
            }
        };

        layout.addSpace(10);
        layout.nestBox(OculusLayout.VERTICAL);
        {
            layout.addSpace(10);
            layout.add(classesCombo);
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
                layout.add(new JButton(importAction));
/*
                layout.addFiller();
                layout.add(new JButton(printAction));
*/
                layout.parent();
            }
            layout.addSpace(10);
            layout.parent();
        }
        layout.addSpace(10);
    }

    public void setModelData(Element classes)
    {
        Element first = null;
        this.classes = classes;
        Iterator i = this.classes.getChildren().iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (first == null) {
                first = e;
            }
            ClassVO vo = ClassVO.parse(e);
            classMap.put(vo.getName(), e);
        }
        classesCombo.setModel(new DefaultComboBoxModel(classMap.keySet().toArray()));
        // Show content in first selected item
        String className = (String) classesCombo.getSelectedItem();
        if(className != null && className.length() > 0) {
            listModel = new XMLRegistrationListModel((Element) classMap.get(className));
            listView.setModel(listModel);
        }
    }
}