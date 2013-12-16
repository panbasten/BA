package com.flywet.platform.bi.di.rest;

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
import com.flywet.platform.bi.di.function.DIFunctions;
import com.flywet.platform.bi.di.service.intf.BIDIDelegates;
import com.flywet.platform.bi.rest.AbastractDirectoryResource;

@Service("bi.resource.diResource")
@Path("/di")
public class BIDIResource extends AbastractDirectoryResource {
	private final Logger logger = Logger.getLogger(BIDIResource.class);

	private static Class<?> PKG = BIDIResource.class;

	public static final String ID_EDITOR_JOBS = "jobs";

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.DI;

	@Resource(name = "bi.service.diService")
	private BIDIDelegates diDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDI() throws BIException {
		// 注册方法
		DIFunctions.register();
		return super.buildNaviContent(diDelegates, DIR_CATEGORY.getRootId(),
				DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@PathParam("id") String id)
			throws BIException {
		return super.removeDirectory(diDelegates, id, DIR_CATEGORY);
	}

	@GET
	@Path("/dir/edit/{pid}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryEditDialog(@PathParam("pid") String pid,
			@PathParam("id") String id, @QueryParam("desc") String desc,
			@QueryParam("targetId") String targetId) throws BIException {
		return super.openDirectoryEditDialog(pid, id, desc, targetId);
	}

	@POST
	@Produces(BIWebUtils.TEXT_PLAIN_DEFAULT_CHARSET)
	@Path("/dir/editsubmit")
	public String openDirectoryEditSubmit(String body) throws BIJSONException {
		return super.openDirectoryEditSubmit(diDelegates, body, DIR_CATEGORY);
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
		return super.openDirectoryCreateSubmit(diDelegates, body, DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(@PathParam("id") String id)
			throws BIException {
		return super.buildNaviContent(diDelegates, Long.parseLong(id),
				DIR_CATEGORY, false);
	}

}
