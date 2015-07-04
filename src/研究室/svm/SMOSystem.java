package 研究室.svm;

import static 研究室.svm.LoggerSMO.logDebug;
import static 研究室.svm.LoggerSMO.print;

import java.io.IOException;
import java.util.Arrays;

import test.LIVSVM.LibsvmReadFileTeacherDataGenerator;
import 研究室.svm.interfaces.Testable;
import 研究室.svm.kernel.PolynomialKernel;
import 研究室.svm.変数取得.SecondRandomFromNotBeBoundary;

public class SMOSystem {
	
	public static void main(String[] args) throws IOException {
		TeacherDataGenerator dataGenerator = new LibsvmReadFileTeacherDataGenerator("a1a", "a1a.t");
		//教師データ
		TeacherData teacherData = dataGenerator.getTeacherData();
		
		teacherData.printLog();
		
		double c = 2.0;
		
		SmoConstant smoConstant = new SmoConstant(teacherData, new PolynomialKernel(3), c);

		//変数選択クラス
		VariableSelector selector = new SecondRandomFromNotBeBoundary(smoConstant);
		
		SMOSystem main = new SMOSystem(selector, smoConstant);
		DecisionFunctions decisionFunction = main.getDecisionFunction();
		
		if (dataGenerator instanceof Testable) {
			printTest(decisionFunction, (Testable) dataGenerator);
		}
		
	}
	
	double[] alphas;
	
	UpdateAlpha smo;
	
	VariableSelector vSector;
	
	SmoConstant constant;
	
	public SMOSystem(VariableSelector selector, SmoConstant smoConstant) {
		this.smo = new UpdateAlpha(smoConstant);;
		alphas = new double[selector.tData.size()];
		this.vSector = selector;
		
		constant = smoConstant;
	}
	
	public DecisionFunctions getDecisionFunction() {
		updateAllAlpha();
		
		logDebug(constant.kernel.getKernelCalcLog());
		
		DecisionFunctions functions = new DecisionFunctions(constant, alphas);
		
		return functions;
	}
	
	public void updateAllAlpha() {
		while (true) {
			int k_index = vSector.getFirstIndex(alphas); //第一変数
			if (k_index == -1) {
				break;
			}
			
			int l_index = vSector.getSecondIndex(alphas, k_index); // 第二変数
			
			while (l_index == k_index) {
				l_index = vSector.getSecondIndex(alphas, k_index); // 第二変数
			}
			
			logDebug("\n selected:first" + k_index + ", secound:" + l_index);
			alphas = smo.getUpdateTwoAlphas(k_index, l_index, alphas);

			//更新済みフラグをつける
			vSector.setUpdateFlg(k_index, l_index);
			
		}
		
		print(Arrays.toString(smo.alphas));
	}
	
	public static void printTest(DecisionFunctions func, Testable testData) {
		TeacherData data = testData.getTestData();

		for (int i = 0; i < data.size(); i++) {
			int value = func.getKronecerUnknownData(data.getX(i));
			if (value == data.getY(i)) {
				LoggerSMO.print("TRUE");
			} else {
				LoggerSMO.print("FALSE");
			}
		}
	}
}
