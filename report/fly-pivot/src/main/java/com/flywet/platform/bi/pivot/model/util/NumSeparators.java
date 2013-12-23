package com.flywet.platform.bi.pivot.model.util;

import java.util.Locale;

/**
 * Singleton
 * Separators are needed for "inverse" cell formatting
 * we assume, that the locale almost never changes
 */
public class NumSeparators {
  public int thouSep; // Thousand separator
  public int decimalSep; // decimal separator
  private Locale theLocale;
  static private NumSeparators theInstance = null;

  private NumSeparators(Locale locale) {
    theLocale = locale;
    thouSep = ',';
    decimalSep = '.';
    if (locale == null)
      return;
    if (locale.getLanguage() == "de" || locale.getCountry() == "DE") {
      decimalSep = ',';
      thouSep = '.';
    } else if (locale.getLanguage() == "hu" || locale.getCountry() == "HU") {
      decimalSep = ',';
      thouSep = ' ';
    }
  }

  static public NumSeparators instance(Locale locale) {
    // probably it is always the same locale
    if (theInstance == null)
      return new NumSeparators(locale);
    if (theInstance.theLocale == locale)
      return theInstance;
    if (locale != null && locale.equals(theInstance.theLocale)) { return theInstance; }
    // this should not occur too often, as there should be only one locale
    return new NumSeparators(locale);
  }
} // NumSeparators
