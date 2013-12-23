package com.flywet.platform.bi.pivot.model.olap.query;

import java.util.List;

import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.model.Position;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * base class for both Mondrian and XMLA Position
 */
public class PositionBase implements Position {

  protected Member[] members;

  // cellList, parent and number are temp variables used by hierarchize sort
  public List cellList = null;
  public PositionBase parent = null;
  public int number; 

  /* 
   * @return array of members
   * @see com.flywet.platform.bi.pivot.model.olap.model.Position#getMembers()
   */
  public Member[] getMembers() {
    return members;
  }
  

  /* (non-Javadoc)
   * @see com.flywet.platform.bi.pivot.model.olap.model.Visitable#accept
   */
  public void accept(Visitor visitor) {
    visitor.visitPosition(this);
  }

  /*
   * @see com.flywet.platform.bi.pivot.model.olap.model.Decorator#getRootDecoree()
   */
  public Object getRootDecoree() {
    return this;
  }

} // PositionBase
