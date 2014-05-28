package uap.vo.bq.chart.model;

import java.awt.Event;
import java.util.EventListener;

import javax.swing.JComponent;

public interface ImageCreatedListener extends EventListener{
	public void onImageCreated(Event event);
	
	public static class ChartImageEvent extends Event{
		private static final long serialVersionUID = 1L;

		public ChartImageEvent(Object target, int id, Object arg) {
			super(target, id, arg);
		}
		
		public JComponent getSource(){
			return (JComponent) this.target;
		}
		
		public Object getArgs(){
			return this.arg;
		}
		
		public int getID(){
			return this.id;
		}
		
	}
}
