/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Dec 21, 2002
 * Time: 10:16:21 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.model;

import org.jdom.Element;
import org.jdom.Attribute;

import javax.swing.*;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;

public class XMLListModel extends AbstractListModel
{
  private String childName;
  private Element root;

  public XMLListModel(Element aRootElement)
  {
    root = aRootElement;
  }

  public XMLListModel(Element aRootElement, String childName)
  {
    root = aRootElement;
    this.childName = childName;
  }

  private List getChildren()
  {
    if(childName != null && childName.trim().length() > 0) {
      return root.getChildren(childName);
    }
    return root.getChildren();
  }

  public int getSize()
  {
    return getChildren().size();
  }

  public Object getElementAt(int index)
  {
    return getChildren().get(index);
  }

  public void setElementAt(Element newElement, int index)
  {
    Collection buffer = new ArrayList();
    Element oldElement = (Element)getChildren().get(index);
    // Add all element of old element to temp buffer
    List list = oldElement.getChildren();
    Iterator i = list.iterator();
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      buffer.add(tmp);
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

  public void moveElement(int fromIndex, int toIndex)
  {
    Element e = elementAt(fromIndex);
    // Detach element to move
    e.detach();

    Collection buffer = new ArrayList();
    List list = getChildren();
    Iterator i = list.iterator();
    // Add all other elements to temp buffer
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      buffer.add(tmp);
    }

    // Detach all other elements
    i = buffer.iterator();
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      tmp.detach();
    }

    // Add all buffered elements and take care of placing requested element correct
    int count = 0;
    i = buffer.iterator();
    while (i.hasNext()) {
      Element tmp = (Element) i.next();
      if(count == toIndex) {
        root.addContent(e);
        e = null;
        count++;
      }
      root.addContent(tmp);
      count++;
    }
    if(e != null) {
      root.addContent(e);
    }
    fireContentsChanged(this, 0, getSize());
  }

  public Element elementAt(int index)
  {
    return (Element)getChildren().get(index);
  }

  public void removeElementAt(int index)
  {
    ((Element)getChildren().get(index)).detach();
    fireIntervalRemoved(this, index, index);
  }

  public void addElement(Element element)
  {
    int index = getSize();
    root.addContent(element);
    fireIntervalAdded(this, index, index);
  }

  public void sort(String sortElementName)
  {
  }
}
