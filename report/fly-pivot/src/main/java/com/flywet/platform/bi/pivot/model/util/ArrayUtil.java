package com.flywet.platform.bi.pivot.model.util;

import java.lang.reflect.Array;

public class ArrayUtil {
  
  /**
   * copy an array to an arry of its natural type
   * @param array input
   * @return array of natural type
   */
  public static Object naturalCast(Object[] array) {
    Class clazz = array[0].getClass();
    Object[] newArray = (Object[]) Array.newInstance(clazz, array.length);
    for (int i = 0; i < newArray.length; i++) {
      newArray[i] = array[i];
    }
    return newArray;
  }

} // ArrayUtil
