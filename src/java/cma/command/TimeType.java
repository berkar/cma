/**
 * Created by IntelliJ IDEA.
 * User: beka
 * Date: Jan 19, 2003
 * Time: 5:07:13 PM
 * To change this template use Options | File Templates.
 */
package cma.command;

public class TimeType
{
    private String id;

    public static final TimeType ABSOLUTE_TIME = new TimeType("Absolut tid");
    public static final TimeType CORRECTED_TIME = new TimeType("Korrigerad tid");
    public static final TimeType RELATIVE_TIME = new TimeType("Relativ tid");

    public TimeType(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof TimeType == false) return false;

        return getId().equals(((TimeType) obj).getId());
    }

    public static TimeType[] getTypes()
    {
        return new TimeType[]{
            CORRECTED_TIME,
            ABSOLUTE_TIME,
            RELATIVE_TIME
        };
    }

    public static TimeType findById(String id)
    {
        if (ABSOLUTE_TIME.getId().equals(id)) {
            return ABSOLUTE_TIME;
        }
        if (CORRECTED_TIME.getId().equals(id)) {
            return CORRECTED_TIME;
        }
        if (RELATIVE_TIME.getId().equals(id)) {
            return RELATIVE_TIME;
        }
        return null;
    }
}
