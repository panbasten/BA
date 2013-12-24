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
/*
 * Created on 12.06.2003
 *  by hh
 */
package com.tonbeller.jpivot.mondrian;

/**
 * Position wrapper for sort
 * @author hh
 */
public class MondrianSortPosition implements Comparable {

  public int index;
  private mondrian.olap.Member[] posMembers;

  /** c'tor
   */
  public MondrianSortPosition(int index, mondrian.olap.Member[] posMembers) {
    this.index = index;
    this.posMembers = posMembers;
  }

  /**
   * comparison
   */
  public int compareTo(Object o) {
    MondrianSortPosition other = (MondrianSortPosition)o;
    DimensionLoop : for (int i = 0; i < this.posMembers.length; i++) {
      mondrian.olap.Member m1 = this.posMembers[i];
      mondrian.olap.Member m2 = other.posMembers[i];
      if (m1.equals(m2))
        continue DimensionLoop;

      // only compare by unique name, 
      //  as this contains the member hierarchy up to the top level
      int ic = m1.getUniqueName().compareTo(m2.getUniqueName());
      if (ic != 0)
        return ic;
      // everything equal up to here
    } // DimensionLoop

    return 0; // equal positions, should not occur
  }

} // End MondrianSortPosition
