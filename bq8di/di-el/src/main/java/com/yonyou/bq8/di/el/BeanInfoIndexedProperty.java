package com.yonyou.bq8.di.el;

import java.beans.IndexedPropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 
 * <p>
 * This contains the information for one indexed property in a BeanInfo -
 * IndexedPropertyDescriptor, read method, and write method. This class is
 * necessary because the read/write methods in the IndexedPropertyDescriptor may
 * not be accessible if the bean given to the introspector is not a public
 * class. In this case, a publicly accessible version of the method must be
 * found by searching for a public superclass/interface that declares the method
 * (this searching is done by the BeanInfoManager).
 * 
 * @author PeterPan
 * 
 **/

public class BeanInfoIndexedProperty {
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

	IndexedPropertyDescriptor mIndexedPropertyDescriptor;

	public IndexedPropertyDescriptor getIndexedPropertyDescriptor() {
		return mIndexedPropertyDescriptor;
	}

	// -------------------------------------
	/**
	 * 
	 * Constructor
	 **/
	public BeanInfoIndexedProperty(Method pReadMethod, Method pWriteMethod,
			IndexedPropertyDescriptor pIndexedPropertyDescriptor) {
		mReadMethod = pReadMethod;
		mWriteMethod = pWriteMethod;
		mIndexedPropertyDescriptor = pIndexedPropertyDescriptor;
	}

	// -------------------------------------
}
