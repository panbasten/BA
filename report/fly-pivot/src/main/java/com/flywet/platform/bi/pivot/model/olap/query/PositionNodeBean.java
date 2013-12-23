package com.flywet.platform.bi.pivot.model.olap.query;


/**
 * serialization for mondrian position tree node - used  by memento
 */
public class PositionNodeBean {
  private ExpBean reference;
  private PositionNodeBean[] children;

  public PositionNodeBean() {
  }
  
  /**
   * @return reference
   */
  public ExpBean getReference() {
    return reference;
  }

  /**
   * @param reference
   */
  public void setReference(ExpBean reference) {
    this.reference = reference;
  }

  /**
   * @return
   */
  public PositionNodeBean[] getChildren() {
    return children;
  }

  /**
   * @param children
   */
  public void setChildren(PositionNodeBean[] children) {
    this.children = children;
  }

} // PositionNodeBean
