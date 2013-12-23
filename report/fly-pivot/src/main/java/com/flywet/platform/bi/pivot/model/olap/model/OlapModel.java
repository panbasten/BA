package com.flywet.platform.bi.pivot.model.olap.model;

import javax.servlet.ServletContext;

import com.flywet.platform.bi.pivot.model.core.Model;
import com.flywet.platform.bi.pivot.model.util.JPivotRuntimeException;

/**
 * Provides access to extensions, result and metadata. It does not specify what
 * data are displayed (the query). Supports navigations thru Extensions.
 * 
 * @see com.flywet.platform.bi.pivot.model.core.Extension
 * @see com.flywet.platform.bi.pivot.model.olap.model.Result
 */
public interface OlapModel extends Model {

	/**
	 * thrown if too many result positions would be returned
	 * 
	 * @see OlapModel#getResult()
	 */
	public class ResultTooLargeException extends JPivotRuntimeException {
		public ResultTooLargeException() {
			super();
		}

		public ResultTooLargeException(Throwable cause) {
			super(cause);
		}

		public ResultTooLargeException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * thrown if the result can not be computed because the cube is empty.
	 * 
	 * @see OlapModel#getResult()
	 */
	public class EmptyCubeException extends JPivotRuntimeException {
		public EmptyCubeException() {
			super();
		}

		public EmptyCubeException(Throwable cause) {
			super(cause);
		}

		public EmptyCubeException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * runs the query and returns the result
	 */
	public Result getResult() throws OlapException;

	/**
	 * @return all dimensions of the cube for navigation
	 */
	Dimension[] getDimensions();

	/**
	 * @return all measures of the cube for navigation
	 */
	Member[] getMeasures();

	/**
	 * called once after creation. E.g. open DB connection
	 */
	void initialize() throws OlapException;

	/**
	 * called once when the not used any longer. E.g. close DB connection
	 * 
	 */
	void destroy();

	/**
	 * @return an ID string for this model
	 */
	String getID();

	/**
	 * sets an ID string for this model
	 */
	void setID(String ID);

	/**
	 * store a servlet context to the model
	 */
	void setServletContext(ServletContext servletContext);

}
