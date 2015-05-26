package 研究室.svm.変数取得;

import 研究室.svm.SmoConstant;
import 研究室.svm.VariableSelector;

/**
 * alphas = [0.6666666666666667, 0.0, 0.3333333333333333]
 * b= -1.0
 * @author kensuke
 *
 */
public class TestOneDementionSelector extends VariableSelector {

	public TestOneDementionSelector(SmoConstant smoConstant) {
		super(smoConstant);
	}

	int count = 0;
	
	@Override
	public int getFirstIndex(double[] alphas) {
		count++;

		switch (count) {
		case 1:
			return 0;
		case 2:
			return 2;
		case 3:
			return -1;
		default:
			throw new RuntimeException("over count first!!");
		}
	}

	@Override
	public int getSecondIndex(double[] alphas, int firstIndex) {
		switch (count) {
		case 1:
			return 1;
		case 2:
			return 0;
		default:
			throw new RuntimeException("over count second!!");
		}
	}

}
