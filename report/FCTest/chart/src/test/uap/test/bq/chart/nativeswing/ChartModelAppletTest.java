package uap.test.bq.chart.nativeswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uap.bq.chart.component.FusionChartsImageUtil;
import uap.bq.chart.generator.ChartGenerateException;
import uap.bq.chart.generator.parser.DefaultSettingParser;
import uap.impl.bq.chart.service.ChartDefineServiceImpl;
import uap.impl.bq.chart.service.ChartThemeServiceImpl;
import uap.ui.bq.chart.component.ChartPanel;
import uap.util.bq.chart.ServerInfoUtil;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;
import uap.vo.bq.chart.model.ExportImageReadyEvent;
import uap.vo.bq.chart.model.IChartJavaScript;

public class ChartModelAppletTest extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean inited = false;
	ChartModel model = null, model2 = null, model3 = null;

	public ChartModelAppletTest() {
		super();
		// init();
	}

	public void init() {
		try {
			this.setLayout(new BorderLayout());
			//ServerInfoUtil.setLocalModel();
			DefaultSettingParser
					.setChartThemeService(new ChartThemeServiceImpl());

			ChartDefineServiceImpl impl = new ChartDefineServiceImpl();
			String strChartType = "MSColumn2D";// "Funnel";//"VBullet";//"Waterfall2D";//
			// "StackedColumn2DLine";//"MSCombi2D";//"MSCombiDY2D";//"Bubble";
			// "Scatter";//"AngularGauge";// "MSArea";// "MSLine";//
			// "MultiAxisLine";
			// "SparkWinLoss";//"SparkColumn";// "SparkLine";
			// //"MultiLevelPie";// "Pie2D";//"StackedColumn2D";//
			final ChartDefine define = impl.getChartDefine(strChartType);
			model = ChartModelFactory.createChartModel(strChartType);

			final JPanel framPanel = new JPanel();
			framPanel.setLayout(new BorderLayout());

			final ChartPanel panel = new ChartPanel();
			panel.createChart(define, model);

			JPanel toolbarPanel = new JPanel();
			toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			toolbarPanel.setBackground(Color.WHITE);

			addToolbarButton(toolbarPanel, panel);

			final JButton jb = new JButton("export Image");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
					public void run() {
							
							FusionChartsImageUtil.generateChartToImage(model, define, 800, 600,  
									new ExportImageReadyEvent() {

										@Override
										public void onImageReady(Image image) {
											if (null != image) {
												JPanel jp = new JPanel();
												jp.setLayout(new BorderLayout());
												jp.add(new JLabel(
														new ImageIcon(image)),
														BorderLayout.CENTER);
												framPanel.add(jp,
														BorderLayout.CENTER);
												framPanel.validate();
											}
										}

									});

						}
					});

				}
			});

			toolbarPanel.add(jb);

			// framPanel.add(panel, BorderLayout.CENTER);
			framPanel.add(toolbarPanel, BorderLayout.SOUTH);

			add(framPanel, BorderLayout.CENTER);

			setSize(new Dimension(1060, 600));
			setBackground(Color.GRAY);

			// setBounds(0, 0, 1024, 768);
		} catch (ChartGenerateException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addToolbarButton(JPanel toolBar, final IChartJavaScript js) {
		JButton jb = null;
		jb = new JButton("Blue BgColor");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setChartBgColor("0000ff");
				js.setChartBgAlpha(50);
			}
		});
		toolBar.add(jb);

		jb = new JButton("Yellow BgColor");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setChartBgColor("ffff00");
				js.setChartBgAlpha(50);
			}
		});
		toolBar.add(jb);

		jb = new JButton("red CanvasColor");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setCanvasBgColor("000000");
				js.setCanvasBgAlpha(50);
			}
		});
		toolBar.add(jb);

		jb = new JButton("Green BorderColor");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setChartBorderColor("00ff00");

			}
		});
		toolBar.add(jb);

		jb = new JButton("5 ChartBorder");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setChartBorderWidth(5);

			}
		});
		toolBar.add(jb);

		jb = new JButton("Right Legend");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setLegendPosition("RIGHT");

			}
		});
		toolBar.add(jb);

		jb = new JButton("10 HGridLine");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setHGridLineNums(10);
			}
		});
		toolBar.add(jb);

		jb = new JButton("Dashed HLine");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setDashedHGridLine(true);
			}
		});
		toolBar.add(jb);

		jb = new JButton("Sub/Caption");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				js.setChartCaption("主标题-测试用例");
				js.setChartSubCaption("子标题-测试用例");

			}
		});
		toolBar.add(jb);

	}

	// public void paint(Graphics g) {
	// if (!inited) {
	//
	// inited = true;
	// }
	// this.paintComponents(g);
	// }
}