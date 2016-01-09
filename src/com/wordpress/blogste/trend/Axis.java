package com.wordpress.blogste.trend;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

/**
 * @author stefanoaniballi
 * 
 */
class Axis extends JPanel {

	private static final long serialVersionUID = 1L;

	private boolean isHorizontal;
	private long currentMills;
	private int max;
	private int min;
	private int divisions;
	private int fontWidth;
	private int fontHeight;

	private FontMetrics metrics;

	/**
	 * @param scaleMax
	 * @param scaleMin
	 * @param divisions
	 * @param isHorizontal
	 */
	public Axis(int max, int min, int divisions, boolean isHorizontal) {
		super();
		this.max = max;
		this.min = min;
		this.divisions = divisions;
		this.isHorizontal = isHorizontal;

		this.setLayout(null);
		currentMills = System.currentTimeMillis();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// recupero le dimensioni del font
		metrics = g.getFontMetrics(g.getFont());
		fontHeight = metrics.getHeight();
		// aggiorno le dimensioni del JPanel in base alle stringhe
		updateSize();
		// disegno le stringhe
		drawScale(g);

	}

	/*
	 * ************* METODI PRIVATI *************
	 */

	/**
	 * Disegna le JLabel per creare la scala dell' asse
	 */
	private void drawScale(Graphics g) {

		double width = this.getSize().getWidth();
		double height = this.getSize().getHeight();

		if (isHorizontal) {
			double x;
			int j = 0;
			for (x = 0; x <= width; x = (x + (width / divisions))) {
				String s = getStringElement(j);
				fontWidth = metrics.stringWidth(s);

				int y = (int) height;
				int k = 0;
				if (j == 0) {
					k = 0;
				} else if (j == divisions) {
					k = fontWidth;
				} else {
					k = fontWidth / 2;
				}
				g.drawString(s, (int) x - k, y - 2);
				j++;
			}
		} else {

			double y;
			int j = 0;
			for (y = 0; y <= height; y = (y + (height / divisions))) {
				String s = getStringElement(j);

				fontWidth = metrics.stringWidth(s);

				int x = (int) (width - fontWidth - 2);
				int k = 0;
				if (j == 0) {
					k = fontHeight - 2;
				} else if (j == divisions) {
					k = -2;
				} else {
					k = fontHeight / 3;
				}
				g.drawString(s, x, (int) y + k);
				j++;
			}
		}
	}

	private String getStringElement(int element) {

		if (isHorizontal) {
			long num;
			num = (max - min) / divisions * (divisions - element);
			Date date = new Date(currentMills - num);
			SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
			return s.format(date);
		} else {
			if (max > 0) {
				double num;
				num = min + (double) (max - min) / divisions
						* (divisions - element);
				return Integer.toString((int) num);
			} else {
				double num;
				num = min + (double) (max - min) / divisions
						* (divisions - element);
				return Integer.toString((int) num);
			}
		}

	}

	private void updateSize() {

		if (isHorizontal) {

			this.setPreferredSize(new Dimension(0, fontHeight));

		} else {

			int x = 0;
			for (int i = 0; i < divisions + 1; i++) {
				String s = getStringElement(i);
				fontWidth = metrics.stringWidth(s);
				if (fontWidth > x)
					x = fontWidth;
			}
			this.setPreferredSize(new Dimension(x + 2, 0));
		}
		revalidate();
	}

	/*
	 * ************* METODI SETTER AND GETTER *************
	 */

	/**
	 * @return
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param scaleMin
	 */
	public void setScale(int min, int max) {
		if (min < max) {
			this.max = max;
			this.min = min;
			repaint();
		} else {
			throw new IllegalArgumentException("Max can not be less than min");
		}
	}

	public void setDivisions(int divisions) {
		this.divisions = divisions;
		repaint();
	}

	public long getCurrentMills() {
		return currentMills;
	}

	public void setCurrentMills(long currentMills) {
		this.currentMills = currentMills;
		this.repaint();
	}

	/**
	 * Restituisce la dimensione orizzontale in pixel
	 */
	public int getXDimension() {
		return (int) this.getSize().getWidth();

	}

	/**
	 * Restituisce la dimensione verticale in pixel
	 */
	public int getYDimension() {
		return (int) this.getSize().getHeight();
	}

}
