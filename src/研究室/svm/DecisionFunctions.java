package 研究室.svm;


public class DecisionFunctions {
	
	TeacherData tData;
	Kernel kernel;
	double c;
	
	double[] alphas;
	
	public DecisionFunctions(SmoConstant constData, double[] alphas) {
		tData = constData.tData;
		kernel = constData.kernel;
		c = constData.c;
		this.alphas = alphas;
	}
	
	public double checkUnknownData(double[] unKnownX) {
		if (tData.x[0].length != unKnownX.length) {
			throw new RuntimeException("not same size of unknown data size and teacher data size!!");
		}
		
//		return "未知データ:" + Arrays.toString(unKnownX) + "は" + getDecisionDunction(unKnownX);
		return getDecisionDunction(unKnownX);
	}
	
	/**
	 * 決定関数を取得する
	 * @param unKnownX
	 * @return
	 */
	public double getDecisionDunction(double[] unKnownX) {
		double sum = 0;
		
		double[][] x = tData.getX();
		int[] y = tData.getY();
		
		for (int i = 0; i < x.length; i++) {
			//サポートベクトルのみで構成する
			if (0 < alphas[i] && alphas[i] <= c) {
				sum += alphas[i] * y[i] * kernel.getValue(unKnownX, x[i]);
			}
		}
		
		return sum += getBiasTerm();
	}
	
	/**
	 * バイアス項を取得する
	 * @param alphas 
	 * @return
	 */
	protected double getBiasTerm() {
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
	
	public String getMathmaticaStringt() {
		StringBuilder sb = new StringBuilder();
		sb.append("Simplify[");
		
		for (int i = 0; i < alphas.length; i++) {
			sb.append((alphas[i] * tData.y[i]) );
			sb.append(" * ");
			sb.append(kernel.getMathematicaString(tData.x[i]));
			sb.append(" + ");
		}
		sb.append(getBiasTerm());
		
		sb.append(" == 0");
		sb.append(" ]");
		
		return sb.toString();
	}
}
