package 画像;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.ImageUtil;

public class ScanImage {
	
	public static void main(String[] args) throws IOException {
		ScanImage scanImage = new ScanImage(5);
		scanImage.readOneImage(new File("C:\\Users\\kensuke\\Desktop\\研究室\\2\\2.jpg"));
		
	}
	
	 int partition;
	 
	 public ScanImage(int partition) {
		 if (partition <= 0) {
			 throw new RuntimeException("partition need to upper zero.partition  c:" + partition);
		 }
		 this.partition = partition;
	}
	
	public double[] readOneImage(File imageFile) throws IOException {
		//黒率を入れる配列を初期化する
		rateList = new double[partition * partition];
		//格子の座標を入れるリストを初期化する
		drowList.clear();
		
		if (!imageFile.isFile()) {
			throw new RuntimeException("image file is not file:" + imageFile.getPath());
		}
		//余白を切り取る
		BufferedImage image = ImageUtil.cutMargin(ImageIO.read(imageFile));
		
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.red);
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		//分割数
		int c = partition;
		
		//格子のカウント
		int analyzeCount = 0;
		for (int hCount = 0; hCount < c; hCount++) {
			for (int wCount = 0; wCount < c; wCount++) {
				rateList[analyzeCount] = analyzeRangeImage((height / c) * hCount, (height / c) * (hCount + 1) - 1, (width / c) * wCount, (width / c ) * (wCount + 1) - 1, image);
				analyzeCount++;
			}
		}
		//格子を描写する
		for (int[] points : drowList) {
			g2.drawRect(points[2], points[0], points[3] - points[2], points[1] - points[0]);
		}
//		g2.dispose();
//		ImageIO.write(image, "jpeg", new File("C:\\Users\\kensuke\\Desktop\\研究室\\2\\2-1.jpg"));
		
		return rateList;
	}
	
	ArrayList<int[]> drowList = new ArrayList<>();
	
	double[] rateList;

	private double analyzeRangeImage(int startH, int endH, int startW, int endW, BufferedImage image) {
		//格子の座標を入れる
		drowList.add(new int[]{startH, endH, startW, endW});
		
		AnalyzeImagePart analyzeImagePart = new AnalyzeImagePart();
		for (int h = startH; h < endH; h++) {
			for (int w = startW; w < endW; w++) {
				analyzeImagePart.analyzeOnePixel(image.getRGB(w, h));
			}
		}
		return analyzeImagePart.getRaitoBlack();
	}

	
	 class AnalyzeImagePart {
		int allPixel;
		
		int blackCount = 0;

		public AnalyzeImagePart() {
		}
		
		public void analyzeOnePixel(int argb) {
			allPixel++;
			
			boolean isNotWhilte = ImageUtil.isBlack(argb);
			
			if (isNotWhilte) {
				blackCount++;
			}
			
		}

		protected void getColorString(int argb) {
			int a = (argb>>>24) & 0xff;
			int r = (argb>>>16) & 0xff;
			int g = (argb>>> 8) & 0xff;
			int b = argb        & 0xff;
			System.out.println("a:"+a+" r:"+r+" g:"+g+" b:"+b);
		}
		
		public double getRaitoBlack() {
			return 1.0 * (double)blackCount / (double)allPixel;
		}
	}
}
