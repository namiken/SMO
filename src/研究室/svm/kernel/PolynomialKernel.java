package 研究室.svm.kernel;

import util.ArrayCalcUtil;
import 研究室.svm.Kernel;


public class PolynomialKernel extends Kernel {
	int d;
	
	public PolynomialKernel(int d) {
		this.d = d;
	}
	
	@Override
	protected double getValueOverride(double[] x1, double[] x2) {
		double temp = ArrayCalcUtil.innerProduct(x1, x2) + 1.0;
		return Math.pow(temp, d);
	}

}
