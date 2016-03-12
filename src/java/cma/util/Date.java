package cma.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date
{
  public static String dateFormat = "yyyy-MM-dd";
  public static String dateFormatExt = "yyyy-MM-dd HH:mm";

  private static DateFormat getInstance(String format)
  {
    return new SimpleDateFormat(format);
  }

  public static String date2String(java.util.Date date)
  {
    return getInstance(dateFormat).format(date);
  }

  public static String dateExt2String(java.util.Date date)
  {
    return getInstance(dateFormatExt).format(date);
  }

  public static java.util.Date string2Date(String date) throws ParseException
  {
    SimpleDateFormat sdf;
    java.text.ParsePosition pos;

    sdf = (SimpleDateFormat) getInstance(dateFormat);
    pos = new java.text.ParsePosition(0);
    sdf.setLenient(false);

    return sdf.parse(date, pos);
  }

  public static java.util.Date string2DateExt(String date) throws ParseException
  {
    SimpleDateFormat sdf;
    java.text.ParsePosition pos;

    sdf = (SimpleDateFormat) getInstance(dateFormatExt);
    pos = new java.text.ParsePosition(0);
    sdf.setLenient(false);

    return sdf.parse(date, pos);
  }

  public static String toDatePattern()
  {
    return ((SimpleDateFormat) getInstance(dateFormat)).toPattern();
  }

  public static String toDateExtPattern()
  {
    return ((SimpleDateFormat) getInstance(dateFormatExt)).toPattern();
  }

  /**
   *    Validates the users inputted date value
   *
   * @param theIncomingDate   The Date in String form
   * @param locale   Check dateFormat according to this locale
   * @return true if user inputted corect dateFormat, otherwise false
   */
  public static boolean validateDateFormat(String theIncomingDate)
  {
    java.util.Date dbDate;

    dbDate = null;
    try {
      if (theIncomingDate == null || theIncomingDate.equals(""))
        return false;

      dbDate = string2Date(theIncomingDate);

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

  /**
   *    Validates the users inputted date value
   *
   * @param theIncomingDate   The Date in String form
   * @param locale   Check dateFormat according to this locale
   * @return true if user inputted corect dateFormat, otherwise false
   */
  public static boolean validateDateExtFormat(String theIncomingDate)
  {
    java.util.Date dbDate;

    dbDate = null;
    try {
      if (theIncomingDate == null || theIncomingDate.equals(""))
        return false;

      dbDate = string2DateExt(theIncomingDate);

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

  /**
   *    Converts the users inputted date value to java.sql.Date
   *
   * @param theIncomingDate   The Date
   * @return The converted date in java.sql.date dateFormat
   */
/*
public static java.sql.Date convertDate(String theIncomingDate, Locale locale)
{
  SimpleDateFormat df;
  java.cma.util.Date dbDate = null;
  java.sql.Date sqldate = null;
  try {

    if (theIncomingDate == null ||
        theIncomingDate.equals (""))
      return null;

    df = (SimpleDateFormat) DateFormat.getDateInstance(
      dateFormat,
      locale
    );

    SimpleDateFormat sdf = new SimpleDateFormat(df.toPattern());
    java.text.ParsePosition pos = new java.text.ParsePosition(0);
    sdf.setLenient(false);
    dbDate = sdf.parse(theIncomingDate, pos);
    sqldate = new java.sql.Date(dbDate.getTime());

    if (dbDate == null)
      return null;
    else
    {
      if  (dbDate.getTime () <= 0)
        return null;
      else
        return sqldate;
    }
  } catch (Exception e) {
    return null;
  }
}
  */

  /**
   *	Converts the date to the database specific dateformat.
   *
   * @param theIncomingDate The Date
   * @return The converted date in String, null if something bad occured
   */
  /*
public static String dbDate(java.sql.Date theIncomingDate)
{
  if (theIncomingDate == null)
    return null;
  java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy'-'MM'-'dd");
  return "to_date('" + sdf2.format(theIncomingDate) +
    "','YYYY-MM-DD')";
}
  */

  public static void main(String[] args)
  {
    try {
      String cmd = args[0];
      if ("validate".equalsIgnoreCase(cmd)) {
        System.out.print("Validating date: " + args[1] + " => ");
        if (Date.validateDateFormat(args[1]) == true) {
          System.out.println("Valid!");
          System.out.println("string2Date: " + Date.string2Date(args[1]));
          System.out.println("date2long: " + Date.string2Date(args[1]).getTime());
          System.out.println("date2String: " + Date.date2String(Date.string2Date(args[1])));
        }
        else {
          System.out.println("NOT Valid!");
          System.out.println("Use pattern: " + Date.toDatePattern());
        }

        System.out.println();
        System.out.print("Validating ext date: " + args[1] + " => ");
        if (Date.validateDateExtFormat(args[1]) == true) {
          System.out.println("Valid!");
          System.out.println("string2Date: " + Date.string2DateExt(args[1]));
          System.out.println("date2long: " + Date.string2DateExt(args[1]).getTime());
          System.out.println("date2String: " + Date.dateExt2String(Date.string2DateExt(args[1])));
        }
        else {
          System.out.println("NOT Valid!");
          System.out.println("Use pattern: " + Date.toDateExtPattern());
        }
      }
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
  }
}

