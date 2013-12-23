package com.flywet.platform.bi.pivot.model.core;

/**
 * An optional extension to a model
 * @see Model
 * @author av
 */

public interface Extension {

  /**
   * Set the model that is extended.
   */
  void setModel(Model model);
  
  /**
   * returns the id of this extension. The id identifies the extension within the model.
   */
  String getId();
  
  /**
   * allow an extension to decorate the model. The default implementation should
   * return  <code>modelToDecorate</code>
   * @param modelToDecorate the model to decorate. It may be different from the model
   * passed to setModel() because other extensions may already have decorated 
   * modelToDecorate.
   */
  Model decorate(Model modelToDecorate);
  
  /**
   * Notification after model initialization is complete 
   */
  void modelInitialized();
  
}
