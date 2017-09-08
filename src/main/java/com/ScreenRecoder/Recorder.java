package com.ScreenRecoder;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class Recorder {
	
	public static Rectangle selectionBound;
	
	public static void record() {
		record(selectionBound);
	}
	
	public static void takeScreenShot() {
		takeScreenShot(selectionBound);
	}
	
	public static void record(Rectangle area) {
    	Robot robot;
		try {
			robot = new Robot();
			BufferedImage bufferedImage = robot.createScreenCapture(area);
			
			File file = getSaveFile("png");
			if (file != null) {
				ImageIO.write(bufferedImage, "png", file);				
			}
			
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void takeScreenShot(Rectangle area) {
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage bufferedImage = robot.createScreenCapture(area);
			
			File file = getSaveFile("png");
			if (file != null) {
				ImageIO.write(bufferedImage, "png", file);
			}
			
			FrameFactory.getFrame(FrameFactory.FRAME_MAIN).setVisible(true);
			FrameFactory.getFrame(FrameFactory.FRAME_MAIN).setState(Frame.NORMAL);
			
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getSaveFile(String subfix) {
		JFileChooser fc = new JFileChooser();
		int rValue = fc.showSaveDialog(FrameFactory.frmMain);
		
		if (rValue == JFileChooser.APPROVE_OPTION) {
			return new File(fc.getSelectedFile().getAbsolutePath() + "." + subfix);
		}
		
		return null;
	}

}
