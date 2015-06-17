package 研究室.svm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Kernel {
	
	KernelCashe cashe = new KernelCashe();
	
	public final double getValue(double[] x1, double[] x2) {
		double valueOverride = getValueOverride(x1, x2);
		
		//キャッシュを残しておく
		cashe.add(x1, x2, valueOverride);
		
		return valueOverride;
	}
	
	public String getKernelCalcLog() {
		return "\n====カーネルログ=====\n" + cashe.toString() + "====カーネルログ=====\n";
	}
	
	protected abstract double getValueOverride(double[] x1, double[] x2);
	
	//TODO ログをここに置くのはナンセンス
	class KernelCashe {
		HashMap<Integer, Double> cashe = new HashMap<>();
		HashMap<Integer, double[]> x1_log = new HashMap<>();
		HashMap<Integer, double[]> x2_log = new HashMap<>();
		
		void add(double[] x1, double[] x2, double value) {
			int x1Hash = x1.hashCode();
			int x2Hash = x2.hashCode();
			
			int key = x1Hash + x2Hash;
			//キャッシュに入れる
			cashe.put(key, value);
			
			//ログを残す
			x1_log.put(key, x1);
			x2_log.put(key, x2);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			//1つずつループさせてログを出力する
			for (Entry<Integer, Double> entety : cashe.entrySet()) {
				int key = entety.getKey();
				sb.append("k(" + Arrays.toString(x1_log.get(key)) + ", " + Arrays.toString(x2_log.get(key))  + ") = " + entety.getValue());
				sb.append("\n");
			}
			return sb.toString();
		}
	}
	
	public String getMathematicaString(double[] x1) {
		throw new RuntimeException("need to Override");
	}
}