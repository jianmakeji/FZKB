package com.jianma.fzkb.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
 
import javax.imageio.ImageIO;
 

public class CaptchaUtil {
 
	private static final String RANDOM_STRS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
	private static final String FONT_NAME = "Fixedsys";
	private static final int FONT_SIZE = 18;
 
	private Random random = new Random();
 
	private int width = 80;
	private int height = 25;
	private int lineNum = 50;
	private int strNum = 4;
 
	public BufferedImage genRandomCodeImage(StringBuffer randomCode) {

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
 
		g.setColor(getRandColor(110, 120));
 
		for (int i = 0; i <= lineNum; i++) {
			drowLine(g);
		}
		g.setFont(new Font(FONT_NAME, Font.ROMAN_BASELINE, FONT_SIZE));
		for (int i = 1; i <= strNum; i++) {
			randomCode.append(drowString(g, i));
		}
		g.dispose();
		return image;
	}
 
	
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
 
	
	private String drowString(Graphics g, int i) {
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
				.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(RANDOM_STRS
				.length())));
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return rand;
	}
 
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int x0 = random.nextInt(16);
		int y0 = random.nextInt(16);
		g.drawLine(x, y, x + x0, y + y0);
	}
 
	
	private String getRandomString(int num) {
		return String.valueOf(RANDOM_STRS.charAt(num));
	}
 
	public static void main(String[] args) {
		CaptchaUtil tool = new CaptchaUtil();
		StringBuffer code = new StringBuffer();
		BufferedImage image = tool.genRandomCodeImage(code);
		System.out.println("&gt;&gt;&gt; random code =: " + code);
		try {

			ImageIO.write(image, "JPEG", new FileOutputStream(new File(
					"random-code.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
}