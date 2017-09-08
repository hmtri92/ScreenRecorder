package com.ScreenRecoder.ui;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ScreenRecoder.FrameFactory;
import com.ScreenRecoder.ui.selection.SelectionRectangle;

public class ControlPane extends JPanel {

	private static final long serialVersionUID = 2868563937949804022L;
	private JButton btnTakeScreen;
	private JButton btnRecord;
	
	public ControlPane() {
		init();
		addActionListenner();
	}

	private void init() {
		this.setLayout(new FlowLayout());
		btnTakeScreen = new JButton("Take Screen");
		this.add(btnTakeScreen);
		
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
		
		btnTakeScreen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnTakeScreen_Click(e);
			}
		});
	}
	
	private void btnRecord_Click(ActionEvent e) {
		FrameFactory.mode = FrameFactory.MODE_RECORD;
		
		new SelectionRectangle();
		FrameFactory.getFrame(FrameFactory.FRAME_MAIN).dispose();
	}
	
	private void btnTakeScreen_Click(ActionEvent e) {
		FrameFactory.mode = FrameFactory.MODE_TAKE_SCREEN;
		
		new SelectionRectangle();
		FrameFactory.getFrame(FrameFactory.FRAME_MAIN).dispose();
	}
	
}
