package uap.ui.bq.chart.component;

import java.lang.ref.WeakReference;

import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

class WebBrowserListener extends WebBrowserAdapter{
		WeakReference<ChartPanel> chartPanelRef = null;
		public WebBrowserListener(ChartPanel chartPanel){
			chartPanelRef = new WeakReference<ChartPanel>(chartPanel);
		}

		@Override
		public void locationChanged(WebBrowserNavigationEvent e) {
			ChartPanel chartPanel = chartPanelRef.get();
			if(chartPanel != null){
				chartPanel.switchPanelTo(ChartPanel.PANEL_FUSIONCHART);
			}
		}

		public void loadingProgressChanged(WebBrowserEvent e) {
//			if (e.getWebBrowser().getLoadingProgress() == 100) {
//				// registeWebBrowserFunctions(e.getWebBrowser());
//			}
		}
	
	}