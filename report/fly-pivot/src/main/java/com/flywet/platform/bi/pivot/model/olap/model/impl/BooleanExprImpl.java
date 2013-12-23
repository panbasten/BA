package com.flywet.platform.bi.pivot.model.olap.model.impl;

import com.flywet.platform.bi.pivot.model.olap.model.BooleanExpr;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

public class BooleanExprImpl implements BooleanExpr {
  private boolean value;

  public BooleanExprImpl() {
  }

  public BooleanExprImpl(boolean value) {
    this.value = value;
  }

  public boolean getValue() {
    return value;
  }

  public void setValue(boolean b) {
    value = b;
  }

  public void accept(Visitor visitor) {
    visitor.visitBooleanExpr(this);
  }

}
