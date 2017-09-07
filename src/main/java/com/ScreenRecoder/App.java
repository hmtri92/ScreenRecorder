package com.ScreenRecoder;

import java.awt.BorderLayout;
import java.awt.Rectangle;

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
}
