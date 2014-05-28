package uap.bq.chart.component;

import java.io.IOException;

import nc.vo.pub.BusinessRuntimeException;

import chrriis.common.SystemProperty;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

public class NativeSwingHelper {

	/* ���ؽӿ��Ƿ�� */
	private static boolean nativeInterfactOpened = false;;

	/**
	 * ȷ�����ص���ȷ��SWT Jar��
	 * ȷ������jna��
	 */
	@SuppressWarnings("unused")
	private static void ensureForUAPClassLoader(){
		try{
			try{
				com.sun.jna.Function f = com.sun.jna.Function.getFunction(null);
			}catch(Exception e){
//				throw new RuntimeException (e);
			}

			String os = SystemProperty.OS_NAME.get();
			if (os.toLowerCase().indexOf("windows") > -1) {
				os = "windows";
			} else if (os.toLowerCase().indexOf("linux") > -1) {
				os = "linux";
			} else if (os.toLowerCase().indexOf("mac") > -1) {
				os = "mac";
			} else {
				os = "default";
			}

			if(os.equals("windows")){
				if(SystemProperty.OS_ARCH.get().indexOf("64") > 0){
					Class.forName("IsWin64");
				}else{
					Class.forName("IsWin32");
				}
			} else if (os.equals("mac")){
				if(SystemProperty.OS_ARCH.get().indexOf("64") > 0){
					Class.forName("IsMac64");
				}else{
					Class.forName("IsMac32");
				}
			} else if (os.equals("linux")){
				if(SystemProperty.OS_ARCH.get().indexOf("64") > 0){
					Class.forName("IsLinux64");
				}else{
					Class.forName("IsLinux32");
				}
			}

		}catch(Exception e){
			//do nothing
		}
	}

	/**
	 * ִ��������
	 */
	public static void runEventPump(){
		NativeInterface.runEventPump();
	}

	/**
	 * �򿪱��ؽӿ�
	 * @throws IOException
	 */
	public static void openNativeInterface() throws IOException {

		if(!nativeInterfactOpened){
			//if(!isLocal()){
				ensureForUAPClassLoader();
			//}

			try{
				System.setProperty("sun.awt.disableMixing", "true");
				NativeInterface.open();
			}catch(Exception e){
				throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0058")/*@res "��DJNativeSwing���ؽӿ�ʧ�ܣ�"*/, e);
			}

			nativeInterfactOpened = true;
		}
	}

	private static boolean isLocal() {
		String thisClassAddr = NativeSwingHelper.class.getResource(
				"/" + NativeSwingHelper.class.getName().replace(".", "/")
						+ ".class").toString();
		if (thisClassAddr.indexOf("file:/") >= 0) {
			return true;
		} else {
			return false;
		}
	}
}