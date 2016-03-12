/*
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: 15-Jun-02
 * Time: 19:33:03
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package cma.vo;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RegistrationVO
{

    private long startNumber;

    private String club;
    private String name;
    private String address;
    private String postAddress;
    private String phone;
    private String mail;
    private String dogs;
    private String did;

    private boolean paymentReceived;

    private boolean starting = true;
    private long starttime;
    private long time1;
    private long time2;
    private long time3;
    private long totaltime;
    private long diff;
    private long position;

    private boolean abandon = false;
    private boolean warning = false;
    private boolean disqualified = false;

    private String note;

    public long getStartNumber()
    {
        return startNumber;
    }

    public void setStartNumber(long startNumber)
    {
        this.startNumber = startNumber;
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPostAddress()
    {
        return postAddress;
    }

    public void setPostAddress(String postAddress)
    {
        this.postAddress = postAddress;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getDogs()
    {
        return dogs;
    }

    public void setDogs(String aDogs)
    {
        dogs = aDogs;
    }

    public String getDid()
    {
        return did;
    }

    public void setDid(String did)
    {
        this.did = did;
    }

    public boolean isPaymentReceived()
    {
        return paymentReceived;
    }

    public void setPaymentReceived(boolean paymentReceived)
    {
        this.paymentReceived = paymentReceived;
    }

    public boolean isStarting()
    {
        return starting;
    }

    public void setStarting(boolean starting)
    {
        this.starting = starting;
    }

    public long getStarttime()
    {
        return starttime;
    }

    public void setStarttime(long starttime)
    {
        this.starttime = starttime;
    }

    public long getTime1()
    {
        return time1;
    }

    public void setTime1(long time1)
    {
        this.time1 = time1;
    }

    public long getTime2()
    {
        return time2;
    }

    public void setTime2(long time2)
    {
        this.time2 = time2;
    }

    public long getTime3()
    {
        return time3;
    }

    public void setTime3(long time3)
    {
        this.time3 = time3;
    }

    public long getTotaltime()
    {
        return totaltime;
    }

    public void setTotaltime(long totaltime)
    {
        this.totaltime = totaltime;
    }

    public long getDiff()
    {
        return diff;
    }

    public void setDiff(long diff)
    {
        this.diff = diff;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public boolean isAbandon()
    {
        return abandon;
    }

    public void setAbandon(boolean abandon)
    {
        this.abandon = abandon;
    }

    public boolean isWarning()
    {
        return warning;
    }

    public void setWarning(boolean warning)
    {
        this.warning = warning;
    }

    public boolean isDisqualified()
    {
        return disqualified;
    }

    public void setDisqualified(boolean disqualified)
    {
        this.disqualified = disqualified;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String toString()
    {
        return club + ", " + name + ", " + dogs;
    }

    public String describe()
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Element e = RegistrationVO.describe(this);
        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        try {
            output.output(e, os);
            return os.toString();
        }
        catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return super.toString();
        }
    }

    /**
     */
    public static Element describe(RegistrationVO registration)
    {
        Element tmp = new Element("registration");
        if (registration.getStartNumber() > 0) {
            tmp.addContent(new Element("startnumber").addContent(Long.toString(registration.getStartNumber())));
        }
        tmp.addContent(new Element("club").addContent(registration.getClub()));
        tmp.addContent(new Element("name").addContent(registration.getName()));
        tmp.addContent(new Element("address").addContent(registration.getAddress()));
        tmp.addContent(new Element("post-address").addContent(registration.getPostAddress()));
        tmp.addContent(new Element("phone").addContent(registration.getPhone()));
        tmp.addContent(new Element("mail").addContent(registration.getMail()));
        tmp.addContent(new Element("dogs").addContent(registration.getDogs()));
        tmp.addContent(new Element("did").addContent(registration.getDid()));
        tmp.addContent(new Element("payment").addContent(new Boolean(registration.isPaymentReceived()).toString()));
        tmp.addContent(new Element("starting").addContent(new Boolean(registration.isStarting()).toString()));
        tmp.addContent(new Element("abandon").addContent(new Boolean(registration.isAbandon()).toString()));
        tmp.addContent(new Element("warning").addContent(new Boolean(registration.isWarning()).toString()));
        tmp.addContent(new Element("disqualified").addContent(new Boolean(registration.isDisqualified()).toString()));
        if (registration.getStarttime() > 0)
            tmp.addContent(new Element("starttime").addContent(Long.toString(registration.getStarttime())));
        if (registration.getTime1() > 0)
            tmp.addContent(new Element("time1").addContent(Long.toString(registration.getTime1())));
        if (registration.getTime2() > 0)
            tmp.addContent(new Element("time2").addContent(Long.toString(registration.getTime2())));
        if (registration.getTime3() > 0)
            tmp.addContent(new Element("time3").addContent(Long.toString(registration.getTime3())));
        if (registration.getTotaltime() > 0)
            tmp.addContent(new Element("totaltime").addContent(Long.toString(registration.getTotaltime())));
        if (registration.getDiff() > 0)
            tmp.addContent(new Element("difftime").addContent(Long.toString(registration.getDiff())));
        if (registration.getPosition() > 0)
            tmp.addContent(new Element("position").addContent(Long.toString(registration.getPosition())));
        tmp.addContent(new Element("note").addContent(registration.getNote()));

        return tmp;
    }

    public static RegistrationVO parse(String registration)
    {
        InputStream is = new ByteArrayInputStream(registration.getBytes());
        SAXBuilder builder = new SAXBuilder(false);

        try {
            Document doc = builder.build(is);
            return RegistrationVO.parse(doc.getRootElement());
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return null;
        }
    }

    public static RegistrationVO parse(Element registration)
    {
        RegistrationVO vo = new RegistrationVO();
        String tmp = registration.getChildTextTrim("startnumber");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setStartNumber(Long.parseLong(tmp));
        }
        vo.setClub(registration.getChildTextTrim("club"));
        vo.setName(registration.getChildTextTrim("name"));
        vo.setAddress(registration.getChildTextTrim("address"));
        vo.setPostAddress(registration.getChildTextTrim("post-address"));
        vo.setPhone(registration.getChildTextTrim("phone"));
        vo.setMail(registration.getChildTextTrim("mail"));
        vo.setDogs(registration.getChildTextTrim("dogs"));
        vo.setDid(registration.getChildTextTrim("did"));
        vo.setPaymentReceived(Boolean.valueOf(registration.getChildTextTrim("payment")).booleanValue());
        vo.setStarting(Boolean.valueOf(registration.getChildTextTrim("starting")).booleanValue());
        vo.setAbandon(Boolean.valueOf(registration.getChildTextTrim("abandon")).booleanValue());
        vo.setWarning(Boolean.valueOf(registration.getChildTextTrim("warning")).booleanValue());
        vo.setDisqualified(Boolean.valueOf(registration.getChildTextTrim("disqualified")).booleanValue());
        tmp = registration.getChildTextTrim("starttime");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setStarttime(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("time1");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setTime1(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("time2");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setTime2(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("time3");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setTime3(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("totaltime");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setTotaltime(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("difftime");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setDiff(Long.parseLong(tmp));
        }
        tmp = registration.getChildTextTrim("position");
        if (tmp != null && tmp.trim().length() > 0) {
            vo.setPosition(Long.parseLong(tmp));
        }
        vo.setNote(registration.getChildTextTrim("note"));
        return vo;
    }
}
