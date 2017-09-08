package com.ScreenRecoder.ui.selection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ScreenRecoder.FrameFactory;
import com.ScreenRecoder.Recorder;

public class SelectionPane extends JPanel {
	private static final long serialVersionUID = 3440061007737153626L;
	private JButton btnOk;
	private JLabel lblRectangle;

	public SelectionPane() {
		init();
		addAcctionListenner();
	}

	private void init() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		btnOk = new JButton("Ok");
		btnOk.setBorderPainted(false);
		btnOk.setFocusPainted(false);

		lblRectangle = new JLabel("Rectangle");
		lblRectangle.setOpaque(true);
		lblRectangle.setBorder(new EmptyBorder(4, 4, 4, 4));
		lblRectangle.setBackground(Color.GRAY);
		lblRectangle.setForeground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblRectangle, gbc);

		gbc.gridy++;
		add(btnOk, gbc);
	}

	private void addAcctionListenner() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnOk_Click (e);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				lblRectangle.setText("Rectangle " + getX() + "x" + getY() + "x" + getWidth() + "x" + getHeight());
			}
		});
	}
	
	private void btnOk_Click (ActionEvent e) {
		Recorder.selectionBound = new Rectangle(getX(), getY(), getWidth(), getHeight());
		FrameFactory.getFrame(FrameFactory.FRAME_SELECT_RECTANGLE).dispose();
		
		switch (FrameFactory.mode) {
		case FrameFactory.MODE_TAKE_SCREEN:
			takeScreen();
			break;
		case FrameFactory.MODE_RECORD:
			record();
			break;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(128, 128, 128, 64));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		float dash1[] = { 10.0f };
		BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
		g2d.setColor(Color.RED);
		g2d.setStroke(dashed);
		g2d.drawRect(0, 0, getWidth() - 3, getHeight() - 3);
		g2d.dispose();
	}
	
	private void takeScreen() {
		Recorder.takeScreenShot();
	}
	
	private void record() {
		FrameFactory.getFrame(FrameFactory.FRAME_RECORD).setVisible(true);
	}

}
