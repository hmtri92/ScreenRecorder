package com.ScreenRecoder;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RecorderPane extends JPanel {

	private static final long serialVersionUID = 2868563937949804022L;
	private JButton btnRecord;
	private SelectionRectangle selectionRectangle;
	
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
		
		selectionRectangle = new SelectionRectangle();
	}
}
