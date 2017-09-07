package com.ScreenRecoder;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RecorderPane extends JPanel {

	private static final long serialVersionUID = 2868563937949804022L;
	private JButton btnRecord;
	
	public RecorderPane() {
		init();
		addActionListenner();
	}

	private void init() {
		this.setLayout(new FlowLayout());
		btnRecord = new JButton("Screen record");
		this.add(btnRecord);
		
	}
	
	private void addActionListenner() {
		btnRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRecord_Click(e);
			}
		});
	}
	
	private void btnRecord_Click(ActionEvent e) {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setVisible(false);
		
		new SelectionRectangle(this);
	}
	
	public void show(boolean value) {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.setVisible(value);
	}
	
	public void record(Rectangle area) {
    	Robot robot;
		try {
			robot = new Robot();
//			int x = 100;
//			int y = 100;
//			int width = 200;
//			int height = 200;
//			Rectangle area = new Rectangle(x, y, width, height);
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
