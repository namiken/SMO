package 研究室.svm;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 教師データを管理するクラス
 * @author kensuke
 *
 */
public class TeacherData implements Serializable{
	
	private static final long serialVersionUID = -9079981953711598917L;

	public int[] y;
	public double[][] x;
	
	public int[] getY() {
		assertSize();
		return y;
	}
	
	public double[][] getX() {
		assertSize();
		return x;
	}

	public int getY(int i) {
		assertSize();
		return y[i];
	}
	
	public double[] getX(int i) {
		assertSize();
		return x[i];
	}
	
	public int size() {
		assertSize();
		return x.length;
	}
	
	/**
	 * xとyのサイズが異るとき、Exceptionを起こす
	 */
	private void assertSize() {
		if (y.length != x.length) {
			throw new RuntimeException("teacher data pare is not same size");
		}
	}
	
	public void printLog() {
		double[][] xData = x;
		int[] yData = y;
		LoggerSMO.logNormal("=========Teacher Data========");
		for (int i = 0; i < size(); i++) {
			LoggerSMO.logNormal(i + ":( x=" + Arrays.toString(xData[i]) + " ,y=" + yData[i] + ")");
		}
		LoggerSMO.logNormal("=========Teacher Data========");
	}
}
