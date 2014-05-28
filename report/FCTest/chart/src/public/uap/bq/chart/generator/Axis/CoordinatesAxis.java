package uap.bq.chart.generator.Axis;

import java.util.LinkedList;
import java.util.List;

import uap.bq.chart.generator.ChartGenerateException;

public class CoordinatesAxis {
	
	/*
	 * 数量级
	 */
	private int ordermagntiude;
	
	/*
	 * 保留小数点位数的个数
	 */
	private int numberOfDecimalPlace = 0;
	
	/*
	 * 坐标起始值
	 */
	private double startCoordinates;
	
	/*
	 * 坐标终止值
	 */
	private double endCoordinates;
	
	/*
	 * 坐标最小刻度
	 */
	private double stepCoordinates;
	

	public void clear(){
		ordermagntiude = 0;
		numberOfDecimalPlace = 0;
		startCoordinates = 0;
		endCoordinates = 0;
		stepCoordinates = 0;
	}
	
	/**
	 * 获取步长科学计数法的小数位数
	 * @return
	 */
	public int getNumberOfDecimalPlace() {
		return numberOfDecimalPlace;
	}

	/**
	 * 根据最大、最小值生成坐标点
	 * @param min
	 * @param max
	 * @return
	 */
	public List<Double> generateCoordinates(double min,double max) {
		if(max <= min){
			double temp = max;
		    max = min;
		    min = temp;
		}
		
		//坐标点初始间隔
		double difference = max-min;
		
		//初始起点大于0并接近0，0作为初始起点
		if(min>0 && difference/min > 1.0/3)
			min = 0;
		
		//初始终点小于0，并接近0,0作为初始终点
		if(max<0 && difference/(-max) > 1.0/3)
			max = 0;
		
		//求出起点和终点的间隔
		difference = max-min;
		
		//求出间隔的数量级
		ordermagntiude = getOrderOfMagntiude(difference);
		
		//间隔有几个数量级
		double floor =  difference/Math.pow(10, ordermagntiude);
		
		// 根据间隔的数量级个事、最小值、最大值，设定坐标的起始点、终止点和步长
		setCoordinates(floor,min,max);
		
		//计算步长的最终小数点位数
		setnumberOfDecimalPlace();
		
		//计算各个坐标点
		List<Double> coordinates = computecoordinates(min, max);
		
		return coordinates;
	}
	
	
	/**
	 * 计算各个坐标点
	 * @param min
	 * @param max
	 * @return
	 */
	private List<Double> computecoordinates(double min, double max){
		List<Double> coordinates = new LinkedList<Double>();
		double coordinatestemp ;
		
		if(max>0){
			//如果最大值大于0，从起始点开始计算
			coordinates.add(startCoordinates);
			coordinatestemp =  startCoordinates;
			do{
				coordinatestemp += stepCoordinates;
				coordinates.add(coordinatestemp);
			}while(coordinatestemp <= max);
			endCoordinates = coordinatestemp;
		}else {
			//如果最大值小于0，从终止点开始计算
			coordinates.add(endCoordinates);
			coordinatestemp = endCoordinates;
			do{
				coordinatestemp -= stepCoordinates;
				coordinates.add(0, coordinatestemp);
			}while(coordinatestemp>=min);
			startCoordinates = coordinatestemp;
		}
		
		return coordinates;
	}
	
	
	/**
	 * 计算步长的小数点位数
	 */
	private void setnumberOfDecimalPlace(){
		if(ordermagntiude > 0)
			numberOfDecimalPlace=0;
		else
			numberOfDecimalPlace = numberOfDecimalPlace-ordermagntiude;
	}
	
	
	/**
	 * 根据间隔的数量级个事、最小值、最大值，设定坐标的起始点、终止点和步长
	 * @param floor
	 * @param min
	 * @param max
	 */
	private void setCoordinates(double floor, double min, double max){
		if(floor<=1.5){
			//如果间隔小于2个数量级，步长取1/4的数量级
			stepCoordinates = Math.pow(10, ordermagntiude)*0.25;
			//起始坐标点取整
			startCoordinates = getStartdinates(min,stepCoordinates*2);
			//终止坐标点取整
			endCoordinates = getEnddinates(max,stepCoordinates*2);
			numberOfDecimalPlace+=2;
		}else if(floor<3){
			//如果间隔小于4个数量级，步长取1/2的数量级
			stepCoordinates = Math.pow(10, ordermagntiude)*0.5;
			startCoordinates = getStartdinates(min,stepCoordinates*2);
			endCoordinates = getEnddinates(max,stepCoordinates*2);
			numberOfDecimalPlace++;
		}
		else if(floor>6){
			stepCoordinates = Math.pow(10, ordermagntiude)*2;
			startCoordinates = getStartdinates(min,stepCoordinates);
			endCoordinates = getEnddinates(max,stepCoordinates);
		}
		else{
			stepCoordinates = Math.pow(10, ordermagntiude);
			startCoordinates = getStartdinates(min,stepCoordinates);
			endCoordinates = getEnddinates(max,stepCoordinates);
		}
	}
	
	
	/**
	 * 求坐标起始值
	 *减0.1是为了不让点有机会出现在起始坐标轴上，0除外
	 */
	private double getStartdinates(double min,double step){
		if(min==0)return 0;
		return Math.floor(min/Math.abs(step)-0.1)*Math.abs(step);
	}
	
	
	/**
	 * 求坐标终止值
	 *加0.1是为了不让点有机会出现在起始坐标轴上,0除外
	 */
	private double getEnddinates(double max,double step){
		if(max==0)return 0;
		return Math.ceil(max/Math.abs(step)+0.1)*Math.abs(step);
	}
	
