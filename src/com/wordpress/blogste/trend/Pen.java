package com.wordpress.blogste.trend;

import java.awt.Color;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author stefanoaniballi
 * 
 *         <p>
 *         Classe per la registrazione di una variabile double in funzione del
 *         tempo, il tempo è registrato con la variabile restituita da
 *         System.currentTimeMillis().
 *         </p>
 * 
 *         La registrazione è comandata da un thread a tempo fisso eseguito ogni
 *         10 ms, per comandare la registrazione richiamare i metodi startReg()
 *         e stopReg()
 * 
 */
public class Pen extends Observable {

	private PenModel model;
	private double value = 0;
	private Timer timerRegPen;
	private ThreadReg threadRegPen;
	private static final int SAMPLE_TIME = 10;

	/**
	 * Costruisce una nuova penna e istanzia un oggetto timer interno che
	 * richiama un thread ogni 10 ms per la registrazione dei valori.
	 * 
	 * @param value
	 *            - valore della penna
	 * @param name
	 *            - nome della penna
	 * @param color
	 *            - colore della penna
	 */
	public Pen(String name, Color color) {
		model = new PenModel(name, "", "", color, 0, 0);

		// Inizializzo il timer per il campionamento dei dati
		inizializeTimer();
	}

	/**
	 * Costruisce una nuova penna e istanzia un oggetto timer interno che
	 * richiama un thread ogni 10 ms per la registrazione dei valori.
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
	public Pen(String name, String description, String units, Color color, int min, int max) {
		model = new PenModel(name, description, units, color, min, max);

		// Inizializzo il timer per il campionamento dei dati
		inizializeTimer();
	}

	/**
	 * Costruisce una nuova penna tramite la classe model passata come parametro
	 * e istanzia un oggetto timer interno che richiama un thread ogni 10 ms per
	 * la registrazione dei valori.
	 * 
	 * @param model
	 *            - classe model della penna
	 */
	public Pen(PenModel model) {
		this.model = model;

		// Inizializzo il timer per il campionamento dei dati
		inizializeTimer();

	}

	/*
	 * ************* METODI PRIVATI *************
	 */

	private void inizializeTimer() {
		timerRegPen = new Timer(true);
		threadRegPen = new ThreadReg();
		timerRegPen.scheduleAtFixedRate(threadRegPen, 0, SAMPLE_TIME);
	}

	/*
	 * ************* METODI PUBBLICI *************
	 */

	/**
	 * <p>
	 * public void startReg()
	 * </p>
	 * 
	 * Start registrazione del valore della penna nel registro
	 */
	public void startReg() {
		model.clear();
		threadRegPen.start();

	}

	/**
	 * <p>
	 * public void stopReg()
	 * </p>
	 * 
	 * Stop registrazione del valore della penna nel registro
	 */
	public void stopReg() {
		threadRegPen.stop();

	}

	/**
	 * public boolean isRecording()
	 * 
	 * @return lo stato del thread di registrazione
	 */
	public boolean isRecording() {
		return threadRegPen.isRunning();
	}

	/**
	 * public double getValueAtTime(long mills)
	 * 
	 * @param mills
	 *            - millesecondi passati dal 1 gennaio 1970
	 * @return il valore della penna ai millesecondi passati come argomento, se
	 *         non c'� nessun valore restituisce il massimo valore di una
	 *         variabile double
	 */
	public double getValueAtTime(long mills) {
		return model.getValueAtTime(mills);
	}

	/**
	 * public long getFirstMillsElement()
	 * 
	 * @return time in millesecondi passati dal 1 gennaio 1970 del primo valore
	 *         registrato
	 */
	public long getFirstMillsElement() {
		return model.getFirstMillsElement();
	}

	/**
	 * public long getLastMillsElement()
	 * 
	 * @return time in millesecondi passati dal 1 gennaio 1970 dell' ultimo
	 *         valore registrato
	 */
	public long getLastMillsElement() {
		return model.getLastMillsElement();
	}

	/**
	 * <p>
	 * public void clear()
	 * </p>
	 * 
	 * Svuota il registro della penna
	 */
	public void clear() {
		model.clear();
	}

	public String toString() {
		return model.getName() + " - " + value + " " + model.getUnits();
	}

	/*
	 * ************* METODI GETTER AND SETTER *************
	 */

	/**
	 * public double getValue()
	 * 
	 * @return valore attuale della penna in formato double
	 */
	public double getValue() {
		return value;
	}

	/**
	 * public void setValue(double value)
	 * 
	 * @param value
	 *            - set del valore della penna
	 */
	public void setValue(double value) {
		if (model.isScale()) {
			double ratio = (double)(model.getScaleOutMax() - model.getScaleOutMin()) / (double)(model.getScaleInMax() - model.getScaleInMin());
			this.value = value * ratio + model.getScaleOutMin();
		} else {
			this.value = value;
		}
		setChanged();
		this.notifyObservers();
	}

