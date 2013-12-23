package com.flywet.platform.bi.pivot.model.core;

/**
 * Default implementation of an Extension
 * @author av
 */

public class ExtensionSupport implements Extension {

  private String id;
  private Model model;

  /**
   * Returns the id.
   * @return String
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the model.
   * @return ModelSupport
   */
  public Model getModel() {
    return model;
  }

  /**
   * Sets the id.
   * @param id The id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets the model. Used by ModelFactory.
   * @param model The model to set
   */
  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * does not decorate, returns the parameter
   */
  public Model decorate(Model decoratedModel) {
    return decoratedModel;
  }

  /**
  	* Notification after model initialization is complete 
  	*/
  public void modelInitialized(){
  	// default: no action
  }

}