	/**
	 * 数的数量级，比如100为2，10数量级为1，8为0，0.1为-1，0.0345为-2
	 */
	private int getOrderOfMagntiude(double doublenumber){
		int orderMagntiude =0;
		//int sign = Math.abs(doublenumber)<1 ? 1:-1;   //幂数的正负号取反
		int sign = Math.log10(doublenumber) < 0 ? 1:-1;  //幂数的正负号取反
		while (!isUnitsDigit( Math.abs(doublenumber*Math.pow(10, orderMagntiude))) ){
			orderMagntiude+=1*sign;
		}
		return -orderMagntiude;
	}
	
	/**
	 * 最大位数是否为个位
	 */
	private boolean isUnitsDigit(double doublenumber){
		doublenumber = Math.abs(doublenumber);
		if(1<=doublenumber && doublenumber<10)
			return true;
		return false;
	}

	
	/*
	public static void main(String[] args){
		CoordinatesAxis coordiandte = new CoordinatesAxis();
		System.out.println("************************************************");
		System.out.println("in:  1.5    7.4");
		System.out.println("out: "+coordiandte.generateCoordinates(1.5, 7.4));
		System.out.println("步长：       "+coordiandte.stepCoordinates+"  保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:  0.3    1.6");
		System.out.println("out: "+coordiandte.generateCoordinates(0.3, 1.6 ));
		System.out.println("步长：   "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   -0.035  -0.009");
		System.out.println("out: "+coordiandte.generateCoordinates(-0.035, -0.009));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   4    1671");
		System.out.println("out: "+coordiandte.generateCoordinates(4, 1671));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   -10   2");
		System.out.println("out: "+coordiandte.generateCoordinates(-10,2));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"  保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   0    1234 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0,1234 ));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"  保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   -1234    0 ");
		System.out.println("out: "+coordiandte.generateCoordinates(-1234,0 ));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   10028    10086 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10028,10086));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:    -10086    -10028");
		System.out.println("out: "+coordiandte.generateCoordinates(-10086,-10028));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:    1.3    2.6");
		System.out.println("out: "+coordiandte.generateCoordinates(1.3,2.6));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:    10.3    12.6");
		System.out.println("out: "+coordiandte.generateCoordinates(10.3,12.6));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   10028.000001345    10028.00000457 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10028.000001345,10028.00000457));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   10.2    11.1 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10.2,11.1));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    1.4 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,1.4));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    1.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,1.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    2.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,2.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    3.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,3.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   0.2    4.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,4.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    5.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,5.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    6.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,6.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    7.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,7.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    8.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,8.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    9.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,9.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    10.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,10.8));
		System.out.println("步长：    "+coordiandte.stepCoordinates+"   保留小数位：     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
	}
	*/
	
}
