package com.ScreenRecoder;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class SelectionRectangle {

	public static void main(String[] args) {
//		new SelectionRectangle();
	}
	
	JFrame frmSelection;
	RecorderPane recorderPane;

	public SelectionRectangle(RecorderPane recorderPane) {
		this.recorderPane = recorderPane;
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
				frmSelection = new JFrame("Select Area");
				frmSelection.setUndecorated(true);
				frmSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmSelection.setLayout(new BorderLayout());
				// transparent
				frmSelection.setBackground(new Color(0, 0, 0, 0));
				frmSelection.add(new SnipItPane());
				frmSelection.setBounds(getVirtualBounds());
				frmSelection.setLocationRelativeTo(null);
				frmSelection.setVisible(true);
			}
		});
	}

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

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
			Area area = new Area(bounds);
			area.subtract(new Area(selectionPane.getBounds()));

			g2d.setColor(new Color(192, 192, 192, 64));
			g2d.fill(area);
		}
	}

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
			Rectangle bound = new Rectangle(getX(), getY(), getWidth(), getHeight());
			SwingUtilities.getWindowAncestor(SelectionPane.this).dispose();
			recorderPane.record(bound);
			recorderPane.show(true);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(new Color(128, 128, 128, 64));
			g2d.fillRect(0, 0, getWidth(), getHeight());

			float dash1[] = { 10.0f };
			BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(dashed);
			g2d.drawRect(0, 0, getWidth() - 3, getHeight() - 3);
			g2d.dispose();
		}

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

	
	public JFrame getFrmSelection() {
		return frmSelection;
	}

}
