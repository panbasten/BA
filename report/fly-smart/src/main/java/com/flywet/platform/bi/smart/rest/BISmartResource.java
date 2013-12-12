package com.flywet.platform.bi.smart.rest;

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
import com.flywet.platform.bi.smart.service.intf.BISmartDelegates;

@Service("bi.resource.smartResource")
@Path("/smart")
public class BISmartResource extends AbastractDirectoryResource {

	private final Logger logger = Logger.getLogger(BISmartResource.class);

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.DOMAIN;

	@Resource(name = "bi.service.smartService")
	private BISmartDelegates smartDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDI() throws BIException {
		// 注册方法
		return super.buildNaviContent(smartDelegates, DIR_CATEGORY.getRootId(),
				DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@PathParam("id") String id)
			throws BIException {
		return super.removeDirectory(smartDelegates, id, DIR_CATEGORY);
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
		return super.openDirectoryCreateSubmit(smartDelegates, body,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(@PathParam("id") String id)
			throws BIException {
		return super.buildNaviContent(smartDelegates, Long.parseLong(id),
				DIR_CATEGORY, false);
	}
}
