package com.flywet.platform.bi.pivot.model.core;

import org.apache.log4j.Logger;
import org.pentaho.di.core.database.DatabaseMeta;

import com.flywet.platform.bi.pivot.model.mondrian.MondrianModel;

/**
 * 通过配置创建一个MondrianModel对象
 * 
 * @author PeterPan
 * 
 */
public class MondrianModelFactory {

	private static Logger logger = Logger.getLogger(MondrianModelFactory.class);

	private MondrianModelFactory() {
	}

	public static MondrianModel instance(Config cfg) {

	}

	/**
	 * 创建Mondrian模型的配置对象
	 * 
	 * @author PeterPan
	 * 
	 */
	public static class Config {

		// 分析数据库的元数据
		private DatabaseMeta databaseMeta;

		// 分析模型ID
		private long catalogId;

		// MDX
		private String mdxQuery;

		public DatabaseMeta getDatabaseMeta() {
			return databaseMeta;
		}

		public void setDatabaseMeta(DatabaseMeta databaseMeta) {
			this.databaseMeta = databaseMeta;
		}

		public long getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(long catalogId) {
			this.catalogId = catalogId;
		}

		public String getMdxQuery() {
			return mdxQuery;
		}

		public void setMdxQuery(String mdxQuery) {
			this.mdxQuery = mdxQuery;
		}

	}
}
