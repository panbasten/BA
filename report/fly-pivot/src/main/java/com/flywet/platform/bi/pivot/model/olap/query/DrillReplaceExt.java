package com.flywet.platform.bi.pivot.model.olap.query;

import com.flywet.platform.bi.pivot.model.core.ExtensionSupport;
import com.flywet.platform.bi.pivot.model.olap.model.Hierarchy;
import com.flywet.platform.bi.pivot.model.olap.model.Member;
import com.flywet.platform.bi.pivot.model.olap.navi.DrillReplace;

/**
 * @author hh
 *
 */
public class DrillReplaceExt extends ExtensionSupport implements DrillReplace {

  /**
   * Constructor sets ID
   */
  public DrillReplaceExt() {
    super.setId(DrillReplace.ID);
  }

  /**
   * drill down is possible if <code>member</code> has children
   */
  public boolean canDrillDown(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    return model.getQueryAdapter().canDrillDown(member);
  }

  /**
   * drill up is possible if not all members of the top level 
   * hierarchy are shown.
   */
  public boolean canDrillUp(Hierarchy hier) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    return model.getQueryAdapter().canDrillUp(hier);
  }

  /**
   * replaces the members. Let <code>H</code> be the hierarchy
   * that member belongs to. Then drillDown will replace all members from <code>H</code>
   * that are currently visible with the children of <code>member</code>.
   */
  public void drillDown(Member member) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    model.getQueryAdapter().drillDown(member);
  }

  /**
   * replaces all visible members of hier with the members of the
   * next higher level.
   */
  public void drillUp(Hierarchy hier) {
    QueryAdapter.QueryAdapterHolder model = (QueryAdapter.QueryAdapterHolder) getModel();
    model.getQueryAdapter().drillUp(hier);
  }

} // End DrillReplaceExt
