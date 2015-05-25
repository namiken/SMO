package 研究室.svm;

public class SmoConstant {
	public TeacherData tData;
	public Kernel kernel;
	public double c;
	
	public SmoConstant(TeacherData tData, Kernel kernel, double c) {
		this.tData = tData;
		this.kernel = kernel;
		this.c = c;
	}
}
