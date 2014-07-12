package com.flywet.platform.bi.di.rest;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.DateUtils;
import com.flywet.platform.bi.di.model.DateDimension;
import com.flywet.platform.bi.di.model.TimeDimension;
import com.flywet.platform.bi.model.BIDatabaseConnectionDelegate;
import com.flywet.platform.bi.services.intf.BIDatabaseDelegates;

@Service("bi.resource.diWizardResource")
@Path("/diWizard")
public class BIDIWizardResource {
	private final Logger logger = Logger.getLogger(BIDIWizardResource.class);

	private static Class<?> PKG = BIDIWizardResource.class;

	@Resource(name = "bi.service.databaseServices")
	private BIDatabaseDelegates dbDelegates;

	@GET
	@Path("/createTimeDim")
	@Produces(MediaType.TEXT_PLAIN)
	public String createTimeDim(@QueryParam("targetId") String targetId)
			throws BIException {
		ActionMessage am = new ActionMessage();
		BIDatabaseConnectionDelegate dcd = dbDelegates
				.getDatabaseConnectionDelegate(3L);

		try {
			dcd.connect();

			String startDateStr = "1900-01-01 00:00:00";
			Date startDate = DateUtils.parseDate(startDateStr);

			Calendar sdCal = Calendar.getInstance();
			sdCal.setTime(startDate);
			TimeDimension td;

			for (int i = 0; i < 60 * 60 * 60; i++) {
				td = TimeDimension.instance(sdCal.getTime());

				RowMetaAndData rmd = createTimeDimMAD(td);
				dcd.insertTableRow("BIHR_D_TIME", rmd);

				sdCal.add(Calendar.SECOND, 1);

				if (i % 1000 == 0) {
					dcd.commit();
				}
			}

			am.addMessage("创建时间维度成功");
		} catch (Exception e) {
			logger.error("创建时间维度失败", e);
			am.addMessage("创建时间维度失败");
		} finally {
			dcd.disconnect();
		}

		return am.toJSONString();
	}

	private RowMetaAndData createTimeDimMAD(TimeDimension td) {
		RowMetaAndData rmd = new RowMetaAndData();

		rmd.addValue("ID_TIME", ValueMetaInterface.TYPE_STRING, td.getId());
		rmd.addValue("DESC", ValueMetaInterface.TYPE_STRING, td.getDesc());

		rmd.addValue("HH", ValueMetaInterface.TYPE_STRING, td.getHh());
		rmd.addValue("MM", ValueMetaInterface.TYPE_STRING, td.getMm());
		rmd.addValue("SS", ValueMetaInterface.TYPE_STRING, td.getSs());
		rmd.addValue("HH_MM", ValueMetaInterface.TYPE_STRING, td.getHhmm());
		rmd.addValue("HH_MM_SS", ValueMetaInterface.TYPE_STRING, td.getHhmmss());
		rmd.addValue("MM_SS", ValueMetaInterface.TYPE_STRING, td.getMmss());
		rmd.addValue("INTERVAL_DESC", ValueMetaInterface.TYPE_STRING,
				td.getIntervalDesc());

		rmd.addValue("HOUR", ValueMetaInterface.TYPE_INTEGER,
				Integer.valueOf(td.getHour()).longValue());
		rmd.addValue("MINUTE", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(td.getMinute()).longValue());
		rmd.addValue("SECOND", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(td.getSecond()).longValue());

		rmd.addValue("MINUTE_IDX", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(td.getMinuteIdx()).longValue());
		rmd.addValue("SECOND_IDX", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(td.getSecondIdx()).longValue());

		return rmd;
	}

