package 研究室.svm.教師データ;

import 研究室.svm.TeacherData;
import 研究室.svm.TeacherDataGenerator;

public class TwoDementionData implements TeacherDataGenerator{

	@Override
	public TeacherData getTeacherData() {
		TeacherData tData = new TeacherData();
		
		tData.x = new double[][]{{0,1},{1,0},{1,0},{0,-1},{-1,0}};
		tData.y = new int[]{1, 1, 1, -1, -1};
		return tData;
	}

}
