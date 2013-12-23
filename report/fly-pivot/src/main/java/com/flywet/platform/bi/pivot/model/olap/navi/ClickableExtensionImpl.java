package com.flywet.platform.bi.pivot.model.olap.navi;

import java.util.Collection;
import java.util.Collections;

import com.flywet.platform.bi.pivot.model.core.ExtensionSupport;

public class ClickableExtensionImpl extends ExtensionSupport implements ClickableExtension {
  Collection clickables = Collections.EMPTY_LIST;
  public String getId() {
    return ID;
  }
  public Collection getClickables() {
    return clickables;
  }

  public void setClickables(Collection clickables) {
    this.clickables = clickables;
  }

}