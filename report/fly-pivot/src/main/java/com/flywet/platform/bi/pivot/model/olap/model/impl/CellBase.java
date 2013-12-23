package com.flywet.platform.bi.pivot.model.olap.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.pivot.model.olap.model.Cell;
import com.flywet.platform.bi.pivot.model.olap.model.NumberFormat;
import com.flywet.platform.bi.pivot.model.olap.model.Property;
import com.flywet.platform.bi.pivot.model.olap.model.Visitor;

/**
 * Cell base for both XMLA and Mondrian
 */
public abstract class CellBase implements Cell {

	static Logger logger = Logger.getLogger(CellBase.class);

	protected String formattedValue;
	private List properties = null;

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Cell#getValue()
	 */
	public abstract Object getValue();

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Cell#getFormat()
	 */
	public abstract NumberFormat getFormat();

	/**
	 * @return the formatted value String
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Cell#getFormattedValue()
	 */
	public String getFormattedValue() {
		return formattedValue;
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Cell#isNull()
	 */
	public abstract boolean isNull();

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyHolder#getProperties()
	 */
	public Property[] getProperties() {
		if (properties == null)
			return new Property[0];
		else
			return (Property[]) properties.toArray(new Property[0]);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.PropertyHolder#getProperty(String)
	 */
	public Property getProperty(String name) {

		if (properties == null)
			return null;

		for (Iterator iter = properties.iterator(); iter.hasNext();) {
			Property prop = (Property) iter.next();
			if (prop.getName().equalsIgnoreCase(name))
				return prop;

		}
		return null;
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Visitable#accept(Visitor)
	 */
	public void accept(Visitor visitor) {
		visitor.visitCell(this);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.model.Decorator#getRootDecoree()
	 */
	public Object getRootDecoree() {
		return this;
	}

	/**
	 * @param string
	 */
	public void setFormattedValue(String string, FormatStringParser parser) {
		FormatStringParser.Result res = parser.parse(this, string);
		formattedValue = res.getFormattedValue();
		if (res.getProperties().size() > 0) {
			if (properties == null)
				properties = new ArrayList();
			properties.addAll(res.getProperties());
		}
	}

	/**
	 * add property to cell
	 * 
	 * @param prop
	 * @param value
	 */
	public void addProperty(String prop, String value) {
		Property p = this.getProperty(prop);
		if (p != null) {
			((PropertyImpl) p).setValue(value);
		} else {
			PropertyImpl pi = new PropertyImpl();
			pi.setName(prop);
			pi.setLabel(prop);
			pi.setValue(value);
			if (properties == null)
				properties = new ArrayList();
			properties.add(pi);
		}
	}

} // CellBase
