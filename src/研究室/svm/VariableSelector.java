package 研究室.svm;


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
//		updateAlphaFlgs[secondIndex] = true;
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
		int[] y = tData.getY();
		
		double sum = 0;
		
		int count = 0;
		for (int j = 0; j < alphas.length; j++) {
			//上限に達しているサポートベクトルを選択
			if (0 < alphas[j] && alphas[j] < c) {
				count ++;
				sum += (y[j] - getCalc(alphas, j));
			}
		}
		
		if (count != 0) {
			return sum / count;
		} else {
			return 0;
		}
	}
	
	protected double getCalc(double[] alphas, int j) {
		double[][] x = tData.getX();
		int[] y = tData.getY();
		
		double sum = 0;
		for (int i = 0; i < alphas.length; i++) {
			sum += alphas[i] * y[i] * kernel.getValue(x[i], x[j]);
		}
		
		return sum;
	}
}
