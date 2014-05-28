package uap.util.bq.chart;

import java.io.File;

import nc.bs.framework.common.UserExit;
import nc.bs.framework.server.ServerConfiguration;

/**
 * ��˻�÷�������Ϣ
 * 
 * @author zhanglld
 * 
 */
public class ServerInfoUtil {
	
	private static boolean localModel = false;
	private static String serverRootPath = null;
	private static Boolean runInNCApplet = null;

	public static String getServerRootURL() {
		// ��ǰ�㷨δ��������ͨ�������е��Եĳ������Ժ������
		if (serverRootPath != null) {
			return serverRootPath;
		}
		
		//���1������ģʽ
		if(localModel){
			String thisClassAddr = ServerInfoUtil.class.getResource(
					"/" + ServerInfoUtil.class.getName().replace(".", "/")
							+ ".class").toString();

			int index = thisClassAddr.indexOf(ServerInfoUtil.class.getName()
					.replace(".", "/")) - 1;
			String serverRoot = thisClassAddr.substring(0, index);

			if (!isDeveloping()) {
				serverRootPath = serverRoot.substring("file:/".length(), index)+ "/../../../build/disk/hotwebs/chart";	
			} else {//dev
				serverRootPath = serverRoot.substring("file:/".length(), index);	
			}
			
			return serverRootPath;// ���ԣ�δ����������
		}

		
		boolean runInServer = isRunInServer();
		boolean runInClient = isRunInClient();
		// ���2���������Ѿ���������ǰ̨����
		if(runInClient){
			String baseURL = UserExit.getInstance().getServerBaseURL();
			int index_1 = baseURL.indexOf("//")+2;
			int index_2 = baseURL.indexOf(":", index_1);
			String serverAddr = baseURL.substring(index_1, index_2);
			int index_3 = baseURL.indexOf("/", index_2);
			String serverPort ;
			if(index_3 == -1)
				serverPort = baseURL.substring(index_2+1);
			else 
				serverPort = baseURL.substring(index_2+1, index_3);
			serverRootPath = "http://" + serverAddr + ":"+ serverPort + "/bqchart";
			//serverRootPath = UserExit.getInstance().getServerBaseURL()+ "/bqchart";
			return serverRootPath;
		}
		
		/*StackTraceElement[] elements = new Throwable().getStackTrace();
		for (StackTraceElement element : elements) {
			String className = element.getClassName().toLowerCase();
			if (className.indexOf("nc.ui") >= 0 || className.indexOf("java.awt") >= 0) {
				String baseURL = UserExit.getInstance().getServerBaseURL();
				int index_1 = baseURL.indexOf("//")+2;
				int index_2 = baseURL.indexOf(":", index_1);
				String serverAddr = baseURL.substring(index_1, index_2);
				int index_3 = baseURL.indexOf("/", index_2);
				String serverPort ;
				if(index_3 == -1)
					serverPort = baseURL.substring(index_2+1);
				else 
					serverPort = baseURL.substring(index_2+1, index_3);
				serverRootPath = "http://" + serverAddr + ":"+ serverPort + "/bqchart";
				//serverRootPath = UserExit.getInstance().getServerBaseURL()+ "/bqchart";
				return serverRootPath;
			}
		}*/

		// ���3���������Ѿ��������ں�̨����
		ServerConfiguration conf = ServerConfiguration.getServerConfiguration();
		if(conf != null){
			
			try {
				Class backEndServerInfoUtilClazz = Class.forName("uap.impl.bq.chart.util.BackEndServerInfoUtil");
				java.lang.reflect.Method getServerRootURL = backEndServerInfoUtilClazz.getDeclaredMethod("getServerRootURL");
				serverRootPath = (String)getServerRootURL.invoke(null);
				return serverRootPath;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new RuntimeException(e);
			}
			/*
			String serverName = ServerConfiguration.getServerConfiguration()
					.getServerName();
			String addr = ServerConfiguration.getServerConfiguration()
					.getAddressOfServer(serverName);
			String port = ServerConfiguration.getServerConfiguration()
					.getServerEndpointURL(serverName);
			int index = port.indexOf("//") + 2;
			index = port.indexOf(":", index);
			if (-1 != index){
				port = port.substring(index+1, port.indexOf("/", index));
				serverRootPath = "http://" + addr + ":"
						+ port + "/" + "bqchart";
			}
	
			return serverRootPath;// �������������ں�̨����
			*/
			//BackEndServerInfoUtil
		}
		
		return null;
	}
	
	
	public static boolean isRunInClient(){
		boolean runInClient ;
		try {
			Class.forName("uap.ui.bq.chart.component.RunInClient");
			runInClient = true;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			runInClient = false;
		}
		return runInClient;
	}
	
	
	
	public static boolean isRunInServer(){
		boolean runInServer ;
		try {
			Class.forName("nc.bs.framework.server.ServerConfiguration");
			runInServer = true;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			runInServer = false;
		}
		return runInServer;
	}
	
	/**
	 * �Ƿ��ڿ���������
	 * @return
	 */
	private static boolean isDeveloping() {
		String thisClassAddr = ServerInfoUtil.class.getResource(
				"/" + ServerInfoUtil.class.getName().replace(".", "/")
						+ ".class").toString();

		int index = thisClassAddr.indexOf(ServerInfoUtil.class.getName()
				.replace(".", "/")) - 1;
		String serverRoot = thisClassAddr.substring(0, index);
		
		if (serverRoot.startsWith("file:/")) {
			serverRoot = serverRoot.substring(6);
		}
			
		File file = new File(serverRoot + "/WEB-INF");
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		
		return false;
	}

	/**
	 * �Ƿ�������NC��Applet��
	 * @return
	 */
	public static boolean runInNCApplet() {
		if(localModel){
			runInNCApplet = false;
			
			return runInNCApplet;
		}
		
	
		String serverRootURL = getServerRootURL();
		if (serverRootURL == null || serverRootURL.charAt(1) == ':') {
			runInNCApplet = false;
			return runInNCApplet;
		} else {
			StackTraceElement[] elements = new Throwable().getStackTrace();
			for (StackTraceElement element : elements) {
				String className = element.getClassName().toLowerCase();
				if (className.indexOf("nc.ui") >= 0 || className.indexOf("java.awt") >= 0) {
					runInNCApplet = true;
					return runInNCApplet;
				}
			}

			runInNCApplet = false;
			return runInNCApplet;
		}
	
	}
	
	/**
	 * ����Ϊ����ģʽ��������������
	 */
	public static void setLocalModel(){
		localModel = true;
	}

	/**
	 * �Ƿ��Ǳ���ģʽ
	 * @return
	 */
	public static boolean isLocalModel() {
		return localModel;
	}
}
