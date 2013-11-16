package com.flywet.platform.bi.base.rest;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.rest.AbastractDirectoryResource;
import com.flywet.platform.bi.services.intf.BIPageDelegates;

@Service("bi.resource.reportResource")
@Path("/report")
public class BIReportResource extends AbastractDirectoryResource {
	private final Logger logger = Logger.getLogger(BIReportResource.class);

	public final static long DIRECTORY_ROOT_ID = 1L;

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.REPORT;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentReport() throws BIException {
		return super.buildNaviContent(pageDelegates, DIR_CATEGORY.getRootId(),
				DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@PathParam("id") String id)
			throws BIException {
		return super.removeDirectory(pageDelegates, id, DIR_CATEGORY);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(@PathParam("id") String id,
			@QueryParam("targetId") String targetId) throws BIException {
		return super.openDirectoryCreateDialog(id, targetId);
	}

	@POST
	@Produces(BIWebUtils.TEXT_PLAIN_DEFAULT_CHARSET)
	@Path("/dir/createsubmit")
	public String openDirectoryCreateSubmit(String body) throws BIJSONException {
		return super.openDirectoryCreateSubmit(pageDelegates, body,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(@PathParam("id") String id)
			throws BIException {
		return super.buildNaviContent(pageDelegates, Long.parseLong(id),
				DIR_CATEGORY, false);
	}

}
