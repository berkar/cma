/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Dec 22, 2002
 * Time: 12:15:45 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.model;

import org.jdom.Element;
import org.jdom.Attribute;
import cma.vo.ClassVO;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class XMLClassesListModel extends XMLListModel
{
  public XMLClassesListModel(Element anElement)
  {
    super(anElement);
  }

  public Object getElementAt(int index)
  {
    Element clazz = (Element) super.getElementAt(index);
    List list = clazz.getChildren("registration");
    ClassVO vo = ClassVO.parse(clazz);
    return vo.toString() + " <" + list.size() + "st.>";
  }

  public void setElementAt(Element newElement, int index)
  {
    Collection buffer = new ArrayList();
    Element oldElement = (Element)super.getElementAt(index);
    // Add all element of old element to temp buffer
    List list = oldElement.getChildren();
    Iterator i = list.iterator();
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      // Leave registrations where they are
      if("registration".equals(tmp.getName()) == false) {
        buffer.add(tmp);
      }
    }
    // Add all attributes of old element to temp buffer
    list = oldElement.getAttributes();
    i = list.iterator();
    while (i.hasNext()) {
      Attribute tmp = (Attribute) i.next();
      buffer.add(tmp);
    }
    // Remove all children/attributes of old element,
    // but use temp buffer to handle ConcurrentException that otherwise occurs
    i = buffer.iterator();
    while (i.hasNext()) {
      Object obj = i.next();
      if(obj instanceof Element)
        ((Element)obj).detach();
      else if(obj instanceof Attribute)
        ((Attribute)obj).detach();
    }
    // Add all element of new element to temp buffer
    buffer.clear();
    list = newElement.getChildren();
    i = list.iterator();
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      buffer.add(tmp);
    }
    // Add all attributes of new element to temp buffer
    list = newElement.getAttributes();
    i = list.iterator();
    while (i.hasNext()) {
      Attribute tmp = (Attribute) i.next();
      buffer.add(tmp);
    }
    // Remove all children/attributes of new element,
    // but use temp buffer to handle ConcurrentException that otherwise occurs
    i = buffer.iterator();
    while (i.hasNext()) {
      Object obj = i.next();
      if(obj instanceof Element)
        ((Element)obj).detach();
      else if(obj instanceof Attribute)
        ((Attribute)obj).detach();
    }
    // Add all children of new element to old element
    i = buffer.iterator();
    while (i.hasNext()) {
      Object obj = i.next();
      if(obj instanceof Element)
        oldElement.addContent((Element)obj);
      else if(obj instanceof Attribute)
        oldElement.setAttribute((Attribute)obj);
    }
    fireContentsChanged(this, index, index);
  }
}
