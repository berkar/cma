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
import org.jdom.Element;

public class XMLRegistrationListModel extends XMLListModel
{
  public XMLRegistrationListModel(Element anElement)
  {
    super(anElement);
  }

  public Object getElementAt(int index)
  {
    RegistrationVO vo = RegistrationVO.parse((Element)super.getElementAt(index));
    return vo.toString();
  }
}
