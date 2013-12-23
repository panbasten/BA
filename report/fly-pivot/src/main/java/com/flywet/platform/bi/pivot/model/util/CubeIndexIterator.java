package com.flywet.platform.bi.pivot.model.util;

/**
 * generate array of indexes for an n-dimensional qube
 * basically "n" nested loops
 * for (i1 =0; i1 < n1 ; i1++ )
 *   for (i2 =0; i2 < n2 ; i2++ )
 *  ...
 *     for (im =0; im < nm ; im++ )
 */
public class CubeIndexIterator {
  int[] ni;
  int[] iCurrent;
  boolean firstFast;
  int nDim;

  /**
   * c'tor
   * @param ni - array of max indexes for dimensions
   * @param firstFast , first index runs fastest if true 
   */
  public CubeIndexIterator(int[] ni, boolean firstFast) {
    this.ni = ni;
    this.firstFast = firstFast;
    nDim = ni.length;
    iCurrent = new int[nDim];
    reset();
  }

  /**
   * @return next index array, null if done
   */
  public int[] next() {
    if (nDim == 0)
      return null;
    if (firstFast) {
      ++iCurrent[0];
      for (int i = 0; i < nDim - 1; i++) {
        if (iCurrent[i] > ni[i]) {
          ++iCurrent[i + 1];
          iCurrent[i] = 0;
        }
      }
      if (iCurrent[nDim - 1] > ni[nDim - 1])
        return null;
    } else {
      ++iCurrent[nDim - 1];
      for (int i = nDim - 1; i > 0; i--) {
        if (iCurrent[i] > ni[i]) {
          ++iCurrent[i - 1];
          iCurrent[i] = 0;
        }
      }
      if (iCurrent[0] > ni[0])
        return null;
    }
    return iCurrent;
  }

  /**
   * reset 
   */
  public void reset() {
    if (nDim == 0)
      return;
    for (int i = 0; i < nDim; i++) {
      if (!firstFast && i == nDim - 1)
        iCurrent[i] = -1;
      else if (firstFast && i == 0)
        iCurrent[i] = -1;
      else
        iCurrent[i] = 0;
    }
  }
} // CubeIndexIterator
