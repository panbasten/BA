package com.flywet.platform.bi.cust.p002.services;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

@Service("cust.service.cyServices")
public class CYServices implements CYDelegates {

	private final Logger log = Logger.getLogger(CYServices.class);

	private static Class<?> PKG = CYServices.class;

	@Resource(name = "bi.service.databaseServices")
	private BIDatabaseDelegates dbDelegates;

	private static final String TEMPLATE_UPLOAD_EMPLOYEE_INFO = "updateEmployeeInfo.h";

	/**
	 * 上传员工信息-打开页面
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	@Override
	public String uploadEmployeeInfo(String targetId,
			HashMap<String, Object> context) throws BIJSONException {
		try {

			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			attrsMap.addVariable("menuId", context.get("id"));

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_UPLOAD_EMPLOYEE_INFO, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("上传员工信息界面出现问题。");
		}

		return ActionMessage.instance().failure("上传员工信息界面出现问题。").toJSONString();
	}

	/**
	 * 上传员工信息-操作
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	@Override
	public String uploadEmployeeInfoSubmit(String targetId,
			ParameterContext context) throws BIJSONException {

		try {
			// 1.处理2013离职
			String filename = "D:/_doc/03.工作目录/02.人员工时统计/201405-在职离职人员信息.xlsx";
			String sheetname = "2013离职";
//			OffJob2003Deal d1 = new OffJob2003Deal(filename, sheetname,
//					dbDelegates);
//			d1.deal();
			
			// 2.处理2014离职
			filename = "D:/_doc/03.工作目录/02.人员工时统计/201406.xlsx";
			sheetname = "离职";
			OffJob2014Deal d2 = new OffJob2014Deal(filename, sheetname,
					dbDelegates);
			d2.deal();

			return ActionMessage.instance().success("上传员工信息成功。").toJSONString();
		} catch (Exception e) {
			log.error("上传员工信息出现问题。", e);
		}

		return ActionMessage.instance().failure("上传员工信息出现问题。").toJSONString();
	}

}
