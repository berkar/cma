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

public class ApplicationPropertiesVO
{
  private String browserPath;

  public ApplicationPropertiesVO()
  {
    browserPath = "";
  }

  public String getBrowserPath()
  {
    return browserPath;
  }

  public void setBrowserPath(String aClub)
  {
    browserPath = aClub;
  }

  public String toString()
  {
      return "{" + browserPath + ",...}";
  }

  public static Element describe(ApplicationPropertiesVO vo)
  {
    Element tmp = new Element("common");
    tmp.addContent( new Element("browserPath").addContent(vo.getBrowserPath()));
    return tmp;
  }

  public static ApplicationPropertiesVO parse(Element prop)
  {
    ApplicationPropertiesVO vo = new ApplicationPropertiesVO();
    vo.setBrowserPath(prop.getChildTextTrim("browserPath"));
    return vo;
  }
}
