/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

public class ClubVO
{
    private String shortName;
    private String name;

    public ClubVO()
    {
        shortName = "";
        name = "";
    }

    public ClubVO(String shortName, String name)
    {
        this.shortName = shortName;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String toString()
    {
        return "{" + shortName + "," + name + "}";
    }
}
