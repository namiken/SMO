package util;

import java.awt.image.BufferedImage;

public class ImageUtil {
	
	public static BufferedImage cutMargin(BufferedImage image) {
		int height = image.getHeight();
		int width = image.getWidth();
		
		//上の余白
		int upBound = 0;
		upLoop:
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int argb = image.getRGB(j, i);
				
				if (isBlack(argb)) {
					upBound = i;
					break upLoop;
				}
			}
		}

		//下の余白
		int downBound = 0;
		downLoop:
		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				int argb = image.getRGB(j, i);
				
				if (isBlack(argb)) {
					downBound = i;
					break downLoop;
				}
			}
		}

		//右の余白
		int rightBound = 0;
		rightLoop:
		for (int i = width - 1; i >= 0; i--) {
			for (int j = 0; j < height; j++) {
				int argb = image.getRGB(i, j);
				
				if (isBlack(argb)) {
					rightBound = i;
					break rightLoop;
				}
			}
		}
		
		//左の余白
		int leftBound = 0;
		leftLoop:
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int argb = image.getRGB(i, j);
				
				if (isBlack(argb)) {
					leftBound = i;
					break leftLoop;
				}
			}
		}
		
		return image.getSubimage(leftBound, upBound, rightBound - leftBound, downBound - upBound);
	}
	
	/**
	 * 指定されたargbが黒判定ならTRUE
	 * @param argb
	 * @return
	 */
	public static boolean isBlack(int argb) {
		boolean isNotWhilte = false;
		//red
		if (!isNotWhilte && 0xFF != ((argb>>>16) & 0xff)) {
			isNotWhilte = true;
		}
		
		//green
		if (!isNotWhilte && 0xFF != ((argb>>>8) & 0xff)) {
			isNotWhilte = true;
		}
		
		//blue
		if (!isNotWhilte && 0xFF != (argb & 0xff)) {
			isNotWhilte = true;
		}
		return isNotWhilte;
	}
}
