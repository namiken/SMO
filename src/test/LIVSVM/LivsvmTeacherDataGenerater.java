package test.LIVSVM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import util.Util;
import 研究室.svm.TeacherData;
import 研究室.svm.TeacherDataGenerator;
import 研究室.svm.interfaces.Testable;

public class LivsvmTeacherDataGenerater implements TeacherDataGenerator, Testable{
	File file;
	File testFile;
	
	TeacherData teacherData;

	TeacherData testData;
	
	public LivsvmTeacherDataGenerater(String path, String testPath) throws IOException{
		file = new File(path);
		testFile = new File(testPath);
	}
	
	@Override
	public TeacherData getTeacherData() {
		if (teacherData == null) {
			teacherData = createtTeacherDataByFile(file);
		}
		
		return teacherData;
	}

	/**
	 * Fileから教師データを作成する
	 * @param file
	 * @return
	 */
	protected TeacherData createtTeacherDataByFile(File file) {
		ArrayList<double[]> xList = new ArrayList<>();
		ArrayList<Integer> yList = new ArrayList<Integer>();
		
		int demention = -1;
		
		try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;
			while ((line = bf.readLine()) != null) {
				String[] split = line.split(" ");
				//次元数を設定する
				if (demention == -1) {
					demention = split.length;
				}

				//不正なデータのときは無視する
				if (split.length != demention) {
					continue;
				}
				//クラスを設定する
				yList.add(Integer.parseInt(split[0]));
				
				double[] x = new double[split.length - 1];
				for (int i = 1; i < split.length; i++) {
					x[i-1] = Double.parseDouble(split[i].replace(":1", ""));
				}
				
				xList.add(x);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TeacherData teacherData = new TeacherData();
		teacherData.x = xList.toArray(new double[0][0]);
		teacherData.y = Util.toArray(yList);
		return teacherData;
	}

	@Override
	public TeacherData getTestData() {
		if (testData == null) {
			testData = createtTeacherDataByFile(testFile);
		}
		return teacherData;
	}

}
