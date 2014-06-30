package com.flywet.platform.bi.pivot.utils;

import javax.sql.DataSource;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DataSourceProviderFactory;
import org.pentaho.di.core.database.DatabaseMeta;

import com.flywet.platform.bi.core.exception.BIDBException;
import com.flywet.platform.bi.smart.utils.BIOlapSchemaProcessor;

public class MondrianOlapHelper {

	public static final String SCHEMA_PROCESSOR_CLASS_STRING = BIOlapSchemaProcessor.class
			.getName();

	// 分析数据库的元数据
	private DatabaseMeta databaseMeta;

	// 分析模型ID
	private long catalogId;

	// MDX
	private String mdx;

	// 数据库连接
	private Connection connection;
	private Result result;
	private Query query;

	private MondrianOlapHelper(DatabaseMeta databaseMeta, long catalogId,
			String mdx) {
		this.databaseMeta = databaseMeta;
		this.catalogId = catalogId;
		this.mdx = mdx;
	}

	/**
	 * 实例化一个OLAP帮助器
	 * 
	 * @param databaseMeta
	 * @param catalog
	 * @param mdx
	 * @return
	 */
	public static MondrianOlapHelper instance(DatabaseMeta databaseMeta,
			long catalogId, String mdx) {
		return new MondrianOlapHelper(databaseMeta, catalogId, mdx);
	}

	public void openConnection() throws BIDBException {
		connection = null;

		try {
			if (databaseMeta.getAccessType() == DatabaseMeta.TYPE_ACCESS_JNDI) {
				DataSource dataSource = DataSourceProviderFactory
						.getDataSourceProviderInterface().getNamedDataSource(
								databaseMeta.getDatabaseName());
				mondrian.olap.Util.PropertyList propList = new mondrian.olap.Util.PropertyList();
				propList.put("Provider", "mondrian");
				propList.put("Catalog", Long.toString(catalogId));
				propList.put("DynamicSchemaProcessor",
						SCHEMA_PROCESSOR_CLASS_STRING);

				connection = DriverManager.getConnection(propList, null,
						dataSource);
			} else {

				String connectString = "Provider=mondrian;" + "Jdbc='"
						+ databaseMeta.getURL() + "';" + "Catalog='"
						+ Long.toString(catalogId) + "';" + "JdbcDrivers="
						+ databaseMeta.getDriverClass() + ";"
						+ "DynamicSchemaProcessor="
						+ SCHEMA_PROCESSOR_CLASS_STRING + ";";

				if (!Const.isEmpty(databaseMeta.getUsername())) {
					connectString += "JdbcUser=" + databaseMeta.getUsername()
							+ ";";
				}
				if (!Const.isEmpty(databaseMeta.getPassword())) {
					connectString += "JdbcPassword="
							+ databaseMeta.getPassword() + ";";
				}

				connection = DriverManager.getConnection(connectString, null);

			}

		} catch (Exception e) {
			throw new BIDBException("创建数据库连接出现错误", e);
		}
	}

	/**
	 * 执行查询
	 * 
	 * @throws BIDBException
	 */
	public void query() throws BIDBException {
		if (connection == null) {
			openConnection();
		}
		query = connection.parseQuery(mdx);
		result = connection.execute(query);
	}

	/**
	 * 关闭帮助器
	 */
	public void close() {
		if (result != null)
			result.close();
		if (query != null)
			query.clone();
		if (connection != null)
			connection.close();
	}

	public DatabaseMeta getDatabaseMeta() {
		return databaseMeta;
	}

	public long getCatalogId() {
		return catalogId;
	}

	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

}
