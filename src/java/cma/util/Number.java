package cma.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Number
{
  public static String number2String(double value, Locale locale)
  {
    NumberFormat nf = NumberFormat.getNumberInstance(
      locale
    );
    return nf.format(new Double(value));
  }

  public static String currency2String(double value, Locale locale)
  {
    NumberFormat nf = NumberFormat.getCurrencyInstance(
      locale
    );
    return nf.format(new Double(value));
  }

  public static java.lang.Number string2Number(String value, Locale locale)
    throws ParseException
  {
    NumberFormat nf = NumberFormat.getNumberInstance(
      locale
    );
    return nf.parse(value);
  }

  public static java.lang.Number string2Currency(String value, Locale locale)
    throws ParseException
  {
    NumberFormat nf = NumberFormat.getNumberInstance(
      locale
    );
    return nf.parse(value);
  }
}

