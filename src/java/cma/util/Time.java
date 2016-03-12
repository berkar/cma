package cma.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Time
{
    public static String timeFormat = "HH:mm:ss";
    public static String timeFormatExt = "HH:mm:ss.SSS";

    private static DateFormat getInstance(String timeFormat)
    {
        return new SimpleDateFormat(timeFormat);
    }

    public static String interval2StringXX(long interval)
    {
        if (interval < 0) return null;

        long tmp = interval;
        StringBuffer sb = new StringBuffer();

        long h = tmp / 3600000;
        tmp -= (h * 3600000);
        if (h < 10) sb.append("0");
        sb.append(h);
        sb.append(":");

        long m = tmp / 60000;
        tmp -= (m * 60000);
        if (m < 10) sb.append("0");
        sb.append(m);
        sb.append(":");

        long s = tmp / 1000;
        tmp -= (s * 1000);
        if (s < 10) sb.append("0");
        sb.append(s);

/*  Milliseconds
    sb.append(".");
    int ms = tmp;
    if(ms < 100) sb.append("0");
    if(ms < 10) sb.append("0");
    sb.append(ms);
*/

        return sb.toString();
    }

    public static String interval2String(long interval)
    {
        if (interval < 0) return null;
        java.util.Date tmp = new Date(interval - TimeZone.getDefault().getRawOffset());
        return time2String(tmp);
    }

    /**
     * Validation of format has to be done with {@link #validateTimeFormat}
     * @param interval
     * @return interval calculated or -1 if errors
     */
    public static long string2Interval(String interval)
    {
        if (interval == null || interval.trim().length() == 0) return -1;
        try {
            Date tmp = string2Time(interval);
            return tmp.getTime() + TimeZone.getDefault().getRawOffset();
        }
        catch (ParseException e) {
            return -1;
        }
    }

    public static String time2String(java.util.Date date)
    {
        if (date == null) return null;
        return getInstance(timeFormat).format(date);
    }

    public static String timeExt2String(java.util.Date date)
    {
        return getInstance(timeFormatExt).format(date);
    }

    public static java.util.Date string2Time(String time) throws ParseException
    {
        if (time == null || time.trim().length() == 0) return null;

        SimpleDateFormat sdf;
        java.text.ParsePosition pos;

        sdf = (SimpleDateFormat) getInstance(timeFormat);
        pos = new java.text.ParsePosition(0);
        sdf.setLenient(false);

        return sdf.parse(time, pos);
    }

    public static java.util.Date string2TimeExt(String time) throws ParseException
    {
        SimpleDateFormat sdf;
        java.text.ParsePosition pos;

        sdf = (SimpleDateFormat) getInstance(timeFormatExt);
        pos = new java.text.ParsePosition(0);
        sdf.setLenient(false);

        return sdf.parse(time, pos);
    }

    public static String toTimePattern()
    {
        return ((SimpleDateFormat) getInstance(timeFormat)).toPattern();
    }

    public static String toTimeExtPattern()
    {
        return ((SimpleDateFormat) getInstance(timeFormatExt)).toPattern();
    }

    /**
     *    Validates the users inputted date value
     *
     * @param theIncomingDate   The Date in String form
     * @param locale   Check dateFormat according to this locale
     * @return true if user inputted corect dateFormat, otherwise false
     */
    public static boolean validateTimeFormat(String theIncomingDate)
    {
        SimpleDateFormat df;
        SimpleDateFormat sdf;
        java.text.ParsePosition pos;
        java.util.Date dbDate;

        dbDate = null;
        try {
            if (theIncomingDate == null || theIncomingDate.equals(""))
                return false;

            df = (SimpleDateFormat) getInstance(timeFormat);

            sdf = new SimpleDateFormat(df.toPattern());
            pos = new java.text.ParsePosition(0);
            sdf.setLenient(false);
            dbDate = sdf.parse(theIncomingDate, pos);

            if (dbDate == null)
                return false;
            else
                return true;
        }
        catch (Exception e) {
            System.out.println("Getting Exception" +
                               " [ " + e + "]");
            return false;
        }
    }

    public static boolean validateTimeExtFormat(String theIncomingDate)
    {
        SimpleDateFormat df;
        SimpleDateFormat sdf;
        java.text.ParsePosition pos;
        java.util.Date dbDate;

        dbDate = null;
        try {
            if (theIncomingDate == null || theIncomingDate.equals(""))
                return false;

            df = (SimpleDateFormat) getInstance(timeFormatExt);

            sdf = new SimpleDateFormat(df.toPattern());
            pos = new java.text.ParsePosition(0);
            sdf.setLenient(false);
            dbDate = sdf.parse(theIncomingDate, pos);

            if (dbDate == null)
                return false;
            else {
                if (dbDate.getTime() <= 0)
                    return false;
                else
                    return true;
            }
        }
        catch (Exception e) {
            System.out.println("Getting Exception" +
                               " [ " + e + "]");
            return false;
        }
    }

    public static void main(String[] args)
    {
        try {
            String cmd = args[0];
            if ("validate".equalsIgnoreCase(cmd)) {
                System.out.print("Validating time: " + args[1] + " => ");
                if (Time.validateTimeFormat(args[1]) == true) {
                    System.out.println("Valid!");
                    System.out.println("string2Time: " + Time.string2Time(args[1]));
                    System.out.println("time2long: " + Time.string2Time(args[1]).getTime());
                    System.out.println("time2String: " + Time.time2String(Time.string2Time(args[1])));
                }
                else {
                    System.out.println("NOT Valid!");
                    System.out.println("Use pattern: " + Time.toTimePattern());
                }

                System.out.println();
                System.out.print("Validating ext time: " + args[1] + " => ");
                if (Time.validateTimeExtFormat(args[1]) == true) {
                    System.out.println("Valid!");
                    System.out.println("string2TimeExt: " + Time.string2TimeExt(args[1]));
                    System.out.println("time2long: " + Time.string2TimeExt(args[1]).getTime());
                    System.out.println("timeExt2String: " + Time.timeExt2String(Time.string2TimeExt(args[1])));
                }
                else {
                    System.out.println("NOT Valid!");
                    System.out.println("Use pattern: " + Time.toTimeExtPattern());
                }
            }
            else if ("interval".equalsIgnoreCase(cmd)) {
                System.out.println("interval2String: " + Time.interval2String(3600000));
                System.out.println("string2Interval: " + Time.string2Interval("01:00:00"));
            }
            else if ("timezone".equalsIgnoreCase(cmd)) {
                String[] tmp = TimeZone.getAvailableIDs(0);
                for (int i = 0; i < tmp.length; i++) {
                    String s = tmp[i];
                    System.out.println(s);
                }
            }
            else if ("test".equalsIgnoreCase(cmd)) {
                TimeZone tz0 = TimeZone.getTimeZone("GMT");
                System.out.println("Offset: " + tz0.getRawOffset());

                TimeZone tz1 = TimeZone.getDefault();
                System.out.println("Offset: " + tz1.getRawOffset());

                Calendar c = Calendar.getInstance(tz0);
                c.setTime(new java.util.Date(1));

                System.out.println("TZ0: " + c.getTime());
                //System.out.println("TZ0: " + c.toString());
                System.out.println("TZ0: " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND));

                c.setTimeZone(tz1);
                System.out.println("TZ1: " + c.getTime());
                //System.out.println("TZ1: " + c.toString());
                System.out.println("TZ1: " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

