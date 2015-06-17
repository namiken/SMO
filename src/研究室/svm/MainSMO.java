package 研究室.svm;

import static 研究室.svm.LoggerSMO.logDebug;
import static 研究室.svm.LoggerSMO.print;

import java.io.IOException;
import java.util.Arrays;

import 研究室.svm.kernel.LinearKernel;
import 研究室.svm.kernel.PolynomialKernel;
import 研究室.svm.変数取得.SecondRandomFromNotBeBoundary;
import 研究室.svm.教師データ.TwoDementionData;

public class MainSMO {
	
	public static void main(String[] args) throws IOException {
		TeacherDataGenerator dataGenerator = new TwoDementionData();
//				new ImageTeacherData(9, 
//				new File("C:\\Users\\kensuke\\Desktop\\研究室\\1"),
//				new File("C:\\Users\\kensuke\\Desktop\\研究室\\2"));
		
		//教師データ
		TeacherData teacherData = dataGenerator.getTeacherData();
		
		teacherData.printLog();
		
		double c = 2.0;
		
		SmoConstant smoConstant = new SmoConstant(teacherData, new LinearKernel(), c);

		//変数選択クラス
		VariableSelector selector = new SecondRandomFromNotBeBoundary(smoConstant);
		
		MainSMO main = new MainSMO(selector, smoConstant);
		main.updateAllAlpha();
		
		logDebug(smoConstant.kernel.getKernelCalcLog());
		
		DecisionFunctions functions = new DecisionFunctions(smoConstant, main.alphas);
		
		print("b = " + functions.getBiasTerm());
		
//		double checkUnknownData = functions.checkUnknownData(new ScanImage(9).readOneImage(new File("C:\\Users\\kensuke\\Desktop\\研究室\\test\\test.jpg")));
//		print(checkUnknownData > 0 ? "未知データは：１" : "未知データは：２");
		
		logDebug(functions.getMathmaticaStringt());
		
	}
	
	double[] alphas;
	
	UpdateAlpha smo;
	
	VariableSelector vSector;
	
	SmoConstant constant;
	
	public MainSMO(VariableSelector selector, SmoConstant smoConstant) {
		this.smo = new UpdateAlpha(smoConstant);;
		alphas = new double[selector.tData.size()];
		this.vSector = selector;
		
		constant = smoConstant;
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
	
}
