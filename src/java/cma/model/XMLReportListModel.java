/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Dec 22, 2002
 * Time: 12:15:45 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.model;

import cma.vo.RegistrationVO;
import cma.vo.ReportVO;
import org.jdom.Element;

public class XMLReportListModel extends XMLListModel
{
  public XMLReportListModel(Element anElement)
  {
    super(anElement);
  }

  public Object getElementAt(int index)
  {
    ReportVO vo = ReportVO.parse((Element)super.getElementAt(index));
    return vo.getName();
  }
}
