package com.tonbeller.jpivot.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import com.tonbeller.jpivot.olap.model.Alignable;
import com.tonbeller.jpivot.table.SpanBuilder.SBContext;
import com.tonbeller.jpivot.table.span.Span;
import com.tonbeller.wcf.popup.MenuItem;
import com.tonbeller.wcf.popup.MenuItemSupport;
import com.tonbeller.wcf.popup.PopUp;

public class AxisHeaderBuilderSupport implements AxisHeaderBuilder {

	private SpanBuilder spanBuilder;

	public AxisHeaderBuilderSupport(SpanBuilder spanBuilder) {
		this.spanBuilder = spanBuilder;
	}

	class MySBContext implements SBContext {
		Element captionElem;
		JSONObject captionJo;
		String captionLabel;
		List clickables = new ArrayList();

		public void setCaption(Element elem, String label) {
			this.captionElem = elem;
			this.captionLabel = label;
		}

		@Override
		public void setCaptionJo(JSONObject elem, String label) {
			this.captionJo = elem;
			this.captionLabel = label;
		}

		public void addClickable(String href, String label) {
			MenuItemSupport mis = new MenuItemSupport(href);
			mis.setLabel(label);
			clickables.add(mis);
		}

		void render() {
			if (clickables.isEmpty())
				return;

			if (clickables.size() == 1) {
				MenuItem mi = (MenuItem) clickables.get(0);
				if (captionElem != null) {
					captionElem.setAttribute("href", mi.getHref());
				}
				if (captionJo != null) {
					captionJo.put("href", mi.getHref());
				}
				return;
			}

			PopUp pu = new PopUp();
			pu.setLabel(captionLabel);
			for (Iterator it = clickables.iterator(); it.hasNext();) {
				MenuItem mi = (MenuItem) it.next();
				pu.addItem(mi);
			}

			if (captionElem != null) {
				Element e = pu.render(captionElem.getOwnerDocument());
				captionElem.appendChild(e);
			}

			if (captionJo != null) {
				JSONObject e = pu.renderJo(captionJo);
				captionJo.put("popup-menu", e);
			}
		}
	}

	@Override
	public void build(Element row, Span span, int rowspan, int colspan,
			boolean even, boolean memberIndent) {
		MySBContext sbctx = new MySBContext();
		Element elem = spanBuilder.build(sbctx, span, even);
		sbctx.render();
		elem.setAttribute("rowspan", Integer.toString(rowspan));
		elem.setAttribute("colspan", Integer.toString(colspan));

		// no special formatting present?
		if (elem.getAttribute("style").length() == 0) {
			// valid styles are { span, even, odd, span-right, even-right,
			// odd-right}
			String style;
			if (colspan > 1 || rowspan > 1)
				style = "span";
			else if (even)
				style = "even";
			else
				style = "odd";
			if (isRightAligned(span))
				style = style + "-right";
			elem.setAttribute("style", style);
		}

		// indent level for hierarchy style view
		if (memberIndent && span.isMember()) {
			elem.setAttribute("indent", Integer.toString(span.getIndent()));
		}

		row.appendChild(elem);
	}

	@Override
	public Object[] buildJo(Span span, int rowspan, int colspan, boolean even,
			boolean memberIndent) {
		MySBContext sbctx = new MySBContext();
		Object[] jos = spanBuilder.buildJo(sbctx, span, even);
		sbctx.render();
		JSONObject jo = (JSONObject) jos[1];
		jo.put("rowspan", Integer.toString(rowspan));
		jo.put("colspan", Integer.toString(colspan));

		// no special formatting present?

		if (!jo.containsKey("style")
				|| (jo.containsKey("style") && ((String) jo.get("style"))
						.length() == 0)) {
			// valid styles are { span, even, odd, span-right, even-right,
			// odd-right}
			String style;
			if (colspan > 1 || rowspan > 1)
				style = "span";
			else if (even)
				style = "even";
			else
				style = "odd";
			if (isRightAligned(span))
				style = style + "-right";
			jo.put("style", style);
		}

		// indent level for hierarchy style view
		if (memberIndent && span.isMember()) {
			jo.put("indent", Integer.toString(span.getIndent()));
		}

		return new Object[] { jos[0], jo };
	}

	private boolean isRightAligned(Span span) {
		Object obj = span.getObject();
		if (obj instanceof Alignable)
			return ((Alignable) obj).getAlignment() == Alignable.Alignment.RIGHT;
		return false;
	}

}
