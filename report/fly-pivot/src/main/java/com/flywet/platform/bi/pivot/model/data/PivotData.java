package com.flywet.platform.bi.pivot.model.data;

import java.io.IOException;

import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DataSourceProviderFactory;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;
import com.flywet.platform.bi.smart.utils.BIOlapSchemaProcessor;
import com.tonbeller.jpivot.mondrian.MondrianModel;
import com.tonbeller.jpivot.olap.model.OlapModel;
import com.tonbeller.jpivot.table.TableComponent;
import com.tonbeller.jpivot.table.TableComponentFactory;
import com.tonbeller.jpivot.tags.MondrianModelFactory;
import com.tonbeller.wcf.controller.RequestContext;

public class PivotData implements IRegionData {

	public static final String SCHEMA_PROCESSOR_CLASS_STRING = BIOlapSchemaProcessor.class
			.getName();

	public static final String REGION_DATA_NAME = "Pivot";

	// 初始的mdx
	private String oraMdx;

	// 用于缓存数据的对象
	private PivotDataData data;

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

	// OLAP:多维模型
	private OlapModel om;

	// OLAP:透视报表表格组件
	private TableComponent tc;

	private PivotData() {

	}

	@Override
	public void init(RequestContext context) throws BIException {
		// 如果data缓存值不为空，延迟计算TC
		if (data != null) {
			return;
		}

		// 如果data缓存值为空，尝试进行计算TC
		flush(context);

	}

	@Override
	public void flush(RequestContext context) throws BIException {
		if (Const.isEmpty(catalog)) {
			if (catalogId == null) {
				throw new BIPivotException("分析主题ID为空.");
			}
		}

		if (databaseMeta == null) {
			BIDatabaseDelegates dbDelegates = BIWebUtils
					.getBean("bi.service.databaseServices");
			if (databaseMetaId == null) {
				throw new BIPivotException("数据源元数据ID未设置.");
			}
			databaseMeta = dbDelegates.getDatabaseMeta(databaseMetaId);
		}

		try {
			MondrianModel mm = createOlapModel(mdx);
			mm.initialize();
			om = (OlapModel) mm.getTopDecorator();

			tc = TableComponentFactory.instance("olap_" + catalogId, om);
			tc.initialize(context);
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
		// TODO 自定义catalog
		cfg.setSchemaUrl(String.valueOf(catalogId));
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

	public static PivotData instance(Node node) throws BIException {
		PivotData pd = new PivotData();

		pd.databaseMetaId = Utils.toLong(
				XMLHandler.getTagAttribute(node, "databaseMetaId"), null);

		pd.catalogId = Utils.toLong(
				XMLHandler.getTagAttribute(node, "catalogId"), null);

		pd.oraMdx = Const.trim(XMLHandler.getTagValue(node, "mdx"));
		pd.mdx = pd.oraMdx;

		pd.catalog = Const.trim(XMLHandler.getTagValue(node, "catalog"));

		// 缓存在xml中的数据 data
		Node dataNode = XMLHandler.getSubNode(node, "data");
		if (dataNode != null) {
			pd.data = PivotDataData.instance(dataNode);
		}

		return pd;
	}

	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Long getDatabaseMetaId() {
		return databaseMetaId;
	}

	public void setDatabaseMetaId(Long databaseMetaId) {
		this.databaseMetaId = databaseMetaId;
	}

	public String getOraMdx() {
		return oraMdx;
	}

	public DatabaseMeta getDatabaseMeta() {
		return databaseMeta;
	}

	public OlapModel getOm() {
		return om;
	}

	public PivotDataData getData() {
		return data;
	}

	public void setData(PivotDataData data) {
		this.data = data;
	}

	@Override
	public String getTypeName() {
		return REGION_DATA_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		try {
			JSONObject jo = new JSONObject();
			jo.put(REGION_DATA_TYPE, getTypeName());

			if (data != null) {
				jo.put(PROP_NAME_DATA, data.renderJo(context));
			} else {
				jo.put(PROP_NAME_DATA, tc.renderJo(context));
			}

			return jo;
		} catch (Exception e) {
			throw new BIPivotException("渲染透视报表区域数据出现错误.", e);
		}
	}

}
