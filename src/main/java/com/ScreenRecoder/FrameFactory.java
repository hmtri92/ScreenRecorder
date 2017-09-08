package com.ScreenRecoder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.ScreenRecoder.ui.ControlPane;
import com.ScreenRecoder.ui.RecordPane;
import com.ScreenRecoder.ui.selection.SnipItPane;

public class FrameFactory {
	public static final int FRAME_MAIN = 1;
	public static final int FRAME_SELECT_RECTANGLE = 2;
	public static final int FRAME_RECORD = 3;
	
	public static final int MODE_TAKE_SCREEN = 10;
	public static final int MODE_RECORD = 11;
	
	public static int mode = 0;
	
	static JFrame frmMain;
	static JFrame frmSelection;
	static JFrame frmRecord;
	static SnipItPane snipItPane;
	
	public static JFrame getFrame (int frameType) {
		switch (frameType) {
		case FRAME_MAIN:
			return getMainFrame();
		case FRAME_SELECT_RECTANGLE:
			return getSelectionFrame();
		case FRAME_RECORD:
			return getRecordFrame();
		}
		
		return null;
	}
	
	public static void removeFrame (int frameType) {
		switch (frameType) {
		case FRAME_MAIN:
			frmMain = null;
			break;
		case FRAME_SELECT_RECTANGLE:
			frmSelection = null;
			break;
		case FRAME_RECORD:
			frmRecord = null;
			break;
		}
		
	}
	
	private static JFrame getMainFrame() {
		if (frmMain != null) {
			return frmMain;
		}
		
		frmMain = new JFrame("Recorder");
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setLayout(new BorderLayout());
		frmMain.add( new ControlPane());
		frmMain.setBounds(new Rectangle(300, 70));
		frmMain.setLocationRelativeTo(null);
		
		return frmMain;
	}
	
	private static JFrame getRecordFrame() {
		if (frmRecord != null) {
			return frmRecord;
		}
		
		frmRecord = new JFrame("Recorder");
		frmRecord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRecord.setLayout(new BorderLayout());
		frmRecord.add( new RecordPane());
		frmRecord.setBounds(new Rectangle(300, 70));
		frmRecord.setLocationRelativeTo(null);
		
		return frmRecord;
	}
	
	private static JFrame getSelectionFrame() {
		if (frmSelection != null) {
			snipItPane.resetSelection();
			return frmSelection;
		}
		
		snipItPane = new SnipItPane();
		
		frmSelection = new JFrame("Select Area");
		frmSelection.setUndecorated(true);
		frmSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSelection.setLayout(new BorderLayout());
		// transparent
		frmSelection.setBackground(new Color(0, 0, 0, 0));
		frmSelection.add(snipItPane);
		frmSelection.setBounds(getVirtualBounds());
		frmSelection.setLocationRelativeTo(null);
		
		return frmSelection;
	}
	
	public static Rectangle getVirtualBounds() {
		Rectangle bounds = new Rectangle(0, 0, 0, 0);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice lstGDs[] = ge.getScreenDevices();
		int minx = Integer.MAX_VALUE;
		int miny = Integer.MAX_VALUE;
		int maxx = Integer.MIN_VALUE;
		int maxy = Integer.MIN_VALUE;

		for (GraphicsDevice gd : lstGDs) {
			for (GraphicsConfiguration config : gd.getConfigurations()) {
				bounds = config.getBounds();
				minx = Math.min(minx, bounds.x);
				miny = Math.min(miny, bounds.y);
				maxx = Math.max(maxx, bounds.x + bounds.width);
				maxy = Math.max(maxy, bounds.y + bounds.height);
			}
		}
		bounds = new Rectangle(new Point(minx, miny), new Dimension(
				maxx - minx, maxy - miny));
		return bounds;
	}
}
