package com.flywet.platform.bi.pivot.model.res;

/**
 * initial provider. looks up variables
 * <ol>
 * <li>in JNDI java:/comp/env context</li>
 * <li>System.getProperty()</li>
 * <li>user.properties in root classpath</li>
 * <li>resfactory.properties in root classpath</li>
 * </ol>
 * in that order.
 * 
 * @author av
 */
public class JNDIInitialProvider extends SimpleInitialProvider{
  public JNDIInitialProvider() {
    add(0, new JNDIResourceProvider());
  }
}