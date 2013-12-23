package com.flywet.platform.bi.pivot.model.olap.query;

import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.model.OlapException;

/**
 * MDX Member
 */
public interface MDXMember extends Member, MDXElement {

  /**
   * get parent member
   */
  Member getParent() throws OlapException;

  /**
   * get parent member unique name 
   */
  String getParentUniqueName();

} // MDXMember
