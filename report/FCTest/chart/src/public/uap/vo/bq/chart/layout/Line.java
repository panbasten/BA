package uap.vo.bq.chart.layout;

import nc.vo.pub.BusinessRuntimeException;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 布局文件中的线
 *
 * @author zhanglld
 *
 */
public class Line implements LayoutElement{

	private static final long serialVersionUID = 1L;

	/* 线类型 */
	private LineType type;

	public Line(LineType type){

	}

	public LineType getType() {
		return type;
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public Object clone(){
		try {
			Line copy = (Line) super.clone();
			copy.type = this.type;
			return copy;
		} catch (RuntimeException e) {
			AppDebug.error("Line clone error !");
			AppDebug.error(e);
			throw e;
		} catch (Exception e) {
			AppDebug.error("Line clone error");
			AppDebug.error(e);
			throw new RuntimeException("Line clone error", e);
		}
	}



	/**
	 * 线类型
	 *
	 * @author zhanglld
	 */
	public enum LineType{
		//目前仅支持这一种，以后添加其他
		DOT("dot");

		private String value;

		private LineType(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}

//		public boolean equals(String value){
//			return this.value.equals(value);
//		}
//
//		public final int hashCode(){
//			return 1;
//		}

		public static LineType genLineType(String value){
			if(value == null){
				return DOT;
			} else if(DOT.getValue().equals(value)) {
				return DOT;
			} else {
				throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0081")/*@res "不正确的线类型枚举值："*/ + value );
			}
		}
	}

}