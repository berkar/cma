/*
  ----------------------------------------------------------------
  CmIdentification

  $RCSfile: Charset.java,v $
  $Revision: 1.1 $
  $Date: 2004/01/10 21:18:12 $
  $State: Exp $
  ----------------------------------------------------------------
*/

/*
  ----------------------------------------------------------------
  Copyright ) 2001 Frontec Norr AB (Work in progress)
  All rights reserved.

  THIS IS AN UNPUBLISHED WORK CONTAINING FRONTEC NORR AB
  CONFIDENTIAL AND PROPRIETARY INFORMATION. DISCLOSURE,USE OR
  REPRODUCTION WITHOUT AUTHORIZATION OF FRONTEC NORR AB
  IS PROHIBITED.

  This software is copyrighted. The software may not be copied,
  reproduced, translated or reduced to any electronic medium or
  machine-readable form without the prior written consent of
  Frontec Norr AB except that you may make one copy of the
  program disks solely for back-up purposes.
  ----------------------------------------------------------------
*/

package cma.util;

import java.util.Locale;

public class Charset
{
  private static final String charset[][] = {
    {"ar", "ISO-8859-6"}, {"be", "ISO-8859-5"},
    {"bg", "ISO-8859-5"}, {"ca", "ISO-8859-1"},
    {"cs", "ISO-8859-2"}, {"da", "ISO-8859-1"},
    {"de", "ISO-8859-1"}, {"el", "ISO-8859-7"},
    {"en", "ISO-8859-1"}, {"es", "ISO-8859-1"},
    {"et", "ISO-8859-1"}, {"fi", "ISO-8859-1"},
    {"fr", "ISO-8859-1"}, {"hr", "ISO-8859-2"},
    {"hu", "ISO-8859-2"}, {"is", "ISO-8859-1"},
    {"it", "ISO-8859-1"}, {"iw", "ISO-8859-8"},
    {"ja", "Shift_JIS"}, {"ko", "EUC-KR"},
    {"lt", "ISO-8859-2"}, {"lv", "ISO-8859-2"},
    {"mk", "ISO-8859-5"}, {"nl", "ISO-8859-1"},
    {"no", "ISO-8859-1"}, {"pl", "ISO-8859-2"},
    {"pt", "ISO-8859-1"}, {"ro", "ISO-8859-2"},
    {"ru", "ISO-8859-5"}, {"sh", "ISO-8859-5"},
    {"sk", "ISO-8859-2"}, {"sl", "ISO-8859-2"},
    {"sq", "ISO-8859-2"}, {"sr", "ISO-8859-5"},
    {"sv", "ISO-8859-1"}, {"tr", "ISO-8859-9"},
    {"uk", "ISO-8859-5"}, {"zh", "RB2312"},
    {"zh_TW", "Big5"}
  };

  private static Locale locales[] = {
    new Locale("en", "GB"),
    new Locale("en", "US"),
    new Locale("sv", "SE"),
    new Locale("fr", "FR"),
    new Locale("fi", "FI"),
    new Locale("no", "NO"),
    new Locale("da", "DK"),
    new Locale("pl", "PL")
  };


  /**
   * Find appropriate character encoding in iso-code for your language.
   * If the language were not found default charset "ISO-8859-1" is
   * returned. If langCntr is null or <2 default lang is returned.
   *
   * @param  langCntr - language expressed in "en" or "en_GB"
   * @return String - iso-code for searched language.
   */
  public static String getIso(String langCntr)
  {
    String lang, isocode = "ISO-8859-1";

    try {
      if (langCntr == null || langCntr.length() < 2)
        return isocode;

      lang = langCntr;
      for (int i = 0; i < charset.length; i++)
        if (lang.equalsIgnoreCase(charset[i][0])) {
          return charset[i][1];
        }

      lang = langCntr.substring(0, 2);
      for (int i = 0; i < charset.length; i++)
        if (lang.equalsIgnoreCase(charset[i][0])) {
          return charset[i][1];
        }
    }
    catch (IndexOutOfBoundsException e) {
      ;
    }
    return isocode;
  }


  /**
   * Get supported Locales.
   *
   * @return Locale[] - array of supported locales.
   */
  public static Locale[] suppLocales()
  {
    return locales;
  }

  final static String CVSID = "$Id";
}
