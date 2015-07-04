package 研究室.svm.教師データ;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import 画像.ScanImage;
import 研究室.svm.TeacherData;
import 研究室.svm.TeacherDataGenerator;

public class ImageTeacherData implements TeacherDataGenerator {
	
	ScanImage scanImage;
	
	File oneImageFolder;
	File twoImageFolder;
	public ImageTeacherData(int c, File oneImageFolder, File twoImageFolder) {
		scanImage = new ScanImage(c);
		this.oneImageFolder = oneImageFolder;
		this.twoImageFolder = twoImageFolder;
	}

	@Override
	public TeacherData getTeacherData() {
		TeacherData teacherData = new TeacherData();
		try {
			ArrayList<double[]> x = new ArrayList<double[]>();
			ArrayList<Integer> yTemp = new ArrayList<Integer>();
			
			for (File file : oneImageFolder.listFiles()) {
				if (file.isHidden()) {
					continue;
				}
				x.add(scanImage.readOneImage(file));
				yTemp.add(1);
			}

			for (File file : twoImageFolder.listFiles()) {
				if (file.isHidden()) {
					continue;
				}
				x.add(scanImage.readOneImage(file));
				yTemp.add(-1);
			}
			
			teacherData.x = x.toArray(new double[0][]);
			
			int[] y = new int[yTemp.size()];
			for (int i = 0; i < yTemp.size(); i++) {
				y[i] = yTemp.get(i);
			}
			teacherData.y = y;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return teacherData;
	}
}
