package com.flywet.platform.bi.pivot.model.olap.query;

/**
 * MDX levels have a depth, root level has depth = 0
 */
public interface MDXLevel extends MDXElement {
  
  /**
   * @return the level's depth
   */
  public int getDepth();

  /**
   * @return true, if the level is "All"
   */
  public boolean isAll();  
  
  /**
   * @return true, if the level is not Bottom level
   */
  public boolean hasChildLevel();
  

} // MDXLevel
