package com.flywet.platform.bi.pivot.model.core;

import java.util.EventObject;

/**
 * informs a listener that the model has changed
 * 
 * @author av
 */
public class ModelChangeEvent extends EventObject {

  /**
   * Constructor for ModelChangeEvent.
   * @param arg0
   */
  public ModelChangeEvent(Object arg0) {
    super(arg0);
  }

}
