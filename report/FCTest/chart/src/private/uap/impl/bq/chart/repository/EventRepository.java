package uap.impl.bq.chart.repository;

import java.io.Serializable;

import uap.vo.bq.chart.define.EventDefine;


public class EventRepository implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	private EventDefine[] eventDefines = null;
	
	public EventRepository(EventDefine[] eventDefines){
		this.eventDefines = eventDefines;
	}

	public EventDefine[] getEventDefines() {
		return eventDefines;
	}

	void setEventDefines(EventDefine[] eventDefines) {
		this.eventDefines = eventDefines;
	}
	

	public EventDefine getEventDefine(String code) {

	
	
		return null;
	}
	

	
}
