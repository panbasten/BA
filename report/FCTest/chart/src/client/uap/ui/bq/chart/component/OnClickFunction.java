package uap.ui.bq.chart.component;

import java.lang.ref.WeakReference;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserFunction;

class OnClickFunction extends WebBrowserFunction {
		WeakReference<ChartPanel> chartPanelRef = null;
		public OnClickFunction(ChartPanel chartPanel){
			super("_onClick");
			chartPanelRef = new WeakReference<ChartPanel>(chartPanel);
		}

		@Override
		public Object invoke(JWebBrowser webBrowser, final Object... args) {
			String jsonData = args[2] == null ? "" : args[2].toString();
			int x = Float.valueOf(args[0].toString()).intValue();
			int y = Float.valueOf(args[1].toString()).intValue();
			String data = jsonData.replace("^", "\"");
			//
			ChartPanel chartPanel = chartPanelRef.get();
			if(chartPanel != null){
				chartPanel.callSWLeftClick(x, y, data);
			}
			return "";
		}

	}
