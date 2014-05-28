package uap.bq.chart.generator.link;

import java.util.Stack;

public class JsonStack<T> extends Stack<T>{
	
	public JsonStack(){
		
	}
	
   // public JsonStack(String label){
	//	this.label = label;
	//}
    
    
   // private String label = ",";
	
	public String toString(){
		return this.toString(",");
	}
	
	public String toString(String label){
		StringBuilder result = new StringBuilder();
		int i = 0;
		for(T t : this){
			result.append(t);
			if(i < this.size()-1)
				result.append(label);
			i++;
		}
		return result.toString();
	}
	
	public String toJsonArray(){
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
		for(T t : this){
			result.append("\\\"").append(t).append("\\\"");
			if(i < this.size()-1)
				result.append(",");
			i++;
		}
		result.append("]");
		return result.toString();
	}

}
