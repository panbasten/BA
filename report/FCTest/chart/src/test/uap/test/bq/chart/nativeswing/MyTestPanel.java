package uap.test.bq.chart.nativeswing;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class MyTestPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MyTestPanel() {

		super(new BorderLayout());

		JScrollPane webBrowserPanel = new JScrollPane();

		webBrowserPanel
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		final JWebBrowser webBrowser = new JWebBrowser();

		webBrowser.setBarsVisible(false);

		webBrowser.navigate("http://ufpark"); //http://127.0.0.1/bqchart/test.html

		add(webBrowser, BorderLayout.CENTER);

	}

	public static void main(String[] args) {

		UIUtils.setPreferredLookAndFeel();

		NativeInterface.open();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				JFrame frame = new JFrame("测试");

				//frame.setUndecorated(true);// 禁用此窗体的装饰

				frame.setLocationRelativeTo(null);// 居中显示

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.getContentPane().add(new MyTestPanel(),
						BorderLayout.CENTER);
				
				frame.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						System.out.println("@@@ windowDeactivated");
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						System.out.println("@@@ windowActivated");
					}
				});

				frame.setSize(938, 614);

				frame.setLocationByPlatform(true);

				frame.setVisible(true);

			}

		});

		NativeInterface.runEventPump();

	}

}
