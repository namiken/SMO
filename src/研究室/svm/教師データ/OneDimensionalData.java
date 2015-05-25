package 研究室.svm.教師データ;

import java.util.ArrayList;

import 研究室.svm.TeacherData;
import 研究室.svm.TeacherDataGenerator;

public class OneDimensionalData implements TeacherDataGenerator{
	
	@Override
	public TeacherData getTeacherData() {
		PairDataList list = new PairDataList();
//		list.add(-2, -1);
		list.add(-1, -1);
//		list.add(-0.5, 1);
		list.add(0, 1);
//		list.add(0.5, 1);
		list.add(1, -1);
//		list.add(2, -1);
		
		TeacherData teacherData = new TeacherData();
		teacherData.x = list.getXData();
		teacherData.y = list.getYData();
		
		return teacherData;
	}
	
	
	class PairDataList{
		ArrayList<double[]> xList = new ArrayList<>();
		ArrayList<Integer> yList = new ArrayList<>();
		
		void add(double x, int y) {
			xList.add(new double[]{x});
			yList.add(y);
		}
		
		double[][] getXData() {
			double[][] temp = new double[xList.size()][];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = xList.get(i);
			}
			
			return temp;
		}
		
		int[] getYData() {
			int[] temp = new int[yList.size()];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = yList.get(i);
			}
			
			return temp;
		}
	}
}
