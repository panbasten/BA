package com.flywet.platform.bi.pivot.model.olap.query;


/*
 * serialization for Exp - used  by memento
 */
public class ExpBean {
  public static final int TYPE_MEMBER = 0;
  public static final int TYPE_FUNCALL = 1;
  public static final int TYPE_LEVEL = 2;
  public static final int TYPE_DIM = 3;
  public static final int TYPE_HIER = 4;
  public static final int TYPE_STRING_LITERAL = 5;
  public static final int TYPE_INTEGER_LITERAL = 6;
  public static final int TYPE_DOUBLE_LITERAL = 7;
  public static final int TYPE_TOPLEVEL_MEMBERS = 8;
  
  private int type;
  private String name;
  private ExpBean[] args;
  private Object literalValue = null;

  /**
   * @return args
   */
  public ExpBean[] getArgs() {
    return args;
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * @param args
   */
  public void setArgs(ExpBean[] args) {
    this.args = args;
  }

  /**
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return type
   */
  public int getType() {
    return type;
  }

  /**
   * @param type
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * @return
   */
  public Object getLiteralValue() {
    return literalValue;
  }

  /**
   * @param object
   */
  public void setLiteralValue(Object object) {
    literalValue = object;
  }

} //ExpBean
