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

import java.util.Date;

import cma.util.DateSupport;

public class CompetitionPropertiesVO
{
  private String club;
  private String name;
  private String place;
  private Date date;
  private long time;
  private long startInterval;
  private long classInterval;
  private int firstNumber;

  public CompetitionPropertiesVO()
  {
    club = "";
    name = "";
    place = "";
    date = new Date();
    time = 0;
    startInterval = 60;
    classInterval = 60;
    firstNumber = 100;
  }

  public String getClub()
  {
    return club;
  }

  public void setClub(String aClub)
  {
    club = aClub;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String aName)
  {
    name = aName;
  }

  public String getPlace()
  {
    return place;
  }

  public void setPlace(String aPlace)
  {
    place = aPlace;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date aDate)
  {
    date = aDate;
  }

  public long getTime()
  {
    return time;
  }

  public void setTime(long aTime)
  {
    time = aTime;
  }

  public long getStartInterval()
  {
    return startInterval;
  }

  public void setStartInterval(long aStartInterval)
  {
    startInterval = aStartInterval;
  }

  public long getClassInterval()
  {
    return classInterval;
  }

  public void setClassInterval(long aClassInterval)
  {
    classInterval = aClassInterval;
  }

  public int getFirstNumber()
  {
    return firstNumber;
  }

  public void setFirstNumber(int aFirstNumber)
  {
    firstNumber = aFirstNumber;
  }

  public String toString()
  {
      return "{" + club + "," + name + ",...}";
  }

  public static Element describe(CompetitionPropertiesVO vo)
  {
    Element tmp = new Element("common");
    tmp.addContent( new Element("club").addContent(vo.getClub()));
    tmp.addContent(new Element("name").addContent(vo.getName()));
    tmp.addContent(new Element("place").addContent(vo.getPlace()));
    tmp.addContent(new Element("date").addContent(((vo.getDate() != null) ? DateSupport.formatDate(vo.getDate()) : "")));
    tmp.addContent(new Element("time").addContent("" + vo.getTime()));
    tmp.addContent(new Element("startinterval").addContent("" + vo.getStartInterval()));
    tmp.addContent(new Element("classinterval").addContent("" + vo.getClassInterval()));
    tmp.addContent(new Element("firstnumber").addContent("" + vo.getFirstNumber()));

    return tmp;
  }

  public static CompetitionPropertiesVO parse(Element prop)
  {
    CompetitionPropertiesVO vo = new CompetitionPropertiesVO();
    vo.setClub(prop.getChildTextTrim("club"));
    vo.setName(prop.getChildTextTrim("name"));
    vo.setPlace(prop.getChildTextTrim("place"));
    vo.setDate(DateSupport.parseDate(prop.getChildTextTrim("date")));
    vo.setTime(Long.parseLong(prop.getChildTextTrim("time")));
    vo.setStartInterval(Long.parseLong(prop.getChildTextTrim("startinterval")));
    vo.setClassInterval(Long.parseLong(prop.getChildTextTrim("classinterval")));
    vo.setFirstNumber(new Integer(prop.getChildTextTrim("firstnumber")).intValue());
    return vo;
  }
}
