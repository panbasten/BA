/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.format;

import java.util.List;


/**
 * @author av
 */
public class BooleanHandler extends FormatHandlerSupport {
  /**
   * formats a Boolean instance to "true" or "false"
   * @param o a Boolean instance, null will be rendered as "false"
   */
  public String format(Object o, String userPattern) {
    if (o == null) {
      return "false";
    }

    return ((Boolean) o).toString();
  }

  public Object parse(String s, String userPattern) {
    return new Boolean(s);
  }
  
  public boolean canHandle(Object value) {
    return value instanceof Boolean;
  }

  public Object toNativeArray(List list) {
    boolean[] array = new boolean[list.size()];
    for (int i = 0; i < array.length; i++)
      array[i] = ((Boolean)list.get(i)).booleanValue();
    return array;
  }
  
  public Object[] toObjectArray(Object value) {
  	if (value instanceof Boolean)
  	  return new Boolean[]{(Boolean)value};
  	boolean[] src = (boolean[])value;
    Boolean[] dst = new Boolean[src.length];
    for (int i = 0; i < src.length; i++)
      dst[i] = new Boolean(src[i]);
    return dst;
  }
  
}