package cma.components;

import cma.ClassesTableModel;
import cma.vo.ClassVO;
import cma.view.ClassInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 */
public class ClassesItemPanel extends JPanel
{
  private final String[] columns = new String[] {"Klassnamn", "Distans (km)"};

  /** The spacing between cma.components in pixels */
  private static final int COMPONENT_SPACING = 10;

  private JTable tableView;

  /**
   * This method is the default constructor.
   */
  public ClassesItemPanel(ClassesTableModel model)
  {
    init(model);
  }

  /**
   * This method initialises the panel and layout manager.
   */
  private void init(ClassesTableModel model)
  {
    // Setup the internal content pane to hold the user content pane
    // and the standard button panel

    JPanel internalContentPane = new JPanel();
    internalContentPane.setLayout(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
    internalContentPane.setBorder(BorderFactory.createEmptyBorder(
      COMPONENT_SPACING, COMPONENT_SPACING, COMPONENT_SPACING, COMPONENT_SPACING)
    );

    tableView = new JTable(model);
    JScrollPane scrollpane = new JScrollPane(tableView);
    //scrollpane.setPreferredSize(new Dimension(200, 100));
    internalContentPane.add(scrollpane, BorderLayout.CENTER);

    Action okAction = new AbstractAction("Lägg till...")
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        if(ClassInterface.showDialog() != null) {
          ;
        }
      }
    };
    Action cancelAction = new AbstractAction("Ta bort")
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
      }
    };
    Action moveUpAction = new AbstractAction("Flytta upp")
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
      }
    };
    Action moveDownAction = new AbstractAction("Flytta ner")
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
      }
    };

    JPanel buttonPanel = new JPanel(new GridLayout(4,1));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    buttonPanel.add(new JButton(okAction));
    buttonPanel.add(new JButton(cancelAction));
    buttonPanel.add(new JButton(moveUpAction));
    buttonPanel.add(new JButton(moveDownAction));
    JPanel tmp = new JPanel();
    tmp.add(buttonPanel, BorderLayout.NORTH);
    tmp.add(new JLabel(), BorderLayout.CENTER);
    internalContentPane.add(tmp, BorderLayout.EAST);

    super.add(internalContentPane);
  }
}