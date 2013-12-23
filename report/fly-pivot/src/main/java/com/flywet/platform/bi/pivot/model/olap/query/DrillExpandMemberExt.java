package com.flywet.platform.bi.pivot.model.olap.query;

import com.flywet.platform.bi.pivot.model.core.ExtensionSupport;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.navi.DrillExpandMember;

/**
 * @author hh
 *
 */
public class DrillExpandMemberExt extends ExtensionSupport implements DrillExpandMember {
  /**
    * Constructor sets ID
    */
  public DrillExpandMemberExt() {
    super.setId(DrillExpandMember.ID);
  }

  /**
   * @see com.flywet.platform.bi.pivot.model.olap.navi.DrillExpand#canExpand(Member)
   * @param member the membber to be checked for potential expansion
   * @return true if the member can be expanded
   */
  public boolean canExpand(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    return model.getQueryAdapter().canExpand(member);
  }

  /**
   * @see com.flywet.platform.bi.pivot.model.olap.navi.DrillExpand#canCollapse(Member)
   * @param member member to be expanded
   * @return true if the member can be collapsed
   */
  public boolean canCollapse(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    return model.getQueryAdapter().canCollapse(member);
  }

  /**
   * @see com.flywet.platform.bi.pivot.model.olap.navi.DrillExpand#expand(Member)
   * @param member member to be expanded
   */
  public void expand(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    model.getQueryAdapter().expand(member);
  }

  /**
   * @see com.flywet.platform.bi.pivot.model.olap.navi.DrillExpand#collapse(Member)
   * @param member member to be collapsed
   */
  public void collapse(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    model.getQueryAdapter().collapse(member);
  }

} // End DrillExpandMemberExt
