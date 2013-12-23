package com.flywet.platform.bi.pivot.model.mondrian;

import com.flywet.platform.bi.pivot.model.core.ExtensionSupport;
import com.flywet.platform.bi.pivot.model.olap.model.OlapException;
import com.flywet.platform.bi.pivot.model.olap.navi.MdxQuery;
import com.tonbeller.wcf.format.FormatException;

/**
 * Created on 19.12.2002
 * 
 * @author av
 */
public class MondrianMdxQuery extends ExtensionSupport implements MdxQuery {

	public MondrianMdxQuery() {
		super.setId(MdxQuery.ID);
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.navi.MdxQuery#getMdxQuery()
	 */
	public String getMdxQuery() {
		MondrianModel m = (MondrianModel) getModel();
		return m.getCurrentMdx();
	}

	/**
	 * @see com.flywet.platform.bi.pivot.model.olap.navi.MdxQuery#setMdxQuery(String)
	 */
	public void setMdxQuery(String mdxQuery) {
		try {
			MondrianModel m = (MondrianModel) getModel();
			if (m.setUserMdx(mdxQuery))
				m.fireModelChanged(); // only if changed
		} catch (OlapException e) {
			throw new FormatException(e.getMessage());
		}
	}

} // End MondrianMdxQuery
