package 研究室.svm;

import util.ArrayCalcUtil;

public abstract class VariableSelector {
	public VariableSelector(SmoConstant smoConstant) {
		updateAlphaFlgs = new boolean[smoConstant.tData.size()];
		this.tData = smoConstant.tData;
		this.c = smoConstant.c;
		this.kernel = smoConstant.kernel;
	}
	
	protected TeacherData tData;
	
	protected double c;
	
	protected Kernel kernel;
	
	abstract public int getFirstIndex(double[] alphas);
	
	abstract public int getSecondIndex(double[] alphas, int firstIndex);
	
	//ラグランジュ乗数のアップデートフラグ
	protected boolean[] updateAlphaFlgs;
	
	public void setUpdateFlg(int firstIndex, int secondIndex) {
		updateAlphaFlgs[firstIndex] = true;
		updateAlphaFlgs[secondIndex] = true;
	}
	
	/**
	 * 決定関数を取得する
	 * @param unKnownX
	 * @return
	 */
	public double getDecisionDunction(double[] unKnownX, double[] alphas) {
		double sum = 0;
		
		double[][] x = tData.getX();
		int[] y = tData.getY();
		
		for (int i = 0; i < x.length; i++) {
			//サポートベクトルのみで構成する
			if (0 < alphas[i] && alphas[i] <= c) {
				sum += alphas[i] * y[i] * kernel.getValue(unKnownX, x[i]);
			}
		}
		
		return sum += getBiasTerm(alphas);
	}
	
	//TODO キャッシュを行う
	/**
	 * バイアス項を取得する
	 * @param alphas 
	 * @return
	 */
	protected double getBiasTerm(double[] alphas) {
		double[][] x = tData.getX();
		int[] y = tData.getY();
		
		double sum = 0;
		
		int count = 0;
		for (int i = 0; i < alphas.length; i++) {
			//上限に達しているサポートベクトルを選択
			if (0 < alphas[i] && alphas[i] < c) {
				count ++;
				sum += (y[i] - ArrayCalcUtil.innerProduct(getW(alphas), x[i]));
			}
		}
		
		if (count != 0) {
			return sum / count;
		} else {
			return 0;
		}
	}

	
	private double[] getW(double[] alphas) {
		double[][] x = tData.getX();
		int[] y = tData.getY();
		
		double sum[] = new double[x[0].length];
		for (int i = 0; i < x.length ; i++) {
			sum = ArrayCalcUtil.addition(sum, ArrayCalcUtil.scalarMultiple(alphas[i] * y[i], x[i]));
		}
		return sum;
	}
}
