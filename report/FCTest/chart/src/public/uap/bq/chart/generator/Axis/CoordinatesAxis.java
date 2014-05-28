package uap.bq.chart.generator.Axis;

import java.util.LinkedList;
import java.util.List;

import uap.bq.chart.generator.ChartGenerateException;

public class CoordinatesAxis {
	
	/*
	 * ������
	 */
	private int ordermagntiude;
	
	/*
	 * ����С����λ���ĸ���
	 */
	private int numberOfDecimalPlace = 0;
	
	/*
	 * ������ʼֵ
	 */
	private double startCoordinates;
	
	/*
	 * ������ֵֹ
	 */
	private double endCoordinates;
	
	/*
	 * ������С�̶�
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
	 * ��ȡ������ѧ��������С��λ��
	 * @return
	 */
	public int getNumberOfDecimalPlace() {
		return numberOfDecimalPlace;
	}

	/**
	 * ���������Сֵ���������
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
		
		//������ʼ���
		double difference = max-min;
		
		//��ʼ������0���ӽ�0��0��Ϊ��ʼ���
		if(min>0 && difference/min > 1.0/3)
			min = 0;
		
		//��ʼ�յ�С��0�����ӽ�0,0��Ϊ��ʼ�յ�
		if(max<0 && difference/(-max) > 1.0/3)
			max = 0;
		
		//��������յ�ļ��
		difference = max-min;
		
		//��������������
		ordermagntiude = getOrderOfMagntiude(difference);
		
		//����м���������
		double floor =  difference/Math.pow(10, ordermagntiude);
		
		// ���ݼ�������������¡���Сֵ�����ֵ���趨�������ʼ�㡢��ֹ��Ͳ���
		setCoordinates(floor,min,max);
		
		//���㲽��������С����λ��
		setnumberOfDecimalPlace();
		
		//������������
		List<Double> coordinates = computecoordinates(min, max);
		
		return coordinates;
	}
	
	
	/**
	 * ������������
	 * @param min
	 * @param max
	 * @return
	 */
	private List<Double> computecoordinates(double min, double max){
		List<Double> coordinates = new LinkedList<Double>();
		double coordinatestemp ;
		
		if(max>0){
			//������ֵ����0������ʼ�㿪ʼ����
			coordinates.add(startCoordinates);
			coordinatestemp =  startCoordinates;
			do{
				coordinatestemp += stepCoordinates;
				coordinates.add(coordinatestemp);
			}while(coordinatestemp <= max);
			endCoordinates = coordinatestemp;
		}else {
			//������ֵС��0������ֹ�㿪ʼ����
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
	 * ���㲽����С����λ��
	 */
	private void setnumberOfDecimalPlace(){
		if(ordermagntiude > 0)
			numberOfDecimalPlace=0;
		else
			numberOfDecimalPlace = numberOfDecimalPlace-ordermagntiude;
	}
	
	
	/**
	 * ���ݼ�������������¡���Сֵ�����ֵ���趨�������ʼ�㡢��ֹ��Ͳ���
	 * @param floor
	 * @param min
	 * @param max
	 */
	private void setCoordinates(double floor, double min, double max){
		if(floor<=1.5){
			//������С��2��������������ȡ1/4��������
			stepCoordinates = Math.pow(10, ordermagntiude)*0.25;
			//��ʼ�����ȡ��
			startCoordinates = getStartdinates(min,stepCoordinates*2);
			//��ֹ�����ȡ��
			endCoordinates = getEnddinates(max,stepCoordinates*2);
			numberOfDecimalPlace+=2;
		}else if(floor<3){
			//������С��4��������������ȡ1/2��������
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
	 * ��������ʼֵ
	 *��0.1��Ϊ�˲��õ��л����������ʼ�������ϣ�0����
	 */
	private double getStartdinates(double min,double step){
		if(min==0)return 0;
		return Math.floor(min/Math.abs(step)-0.1)*Math.abs(step);
	}
	
	
	/**
	 * ��������ֵֹ
	 *��0.1��Ϊ�˲��õ��л����������ʼ��������,0����
	 */
	private double getEnddinates(double max,double step){
		if(max==0)return 0;
		return Math.ceil(max/Math.abs(step)+0.1)*Math.abs(step);
	}
	
	/**
	 * ����������������100Ϊ2��10������Ϊ1��8Ϊ0��0.1Ϊ-1��0.0345Ϊ-2
	 */
	private int getOrderOfMagntiude(double doublenumber){
		int orderMagntiude =0;
		//int sign = Math.abs(doublenumber)<1 ? 1:-1;   //������������ȡ��
		int sign = Math.log10(doublenumber) < 0 ? 1:-1;  //������������ȡ��
		while (!isUnitsDigit( Math.abs(doublenumber*Math.pow(10, orderMagntiude))) ){
			orderMagntiude+=1*sign;
		}
		return -orderMagntiude;
	}
	
	/**
	 * ���λ���Ƿ�Ϊ��λ
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
		System.out.println("������       "+coordiandte.stepCoordinates+"  ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:  0.3    1.6");
		System.out.println("out: "+coordiandte.generateCoordinates(0.3, 1.6 ));
		System.out.println("������   "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   -0.035  -0.009");
		System.out.println("out: "+coordiandte.generateCoordinates(-0.035, -0.009));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   4    1671");
		System.out.println("out: "+coordiandte.generateCoordinates(4, 1671));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   -10   2");
		System.out.println("out: "+coordiandte.generateCoordinates(-10,2));
		System.out.println("������    "+coordiandte.stepCoordinates+"  ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   0    1234 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0,1234 ));
		System.out.println("������    "+coordiandte.stepCoordinates+"  ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   -1234    0 ");
		System.out.println("out: "+coordiandte.generateCoordinates(-1234,0 ));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   10028    10086 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10028,10086));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:    -10086    -10028");
		System.out.println("out: "+coordiandte.generateCoordinates(-10086,-10028));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:    1.3    2.6");
		System.out.println("out: "+coordiandte.generateCoordinates(1.3,2.6));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:    10.3    12.6");
		System.out.println("out: "+coordiandte.generateCoordinates(10.3,12.6));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   10028.000001345    10028.00000457 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10028.000001345,10028.00000457));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   10.2    11.1 ");
		System.out.println("out: "+coordiandte.generateCoordinates(10.2,11.1));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    1.4 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,1.4));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    1.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,1.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    2.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,2.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    3.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,3.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		coordiandte.clear();
		System.out.println("in:   0.2    4.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,4.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    5.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,5.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    6.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,6.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    7.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,7.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    8.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,8.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    9.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,9.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
		
		
		coordiandte.clear();
		System.out.println("in:   0.2    10.8 ");
		System.out.println("out: "+coordiandte.generateCoordinates(0.2,10.8));
		System.out.println("������    "+coordiandte.stepCoordinates+"   ����С��λ��     "+coordiandte.getNumberOfDecimalPlace());
		System.out.println("************************************************");
	}
	*/
	
}
