package 研究室.svm.kernel;

import util.ArrayCalcUtil;
import 研究室.svm.Kernel;


public class LinearKernel extends Kernel {
	
	public LinearKernel() {
	}
	
	@Override
	protected double getValueOverride(double[] x1, double[] x2) {
		return ArrayCalcUtil.innerProduct(x1, x2);
	}
	
	@Override
	public String getMathematicaString(double[] teacherData) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		for (int i = 0; i < teacherData.length; i++) {
			sb.append("x" + (i + 1));
			sb.append(" * " + teacherData[i]);
			
			if (i != teacherData.length - 1) {
				sb.append(" + ");
			}
		}
		sb.append(")");
		
		return sb.toString();
	}
}
