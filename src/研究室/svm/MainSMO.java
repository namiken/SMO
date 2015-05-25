package 研究室.svm;

import static 研究室.svm.LoggerSMO.logDebug;
import static 研究室.svm.LoggerSMO.print;

import java.util.Arrays;

import 研究室.svm.kernel.PolynomialKernel;
import 研究室.svm.変数取得.SecondRandomFromNotBeBoundary;
import 研究室.svm.教師データ.OneDimensionalData;

public class MainSMO {
	
	public static void main(String[] args) {
		OneDimensionalData oneDimensionalData = new OneDimensionalData();
		//教師データ
		TeacherData teacherData = oneDimensionalData.getTeacherData();
		
		double c = 2.0;
		
		SmoConstant smoConstant = new SmoConstant(teacherData, new PolynomialKernel(2), c);

		UpdateAlpha smoTest = new UpdateAlpha(smoConstant);

		//変数選択クラス
		VariableSelector selector = new SecondRandomFromNotBeBoundary(smoConstant);
		

		MainSMO main = new MainSMO(smoTest, selector);
		main.excute();
		print("b=:" + selector.getBiasTerm(main.alphas));
	}
	
	double[] alphas;
	
	UpdateAlpha smo;
	
	VariableSelector vSector;
	
	public MainSMO(UpdateAlpha smoTest, VariableSelector selector) {
		this.smo = smoTest;
		alphas = new double[selector.tData.size()];
		this.vSector = selector;
		
	}

	public void excute() {
		int count = 0;
		
		while (true) {
			count++;
			
			int k_index = vSector.getFirstIndex(alphas); //第一変数
			if (k_index == -1) {
				break;
			}
			
			int l_index = vSector.getSecondIndex(alphas, k_index); // 第二変数
			
			while (l_index == k_index) {
				l_index = vSector.getSecondIndex(alphas, k_index); // 第二変数
			}
			
			logDebug("selected:first" + k_index + ", secound:" + l_index);
			alphas = smo.getUpdateTwoAlphas(k_index, l_index, alphas);

			//更新済みフラグをつける
			vSector.setUpdateFlg(k_index, l_index);
			
			//TODO 変数化
			if (smo.y.length + 1 < count) {
				throw new RuntimeException("over loop");
			}
		}
		
		print(Arrays.toString(smo.alphas));
	}
	
}
