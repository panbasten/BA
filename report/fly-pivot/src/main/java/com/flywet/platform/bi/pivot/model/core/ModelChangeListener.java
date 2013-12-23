package com.flywet.platform.bi.pivot.model.core;

import java.util.EventListener;

/**
 * Created on 14.10.2002
 * 
 * @author av
 */
public interface ModelChangeListener extends EventListener {

  /** model data have changed, e.g. user has navigated */
  void modelChanged(ModelChangeEvent e);

  /** major change, e.g. extensions added/removed */
  void structureChanged(ModelChangeEvent e);

}
