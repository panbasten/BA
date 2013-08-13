package com.flywet.platform.bi.web.rest;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
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
import com.flywet.platform.bi.web.service.BIPageDelegates;

@Service("bi.resource.domainResource")
@Path("/domain")
public class BIDomainResource extends AbastractDirectoryResource {

	private final Logger logger = Logger.getLogger(BIDomainResource.class);

	private static final BIDirectoryCategory DIR_CATEGORY = BIDirectoryCategory.DOMAIN;

	@Resource(name = "bi.service.pageServices")
	private BIPageDelegates pageDelegates;

	@GET
	@Path("/navi")
	@Produces(MediaType.TEXT_PLAIN)
	public String createNaviContentDI(
			@CookieParam("repository") String repository) throws BIException {
		// 注册方法
		return super.buildNaviContent(pageDelegates, repository, DIR_CATEGORY
				.getRootId(), DIR_CATEGORY, true);
	}

	@GET
	@Path("/dir/remove/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeDirectory(@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return super.removeDirectory(pageDelegates, repository, id,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/create/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDirectoryCreateDialog(
			@CookieParam("repository") String repository,
			@PathParam("id") String id, @QueryParam("targetId") String targetId)
			throws BIException {
		return super.openDirectoryCreateDialog(repository, id, targetId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dir/createsubmit")
	public String openDirectoryCreateSubmit(
			@CookieParam("repository") String repository, String body)
			throws BIJSONException {
		return super.openDirectoryCreateSubmit(pageDelegates, repository, body,
				DIR_CATEGORY);
	}

	@GET
	@Path("/dir/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String flushNaviContent(
			@CookieParam("repository") String repository,
			@PathParam("id") String id) throws BIException {
		return super.buildNaviContent(pageDelegates, repository, Long
				.parseLong(id), DIR_CATEGORY, false);
	}
}
