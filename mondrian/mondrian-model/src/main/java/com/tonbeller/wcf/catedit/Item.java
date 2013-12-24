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
package com.tonbeller.wcf.catedit;


/**
 * an item that belongs to a category
 * 
 * @author av
 */
public interface Item {
  public String getLabel();

  /**
   * true, if this item may change its category.
   */
  boolean isMovable();
}
