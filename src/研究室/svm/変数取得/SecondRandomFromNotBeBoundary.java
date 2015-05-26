package 研究室.svm.変数取得;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import 研究室.svm.SmoConstant;
import 研究室.svm.VariableSelector;

public class SecondRandomFromNotBeBoundary extends VariableSelector{

	public SecondRandomFromNotBeBoundary(SmoConstant smoConstant) {
		super(smoConstant);
	}

	/**
	 * 第二変数を取得。0<α<cなものからランダムで取得
	 * @param k_index
	 * @return
	 */
	public int getSecondIndex(double[] alphas, int k_index) {
		//
		ArrayList<Integer> unupdateIndexs = new ArrayList<>();
		
		for (int i = 0; i < updateAlphaFlgs.length; i++) {
			if (i == k_index) {
				continue;
			}
			
			if (0 < alphas[i] && alphas[i] < c) {
				unupdateIndexs.add(i);
			}
		}
		
		//0<α<cなものがない場合はランダムで取得
		Random random = new Random();
		if (unupdateIndexs.isEmpty()) {
			return random.nextInt(tData.size());
		}
		
		int nextInt = random.nextInt(unupdateIndexs.size());
		return unupdateIndexs.get(nextInt);
	}

	public int getFirstIndex(double[] alphas) {
		if (allUpdated()) {
			Arrays.fill(updateAlphaFlgs, false);
		}
		
		//上限に達していないサポートベクトルに対してKKT条件の違反チェックを行う
		for (int i = 0; i < tData.size(); i++) {
//			//一度更新したものならスキップする
			if (updateAlphaFlgs[i]) {
				continue;
			}
			if (0 < alphas[i] && alphas[i] < c) {
				if (tData.getY()[i] * getDecisionDunction(tData.getX()[i], alphas) != 1) {
					return i;
				}
			}
		}
		
		for (int i = 0; i < tData.size(); i++) {
			//一度更新したものならスキップする
			if (updateAlphaFlgs[i]) {
				continue;
			}
			
			if (0 < alphas[i] && alphas[i] < c) {
				//何もしない
				continue;
			} else if (alphas[i] == 0) {
				if (tData.getY()[i] * getDecisionDunction(tData.getX()[i], alphas) < 1) {
					return i;
				}
			} else if (alphas[i] == c) {
				if (tData.getY()[i] * getDecisionDunction(tData.getX()[i], alphas) > 1) {
					return i;
				}
			} else {
				throw new RuntimeException("αの範囲が不正です");
			}
			
		}
		
		return -1;
	}

	private boolean allUpdated() {
		for (boolean flg : updateAlphaFlgs) {
			if (!flg) {
				return false;
			}
		}
		return true;
	}
}
