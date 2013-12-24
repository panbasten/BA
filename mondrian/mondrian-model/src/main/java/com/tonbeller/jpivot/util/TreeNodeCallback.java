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

package com.tonbeller.jpivot.util;

/**
 * handle call back for position tree
 */
public interface TreeNodeCallback {

  public static final int CONTINUE = 0;
  public static final int CONTINUE_SIBLING = 1;
  public static final int CONTINUE_PARENT = 2;
  public static final int BREAK = 3;

  /**
   * @param node the current node to handle
   * @return 0 continue tree walk
   *         1 break this node (continue sibling)
   *         2 break this level (continue parent level)
   *         3 break tree walk 
   */
  int handleTreeNode(TreeNode node);
} // TreeNodeCallback
