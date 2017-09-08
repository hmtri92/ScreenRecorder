package com.ScreenRecoder.ui.selection;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

import javax.swing.JPanel;

public class SnipItPane extends JPanel {
	private static final long serialVersionUID = 2462809452329828181L;
	private Point mouseAnchor;
	private Point dragPoint;
	private SelectionPane selectionPane;

	public SnipItPane() {
		init();
		addActionListenner();
	}

	private void init() {
		setOpaque(false);
		setLayout(null);
		this.setLayout(null);
		selectionPane = new SelectionPane();
		this.add(selectionPane);
	}

	private void addActionListenner() {
		MouseAdapter adapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				mouseAnchor = e.getPoint();
				dragPoint = null;
				selectionPane.setLocation(mouseAnchor);
				selectionPane.setSize(0, 0);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				dragPoint = e.getPoint();
				int width = dragPoint.x - mouseAnchor.x;
				int height = dragPoint.y - mouseAnchor.y;

				int x = mouseAnchor.x;
				int y = mouseAnchor.y;

				if (width < 0) {
					x = dragPoint.x;
					width *= -1;
				}

				if (height < 0) {
					y = dragPoint.y;
					height *= -1;
				}

				selectionPane.setBounds(x, y, width, height);
				selectionPane.revalidate();
				repaint();
			}
		};

		addMouseListener(adapter);
		addMouseMotionListener(adapter);
	}
	
	public void resetSelection() {
		selectionPane.setSize(0, 0);
		this.revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
		Area area = new Area(bounds);
		area.subtract(new Area(selectionPane.getBounds()));

		g2d.setColor(new Color(192, 192, 192, 64));
		g2d.fill(area);
		
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

}
