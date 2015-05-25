package 研究室.svm;

/**
 * 教師データを管理するクラス
 * @author kensuke
 *
 */
public class TeacherData {
	
	public int[] y;
	public double[][] x;
	
	public int[] getY() {
		return y;
	}
	
	public double[][] getX() {
		return x;
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
}
