package cma;

/**
 * An adaptor, transforming an XML source to the TableModel interface.
 *
 * @version
 * @author Bertil Karlsson
 */

import javax.swing.table.AbstractTableModel;
import cma.vo.ClassVO;

public class ClassesTableModel extends AbstractTableModel
{
  private String[] columns;
  private ClassVO[] classes;

  public ClassesTableModel(String[] columns, ClassVO[] classes)
  {
    this.columns = columns;
    this.classes = classes;
  }

  public int getRowCount()
  {
    return (classes == null) ? 0 : classes.length;
  }

  public int getColumnCount()
  {
    return (columns == null) ? 0 : columns.length;
  }

  public Object getValueAt(int rowIndex, int columnIndex)
  {
    if(rowIndex >= getRowCount()) return null;
    if(columnIndex >= getColumnCount()) return null;

    ClassVO result = classes[rowIndex];
    if(columnIndex == 0) {
      return result.getName();
    }
    else {
      return new Double(result.getDistance());
    }
  }

  // The default implementations of these methods in
  // AbstractTableModel would work, but we can refine them.
  public String getColumnName(int column)
  {
      return columns[column];
  }

  public Class getColumnClass(int col)
  {
      return getValueAt(0, col).getClass();
  }

  public boolean isCellEditable(int row, int col)
  {
      return true;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex)
  {
    if(rowIndex >= getRowCount()) return;
    if(columnIndex >= getColumnCount()) return;

    ClassVO result = classes[rowIndex];
    if(columnIndex == 0) {
      if(aValue instanceof String) {
        result.setName((String)aValue);
      }
    }
    else {
      if(aValue instanceof Double) {
        result.setDistance(((Double)aValue).doubleValue());
      }
    }
  }
}
