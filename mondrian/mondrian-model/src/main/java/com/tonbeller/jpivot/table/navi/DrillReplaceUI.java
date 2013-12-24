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
package com.tonbeller.jpivot.table.navi;

import com.tonbeller.jpivot.olap.model.Hierarchy;
import com.tonbeller.jpivot.olap.model.Level;
import com.tonbeller.jpivot.olap.model.Member;
import com.tonbeller.jpivot.olap.model.OlapModel;
import com.tonbeller.jpivot.olap.model.VisitorSupportSloppy;
import com.tonbeller.jpivot.olap.navi.DrillReplace;
import com.tonbeller.jpivot.table.span.PropertyHeading;
import com.tonbeller.jpivot.table.span.Span;
import com.tonbeller.jpivot.table.span.SpanVisitor;

/**
 * Created on 03.12.2002
 * 
 * @author av
 */
public class DrillReplaceUI extends DrillExpandUI {

  public static final String ID = "drillReplace";
  public String getId() {
    return ID;
  }

  DrillReplace extension;

  class CanCollapse extends VisitorSupportSloppy implements SpanVisitor {
    boolean result = false;
    public void visitHierarchy(Hierarchy v) {
      result = extension.canDrillUp(v);
    }
    public void visitLevel(Level v) {
      result = extension.canDrillUp(v.getHierarchy());
    }
    public void visitPropertyHeading(PropertyHeading heading) {
    }
  }

  protected boolean canCollapse(Span span) {
    CanCollapse cc = new CanCollapse();
    span.getObject().accept(cc);
    return cc.result;
  }

  class DoCollapse extends VisitorSupportSloppy implements SpanVisitor {
    public void visitHierarchy(Hierarchy v) {
      extension.drillUp(v);
    }
    public void visitLevel(Level v) {
      extension.drillUp(v.getHierarchy());
    }
    public void visitPropertyHeading(PropertyHeading heading) {
    }
  }

  protected void collapse(Span span) {
    DoCollapse dc = new DoCollapse();
    span.getObject().accept(dc);
  }

  protected boolean canExpand(Span span) {
    if (positionContainsMember(span))
      return extension.canDrillDown((Member) span.getMember().getRootDecoree());
    return false;
  }

  protected void expand(Span span) {
    extension.drillDown((Member) span.getMember().getRootDecoree());
  }

  protected boolean initializeExtension() {
    OlapModel om = table.getOlapModel();
    extension = (DrillReplace) om.getExtension(DrillReplace.ID);
    return extension != null;
  }

  protected String getCollapseImage() {
    return "drill-replace-collapse";
  }

  protected String getExpandImage() {
    return "drill-replace-expand";
  }

  protected String getOtherImage() {
    return "drill-replace-other";
  }

}
