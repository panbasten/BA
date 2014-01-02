package com.flywet.platform.bi.pivot.model;

import java.io.IOException;

import javax.sql.DataSource;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DataSourceProviderFactory;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.util.UUIDUtil;
import org.xml.sax.SAXException;

import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;
import com.flywet.platform.bi.smart.utils.BIOlapSchemaProcessor;
import com.tonbeller.jpivot.mondrian.MondrianModel;
import com.tonbeller.jpivot.olap.model.OlapModel;
import com.tonbeller.jpivot.tags.MondrianModelFactory;

/**
 * 透视表的区域模型
 * 
 * @author PeterPan
 * 
 */
public class PivotRegionModel {

	public static final String SCHEMA_PROCESSOR_CLASS_STRING = BIOlapSchemaProcessor.class
			.getName();

	// ID
	private String id;

	// 初始的mdx
	private String oraMdx;

	// mdx
	private String mdx;

	// 分析主题ID
	private Long catalogId;

	// 分析主题内容
	private String catalog;

	// 数据源ID
	private Long databaseMetaId;

	// 数据源元数据
	private DatabaseMeta databaseMeta;

	// 多维模型
	private OlapModel om;

	private PivotRegionModel() {

	}

	public static PivotRegionModel instance(long catelogId,
			Long databaseMetaId, String mdx) throws BIPivotException {
		PivotRegionModel model = new PivotRegionModel();

		model.id = UUIDUtil.getUUIDAsString();

		model.catalogId = catelogId;
		model.databaseMetaId = databaseMetaId;
		model.oraMdx = mdx;

		model.init();
		return model;
	}

	/**
	 * 初始化模型
	 * 
	 * @throws BIPivotException
	 */
	public void init() throws BIPivotException {
		try {
			if (Const.isEmpty(catalog)) {
				if (catalogId == null) {
					throw new BIPivotException("分析主题ID为空.");
				}
			}

			if (databaseMeta == null) {
				BIDatabaseDelegates dbDelegates = BIWebUtils
						.getBean("bi.service.databaseServices");
				databaseMeta = dbDelegates.getDatabaseMeta(databaseMetaId);
			}

			MondrianModel mm = createOlapModel(oraMdx);
			mm.initialize();
			om = (OlapModel) mm.getTopDecorator();
		} catch (Exception e) {
			throw new BIPivotException("获得分析模型出现错误.", e);
		}
	}

	/**
	 * 创建一个多维模型
	 * 
	 * @param mdx
	 * @return
	 * @throws KettleDatabaseException
	 * @throws SAXException
	 * @throws IOException
	 */
	private MondrianModel createOlapModel(String mdx)
			throws KettleDatabaseException, SAXException, IOException {
		MondrianModelFactory.Config cfg = new MondrianModelFactory.Config();
		cfg.setDynResolver(SCHEMA_PROCESSOR_CLASS_STRING);
		cfg.setSchemaUrl(id);
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

	public String getOraMdx() {
		return oraMdx;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public PivotRegionModel setCatalogId(long catalogId) {
		this.catalogId = catalogId;
		return this;
	}

	public String getCatelog() {
		return catalog;
	}

	public PivotRegionModel setCatelog(String catalog) {
		this.catalog = catalog;
		return this;
	}

	public Long getDatabaseMetaId() {
		return databaseMetaId;
	}

	public PivotRegionModel setDatabaseMetaId(Long databaseMetaId) {
		if (this.databaseMetaId != databaseMetaId) {
			this.databaseMetaId = databaseMetaId;
			this.databaseMeta = null;
		}

		return this;
	}

	public DatabaseMeta getDatabaseMeta() {
		return databaseMeta;
	}

	public PivotRegionModel setDatabaseMeta(DatabaseMeta databaseMeta) {
		this.databaseMeta = databaseMeta;
		return this;
	}

	public OlapModel getOm() {
		return om;
	}

}
