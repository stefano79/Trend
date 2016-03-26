package com.wordpress.blogste.trend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.wordpress.blogste.trend.TrendModel.TYPE_SCALE_AXIS_Y;

class Scope extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Color gridColor = new Color(0, 255, 0, 128);
	private Color misureBarColor = new Color(255, 255, 255, 210);
	private Trend trend;
	private int cols;
	private int rows;
	private int yMax = 100;
	private int yMin = 0;
	private int xMax;
	private int xMin;
	private Pen[] pens;
	private long currentMills;
	private boolean play = false;
	private boolean firstScanDrawGrafico = false;
	private boolean misureBarEnable = false;
	private int misureBarPos = 100;
	private TYPE_SCALE_AXIS_Y typeScaleAxisY = TYPE_SCALE_AXIS_Y.AUTOMATIC;
	private int yMinAutomatic = 0;
	private int yMaxAutomatic = 100;
	private int yMinCustom;
	private int yMaxCustom;
	private boolean zoomPaintEnabled = false;
	private int zoomStartX;
	private int zoomStartY;
	private int zoomEndX;
	private int zoomEndY;
	private int xMaxZoomed;
	private int xMaxOld;
	private boolean zoomActive = false;
	private boolean deltaMisureBarEnabled = false;
	private int deltaMisureBarPos = 0;

	public Scope(Trend trend, int columns, int rows, int yMax, int yMin,
			int xMax, int xMin) {
		super();
		this.trend = trend;
		this.cols = columns;
		this.rows = rows;
		this.setScaleAxisY(yMax, yMin);
		this.setScaleAxisX(xMax, xMin);
		addMouseListener(this);
		addMouseMotionListener(this);

		currentMills = System.currentTimeMillis();
	}

	/*
	 * ************* METODI OVERRIDE *************
	 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		if (pens != null)
			for (int i = 0; i < pens.length; i++)
				if (pens[i] != null) {
					drawGrafico(g, pens[i]);
					if (misureBarEnable)
						drawMisureBar(g, pens[i], i);
				}
		if (zoomPaintEnabled) {
			drawZoomWindow(g);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource().equals(this) && this.isPlay() == false) {
			misureBarPos = arg0.getX();
			misureBarEnable = true;
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (!this.isPlay() && pens[0] != null) {
			zoomPaintEnabled = true;
			zoomStartX = arg0.getX();
			zoomStartY = arg0.getY();
			zoomEndX = arg0.getX();
			zoomEndY = arg0.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (zoomPaintEnabled) {
			// Calcolo nuovo valore per l'asse x e per il currentmills
			xMaxZoomed = 0;
			long currentMillsZoom = 0;
			if (zoomStartX < zoomEndX) {
				xMaxZoomed = xMax * (zoomEndX - zoomStartX)
						/ this.getXDimension();
				currentMillsZoom = currentMills
						- (xMax - (xMax * zoomEndX / this.getXDimension()));
			} else if (zoomStartX > zoomEndX) {
				xMaxZoomed = xMax * (zoomStartX - zoomEndX)
						/ this.getXDimension();
				currentMillsZoom = currentMills
						- (xMax - (xMax * zoomStartX / this.getXDimension()));
			}

			xMaxOld = xMax;

			// Calcolo nuovi valori per l'asse y
			int yMinZoomed = 0;
			int yMaxZoomed = 0;
			if (zoomStartY < zoomEndY) {
				yMinZoomed = yMax
						- ((yMax - yMin) * zoomEndY / this.getYDimension());
				yMaxZoomed = yMax
						- ((yMax - yMin) * zoomStartY / this.getYDimension());
			} else if (zoomStartY > zoomEndY) {
				yMinZoomed = yMax
						- ((yMax - yMin) * zoomStartY / this.getYDimension());
				yMaxZoomed = yMax
						- ((yMax - yMin) * zoomEndY / this.getYDimension());
			}
			// Se entrambi i nuovi valori sono validi viene attivato lo zoom
			if (xMaxZoomed > 100 && yMaxZoomed > yMinZoomed) {
				xMax = xMaxZoomed;
				trend.setScaleAxisX(xMax);
				trend.setCurrentMills(currentMillsZoom);
				trend.setScaleAxisY(yMinZoomed, yMaxZoomed);
				zoomActive = true;
			}
			zoomPaintEnabled = false;
			this.repaint();
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (zoomPaintEnabled) {
			zoomEndX = arg0.getX();
			zoomEndY = arg0.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	/*
	 * ************* METODI PRIVATI *************
	 */

	/**
	 * Disegna la griglia con le colonne e rigne impostate
	 */
	private void drawGrid(Graphics g) {
		g.setColor(gridColor);
		double width = this.getSize().getWidth();
		double height = this.getSize().getHeight();

		/* disegna le linee verticali */
		double x;
		int j = 0;
		for (x = 0; x <= width; x = (x + (width / cols))) {
			if (j > 0 && j < cols)
				g.drawLine((int) x, 0, (int) x, (int) this.getSize()
						.getHeight());
			j++;
		}

		/* disegna le linee orizzontali */
		double y;
		j = 0;
		for (y = 0; y <= height; y = (y + (height / rows))) {
			if (j > 0 && j < rows)
				g.drawLine(0, (int) y, (int) width, (int) y);
			j++;
		}

	}

	/**
	 * Disegna il grafico della penna
	 */
	private void drawGrafico(Graphics g, Pen pen) {

		double width = this.getSize().getWidth();
		double height = this.getSize().getHeight();
		if (!zoomActive) {
			switch (typeScaleAxisY) {
			case AUTOMATIC:
				yMin = yMinAutomatic;
				yMax = yMaxAutomatic;
				break;
			case PENS:
				yMin = pen.getMin();
				yMax = pen.getMax();
				firstScanDrawGrafico = true;
				break;
			case CUSTOM:
				yMin = yMinCustom;
				yMax = yMaxCustom;
				firstScanDrawGrafico = true;
			}
		}

		g.setColor(pen.getColor());
		((Graphics2D) g).setStroke(new BasicStroke(1.1f)); // Spessore linea
		double xDelta = width / (xMax - xMin);
		double y = 0;
		double yOld = 0;
		double yDelta = 0;
		if (yMax > 0) {
			yDelta = height / (yMax - yMin);
		} else {
			yDelta = height / (yMin - yMax) * -1;
		}

		boolean firstPoint = true;

		if (play)
			currentMills = System.currentTimeMillis();

		for (int x = 0; x <= getXDimension(); x++) {

			long mills = (long) (currentMills - xMax + (x / xDelta));

			double penValue = pen.getValueAtTime(mills);

			if (penValue != Double.MAX_VALUE) {
				if (yMax > 0) {
					y = height - penValue * yDelta + yMin * yDelta;
				} else {
					y = penValue * yDelta;
				}

				if (firstPoint) {
					g.drawLine(x, (int) y, x, (int) y);
					firstPoint = false;
				} else {
					g.drawLine(x - 1, (int) yOld, x, (int) y);
				}
				// Calcolo scala asse y automatico
				if (typeScaleAxisY == TYPE_SCALE_AXIS_Y.AUTOMATIC) {
					if (firstScanDrawGrafico) {
						yMaxAutomatic = (int) (penValue + (penValue / 10));
						yMinAutomatic = (int) (penValue - (penValue / 10));
						if (yMinAutomatic >= yMaxAutomatic) {
							yMaxAutomatic = yMinAutomatic + 10;
						}
						trend.setScaleAxisY(yMinAutomatic, yMaxAutomatic);
						firstScanDrawGrafico = false;
					} else {

						if (penValue > yMaxAutomatic) {
							yMaxAutomatic = (int) (penValue + (penValue / 10));
							if (yMinAutomatic >= yMaxAutomatic) {
								yMaxAutomatic = yMinAutomatic + 10;
							}
							trend.setScaleAxisY(yMinAutomatic, yMaxAutomatic);
						}
						if (penValue < yMinAutomatic) {
							yMinAutomatic = (int) (penValue - (penValue / 10));
							trend.setScaleAxisY(yMinAutomatic, yMaxAutomatic);
						}
					}
				}
			}
			yOld = y;

		}
	}

	private void drawMisureBar(Graphics g, Pen pen, int index) {
		double width = this.getSize().getWidth();
		double height = this.getSize().getHeight();

		// recupero le dimensioni del font
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontHeight = metrics.getHeight();

		g.setColor(misureBarColor);
		g.drawLine(misureBarPos, 0, misureBarPos, (int) height);

		if (deltaMisureBarEnabled) {
			g.drawLine(deltaMisureBarPos, 0, deltaMisureBarPos, (int) height);
		}

		int x = 0;
		int xDelta = 0;
		int y = fontHeight + fontHeight * index;
		int yDelta = (int) (height - fontHeight + fontHeight * index);

		double xForPixel = width / (xMax - xMin);

		long mills = (long) (currentMills - xMax + (misureBarPos / xForPixel));
		double penValue = pen.getValueAtTime(mills);

		long millsDelta = (long) (currentMills - xMax + (deltaMisureBarPos / xForPixel));
		double penValueDelta = pen.getValueAtTime(millsDelta) - penValue;

		String text;
		String textDelta;
		if (pen.getUnits().length() > 0) {
			text = pen.getName() + " " + String.format("%.2f", penValue)
					+ pen.getUnits();
			textDelta = "Delta " + pen.getName() + " "
					+ String.format("%.2f", penValueDelta) + " " + pen.getUnits();
		} else {
			text = pen.getName() + " " + String.format("%.2f", penValue);
			textDelta = "Delta " + pen.getName() + " "
					+ String.format("%.2f", penValueDelta);
		}

		int textWidth = metrics.stringWidth(text);
		if (textWidth < (width - misureBarPos)) {
			x = misureBarPos + 2;
		} else {
			x = misureBarPos - textWidth - 2;
		}

		int textWidthDelta = metrics.stringWidth(textDelta);
		if (textWidthDelta < (width - deltaMisureBarPos)) {
			xDelta = deltaMisureBarPos + 2;
		} else {
			xDelta = deltaMisureBarPos - textWidthDelta - 2;
		}

		if (penValue != Double.MAX_VALUE) {
			g.drawString(text, x, y);
			if (deltaMisureBarEnabled) {
				g.drawString(textDelta, xDelta, yDelta);
			}
		}

	}

	private void drawZoomWindow(Graphics g) {
		g.setColor(misureBarColor);
		if (zoomStartX < zoomEndX && zoomStartY < zoomEndY) {
			g.drawRect(zoomStartX, zoomStartY, zoomEndX - zoomStartX, zoomEndY
					- zoomStartY);
		} else if (zoomStartX < zoomEndX && zoomStartY > zoomEndY) {
			g.drawRect(zoomStartX, zoomEndY, zoomEndX - zoomStartX, zoomStartY
					- zoomEndY);
		} else if (zoomStartX > zoomEndX && zoomStartY < zoomEndY) {
			g.drawRect(zoomEndX, zoomStartY, zoomStartX - zoomEndX, zoomEndY
					- zoomStartY);
		} else {
			g.drawRect(zoomEndX, zoomEndY, zoomStartX - zoomEndX, zoomStartY
					- zoomEndY);
		}

	}

	/*
	 * ************* METODI PUBBLICI *************
	 */

	public void addPen(Pen[] pens) {
		this.pens = pens;
	}

	public void zoomAdd(int percentualeZoom) {
		int deltaY = (int) (((double)(this.yMax - this.yMin) / 100) * percentualeZoom);
		int yMax = this.yMax - deltaY;
		int yMin = this.yMin + deltaY;

		int deltaX = this.xMax / 100 * percentualeZoom;
		int xMax = this.xMax - deltaX;
		// Se entrambi i nuovi valori sono validi viene attivato lo zoom
		if (xMax > 100 && yMax > yMin && deltaY != 0) {
			if (!zoomActive) {
				xMaxOld = this.xMax;
			}
			this.xMax = xMax;
			zoomActive = true;
			trend.setScaleAxisX(xMax);
			trend.setScaleAxisY(yMin, yMax);			
		}
		this.repaint();
	}

	public void zoomSub(int percentualeZoom) {
		int deltaY = (this.yMax - this.yMin) / 100 * percentualeZoom * -1;
		if (deltaY == 0) {
			deltaY = -5;
		}
		int yMax = this.yMax - deltaY;
		int yMin = this.yMin + deltaY;

		int deltaX = this.xMax / 100 * percentualeZoom * -1;
		int xMax = this.xMax - deltaX;

		if (!zoomActive) {
			xMaxOld = this.xMax;
		}
		this.xMax = xMax;
		zoomActive = true;
		trend.setScaleAxisX(xMax);
		trend.setScaleAxisY(yMin, yMax);
		this.repaint();
	}

	public void zoomReset() {
		if (zoomActive) {
			zoomActive = false;

			// resetto l' asse x
			xMax = xMaxOld;

			trend.setScaleAxisX(xMax);
			// Resetto l'asse y
			switch (typeScaleAxisY) {
			case AUTOMATIC:
				yMin = yMinAutomatic;
				yMax = yMaxAutomatic;
				break;
			case PENS:
				if (pens[0] != null) {
					yMin = pens[0].getMin();
					yMax = pens[0].getMax();
				} else {
					yMin = 0;
					yMax = 100;
				}
				break;
			case CUSTOM:
				yMin = yMinCustom;
				yMax = yMaxCustom;
			}
			trend.setScaleAxisY(yMin, yMax);
			this.repaint();
		}
	}

	public void misureBarDeltaForaward(int pixel) {
		if ((deltaMisureBarPos + pixel) < this.getSize().getWidth()) {
			deltaMisureBarPos = deltaMisureBarPos + pixel;
			this.repaint();
		}
	}

	public void misureBarDeltaReverse(int pixel) {
		if ((deltaMisureBarPos - pixel) > 0)
			deltaMisureBarPos = deltaMisureBarPos - pixel;
		this.repaint();
	}

	/*
	 * ************* METODI SETTER AND GETTER *************
	 */

	/**
	 * Impostazioni scale asse x
	 * 
	 * @param xMax
	 *            - Valore massio asse x
	 * @param xMin
	 *            - Valore minimo asse x
	 */
	public void setScaleAxisX(int xMax, int xMin) {
		this.xMax = xMax;
		this.xMin = xMin;
		repaint();
	}

	/**
	 * Impostazioni scale asse y
	 * 
	 * @param yMax
	 *            - Valore massimo asse y
	 * @param yMin
	 *            - Valore minimo asse y
	 */
	public void setScaleAxisY(int yMax, int yMin) {
		if (!zoomActive) {
			this.yMaxCustom = yMax;
			this.yMinCustom = yMin;
		}
		this.yMax = yMax;
		this.yMin = yMin;
		repaint();
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

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean state) {
		misureBarEnable = !state;
		firstScanDrawGrafico = state;
		zoomReset();
		this.play = state;
	}

	public long getCurrentMills() {
		return currentMills;
	}

	public void setCurrentMills(long currentMills) {
		if (play == false) {
			this.currentMills = currentMills;
			this.repaint();
		}
	}

	/**
	 * @return the typeScaleAxisY
	 */
	public TYPE_SCALE_AXIS_Y getTypeScaleAxisY() {
		return typeScaleAxisY;
	}

	/**
	 * @param typeScaleAxisY
	 *            the typeScaleAxisY to set
	 */
	public void setTypeScaleAxisY(TYPE_SCALE_AXIS_Y typeScaleAxisY) {
		this.typeScaleAxisY = typeScaleAxisY;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols
	 *            the cols to set
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the misureBarEnable
	 */
	public boolean isDeltaMisureBarEnabled() {
		return deltaMisureBarEnabled;
	}

	/**
	 * @param misureBarEnable
	 *            the misureBarEnable to set
	 */
	public void setDeltaMisureBarEnable(boolean state) {
		if (misureBarEnable) {
			this.deltaMisureBarEnabled = state;
			deltaMisureBarPos = misureBarPos;
			this.repaint();
		}
	}

	/**
	 * @return the yMinCustom
	 */
	public int getyMinCustom() {
		return yMinCustom;
	}

	/**
	 * @return the yMaxCustom
	 */
	public int getyMaxCustom() {
		return yMaxCustom;
	}

}
