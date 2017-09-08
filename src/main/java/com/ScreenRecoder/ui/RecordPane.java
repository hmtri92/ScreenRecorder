package com.ScreenRecoder.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RecordPane extends JPanel {

	private static final long serialVersionUID = -6768506709101708268L;
	
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;
	
	public RecordPane() {
		try {
			init();
			addActionListenner();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws IOException {
		this.setLayout(new FlowLayout());
		
		btnStart = new JButton("Start");
		btnStart.setFocusPainted(false);
		
		btnPause = new JButton("Pause");
		btnPause.setFocusPainted(false);
		
		btnStop = new JButton("Stop");
		btnStop.setFocusPainted(false);
		
		this.add(btnStart);
		this.add(btnPause);
		this.add(btnStop);
	}
	
	private void addActionListenner() {
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStart_Click(e);
			}
		});
		btnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause_Click(e);
			}
		});
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStop_Click(e);
			}
		});		
	}
	
	private void btnStart_Click(ActionEvent e) {
		
	}
	
	private void btnPause_Click(ActionEvent e) {
		
	}
	
	private void btnStop_Click(ActionEvent e) {
		
	}

}
