package com.flywet.platform.bi.pivot.model.olap.query;

/**
 * MDX Elements like Members, Levels ,Hierarchies 
 *   have a common set op properties like "Unique Name"
 */
public interface MDXElement {
  
  /**
   * return the unique name of an MDX Olap element
   */
  String getUniqueName();
  

} // MDXElement