	@GET
	@Path("/createDateDim")
	@Produces(MediaType.TEXT_PLAIN)
	public String createDateDim(@QueryParam("targetId") String targetId)
			throws BIException {
		ActionMessage am = new ActionMessage();
		BIDatabaseConnectionDelegate dcd = dbDelegates
				.getDatabaseConnectionDelegate(3L);

		try {
			dcd.connect();

			String startDateStr = "1900-01-01";
			String endDateStr = "2050-12-31";

			Date startDate = DateUtils.parseDate(startDateStr);
			Date endDate = DateUtils.parseDate(endDateStr);

			Calendar sdCal = Calendar.getInstance();
			sdCal.setTime(startDate);
			DateDimension dd;

			for (int i = 0; i < DateUtils.diffDays(endDate, startDate); i++) {
				dd = DateDimension.instance(sdCal.getTime());

				RowMetaAndData rmd = createDateDimMAD(dd);
				dcd.insertTableRow("BIHR_D_DATE", rmd);

				sdCal.add(Calendar.DAY_OF_MONTH, 1);

				if (i % 1000 == 0) {
					dcd.commit();
				}
			}

			am.addMessage("创建日期维度成功");
		} catch (Exception e) {
			logger.error("创建日期维度失败", e);
			am.addMessage("创建日期维度失败");
		} finally {
			dcd.disconnect();
		}

		return am.toJSONString();
	}

	private RowMetaAndData createDateDimMAD(DateDimension dd) {
		RowMetaAndData rmd = new RowMetaAndData();

		rmd.addValue("ID_DATE", ValueMetaInterface.TYPE_STRING, dd.getId());
		rmd.addValue("DESC", ValueMetaInterface.TYPE_STRING, dd.getDesc());

		rmd.addValue("WEEK", ValueMetaInterface.TYPE_STRING, dd.getWeek());
		rmd.addValue("WEEK_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getWeekInt()).longValue());

		rmd.addValue("TEN_DAY", ValueMetaInterface.TYPE_STRING, dd.getTenDay());
		rmd.addValue("TEN_DAY_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getTenDayInt()).longValue());

		rmd.addValue("YYYY", ValueMetaInterface.TYPE_STRING, dd.getYyyy());
		rmd.addValue("MM", ValueMetaInterface.TYPE_STRING, dd.getMm());
		rmd.addValue("MONTH_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getMonthInt()).longValue());

		rmd.addValue("DD", ValueMetaInterface.TYPE_STRING, dd.getDd());
		rmd.addValue("DATE_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getDateInt()).longValue());

		rmd.addValue("YYYY_MM", ValueMetaInterface.TYPE_STRING, dd.getYyyymm());
		rmd.addValue("YYYY_MM_DD", ValueMetaInterface.TYPE_STRING,
				dd.getYyyymmdd());
		rmd.addValue("MM_DD", ValueMetaInterface.TYPE_STRING, dd.getMmdd());
		rmd.addValue("HALF", ValueMetaInterface.TYPE_STRING, dd.getHalf());
		rmd.addValue("HALF_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getHalfInt()).longValue());

		rmd.addValue("QUARTER", ValueMetaInterface.TYPE_STRING, dd.getQuarter());
		rmd.addValue("QUARTER_INT", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getQuarterInt()).longValue());

		rmd.addValue("DATE_IDX", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getDateIdx()).longValue());
		rmd.addValue("WEEK_IDX", ValueMetaInterface.TYPE_INTEGER, Integer
				.valueOf(dd.getWeekIdx()).longValue());

		rmd.addValue("WEEK_END_FLAG", ValueMetaInterface.TYPE_STRING,
				dd.isWeekEndFlag() ? "Y" : "N");
		rmd.addValue("MONTH_END_FLAG", ValueMetaInterface.TYPE_STRING,
				dd.isMonthEndFlag() ? "Y" : "N");
		rmd.addValue("VACATION_FLAG", ValueMetaInterface.TYPE_STRING,
				dd.isVacationFlag() ? "Y" : "N");

		rmd.addValue("IMPORTANT_EVENT", ValueMetaInterface.TYPE_STRING,
				dd.getImportantEvent());

		return rmd;
	}

}
