package uap.impl.bq.chart.util;

import java.io.File;

import nc.bs.framework.server.ServerConfiguration;
import uap.util.bq.chart.ServerInfoUtil;

/**
 * ��˻�÷�������Ϣ
 * 
 * @author zhanglld
 * 
 */
public class BackEndServerInfoUtil {
	private static String _serverRootPath = null;
	private static String _serverRootUrl = null;

	public static String getServerRootPath(){
		if (_serverRootPath != null) {
			return _serverRootPath;
		}
		
		if(ServerInfoUtil.isLocalModel()){
			//return ServerInfoUtil.getServerRootURL();
			return getServerRootURL();
		}
		
		String ncServerLocation = System.getProperty("nc.server.location");
		if(ncServerLocation == null){//δ����������
			String thisClassAddr = ServerInfoUtil.class.getResource(
					"/" + ServerInfoUtil.class.getName().replace(".", "/")
							+ ".class").toString();

			int index = thisClassAddr.indexOf(ServerInfoUtil.class.getName()
					.replace(".", "/")) - 1;
			String serverRoot = thisClassAddr.substring(0, index);
			index = serverRoot.lastIndexOf('/');
			serverRoot = serverRoot.substring(0, index);
			index = serverRoot.lastIndexOf('/');

			_serverRootPath = serverRoot.substring("file:/".length(), index)
					+ "../../../build/disk/hotwebs/chart";
		}else{//����������
			_serverRootPath = ncServerLocation + File.separator + "hotwebs" + File.separator + "bqchart";
		}
		return _serverRootPath;
	}
	
	
	public static String getServerRootURL() {
		
		if (_serverRootUrl != null) {
			return _serverRootUrl;
		}
		// ���3���������Ѿ��������ں�̨����
		ServerConfiguration conf = ServerConfiguration.getServerConfiguration();
		if(conf != null){
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
					_serverRootUrl = "http://" + addr + ":"
								+ port + "/" + "bqchart";
			}
	    }
			return _serverRootUrl;
    }
	
	
}
