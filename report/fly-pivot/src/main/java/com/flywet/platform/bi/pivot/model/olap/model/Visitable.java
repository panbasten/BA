package com.flywet.platform.bi.pivot.model.olap.model;

/**
 * Created on 29.10.2002
 * 
 * @author av
 */
public interface Visitable {
	void accept(Visitor visitor);
}
