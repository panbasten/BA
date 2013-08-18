package com.flywet.platform.bi.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseConnectionPoolParameter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.DatabaseMetaInformation;
import org.pentaho.di.core.database.MySQLDatabaseMeta;
import org.pentaho.di.core.database.PartitionDatabaseMeta;
import org.pentaho.di.core.database.Schema;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.component.components.grid.GridDataObject;
import com.flywet.platform.bi.component.components.selectMenu.OptionsData;
import com.flywet.platform.bi.component.components.tree.FLYTreeMeta;
import com.flywet.platform.bi.component.components.tree.TreeNodeMeta;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.web.entity.ActionMessage;
import com.flywet.platform.bi.web.entity.AjaxResult;
import com.flywet.platform.bi.web.entity.AjaxResultEntity;
import com.flywet.platform.bi.web.model.ParameterContext;
import com.flywet.platform.bi.web.service.BIDatabaseDelegates;
import com.flywet.platform.bi.web.utils.BIWebUtils;

@Service("bi.resource.dbResource")
@Path("/db")
public class BIDBResource {
	private final Logger log = Logger.getLogger(BIDBResource.class);

	private static final String DB_EXPLORER_TEMPLATE = "editor/db/explorer.h";

	private static final String DB_SETTING_TEMPLATE = "editor/db/setting.h";

	private static final String DB_BASE_TEMPLATE = "editor/db/tabs/base.h";

	private static final String DB_CONNECTION_SETTING_TEMPLATE = "editor/db/tabs/setting/_setting.h";

	private static final String ID_EDITOR_CONTENT_NAVI_DB_BP = "editorContent-navi-db-bp";

	@Resource(name = "bi.service.databaseServices")
	private BIDatabaseDelegates dbDelegates;

