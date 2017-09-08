package com.ScreenRecoder.ui.selection;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ScreenRecoder.FrameFactory;

public class SelectionRectangle {

	public SelectionRectangle() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
				FrameFactory.getFrame(FrameFactory.FRAME_SELECT_RECTANGLE).setVisible(true);
			}
		});
	}

}
