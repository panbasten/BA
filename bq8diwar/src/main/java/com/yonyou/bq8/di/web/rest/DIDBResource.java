package com.yonyou.bq8.di.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseConnectionPoolParameter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.MySQLDatabaseMeta;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.component.components.grid.GridDataObject;
import com.yonyou.bq8.di.component.components.selectMenu.OptionsData;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.PageTemplateInterpolator;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.web.entity.AjaxResult;
import com.yonyou.bq8.di.web.entity.AjaxResultEntity;
import com.yonyou.bq8.di.web.model.ParameterContext;
import com.yonyou.bq8.di.web.service.DIDatabaseDelegates;
import com.yonyou.bq8.di.web.utils.DIWebUtils;

@Service("di.resource.dbResource")
@Path("/db")
public class DIDBResource {
	private final Logger log = Logger.getLogger(DIDBResource.class);

	private static final String DB_SETTING_TEMPLATE = "editor/db/setting.h";

	private static final String DB_BASE_TEMPLATE = "editor/db/tabs/base.h";

	private static final String DB_CONNECTION_SETTING_TEMPLATE = "editor/db/tabs/setting/_setting.h";

	private static final String ID_EDITOR_CONTENT_NAVI_DB_BP = "editorContent-navi-db-bp";

	@Resource(name = "di.service.databaseServices")
	private DIDatabaseDelegates dbDelegates;

	/**
	 * 创建数据库页面
	 * 
	 * @throws DIException
	 */
	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDatabase(
			@CookieParam("repository") String repository) throws DIException {
		try {
			BrowseMeta browseMeta = new BrowseMeta();
			dbDelegates.getNavigatorsDatabase(repository, browseMeta);
			browseMeta.addClass("hb-browsepanel");

			AjaxResultEntity browseResult = AjaxResultEntity.instance()
					.setOperation(Utils.RESULT_OPERATION_CUSTOM).setTargetId(
							ID_EDITOR_CONTENT_NAVI_DB_BP).setData(browseMeta);
			browseResult.setCmd("widget.BrowsePanel");

			return AjaxResult.instance().addEntity(browseResult).toJSONString();
		} catch (Exception e) {
			log.error("创建导航的数据库连接内容页面出现错误");
			throw new DIException("创建导航的数据库连接内容页面出现错误", e);
		}
	}

	/**
	 * 改变连接设置
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws DIException
	 */
	@GET
	@Path("/connectionsetting/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String changeConnectionSetting(
			@CookieParam("repository") String repository,
			@PathParam("id") String id,
			@QueryParam("targetId") String targetId,
			@QueryParam("connectionType") String connectionType,
			@QueryParam("accessType") String accessType) throws DIException {
		try {
			BQVariableResolver attrsMap = BQVariableResolver.instance();

			// 1.取得数据库元数据
			DatabaseMeta dbMeta = dbDelegates.getDatabaseMeta(repository, Long
					.valueOf(id));
			dbMeta.setDatabaseType(connectionType);
			int accessTypeInt = Integer.valueOf(accessType);
			attrsMap.addVariable("dbMeta", dbMeta);
			String formId = "db_" + id;
			attrsMap.addVariable("formId", formId);

			// 2.访问方式
			int[] accessTypes = dbMeta.getDatabaseInterface()
					.getAccessTypeList();
			OptionsData opts = new OptionsData();
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
			setConnectionSettings(settings, dbMeta);
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
			throw new DIException("创建导航页面出现错误。", ex);
		}
	}

	@POST
	@Path("/object/{id}/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void saveSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, String body) {
		// List<InPart> parts = bimp.getParts();
		try {
			ParameterContext paramContext = DIWebUtils
					.fillParameterContext(body);
			String aaa = paramContext.getParameter("db_1:id");
			System.out.println(id);
		} catch (Exception e) {
			
		}
	}

	/**
	 * 打开数据库连接设置
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws DIException
	 */
	@GET
	@Path("/object/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openSetting(@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws DIException {
		try {

			BQVariableResolver attrsMap = BQVariableResolver.instance();

			// 初始化数据
			// 1.数据库元数据
			DatabaseMeta dbMeta = dbDelegates.getDatabaseMeta(repository, Long
					.valueOf(id));
			attrsMap.addVariable("dbMeta", dbMeta);
			String formId = "db_" + dbMeta.getObjectId().getId();
			attrsMap.addVariable("formId", formId);
			// 2.数据库插件
			PluginRegistry registry = PluginRegistry.getInstance();
			final List<PluginInterface> dbTypes = registry
					.getPlugins(DatabasePluginType.class);
			attrsMap.addVariable("dbTypes", dbTypes);

			int[] accessTypes = dbMeta.getDatabaseInterface()
					.getAccessTypeList();
			OptionsData opts = new OptionsData();
			for (int accType : accessTypes) {
				opts.addOption(String.valueOf(accType), DatabaseMeta
						.getAccessTypeDescLong(accType));
			}
			attrsMap.addVariable("accessTypes", opts.getOptions());
			// 3.数据库连接设置
			List<String> settings = new ArrayList<String>();
			setConnectionSettings(settings, dbMeta);

			// 4.属性
			attrsMap.addVariable("connectionProperties", GridDataObject
					.instance(dbMeta.getConnectionProperties()).setMinRows(
							HTML.DEFAULT_GRID_ROW_NUMBER));
			attrsMap.addVariable("poolingParameters", GridDataObject
					.instance(getConnectionPoolParameters(dbMeta)));
			attrsMap.addVariable("partitioningInformations", GridDataObject
					.instance().putObjects(dbMeta.getPartitioningInformation())
					.setMinRows(HTML.DEFAULT_GRID_ROW_NUMBER));

			attrsMap.addVariable("connectionSettings", settings);

			Object[] domString = PageTemplateInterpolator.interpolate(
					DB_SETTING_TEMPLATE, attrsMap);

			// 返回页面控制
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception ex) {
			throw new DIException("创建导航页面出现错误。", ex);
		}
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

	private void setConnectionSettings(List<String> settings,
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
}
