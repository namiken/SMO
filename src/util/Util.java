package util;

import java.util.ArrayList;

public class Util {
	public static int[] toArray(ArrayList<Integer> data) {
		int[] rtn = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			rtn[i] = data.get(i);
		}
		
		return rtn;
	}
}
