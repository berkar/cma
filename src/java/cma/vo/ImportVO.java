package cma.vo;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 19, 2003
 * Time: 12:01:03 AM
 * To change this template use Options | File Templates.
 */
public class ImportVO
{
    private String timeType;
    private String position;
    private String fileName;

    public String getTimeType()
    {
        return timeType;
    }

    public void setTimeType(String timeType)
    {
        this.timeType = timeType;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String toString()
    {
        return "{" + timeType + ", " + position + ", " + fileName + "}";
    }
}

