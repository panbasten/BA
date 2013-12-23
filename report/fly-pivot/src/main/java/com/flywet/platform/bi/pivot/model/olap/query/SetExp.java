package com.flywet.platform.bi.pivot.model.olap.query;

import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;

/**
 * Wrapper for Set Object to be placed on a query axis
 */
public class SetExp {
  private int mode;
  private Object oExp;
  private Hierarchy hier;

  public SetExp(int mode, Object oExp, Hierarchy hier) {
    this.mode = mode;
    this.oExp = oExp;
    this.hier = hier;
  }

  /**
   * @return
   */
  public Hierarchy getHier() {
    return hier;
  }

  /**
   * @return
   */
  public int getMode() {
    return mode;
  }

  /**
   * @return
   */
  public Object getOExp() {
    return oExp;
  }

} // SetExp
