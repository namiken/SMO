package util;

public class ArrayCalcUtil{
	/**
	 * スカラー倍 ベクトルAにスカラーcをかける
	 */
	public static double[] scalarMultiple(double c, double[] A) {
		double[] tempA = new double[A.length];
		for (int i = 0; i < A.length; i++) {
			tempA[i] = A[i] * c;
		}
		return tempA;
	}

	/**
	 * ベクトルAとベクトルBの和
	 */
	public static double[] addition(double[] A, double[] B) {
		double[] temp = new double[A.length];
		for (int i = 0; i < A.length; i++) {
			temp[i] = A[i] + B[i];
		}
		return temp;
	}

	/**
	 * ベクトルAとベクトルBの差(A-B) <br />
	 * A - B = A + (-1)*B
	 */
	public static double[] subtraction(double[] A, double[] B) {
		return addition(A, scalarMultiple(-1, B));
	}
	
	/**
	 * 内積を求める
	 * @param A
	 * @param B
	 * @return
	 */
	public static double innerProduct(double[] A, double[] B) {
		double sum = 0;
		for (int i = 0; i < A.length; i++) {
			sum += A[i] * B[i];
		}
		
		return sum;
	}
	

}
