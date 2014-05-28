package uap.impl.bq.chart.repository;

import java.io.Serializable;

import uap.vo.bq.chart.define.APIDefine;

public class APIRepository  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	private static APIRepository instance = null;
	
	private APIDefine[] apiDefines = null;

	public APIRepository(){
		
	}

	public APIDefine[] getApiDefines() {
		return apiDefines;
	}

	void setApiDefines(APIDefine[] apiDefines) {
		this.apiDefines = apiDefines;
	}
	
	public static APIRepository getInstance(){
		if(instance == null){
			instance = loadFromXML();
		}
		
		return instance;
	}
	
	private static APIRepository loadFromXML(){
		//TODO
		//??XML????
		//?????????
		return null;
	}
}
