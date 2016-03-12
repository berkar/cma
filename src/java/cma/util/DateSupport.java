package cma.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class DateSupport
{
  // return revision

  /** Field DATEFORMAT */
  public static final String DATEFORMAT = "yyyy'-'MM'-'dd";
  /** Field DATETIMEFORMAT */
  public static final String DATETIMEFORMAT = "yyyyMMdd HH:mm";
  /** Field SHORTDATETIMEFORMAT */
  public static final String SHORTDATETIMEFORMAT = "yyMMdd HH:mm";
  /** Field TIMEFORMAT */
  public static final String TIMEFORMAT = "HH:mm:ss";
  /** Field TIMEFORMAT */
  public static final String LOGTIMEFORMAT = "yyMMdd HH:mm:ss";
  /** Field cal */
  Calendar cal = null;

  /**
   * Constructor DateSupport
   */
  public DateSupport()
  {
    cal = Calendar.getInstance();
    cal.clear();
    cal.setTime(new java.util.Date(DateSupport.currentTimeMillis()));
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getYear()
  {
    return Integer.toString(cal.get(Calendar.YEAR));
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getMonthNumber()
  {
    return Integer.toString(cal.get(Calendar.MONTH) + 1);
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getMonth()
  {
    String month = "";
    switch (cal.get(Calendar.MONTH)) {
      case 0:
        month = "jan";
        break;
      case 1:
        month = "feb";
        break;
      case 2:
        month = "mar";
        break;
      case 3:
        month = "apr";
        break;
      case 4:
        month = "maj";
        break;
      case 5:
        month = "jun";
        break;
      case 6:
        month = "jul";
        break;
      case 7:
        month = "aug";
        break;
      case 8:
        month = "sep";
        break;
      case 9:
        month = "okt";
        break;
      case 10:
        month = "nov";
        break;
      case 11:
        month = "dec";
        break;
      default :
        break;
    }
    return month;
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getDate()
  {
    return Integer.toString(cal.get(Calendar.DATE));
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getHour()
  {
    return Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
  }

  /**
   * Returns the
   *
   * @return Returns ...
   */
  public String getMinute()
  {
    return Integer.toString(cal.get(Calendar.MINUTE));
  }

  /**
   * @param            inDate
   * @param            formatString
   *
   * @return Returns ...
   */
  public static String formatDate(Date inDate, String formatString)
  {
    if (inDate == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(formatString);
    return sdf.format(inDate);
  }

  /**
   * @param            inDate
   *
   * @return Returns ...
   */
  public static String formatDate(Date inDate)
  {
    return formatDate(inDate, DATEFORMAT);
  }

  /**
   * Method formatDate
   *
   *
   * @return Returns ...
   *
   */
  public static String formatDate()
  {
    return formatDate(new Date(DateSupport.currentTimeMillis()), DATEFORMAT);
  }

  /**
   * @param            inDate
   * @param            formatString
   *
   * @return Returns ...
   */
  public static String formatTime(Date inDate, String formatString)
  {
    return formatDate(inDate, formatString);
  }

  /**
   * @param            inTime
   *
   * @return Returns ...
   */
  public static String formatTime(Date inTime)
  {
    return formatTime(inTime, TIMEFORMAT);
  }

  /**
   * Method formatTime
   *
   *
   * @return Returns ...
   *
   */
  public static String formatTime()
  {
    return formatTime(new Date(DateSupport.currentTimeMillis()), TIMEFORMAT);
  }

  /**
   * @param            date
   *
   * @return Returns ...
   */
  public static java.util.Date parseDate(String date)
  {
    if (date == null || date.trim().length() == 0) return null;

    return parseDate(date, DATEFORMAT);
  }

  /**
   * @param            date
   * @param            formatString
   *
   * @return Returns ...
   */
  public static java.util.Date parseDate(String date, String formatString)
  {
    if (date == null) return null;

    java.util.Date date1 = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(formatString);

      // parsed date
      date1 = sdf.parse(date);
      if (date1 != null) {
        date1 = DateSupport.floor(date1);
      }
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return date1;
  }

  /**
   * Adds days from a date
   * To subtract enter a negative number of days
   *
   * @param date The date to change
   * @param days The number of days to add
   *
   * @return The changed date
   */
  public static Date addDays(Date date, int days)
  {
    Date newDate = null;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, days);
    newDate = cal.getTime();
    return newDate;
  }

  /**
   * Get todays date
   *
   * @return Todays date
   *
   */
  public static Date today()
  {
    return new Date(DateSupport.currentTimeMillis());
  }

  /**
   * Chech if a string contains a valid date
   *
   * @param date A date string to check
   * @return True if the string is a valid date
   */
  public static boolean checkDate(String date)
  {
    return parseDate(date) != null;
  }

  /**
   * @param            time
   *
   * @return Returns ...
   */
  public static java.util.Date parseTime(String time)
  {
    if (time == null || time.trim().length() == 0) return null;

    return parseTime(time, TIMEFORMAT);
  }

  /**
   * @param            time
   * @param            formatString
   *
   * @return Returns ...
   */
  public static java.util.Date parseTime(String time, String formatString)
  {
    if (time == null) return null;

    java.util.Date d = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(formatString);
      d = sdf.parse(time);
    }
    catch (ParseException e) {
    }
    return d;
  }

  /**
   * Method fromSqlDate
   *
   *
   * @param date
   *
   * @return Returns ...
   *
   */
  public static java.util.Date fromSqlDate(java.sql.Date date)
  {
    if (date != null) {
      return new java.util.Date(date.getTime());
    }
    else {
      return null;
    }
  }

  /**
   * Method toSqlDate
   *
   *
   * @param date
   *
   * @return Returns ...
   *
   */
  public static java.sql.Date toSqlDate(java.util.Date date)
  {
    if (date != null) {
      return new java.sql.Date(date.getTime());
    }
    else {
      return null;
    }
  }

  /**
   * Get a date from a web form
   *
   * @param y Year
   * @param m Month
   * @param d Day
   *
   * @return A Date
   */
  public static java.util.Date getFormDate(String y, String m, String d)
  {
    java.util.Date ret = null;
    if ((y != null) && (m != null & d != null)) {
      ret = parseDate(y + "-" + m + "-" + d, "yyyy-MM-dd");
      if (ret != null) {
        ret = DateSupport.floor(ret);
      }
    }
    return ret;
  }

  /**
   * Get the system time with offset.<p>
   *
   * The system property <code>quickdeal.time.offset</code> will be
   * added to the <code>System.currentTimeMillis()</code> value
   *
   * @return Returns ...
   */
  public static long currentTimeMillis()
  {
    String qto = System.getProperty("quickdeal.time.offset");
    if ((qto != null) && (qto.length() > 0)) {
      return Long.parseLong(qto) + System.currentTimeMillis();
    }
    else {
      return System.currentTimeMillis();
    }
  }

  /**
   * Truncate (floor) a Date to zero hours, minutes, secs and millisecs.
   *
   * @param date A date to truncate
   *
   * @return A Date with truncated hours, minutes, secs and millisecs
   */
  public static Date floor(Date date)
  {
    if (date != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      c.set(Calendar.MILLISECOND, 0);
      return c.getTime();
    }
    else {
      return null;
    }
  }

  /**
   * Set (ceil) a Dates hours, minutes, secs and millisecs
   * to the last millisecond on the same day.
   *
   * @param date A date to modify
   *
   * @return A Date with modified hours, minutes, secs and millisecs
   */
  public static Date ceil(Date date)
  {
    if (date != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.set(Calendar.HOUR_OF_DAY, 23);
      c.set(Calendar.MINUTE, 59);
      c.set(Calendar.SECOND, 59);
      c.set(Calendar.MILLISECOND, 999);
      return c.getTime();
    }
    else {
      return null;
    }
  }
}
