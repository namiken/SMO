package 研究室.svm;


public abstract class VariableSelector {
	protected SmoConstant constantData;
	
	public VariableSelector(SmoConstant smoConstant) {
		constantData = smoConstant;
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
	
}