	private String buildNaviContent(String repository, boolean isNew)
			throws BIException {
		try {
			BrowseMeta browseMeta = new BrowseMeta();
			dbDelegates.getNavigatorsDatabase(repository, browseMeta);
			browseMeta.addClass("fly-browsepanel");

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_DB_BP).setData(browseMeta);
			browseResult.setCmd(isNew ? "widget.BrowsePanel" : "this.flush");

			return AjaxResult.instance().addEntity(browseResult).toJSONString();
		} catch (Exception e) {
			log.error("创建导航的数据库连接内容页面出现错误");
			throw new BIException("创建导航的数据库连接内容页面出现错误", e);
		}
	}

	/**
	 * 创建数据库页面
	 * 
	 * @throws BIException
	 */
	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDatabase(
			@CookieParam("repository") String repository) throws BIException {
		return buildNaviContent(repository, true);
	}

	@GET
	@Path("/flush")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContentDatabase(
			@CookieParam("repository") String repository) throws BIException {
		return buildNaviContent(repository, false);
	}

	/**
	 * 改变连接设置
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/connectionsetting/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String changeConnectionSetting(
			@CookieParam("repository") String repository,
			@PathParam("id") String id,
			@QueryParam("targetId") String targetId,
			@QueryParam("connectionType") String connectionType,
			@QueryParam("accessType") String accessType) throws BIException {
		try {
			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			// 1.取得数据库元数据
			DatabaseMeta dbMeta = null;
			if (id.equals("create")) {
				dbMeta = initDatabaseMeta();
			} else {
				dbMeta = dbDelegates.getDatabaseMeta(repository, Long
						.valueOf(id));
			}
			dbMeta.setDatabaseType(connectionType);
			int accessTypeInt = Integer.valueOf(accessType);
			attrsMap.addVariable("dbMeta", dbMeta);
			String formId = "db_" + id;
			attrsMap.addVariable("formId", formId);

			// 2.访问方式
			int[] accessTypes = dbMeta.getDatabaseInterface()
					.getAccessTypeList();
			OptionsData opts = OptionsData.instance();
			boolean marchAccType = false;
			for (int accType : accessTypes) {
				if (accessTypeInt == accType)
					marchAccType = true;
				opts.addOption(String.valueOf(accType), DatabaseMeta
						.getAccessTypeDescLong(accType));
			}
			if (!marchAccType) {
				accessTypeInt = accessTypes[0];
			}
			dbMeta.setAccessType(accessTypeInt);
			attrsMap.addVariable("accessTypes", opts.getOptions());

			// 3.连接区
			List<String> settings = new ArrayList<String>();
			getConnectionSettings(settings, dbMeta);
			attrsMap.addVariable("connectionSettings", settings);

			// 返回页面控制
			// 1.清空内容区
			AjaxResultEntity empty = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_EMPTY).setTargetId(targetId);

			// 2.方式类型
			Object[] domString = PageTemplateInterpolator.interpolate(
					DB_BASE_TEMPLATE, attrsMap, "${formId}:accessType");
			AjaxResultEntity acc = AjaxResultEntity.instance().setOperation(
					Utils.RESULT_OPERATION_UPDATE).setTargetId(
					formId + ":accessType").setDomAndScript(domString);

			// 3.添加连接配置页
			domString = PageTemplateInterpolator.interpolate(
					DB_CONNECTION_SETTING_TEMPLATE, attrsMap);
			AjaxResultEntity content = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_APPEND).setTargetId(
							targetId).setDomAndScript(domString);

			return AjaxResult.instance().addEntity(acc).addEntity(empty)
					.addEntity(content).toJSONString();
		} catch (Exception ex) {
			throw new BIException("改变链接设置出现错误。", ex);
		}
	}

	private void checkDatabaseMeta(DatabaseMeta dbMeta,
			ParameterContext paramContext, String DB_PREFIX, String repository)
			throws BIException {
		String oldName = Const.NVL(dbMeta.getName(), "");
		String newName = Const.NVL(paramContext
				.getParameter(DB_PREFIX + "name"), "");
		if (Const.isEmpty(newName)) {
			throw new BIException("数据库链接名称不能为空");
		}

		if (!oldName.equals(newName)) {
			boolean exist = dbDelegates.existDatabaseMeta(repository, newName);
			if (exist) {
				throw new BIException("数据库链接名称重复");
			}
		}

	}

	@POST
	@Path("/object/{id}/explore")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String exploreSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id,
			@QueryParam("targetId") String targetId, String body)
			throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			String DB_PREFIX = "db_" + id + ":";

			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);
			DatabaseMeta dbMeta = null;
			if (id.equals("create")) {
				dbMeta = initDatabaseMeta();
			} else {
				DatabaseMeta dbMeta0 = dbDelegates.getDatabaseMeta(repository,
						Long.valueOf(id));
				dbMeta = (DatabaseMeta) dbMeta0.clone();
			}

			// 检查数据库元数据
			checkDatabaseMeta(dbMeta, paramContext, DB_PREFIX, repository);

			// 设置数据库元数据
			setDatebaseMeta(dbMeta, paramContext, DB_PREFIX);

			Object[] msg = dbMeta.testConnectionWithState();
			// 测试成功
			if ((Boolean) msg[0]) {
				if (dbMeta.getAccessType() != DatabaseMeta.TYPE_ACCESS_PLUGIN) {
					return createExploreDialog(dbMeta, targetId);
				} else {
					return am.failure("对不起，针对该数据库的浏览还没有实现！").toJSONString();
				}
			}
			return am.failure((String) msg[1]).toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return am.failure(e.getMessage()).toJSONString();
		} catch (Exception e) {
			log.error("浏览数据库出现错误。");
			return am.failure("浏览数据库出现错误！").toJSONString();
		}
	}

	private String createExploreDialog(DatabaseMeta dbMeta, String targetId)
			throws BIException {
		try {

			FLYVariableResolver attrsMap = FLYVariableResolver.instance();

			String id = "db_explore";

			FLYTreeMeta db_tree = new FLYTreeMeta();

			DatabaseMetaInformation dmi = new DatabaseMetaInformation(dbMeta);
			dmi.getData(null, null);

			// Schemas
			TreeNodeMeta schemasNode = TreeNodeMeta.instance();
			schemasNode.setText("数据库元数据");
			db_tree.addContent(schemasNode);

			Schema[] schemas = dmi.getSchemas();
			if (schemas != null) {
				for (Schema s : schemas) {
					TreeNodeMeta tree = TreeNodeMeta.instance();
					tree.setText(s.getSchemaName());

					schemasNode.addContent(tree);
				}
			}

			// Tables
			TreeNodeMeta tablesNode = TreeNodeMeta.instance();
			tablesNode.setText("数据库表");
			db_tree.addContent(tablesNode);

			String[] tables = dmi.getTables();
			if (tables != null) {
				for (String t : tables) {
					TreeNodeMeta tree = TreeNodeMeta.instance();
					tree.setText(t);

					tablesNode.addContent(tree);
				}
			}

			attrsMap.addVariable("id", id);
			attrsMap.addVariable("db_tree", db_tree);

			Object[] domString = PageTemplateInterpolator.interpolate(
					DB_EXPLORER_TEMPLATE, attrsMap);

			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();

		} catch (KettleDatabaseException e) {
			throw new BIException("获得数据库信息出现错误。");
		}
	}

	@POST
	@Path("/object/{id}/test")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String testSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, String body) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			String DB_PREFIX = "db_" + id + ":";

			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);
			DatabaseMeta dbMeta = null;
			if (id.equals("create")) {
				dbMeta = initDatabaseMeta();
			} else {
				DatabaseMeta dbMeta0 = dbDelegates.getDatabaseMeta(repository,
						Long.valueOf(id));
				dbMeta = (DatabaseMeta) dbMeta0.clone();
			}

			// 检查数据库元数据
			checkDatabaseMeta(dbMeta, paramContext, DB_PREFIX, repository);

			// 设置数据库元数据
			setDatebaseMeta(dbMeta, paramContext, DB_PREFIX);

			Object[] msg = dbMeta.testConnectionWithState();
			if ((Boolean) msg[0]) {
				return am.success((String) msg[1]).toJSONString();
			}
			return am.failure((String) msg[1]).toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return am.failure(e.getMessage()).toJSONString();
		} catch (Exception e) {
			log.error("测试数据库设置出现错误。");
			return am.failure("测试数据库设置出现错误！").toJSONString();
		}

	}

	@POST
	@Path("/object/{id}/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, String body) throws BIException {
		ActionMessage am = ActionMessage.instance();
		try {
			String DB_PREFIX = "db_" + id + ":";

			ParameterContext paramContext = BIWebUtils
					.fillParameterContext(body);
			DatabaseMeta dbMeta = null;
			if (id.equals("create")) {
				dbMeta = initDatabaseMeta();
			} else {
				dbMeta = dbDelegates.getDatabaseMeta(repository, Long
						.valueOf(id));
			}

			// 判断是否要在保存成功后删除原有数据源对象
			String oldName = Const.NVL(dbMeta.getName(), "");
			String newName = Const.NVL(paramContext.getParameter(DB_PREFIX
					+ "name"), "");

			// 检查数据库元数据
			checkDatabaseMeta(dbMeta, paramContext, DB_PREFIX, repository);

			// 设置数据库元数据
			setDatebaseMeta(dbMeta, paramContext, DB_PREFIX);

			// 保存到数据库
			dbDelegates.saveDatabaseMeta(repository, dbMeta);

			// 删除旧名称的数据库对象
			if (!oldName.equals(newName) && !Const.isEmpty(oldName)) {
				dbDelegates.deleteDatabaseMeta(repository, oldName);
			}

			return am.success("保存数据库设置成功！").toJSONString();
		} catch (BIException e) {
			log.error(e.getMessage());
			return am.failure(e.getMessage()).toJSONString();
		} catch (Exception e) {
			log.error("保存数据库设置出现错误。");
			return am.failure("保存数据库设置出现错误！").toJSONString();
		}

	}

	/**
	 * 设置数据库元数据
	 * 
	 * @param dbMeta
	 * @param paramContext
	 * @param DB_PREFIX
	 * @throws Exception
	 */
	private void setDatebaseMeta(DatabaseMeta dbMeta,
			ParameterContext paramContext, String DB_PREFIX) throws Exception {
		// 1.Base
		setBase(dbMeta, paramContext, DB_PREFIX);

		// 2.Advance
		setAdvance(dbMeta, paramContext, DB_PREFIX);

		// 3.option
		setOption(dbMeta, paramContext, DB_PREFIX);

		// 4.pool
		setPooling(dbMeta, paramContext, DB_PREFIX);

		// 5.cluster
		setCluster(dbMeta, paramContext, DB_PREFIX);
	}

	private void setCluster(DatabaseMeta dbMeta, ParameterContext paramContext,
			String DB_PREFIX) throws Exception {
		boolean partitioned = paramContext.getBooleanParameter(DB_PREFIX
				+ "partitioned");
		dbMeta.setPartitioned(partitioned);

		if (partitioned) {
			JSONArray ja = JSONUtils.convertStringToJSONArray(paramContext
					.getParameter(DB_PREFIX + "partitioningInformations"
							+ HTML.DATA_GRID_SUFFIX));

			List<PartitionDatabaseMeta> pdms = new ArrayList<PartitionDatabaseMeta>();
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				String partitionId = JSONUtils.NVL_JSON(jo, "partitionId", "");
				String hostname = JSONUtils.NVL_JSON(jo, "hostname", "");
				String port = JSONUtils.NVL_JSON(jo, "port", "");
				String databaseName = JSONUtils
						.NVL_JSON(jo, "databaseName", "");
				String username = JSONUtils.NVL_JSON(jo, "username", "");
				String password = JSONUtils.NVL_JSON(jo, "password", "");

				// TODO
			}
		}
	}

	private void setPooling(DatabaseMeta dbMeta, ParameterContext paramContext,
			String DB_PREFIX) throws Exception {
		boolean usingConnectionPool = paramContext
				.getBooleanParameter(DB_PREFIX + "usingConnectionPool");
		dbMeta.setUsingConnectionPool(usingConnectionPool);

		if (usingConnectionPool) {
			int initialPoolSize = paramContext.getIntParameter(DB_PREFIX
					+ "initialPoolSize", 0);
			dbMeta.setInitialPoolSize(initialPoolSize);

			int maximumPoolSize = paramContext.getIntParameter(DB_PREFIX
					+ "maximumPoolSize", 0);
			dbMeta.setMaximumPoolSize(maximumPoolSize);

			JSONArray ja = JSONUtils.convertStringToJSONArray(paramContext
					.getParameter(DB_PREFIX + "poolingParameters"
							+ HTML.DATA_GRID_SUFFIX));
			Properties properties = new Properties();
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				if (!JSONUtils.isJSONEmpty(jo, "key")) {
					properties.put(JSONUtils.NVL_JSON(jo, "key", ""), JSONUtils
							.NVL_JSON(jo, "value", ""));
				}
			}
			dbMeta.setConnectionPoolingProperties(properties);
		}
	}

	private void setOption(DatabaseMeta dbMeta, ParameterContext paramContext,
			String DB_PREFIX) throws Exception {
		JSONArray ja = JSONUtils.convertStringToJSONArray(paramContext
				.getParameter(DB_PREFIX + "options" + HTML.DATA_GRID_SUFFIX));
		String dbType = dbMeta.getPluginId();

		for (int i = 0; i < ja.size(); i++) {
			JSONObject jo = (JSONObject) ja.get(i);
			if (!JSONUtils.isJSONEmpty(jo, "key")) {
				dbMeta.addExtraOption(dbType, (String) jo.get("key"), JSONUtils
						.NVL_JSON(jo, "value", ""));
			}
		}
	}

	private void setBase(DatabaseMeta dbMeta, ParameterContext paramContext,
			String DB_PREFIX) {
		dbMeta.setName(Const.NVL(paramContext.getParameter(DB_PREFIX + "name"),
				""));
		dbMeta.setDatabaseType(paramContext.getParameter(DB_PREFIX
				+ "connectionType"));
		dbMeta.setAccessType(Integer.valueOf(paramContext
				.getParameter(DB_PREFIX + "accessType")));

		setConnectionSettings(dbMeta, paramContext, DB_PREFIX);
	}

	private void setAdvance(DatabaseMeta dbMeta, ParameterContext paramContext,
			String DB_PREFIX) {
		boolean SUPPORTS_BOOLEAN_DATA_TYPE = Utils.toBoolean(paramContext
				.getParameter(DB_PREFIX + "SUPPORTS_BOOLEAN_DATA_TYPE"), false);
		dbMeta.getAttributes().put("SUPPORTS_BOOLEAN_DATA_TYPE",
				String.valueOf(SUPPORTS_BOOLEAN_DATA_TYPE));

		boolean QUOTE_ALL_FIELDS = Utils.toBoolean(paramContext
				.getParameter(DB_PREFIX + "QUOTE_ALL_FIELDS"), false);
		dbMeta.getAttributes().put("QUOTE_ALL_FIELDS",
				String.valueOf(QUOTE_ALL_FIELDS));

		boolean FORCE_IDENTIFIERS_TO_LOWERCASE = Utils.toBoolean(paramContext
				.getParameter(DB_PREFIX + "FORCE_IDENTIFIERS_TO_LOWERCASE"),
				false);
		dbMeta.getAttributes().put("FORCE_IDENTIFIERS_TO_LOWERCASE",
				String.valueOf(FORCE_IDENTIFIERS_TO_LOWERCASE));

		boolean FORCE_IDENTIFIERS_TO_UPPERCASE = Utils.toBoolean(paramContext
				.getParameter(DB_PREFIX + "FORCE_IDENTIFIERS_TO_UPPERCASE"),
				false);
		dbMeta.getAttributes().put("FORCE_IDENTIFIERS_TO_UPPERCASE",
				String.valueOf(FORCE_IDENTIFIERS_TO_UPPERCASE));

		dbMeta.setPreferredSchemaName(Const.NVL(paramContext
				.getParameter(DB_PREFIX + "preferredSchemaName"), ""));

		dbMeta.setConnectSQL(Const.NVL(paramContext.getParameter(DB_PREFIX
				+ "connectSQL"), ""));
	}

	private DatabaseMeta initDatabaseMeta() {
		DatabaseMeta dbMeta = new DatabaseMeta();
		dbMeta.initializeVariablesFrom(null);
		return dbMeta;
	}

	@DELETE
	@Path("/object/{name}/remove")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeSetting(@CookieParam("repository") String repository,
			@PathParam("name") String name) throws BIJSONException {
		ActionMessage am = ActionMessage.instance();
		try {
			dbDelegates.deleteDatabaseMeta(repository, name);
			return am.success("删除数据库设置成功！").toJSONString();
		} catch (Exception ex) {
			log.error("删除数据库设置出现错误。");
			return am.failure("删除数据库设置出现错误。").toJSONString();

		}
	}

	@GET
	@Path("/create")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@CookieParam("repository") String repository,
			@QueryParam("targetId") String targetId) throws BIException {
		try {
			DatabaseMeta dbMeta = initDatabaseMeta();

			FLYVariableResolver attrsMap = createDatabaseSettingVariables(dbMeta);

			Object[] domString = PageTemplateInterpolator.interpolate(
					DB_SETTING_TEMPLATE, attrsMap);

			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建导航页面出现错误。", ex);
		}

	}

	/**
	 * 打开数据库连接设置
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	@GET
	@Path("/object/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws BIException {
		try {

			// 初始化数据
			DatabaseMeta dbMeta = dbDelegates.getDatabaseMeta(repository, Long
					.valueOf(id));

			FLYVariableResolver attrsMap = createDatabaseSettingVariables(dbMeta);

			Object[] domString = PageTemplateInterpolator.interpolate(
					DB_SETTING_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new BIException("创建导航页面出现错误。", ex);
		}
	}

	private FLYVariableResolver createDatabaseSettingVariables(
			DatabaseMeta dbMeta) {
		FLYVariableResolver attrsMap = FLYVariableResolver.instance();

		// 1.数据库元数据
		attrsMap.addVariable("dbMeta", dbMeta);
		String id = (dbMeta.getObjectId() == null) ? "create" : dbMeta
				.getObjectId().getId();
		String formId = "db_" + id;
		attrsMap.addVariable("dbId", id);
		attrsMap.addVariable("formId", formId);

		// 2.数据库插件
		PluginRegistry registry = PluginRegistry.getInstance();
		final List<PluginInterface> dbTypes = registry
				.getPlugins(DatabasePluginType.class);
		attrsMap.addVariable("dbTypes", dbTypes);

		int[] accessTypes = dbMeta.getDatabaseInterface().getAccessTypeList();
		OptionsData opts = OptionsData.instance();
		for (int accType : accessTypes) {
			opts.addOption(String.valueOf(accType), DatabaseMeta
					.getAccessTypeDescLong(accType));
		}
		attrsMap.addVariable("accessTypes", opts.getOptions());

		// 3.数据库连接设置
		List<String> settings = new ArrayList<String>();
		getConnectionSettings(settings, dbMeta);

		// 4.属性
		attrsMap.addVariable("connectionProperties", GridDataObject.instance(
				dbMeta.getConnectionProperties()).setMinRows(
				HTML.DEFAULT_GRID_ROW_NUMBER));
		attrsMap.addVariable("poolingParameters", GridDataObject
				.instance(getConnectionPoolParameters(dbMeta)));
		attrsMap.addVariable("partitioningInformations", GridDataObject
				.instance().putObjects(dbMeta.getPartitioningInformation())
				.setMinRows(HTML.DEFAULT_GRID_ROW_NUMBER));

		attrsMap.addVariable("connectionSettings", settings);

		return attrsMap;
	}

	private Properties getConnectionPoolParameters(DatabaseMeta dbMeta) {
		Properties cpp = dbMeta.getConnectionPoolingProperties();
		Properties props = new Properties();
		for (DatabaseConnectionPoolParameter p : BaseDatabaseMeta.poolingParameters) {
			String parameter = p.getParameter();
			String value = cpp.getProperty(parameter, p.getDefaultValue());
			props.put(parameter, (value != null) ? value : "");
		}
		return props;
	}

	/**
	 * 获取数据库链接设置
	 * 
	 * @param settings
	 * @param dbMeta
	 */
	private void getConnectionSettings(List<String> settings,
			DatabaseMeta dbMeta) {

		String accessTypeDesc = dbMeta.getAccessTypeDesc();
		String databaseTypeName = dbMeta.getDatabaseInterface().getPluginName();
		if ("Plugin".equals(accessTypeDesc)) {
			if ("SAPR3".equals(databaseTypeName)) {
				settings.add("SAPR3.h");
			} else {
				settings.add("Native.h");
			}
		} else {
			settings.add(accessTypeDesc + ".h");
		}

		if ("ORACLE".equals(databaseTypeName)) {
			settings.add("ORACLE.h");
		}

		if ("INFORMIX".equals(databaseTypeName)
				&& !"ODBC".equals(accessTypeDesc)) {
			settings.add("INFORMIX.h");
		}

		if (!"JNDI".equals(accessTypeDesc)) {
			settings.add("User.h");
		}

		if (dbMeta.getDatabaseInterface() instanceof MySQLDatabaseMeta) {
			settings.add("MYSQL.h");
		}
	}

	private void setConnectionSettings(DatabaseMeta dbMeta,
			ParameterContext paramContext, String DB_PREFIX) {
		String accessTypeDesc = dbMeta.getAccessTypeDesc();
		String databaseTypeName = dbMeta.getDatabaseInterface().getPluginName();
		if ("Plugin".equals(accessTypeDesc)) {
			if ("SAPR3".equals(databaseTypeName)) {
				// SAPR3.h
				dbMeta.setHostname(Const.NVL(paramContext
						.getParameter(DB_PREFIX + "hostname"), ""));
				dbMeta.getAttributes().put(
						"SAPSystemNumber",
						Const.NVL(paramContext.getParameter(DB_PREFIX
								+ "SAPSystemNumber"), ""));
				dbMeta.getAttributes().put(
						"SAPClient",
						Const.NVL(paramContext.getParameter(DB_PREFIX
								+ "SAPClient"), ""));
				dbMeta.getAttributes().put(
						"SAPLanguage",
						Const.NVL(paramContext.getParameter(DB_PREFIX
								+ "SAPLanguage"), ""));
			} else {
				// Native.h
				dbMeta.setHostname(Const.NVL(paramContext
						.getParameter(DB_PREFIX + "hostname"), ""));
				dbMeta.setDBName(Const.NVL(paramContext.getParameter(DB_PREFIX
						+ "databaseName"), ""));
				dbMeta.setDBPort(Const.NVL(paramContext.getParameter(DB_PREFIX
						+ "databasePortNumberString"), ""));
			}
		} else {
			// accessTypeDesc + .h
			// JNDI.h
			if ("JNDI".equals(accessTypeDesc)) {
				dbMeta.setDBName(Const.NVL(paramContext.getParameter(DB_PREFIX
						+ "databaseName"), ""));
			}
			// ODBC.h
			else if ("ODBC".equals(accessTypeDesc)) {
				dbMeta.setDBName(Const.NVL(paramContext.getParameter(DB_PREFIX
						+ "databaseName"), ""));
			}
			// OCI.h
			else if ("OCI".equals(accessTypeDesc)) {
				dbMeta.setDBName(Const.NVL(paramContext.getParameter(DB_PREFIX
						+ "databaseName"), ""));
			}
		}

		if ("ORACLE".equals(databaseTypeName)) {
			// ORACLE.h
			dbMeta.setDataTablespace(Const.NVL(paramContext
					.getParameter(DB_PREFIX + "dataTablespace"), ""));
			dbMeta.setIndexTablespace(Const.NVL(paramContext
					.getParameter(DB_PREFIX + "indexTablespace"), ""));
		}

		if ("INFORMIX".equals(databaseTypeName)
				&& !"ODBC".equals(accessTypeDesc)) {
			// INFORMIX.h
			dbMeta.setServername(Const.NVL(paramContext.getParameter(DB_PREFIX
					+ "servername"), ""));
		}

		if (!"JNDI".equals(accessTypeDesc)) {
			// User.h
			dbMeta.setUsername(Const.NVL(paramContext.getParameter(DB_PREFIX
					+ "username"), ""));
			dbMeta.setPassword(Const.NVL(paramContext.getParameter(DB_PREFIX
					+ "password"), ""));
		}

		if (dbMeta.getDatabaseInterface() instanceof MySQLDatabaseMeta) {
			// MYSQL.h
			dbMeta.setStreamingResults(Utils.toBoolean(paramContext
					.getParameter(DB_PREFIX + "streamingResults"), false));
		}
	}
}