	/**
	 * public PenModel getModel()
	 * 
	 * @return the model
	 */
	public PenModel getModel() {
		return model;
	}

	/**
	 * public void setModel(PenModel model)
	 * 
	 * @param model
	 *            - instanza di una classe PenModel
	 * @throws Exception
	 */
	public void setModel(PenModel model) throws Exception {
		if (threadRegPen.isRunning()) {
			throw new Exception("Impossibile set the model with recording active");
		} else {
			this.model = model;
		}
	}

	/**
	 * public String getName()
	 * 
	 * @return restituisce il nome della penna
	 */
	public String getName() {
		return model.getName();
	}

	/**
	 * public void setName(String name)
	 * 
	 * @param name
	 *            - set nome della penna
	 */
	public void setName(String name) {
		model.setName(name);
		;
	}

	/**
	 * public String getDescription()
	 * 
	 * @return la descrizione della penna
	 */
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * public void setDescription(String description)
	 * 
	 * @param description
	 *            - nuova descrizione della penna o null
	 * 
	 */
	public void setDescription(String description) {
		model.setDescription(description);
		;
	}

	/**
	 * public String getUnits()
	 * 
	 * @return restituisce l' unit� di misura della penna
	 */
	public String getUnits() {
		return model.getUnits();
	}

	/**
	 * public void setUnits(String units)
	 * 
	 * @param units
	 *            - set dell' unit� di misura della penna
	 */
	public void setUnits(String units) {
		model.setUnits(units);
		;
	}

	/**
	 * public int getSize()
	 * 
	 * @return restituisce la dimensione del array per la registrazione dei
	 *         valori della penna
	 */
	public int getSize() {
		return model.getSize();
	}

	/**
	 * public Color getColor()
	 * 
	 * @return il colore della penna in formato Color
	 */
	public Color getColor() {
		return model.getColor();
	}

	/**
	 * public void setColor(Color color)
	 * 
	 * @param color
	 *            - colore da assegnare alla penna
	 */
	public void setColor(Color color) {
		model.setColor(color);
	}

	/**
	 * public int getMin()
	 * 
	 * @return il valore minimo da visualizzare sullo scope del trend
	 */
	public int getMin() {
		return model.getMin();
	}

	/**
	 * public int getMax()
	 * 
	 * @return il valore massimo da visualizzare sullo scope del trend
	 */
	public int getMax() {
		return model.getMax();
	}

	/**
	 * public void setLimit(int min, int max)
	 * 
	 * @param min
	 *            - valore minimo da visualizzare sullo scope del trend
	 * @param max
	 *            - valore massimo da visualizzare sullo scope del trend
	 * @throws IllegalArgumentException
	 *             - se il valore di max � minore di min
	 */
	public void setLimit(int min, int max) {
		model.setLimit(min, max);
	}

	/**
	 * @param value
	 *            - abilitazione scalarizzazione valore della penna
	 * 
	 */
	public void setScale(boolean value) {
		this.model.setScale(value);
	}

	/**
	 * @return
	 */
	public boolean isScale() {
		return this.model.isScale();
	}

	/**
	 * @param outMin
	 * @param outMax
	 */
	public void setScaleValue(int inMin, int inMax, int outMin, int outMax) {
		this.model.setScaleInMax(inMax);
		this.model.setScaleInMin(inMin);
		this.model.setScaleOutMax(outMax);
		this.model.setScaleOutMin(outMin);
	}
	
	public int getScaleInMax(){
		return this.model.getScaleInMax();
	}
	
	public int getScaleInMin(){
		return this.model.getScaleInMin();
	}

	/**
	 * @return
	 */
	public int getScaleOutMax() {
		return this.model.getScaleOutMax();
	}

	/**
	 * @return
	 */
	public int getScaleOutMin() {
		return this.model.getScaleOutMin();
	}

	/*
	 * ************* CLASSI INTERNE *************
	 */

	/*
	 * @author stefanoaniballi
	 * 
	 * Classe di estensione di TimerTask per creare un thread richiamato da un
	 * java.util.timer
	 */
	private class ThreadReg extends TimerTask {

		private boolean run;

		public ThreadReg() {
			super();
			run = false;
		}

		/*
		 * ************* METODI OVERRIDE *************
		 */

		@Override
		public void run() {
			if (run)
				model.regValue(value);

		}

		/*
		 * ************* METODI PUBBLICI *************
		 */

		public boolean isRunning() {
			return run;
		}

		public void start() {
			run = true;
		}

		public void stop() {
			run = false;
		}
	}

}
