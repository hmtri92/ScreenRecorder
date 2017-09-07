package com.ScreenRecoder;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App 
{
    public static void main( String[] args ) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
		}
		
		JFrame mainFrame = new JFrame("Recorder");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add( new RecorderPane());
		mainFrame.setBounds(new Rectangle(300, 70));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
    }
    
    private void record() {
    	Robot robot;
		try {
			robot = new Robot();
			int x = 100;
			int y = 100;
			int width = 200;
			int height = 200;
			Rectangle area = new Rectangle(x, y, width, height);
			BufferedImage bufferedImage = robot.createScreenCapture(area);
			
			// Capture the whole screen
			area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			bufferedImage = robot.createScreenCapture(area);
			
			File file = new File("D:/capture.png");
			ImageIO.write(bufferedImage, "png", file);
			
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
