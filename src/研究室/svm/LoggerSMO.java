package 研究室.svm;

public class LoggerSMO {
	public static void logDebug(String str) {
		if (printLogFlg) {
			System.out.println(str);
		}
	}

	public static void logDebug(String format, Object ... args) {
		if (printLogFlg) {
			System.out.printf(format + "\n", args);
		}
	}
	
	public static void print(String str) {
		System.out.println(str);
	}

	public static void logNormal(String str) {
		if (printLogFlg) {
			System.out.println(str);
		}
	}
	
	public final static boolean printLogFlg = false; 
}
