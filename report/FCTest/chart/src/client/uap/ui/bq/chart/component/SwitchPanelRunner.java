package uap.ui.bq.chart.component;

import java.lang.ref.WeakReference;

class SwitchPanelRunner implements Runnable{
		private WeakReference<ChartPanel> chartPanelRef = null;
		private String name ;
		public SwitchPanelRunner(ChartPanel chartPanel,String name){
			chartPanelRef = new WeakReference<ChartPanel>(chartPanel);
			this.name = name;
		}
		
		public void dorun(){

			ChartPanel chartPanel = chartPanelRef.get();
			if(chartPanel == null){
				return ;
			}
			ChartPanelPrivateData data = chartPanel.getData();
			if (name.equals(ChartPanel.PANEL_WAITLOADING)
					&& !data.getCurrentPanel().equals(
							ChartPanel.PANEL_WAITLOADING)) {
				data.setCurrentPanel(ChartPanel.PANEL_WAITLOADING);
				data.getWaitPanel().setVisible(true);
				data.getBrowserPanel().setVisible(false);
				data.getImgPanel().setVisible(false);
				chartPanel.validate();
			} else if (name.equals(ChartPanel.PANEL_FUSIONCHART)
					&& !data.getCurrentPanel().equals(
							ChartPanel.PANEL_FUSIONCHART)) {
				data.setCurrentPanel(ChartPanel.PANEL_FUSIONCHART);
				data.getImgPanel().setVisible(false);
				data.getBrowserPanel().setVisible(true);
				data.getWaitPanel().setVisible(false);
				chartPanel.validate();
			} else if (name.equals(ChartPanel.PANEL_IMAGECHART)
					&& !data.getCurrentPanel()
							.equals(ChartPanel.PANEL_IMAGECHART)) {
				chartPanel.checkImageAndGen();
				data.setCurrentPanel(ChartPanel.PANEL_IMAGECHART);
				data.getImgPanel().setVisible(true);
				data.getBrowserPanel().setVisible(false);
				data.getWaitPanel().setVisible(false);
				chartPanel.validate();
			}
		
		}
		
		
		@Override
		public void run() {
			ChartPanel chartPanel = chartPanelRef.get();
			if(chartPanel == null){
				return ;
			}
			ChartPanelPrivateData data = chartPanel.getData();
			if (name.equals(ChartPanel.PANEL_WAITLOADING)
					&& !data.getCurrentPanel().equals(
							ChartPanel.PANEL_WAITLOADING)) {
				data.setCurrentPanel(ChartPanel.PANEL_WAITLOADING);
				data.getWaitPanel().setVisible(true);
				data.getBrowserPanel().setVisible(false);
				data.getImgPanel().setVisible(false);
				chartPanel.validate();
			} else if (name.equals(ChartPanel.PANEL_FUSIONCHART)
					&& !data.getCurrentPanel().equals(
							ChartPanel.PANEL_FUSIONCHART)) {
				data.setCurrentPanel(ChartPanel.PANEL_FUSIONCHART);
				data.getImgPanel().setVisible(false);
				data.getBrowserPanel().setVisible(true);
				data.getWaitPanel().setVisible(false);
				chartPanel.validate();
			} else if (name.equals(ChartPanel.PANEL_IMAGECHART)
					&& !data.getCurrentPanel()
							.equals(ChartPanel.PANEL_IMAGECHART)) {
				chartPanel.checkImageAndGen();
				data.setCurrentPanel(ChartPanel.PANEL_IMAGECHART);
				data.getImgPanel().setVisible(true);
				data.getBrowserPanel().setVisible(false);
				data.getWaitPanel().setVisible(false);
				chartPanel.validate();
			}
		}
	
	}