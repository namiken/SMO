package 研究室.svm;


public class UpdateAlpha {
	//マージンパラメータ
	final double c;
	
	double[] alphas;
	final int[] y;
	final double[][] x;
	
	Kernel karnel;
	
	public UpdateAlpha(SmoConstant smoConstant) {
		this.x = smoConstant.tData.getX();
		this.y = smoConstant.tData.getY();
		this.karnel = smoConstant.kernel;
		this.c = smoConstant.c;
	}

	/**
	 * ηを求める
	 * 対称関数
	 * @param xk
	 * @param xl
	 * @return
	 */
	private double eta(double[] xk, double[] xl) {
		return 2.0 * karnel.getValue(xk, xl) - karnel.getValue(xk, xk) - karnel.getValue(xl, xl);
	}
	
	/**
	 * ガンマを求める
	 * 対称関数
	 * @param l_index
	 * @param k_index
	 * @return
	 */
	private double gamma(int l_index, int k_index) {
		return y[k_index] * alphas[k_index] + y[l_index] * alphas[l_index];
	}
	
	/**
	 * F^(old)_l - F^(old)_k を求める
	 * 非対称関数
	 * @param l_index
	 * @param k_index
	 * @return
	 */
	private double oldFSubtraction(int l_index, int k_index) {
		double[] xk = x[k_index];
		double[] xl = x[l_index];
		
		double sum = 0;
		
		//1項目
		for (int i = 0; i < x.length; i++) {
			if (i == k_index || i == l_index) {
				continue;
			}
			
			sum += alphas[i] * (double)y[i] * (karnel.getValue(x[i], xl) - karnel.getValue(x[i], xk));
		}
		
		//2項目
		sum += -1.0 * (y[l_index] - y[k_index]);
		
		//3項目
		sum += gamma(l_index, k_index) * (karnel.getValue(xl, xk) - karnel.getValue(xk, xk));
		
		//4項目
		sum += -1.0 * alphas[l_index] * y[l_index] * eta(xk, xl);
		
		return sum;
	}
	
	/**
	 * 与えられた２つのαを更新する
	 * @param l_index
	 * @param k_index
	 */
	public double[] getUpdateTwoAlphas(int l_index, int k_index, double[] alphas) {
		this.alphas = alphas;
		
		if (eta(x[l_index], x[k_index]) != 0) {
			updateTwoAlphasIfEtaNotZero(l_index, k_index);
		} else {
			updateTwoAlphasIfEtaIsZero(l_index, k_index);
		}
		
		return this.alphas;
	}

	/**
	 * ηが0のとき、αを更新する
	 * @param l_index
	 * @param k_index
	 */
	protected void updateTwoAlphasIfEtaIsZero(int l_index, int k_index) {
		int yl_yk = y[l_index] * y[k_index];
		double tempF = y[l_index] * oldFSubtraction(l_index, k_index);
		
		double l_newAlpha;
		
		if (yl_yk == 1) {
			if (tempF >= 0) {
				l_newAlpha = Math.min(y[l_index] * gamma(l_index, k_index), c);
			} else {
				l_newAlpha = Math.max(0, y[l_index] * gamma(l_index, k_index) - c);
			}
		} else {
			if (tempF >= 0) {
				l_newAlpha = Math.min(y[l_index] * gamma(l_index, k_index) + c, c);
			} else {
				l_newAlpha = Math.max(0, y[l_index] * gamma(l_index, k_index));
			}
		}
		
		//α_lを更新
		alphas[l_index] = l_newAlpha;
		
		//α_kを作成する
		double k_newAlpha = -1.0 * y[k_index] * y[l_index] * alphas[l_index] + y[k_index] * gamma(l_index, k_index);
		
		//α_kを更新
		alphas[k_index] = k_newAlpha;
	}

	/**
	 * ηが0でないとき、αを更新する
	 * @param l_index
	 * @param k_index
	 */
	protected void updateTwoAlphasIfEtaNotZero(int l_index, int k_index) {
		double[] xl = x[l_index];
		double[] xk = x[k_index];
		
		double l_newAlpha = y[l_index] * oldFSubtraction(l_index, k_index) / eta(xk, xl) + alphas[l_index];
		
		//0 <= α_l <= cを満たすように 
		if (l_newAlpha < 0.0) {
			l_newAlpha = 0.0;
		} else if (l_newAlpha > c) {
			l_newAlpha = c;
		}
		
		//0 <= α_k <= cを満たすようなα_lを作成する
		if (y[l_index] * y[k_index] == 1) {
			if (l_newAlpha < y[l_index] * gamma(l_index, k_index) - c) {
				l_newAlpha = y[l_index] * gamma(l_index, k_index) - c;
			} else if (l_newAlpha > y[l_index] * gamma(l_index, k_index)) {
				l_newAlpha = y[l_index] * gamma(l_index, k_index);
			}
		} else {
			if (l_newAlpha > y[l_index] * gamma(l_index, k_index) + c) {
				l_newAlpha = y[l_index] * gamma(l_index, k_index) + c;
			} else if (l_newAlpha < y[l_index] * gamma(l_index, k_index)) {
				l_newAlpha = y[l_index] * gamma(l_index, k_index);
			}
		}
		
		//α_lを更新
		alphas[l_index] = l_newAlpha;
		
		//α_kを作成する
		double k_newAlpha = -1.0 * y[k_index] * y[l_index] * alphas[l_index] + y[k_index] * gamma(l_index, k_index);
		
		//α_kを更新
		alphas[k_index] = k_newAlpha;
	}
	
}

