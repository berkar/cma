package cma.vo;

/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 19, 2003
 * Time: 12:01:03 AM
 * To change this template use Options | File Templates.
 */
public class ImportRegistrationVO
{
    private String separator;
    private String fileName;

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toString()
    {
        return "{file=" + fileName + ", separator=" + separator + "}";
    }
}

