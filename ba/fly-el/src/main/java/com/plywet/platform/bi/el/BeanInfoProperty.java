package com.plywet.platform.bi.el;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 
 * <p>
 * This contains the information for one property in a BeanInfo -
 * PropertyDescriptor, read method, and write method. This class is necessary
 * because the read/write methods in the PropertyDescriptor may not be
 * accessible if the bean given to the introspector is not a public class. In
 * this case, a publicly accessible version of the method must be found by
 * searching for a public superclass/interface that declares the method (this
 * searching is done by the BeanInfoManager).
 * 
 * @author PeterPan
 * 
 **/

public class BeanInfoProperty {
	// -------------------------------------
	// Properties
	// -------------------------------------
	// property readMethod

	Method mReadMethod;

	public Method getReadMethod() {
		return mReadMethod;
	}

	// -------------------------------------
	// property writeMethod

	Method mWriteMethod;

	public Method getWriteMethod() {
		return mWriteMethod;
	}

	// -------------------------------------
	// property propertyDescriptor

	PropertyDescriptor mPropertyDescriptor;

	public PropertyDescriptor getPropertyDescriptor() {
		return mPropertyDescriptor;
	}

	// -------------------------------------
	/**
	 * 
	 * Constructor
	 **/
	public BeanInfoProperty(Method pReadMethod, Method pWriteMethod,
			PropertyDescriptor pPropertyDescriptor) {
		mReadMethod = pReadMethod;
		mWriteMethod = pWriteMethod;
		mPropertyDescriptor = pPropertyDescriptor;
	}

	// -------------------------------------
}
