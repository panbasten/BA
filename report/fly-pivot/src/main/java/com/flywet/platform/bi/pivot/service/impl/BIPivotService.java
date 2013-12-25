package com.flywet.platform.bi.pivot.service.impl;

import java.io.IOException;

import javax.sql.DataSource;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DataSourceProviderFactory;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.xml.XMLUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.pivot.cache.BIOlapModelCache;
import com.flywet.platform.bi.pivot.service.intf.BIPivotDelegates;
import com.flywet.platform.bi.pivot.utils.BIContext;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;
import com.flywet.platform.bi.smart.utils.BIOlapSchemaProcessor;
import com.tonbeller.jpivot.mondrian.MondrianModel;
import com.tonbeller.jpivot.olap.model.OlapModel;
import com.tonbeller.jpivot.table.TableComponent;
import com.tonbeller.jpivot.table.TableComponentFactory;
import com.tonbeller.jpivot.tags.MondrianModelFactory;

@Service("bi.service.pivotService")
public class BIPivotService implements BIPivotDelegates {

	public static final String SCHEMA_PROCESSOR_CLASS_STRING = BIOlapSchemaProcessor.class
			.getName();

	/**
	 * 执行一个Mdx语句
	 */
	@Override
	public void queryMdx() throws BIException {

		String mdx = "select "
				+ "{[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} on columns, "
				+ "{([Promotion Media].[All Media], [Product].[All Products])} ON rows "
				+ "from Sales " + "where ([Time].[1997])";
		long catalogId = 0L;
		long databaseMetaId = 5L;

		try {
			BIDatabaseDelegates dbDelegates = BIWebUtils
					.getBean("bi.service.databaseServices");
			DatabaseMeta dbMeta = dbDelegates.getDatabaseMeta(databaseMetaId);

			// 获得模型
			OlapModel om = BIOlapModelCache.getOlapModel(catalogId);
			if (om == null) {
				MondrianModel mm = createOlapModel(dbMeta, catalogId, mdx);
				mm.initialize();
				om = (OlapModel) mm.getTopDecorator();

				BIOlapModelCache.putOlapModel(catalogId, om);
			}

			// 获得执行模型
			BIContext context = new BIContext("test");
			TableComponent tc = TableComponentFactory.instance("olap_"
					+ catalogId, om);
			tc.initialize(context);

			Document doc = tc.render(context);

			System.out.println(XMLUtils.toXMLString(doc));

		} catch (Exception e) {
			throw new BIException("执行MDX查询出现错误。", e);
		}

	}

	private MondrianModel createOlapModel(DatabaseMeta databaseMeta,
			long catalogId, String mdx) throws KettleDatabaseException,
			SAXException, IOException {
		MondrianModelFactory.Config cfg = new MondrianModelFactory.Config();
		cfg.setDynResolver(SCHEMA_PROCESSOR_CLASS_STRING);
		cfg.setSchemaUrl(Long.toString(catalogId));
		cfg.setMdxQuery(mdx);

		if (databaseMeta.getAccessType() == DatabaseMeta.TYPE_ACCESS_JNDI) {
			DataSource dataSource = DataSourceProviderFactory
					.getDataSourceProviderInterface().getNamedDataSource(
							databaseMeta.getDatabaseName());
			cfg.setExternalDataSource(dataSource);

		} else {
			cfg.setJdbcUrl(databaseMeta.getURL());
			cfg.setJdbcDriver(databaseMeta.getDriverClass());

			if (!Const.isEmpty(databaseMeta.getUsername())) {
				cfg.setJdbcUser(databaseMeta.getUsername());
			}
			if (!Const.isEmpty(databaseMeta.getPassword())) {
				cfg.setJdbcPassword(databaseMeta.getPassword());
			}
		}

		return MondrianModelFactory.instance(cfg);
	}

}
