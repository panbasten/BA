package com.flywet.platform.bi.report.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.report.model.CellArea;
import com.flywet.platform.bi.report.model.CellPosition;

public class SpreedSheetUtils {

	private static final Map<Integer, String> COL_NAME_CACHE = new ConcurrentHashMap<Integer, String>();

	/**
	 * 判断两个区域是否交叉<br>
	 * 算法：两个矩形的重心距离在X和Y轴上都小于两个矩形长或宽的一半之和
	 * 
	 * @return
	 */
	public static boolean cross(CellArea ca1, CellArea ca2) {
		if (Math.abs((ca1.getStartPos().getRow() + ca1.getEndPos().getRow())
				/ 2 - (ca2.getStartPos().getRow() + ca2.getEndPos().getRow())
				/ 2) < ((ca1.getEndPos().getRow() + ca2.getEndPos().getRow()
				- ca1.getStartPos().getRow() - ca2.getStartPos().getRow()) / 2)

				&&

				Math.abs((ca1.getStartPos().getCol() + ca1.getStartPos()
						.getCol())
						/ 2
						- (ca2.getStartPos().getCol() + ca2.getStartPos()
								.getCol()) / 2) < ((ca1.getEndPos().getCol()
						+ ca2.getEndPos().getCol() - ca1.getStartPos().getCol() - ca2
						.getStartPos().getCol()) / 2))
			return true;
		return false;

	}

	/**
	 * 根据两个定位信息，排序生成正常的起始结束定位点
	 * 
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static CellPosition[] sort(CellPosition startPos, CellPosition endPos) {
		if (startPos == null || endPos == null) {
			throw new IllegalArgumentException();
		}

		CellPosition[] rtn = new CellPosition[2];
		if (startPos.getRow() <= endPos.getRow()
				&& startPos.getCol() <= endPos.getCol()) {
			rtn[0] = startPos;
			rtn[1] = endPos;
		} else {
			int startRow = Math.min(startPos.getRow(), endPos.getRow());
			int endRow = Math.max(startPos.getRow(), endPos.getRow());
			int startColumn = Math.min(startPos.getCol(), endPos.getCol());
			int endColumn = Math.max(startPos.getCol(), endPos.getCol());

			rtn[0] = CellPosition.getInstance(startRow, startColumn);
			rtn[1] = CellPosition.getInstance(endRow, endColumn);
		}
		return rtn;
	}

	/**
	 * 获得Cell的位置字符串
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public static String getCellPositionString(int row, int col) {
		return getColumnString(col) + getRowString(row);
	}

	/**
	 * 解析面积位置为定位对象。起始位置为0，例如a1:b2表示为[0,0],[1,1]
	 * 
	 * @param str
	 * @return
	 */
	public static CellPosition[] parserCellAreaPositionString(String str) {
		int idx = str.indexOf(DefaultConst.CELL_POS_SEPARATOR);
		if (idx < 0) {
			CellPosition startPos = parserCellPositionString(str);
			return new CellPosition[] { startPos, startPos };
		} else {
			return sort(parserCellPositionString(str.substring(0, idx)),
					parserCellPositionString(str.substring(idx + 1)));
		}
	}

	/**
	 * 将位置的字母表示解释成为定位对象。起始位置为0，例如a1表示为[0,0]
	 * 
	 * @param str
	 *            非空的区域表示,输入字母的大小写不限，两端是否包含空格不限
	 * @return int[] 0,表示行；1，表示列。
	 */
	public static CellPosition parserCellPositionString(String str) {
		char[] datas = str.toCharArray();
		// 首先得到字母的位置，字母的位数不应该超过2，并且应该是连续的。
		int start = 0, end = 0, sep = 0;
		// 类似Trim的功能
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] != ' ') {
				start = i;
				break;
			}
		}
		for (int i = datas.length - 1; i >= 0; i--) {
			if (datas[i] != ' ') {
				end = i;
				break;
			}
		}
		// 第一个数字位置
		for (int i = 0; i < datas.length; i++) {
			if (Character.isDigit(datas[i])) {
				sep = i;
				break;
			}
		}
		int colValue = 0, rowValue = 0;
		for (int i = start; i < sep; i++) {
			colValue += char2Value(datas[i]) * Math.pow(26, sep - i - 1);
		}
		for (int i = sep; i <= end; i++) {
			rowValue += (datas[i] - '0') * Math.pow(10, end - i);
		}
		rowValue--;
		colValue--;

		return CellPosition.getInstance(rowValue, colValue);
	}

	private static int char2Value(char c) {
		if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 1;
		} else if (c >= 'a' && c <= 'z') {
			return c - 'a' + 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获得列的名称
	 * 
	 * @param col
	 * @return
	 */
	public static String getColumnString(int col) {
		String str = COL_NAME_CACHE.get(col);
		if (str == null) {
			double i = Math.floor(Math.log(25.0 * (col) / 26.0 + 1)
					/ Math.log(26)) + 1;
			if (i > 1) {
				double sub = col - 26 * (Math.pow(26, i - 1) - 1) / 25;
				for (double j = i; j > 0; j--) {
					str += (char) (sub / Math.pow(26, j - 1) + 65);
					sub = sub % Math.pow(26, j - 1);
				}
			} else {
				str += (char) (col + 65);
			}
		}
		return str;
	}

	/**
	 * 获得行的名称
	 * 
	 * @param row
	 * @return
	 */
	public static String getRowString(int row) {
		return "" + (row + 1);
	}
}
