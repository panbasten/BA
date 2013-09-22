package com.flywet.platform.bi.component.resolvers.base;

import java.io.File;
import java.util.List;

import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYPageTemplateUtils;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIPageException;

public class IncludeJsResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			String src = HTML.getTagAttribute(node, HTML.ATTR_SRC, attrs);

			String includeText = null;

			// 如果是Package类型
			if (fileUrl.startsWith(PageTemplateInterpolator.URL_PREFIX_PACKAGE)) {
				Class<?> packageClass = Class.forName(fileUrl
						.substring(PageTemplateInterpolator.URL_PREFIX_PACKAGE
								.length()));
				includeText = PageTemplateInterpolator.interpolateText(
						packageClass, src, attrs);
			}
			// 如果是普通文件类型
			else {
				src = FLYPageTemplateUtils.reletivePathCal(new File(fileUrl)
						.getParent(), src);
				includeText = PageTemplateInterpolator.interpolateText(src,
						attrs);
			}

			if (includeText != null) {
				html.writeText("<script type=\"text/javascript\">");
				html.writeText(includeText);
				html.writeText("</script>");
			}

		} catch (ClassNotFoundException e) {
			throw new BIPageException("包名解析出现错误。");
		} catch (Exception e) {
			throw new BIPageException("Include JS文件出现错误。");
		}

	}
}
