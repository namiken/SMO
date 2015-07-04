package test.LIVSVM;

import java.io.IOException;


public class LibsvmReadFileTeacherDataGenerator extends LivsvmTeacherDataGenerater {

	public static void main(String[] args) throws IOException {
		LibsvmReadFileTeacherDataGenerator ins = new LibsvmReadFileTeacherDataGenerator("a1a", "a1a.t");
		ins.getTeacherData();
	}
	
	public LibsvmReadFileTeacherDataGenerator(String fileName, String testFile) throws IOException {
		super("libsvm\\" + fileName, "libsvm\\" + testFile);
	}
	
}
