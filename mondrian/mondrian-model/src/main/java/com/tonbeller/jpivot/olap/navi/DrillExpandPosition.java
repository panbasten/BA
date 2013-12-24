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
 * $Id$
 */
package com.tonbeller.jpivot.olap.navi;

import com.tonbeller.jpivot.core.Extension;
import com.tonbeller.jpivot.olap.model.Member;
import com.tonbeller.jpivot.olap.model.Position;

/**
 * allows to expand / collapse members on an axis. If a member is expanded, 
 * the member itself plus its children are displayed.
 * <p>
 * Example: if you expand "Europe" you may see "Germany", "France" etc.
 * If you collapse "Europe" the countries will not be shown.
 * <p>
 * If multiple Hierarchies are shown on a single axis, only one
 * position is expanded. For example
 * 
 * <pre>
 * -----+-------
 * 2001 | Europe
 * -----+-------
 * 2002 | Europe
 * -----+-------
 * 2003 | Europe
 * -----+-------
 * </pre>
 * 
 * clicking on Europe in 2002 will give
 * 
 * <pre>
 * -----+-------
 * 2001 | Europe
 * -----+-------
 * 2002 | Europe
 *      |   Germany
 *      |   France
 * -----+-------
 * 2003 | Europe
 * -----+-------
 * </pre>
 * 
 * @author av
 */

public interface DrillExpandPosition extends Extension {

  /**
   * name of the Extension for lookup
   */
  public static final String ID = "drillExpandPosition";
  

  /**
   * true if member has children and Position is not currently expanded
   */
  boolean canExpand(Position position, Member member);
  

  /**
   * true if the Position is currently expanded by specific member. 
   */
  boolean canCollapse(Position position, Member member);

  
  /**
   * expands Position by specific member
   */
  void expand(Position position, Member member);


  /**
   * collapses Position by specific member.
   */
  void collapse(Position position, Member member);
}
