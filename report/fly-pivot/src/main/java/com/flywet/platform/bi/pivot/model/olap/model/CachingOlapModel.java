package com.flywet.platform.bi.pivot.model.olap.model;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.pivot.model.core.Model;
import com.flywet.platform.bi.pivot.model.core.ModelChangeEvent;
import com.flywet.platform.bi.pivot.model.core.ModelChangeListener;

/**
 * An OlapModel decorator that caches the result
 * 
 * @author av
 */
public class CachingOlapModel extends OlapModelDecorator implements
		ModelChangeListener {
	private static Logger logger = Logger.getLogger(CachingOlapModel.class);
	Result result = null;

	/**
	 * invalidates the current result
	 */
	public void modelChanged(ModelChangeEvent e) {
		result = null;
	}

	/**
	 * invalidates the current result
	 */
	public void structureChanged(ModelChangeEvent e) {
		result = null;
	}

	public Result getResult() throws OlapException {
		logger.info("CachingOlapModel: "
				+ ((result == null) ? "getting Result from OLAP Server"
						: "using cached result"));
		if (result == null) {
			long t1 = System.currentTimeMillis();
			result = super.getResult();
			long t2 = System.currentTimeMillis();
			logger.info("Execute Query took " + (t2 - t1) + " millisec");
		}
		return result;
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.core.Extension#decorate(Model)
	 */
	public Model decorate(Model modelToDecorate) {
		modelToDecorate.addModelChangeListener(this);
		return super.decorate(modelToDecorate);
	}

}
