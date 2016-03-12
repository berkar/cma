/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

import org.jdom.Element;

public class ClassVO
{
    private String name;
    private double distance;

    public ClassVO()
    {
        name = "";
        distance = 0.0;
    }

    public ClassVO(String name, double distance)
    {
        this.name = name;
        this.distance = distance;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public String toString()
    {
        return name + ", " + distance + "km";
    }

    public static Element describe(ClassVO vo)
    {
        Element tmp = new Element("class");
        tmp.setAttribute("name", vo.getName());
        tmp.setAttribute("distance", Double.toString(vo.getDistance()));
        return tmp;
    }

    public static ClassVO parse(Element clazz)
    {
        ClassVO vo = new ClassVO();
        vo.setName(clazz.getAttributeValue("name"));
        vo.setDistance(Double.parseDouble(clazz.getAttributeValue("distance")));
        return vo;
    }
}
