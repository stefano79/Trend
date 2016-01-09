package com.wordpress.blogste.trend;

import java.awt.Color;
import java.io.Serializable;

public class PenModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347995829716830560L;
	private String name;
	private String description;
	private String units;
	private Color color;
	private int min = 0;
	private int max = 0;
	private double bufferValue[];
	private long bufferTime[];
	private static final int MAXBUFFER = 60000;
	private int scaleInMin = 0;
	private int scaleInMax = 0;
	private int scaleOutMin = 0;
	private int scaleOutMax = 0;
	private boolean scale = false;

	/**
	 * Classe model della penna contente i parametri necessari per generare l'
	 * istanza di una classe Pen e contiene anche i buffer dei dati dell' ultima
	 * registrazione. Implementa l' interfaccia serializable.
	 * 
	 * @param name
	 *            - nome della penna
	 * @param description
	 *            - descrizione della funzione della penna
	 * @param units
	 *            - unità di misura della variabile assegnata alla penna
	 * @param color
	 *            - colore della penna
	 * @param min
	 *            - valore minimo per visualizzazione sullo scope con asse y in
	 *            modalità penna
	 * @param max
	 *            - valore massimo per visualizzazione sullo scope con asse y in
	 *            modalità penna
	 */
	public PenModel(String name, String description, String units, Color color,
			int min, int max) {
		this.name = name;
		this.description = description;
		this.units = units;
		this.color = color;
		this.min = min;
		this.max = max;

		bufferValue = new double[MAXBUFFER];
		bufferTime = new long[MAXBUFFER];

	}

	/**
	 * Aggiunge un elemento al registro FIFO bufferValue con il valore attuale
	 * della penna e un elemento al registro FIFO con il tempo in millesecondi
	 * passati dal 1 Gennaio 1970
	 */
	void regValue(double value) {
		long tempTime = System.currentTimeMillis();

		for (int i = MAXBUFFER - 1; i > 0; i--) {
			bufferValue[i] = bufferValue[i - 1];
			bufferTime[i] = bufferTime[i - 1];
		}
		bufferValue[0] = value;
		bufferTime[0] = tempTime;
	}

	void clear() {

		for (int i = 0; i < bufferValue.length; i++) {
			bufferValue[i] = 0;
		}

		for (int i = 0; i < bufferTime.length; i++) {
			bufferTime[i] = 0;
		}
	}

	double getValueAtTime(long mills) {
		for (int i = 0; i < MAXBUFFER; i++) {
			if (bufferTime[i] != 0) {
				if (mills >= bufferTime[i] && mills < bufferTime[0]) {
					return bufferValue[i];
				}
			} else {
				break;
			}
		}
		return Double.MAX_VALUE;
	}

	long getFirstMillsElement() {
		long mills = 0;
		for (int i = 1; i < MAXBUFFER; i++) {
			if (bufferTime[i] == 0) {
				mills = bufferTime[i - 1];
				break;
			}
		}
		return mills;
	}

	long getLastMillsElement() {
		return bufferTime[0];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public int getSize() {
		return MAXBUFFER;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public void setLimit(int min, int max) {
		if (min < max) {
			this.max = max;
			this.min = min;
		} else {
			throw new IllegalArgumentException("Max can not be less than min");
		}
	}

	/**
	 * @return the scaleInMin
	 */
	public int getScaleInMin() {
		return scaleInMin;
	}

	/**
	 * @param scaleInMin the scaleInMin to set
	 */
	public void setScaleInMin(int scaleInMin) {
		this.scaleInMin = scaleInMin;
	}

	/**
	 * @return the scaleInMax
	 */
	public int getScaleInMax() {
		return scaleInMax;
	}

	/**
	 * @param scaleInMax the scaleInMax to set
	 */
	public void setScaleInMax(int scaleInMax) {
		this.scaleInMax = scaleInMax;
	}

	/**
	 * @return the scaleMin
	 */
	public int getScaleOutMin() {
		return scaleOutMin;
	}

	/**
	 * @param scaleOutMin the scaleMin to set
	 */
	public void setScaleOutMin(int scaleOutMin) {
		this.scaleOutMin = scaleOutMin;
	}

	/**
	 * @return the scaleMax
	 */
	public int getScaleOutMax() {
		return scaleOutMax;
	}

	/**
	 * @param scaleOutMax the scaleMax to set
	 */
	public void setScaleOutMax(int scaleOutMax) {
		this.scaleOutMax = scaleOutMax;
	}

	/**
	 * @return the scale
	 */
	public boolean isScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(boolean scale) {
		this.scale = scale;
	}
	
	

}
