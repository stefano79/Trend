package com.wordpress.blogste.trend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.wordpress.blogste.trend.TrendModel.TYPE_SCALE_AXIS_Y;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JToggleButton;

/**
 * @author stefanoaniballi</p>
 * 
 *         La classe Trend � la classe principale ed ingobla la funzione di
 *         controller.</p>
 * 
 * 
 * 
 */
public class Trend extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitolo;
	private int cols = 5;
	private int rows = 8;
	private FlowLayout flowLayoutPanelHgap;
	private JButton button_0;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JPanel panelHgap;
	private Axis axisX;
	private Axis axisY;
	private Scope scope;
	private Timer timerScopeUpdate;
	private static final int DELAY_TIMER_UPDATE_SCOPE = 250;
	private Pen[] pens;
	private JPanel panelSouthButton;
	private JPanel panelSouth;
	private JPanel panelNorth;
	private JPanel panel_deltaBar;
	private JPanel panel_Zoom;
	private JButton button_ZoomAdd;
	private JButton button_ZoomSub;
	private JButton button_ZoomReset;
	private JButton button_deltaBar;
	private JButton button_deltaBarSX;
	private JButton button_deltaBarDX;

	/**
	 * public Trend(String titolo)
	 * 
	 * @param titolo
	 *            - Se diverso da null crea una label sul layout north con il
	 *            testo impostato
	 */
	public Trend(String titolo) {
		super();
		this.setLayout(new BorderLayout(0, 0));
		setTitolo(titolo);

		panelNorth = new JPanel();
		this.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(0, 2, 0, 0));

		panel_deltaBar = new JPanel();
		panelNorth.add(panel_deltaBar);
		panel_deltaBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		button_deltaBar = new JButton("Delta Bar");
		button_deltaBar.setPreferredSize(new Dimension(100, 35));
		panel_deltaBar.add(button_deltaBar);

		button_deltaBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (scope.isDeltaMisureBarEnabled()) {
					scope.setDeltaMisureBarEnable(false);
				} else {
					scope.setDeltaMisureBarEnable(true);
				}
				button_deltaBar.setSelected(scope.isDeltaMisureBarEnabled());
				
			}
		});

		button_deltaBarSX = new JButton("<");
		button_deltaBarSX.setPreferredSize(new Dimension(50, 35));
		panel_deltaBar.add(button_deltaBarSX);

		button_deltaBarSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scope.misureBarDeltaReverse(1);
			}
		});

		button_deltaBarDX = new JButton(">");
		button_deltaBarDX.setPreferredSize(new Dimension(50, 35));
		panel_deltaBar.add(button_deltaBarDX);

		button_deltaBarDX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scope.misureBarDeltaForaward(1);
			}
		});

		panel_Zoom = new JPanel();
		panelNorth.add(panel_Zoom);
		panel_Zoom.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		button_ZoomAdd = new JButton("");
		button_ZoomAdd.setPreferredSize(new Dimension(50, 35));
		button_ZoomAdd.setRolloverEnabled(true);
		button_ZoomAdd.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Zoom_in.png")));
		panel_Zoom.add(button_ZoomAdd);

		button_ZoomAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scope.zoomAdd(5);
			}
		});

		button_ZoomSub = new JButton("");
		button_ZoomSub.setPreferredSize(new Dimension(50, 35));
		button_ZoomSub.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Zoom_out.png")));
		panel_Zoom.add(button_ZoomSub);

		button_ZoomSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scope.zoomSub(5);
			}
		});

		button_ZoomReset = new JButton("1:1");
		button_ZoomReset.setPreferredSize(new Dimension(50, 35));
		panel_Zoom.add(button_ZoomReset);

		button_ZoomReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scope.zoomReset();
			}
		});

		panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout(0, 0));
		this.add(panelSouth, BorderLayout.SOUTH);

		panelHgap = new JPanel();
		flowLayoutPanelHgap = (FlowLayout) panelHgap.getLayout();
		flowLayoutPanelHgap.setHgap(10);
		panelSouth.add(panelHgap, BorderLayout.WEST);

		panelSouthButton = new JPanel();
		panelSouthButton.setLayout(new GridLayout(0, 7, 0, 0));
		panelSouth.add(panelSouthButton, BorderLayout.SOUTH);

		// Agiungo i bottoni
		button_0 = new JButton("");
		button_0.setToolTipText("Run Record");
		button_0.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Record.png")));
		button_0.setEnabled(false);
		panelSouthButton.add(button_0);

		button_1 = new JButton("");
		button_1.setToolTipText("Stop Record");
		button_1.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Stop.png")));
		button_1.setEnabled(false);
		panelSouthButton.add(button_1);

		button_2 = new JButton("");
		button_2.setToolTipText("Inizio");
		button_2.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Start.png")));
		panelSouthButton.add(button_2);

		button_3 = new JButton("");
		button_3.setToolTipText("Indietro");
		button_3.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Arrow-back.png")));
		panelSouthButton.add(button_3);

		button_4 = new JButton("");
		button_4.setToolTipText("Pausa");
		button_4.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Pause.png")));
		button_4.setEnabled(false);
		panelSouthButton.add(button_4);

		button_5 = new JButton("");
		button_5.setToolTipText("Avanti");
		button_5.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/Arrow-front.png")));
		panelSouthButton.add(button_5);

		button_6 = new JButton("");
		button_6.setToolTipText("Fine");
		button_6.setIcon(new ImageIcon(Trend.class
				.getResource("/icons/End.png")));
		panelSouthButton.add(button_6);

		// Definisco le azioni dei bottoni

		button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pens != null) {
					for (Pen pen : pens) {
						if (pen != null)
							pen.startReg();
					}
					timerScopeUpdate.start();
					scope.setPlay(true);
					button_4.setSelected(false);
					button_0.setEnabled(false);
					button_1.setEnabled(true);
					button_4.setEnabled(true);
				}
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Pen pen : pens) {
					if (pen != null)
						pen.stopReg();
				}
				timerScopeUpdate.stop();
				scope.setPlay(false);
				button_0.setEnabled(true);
				button_1.setEnabled(false);
				button_4.setEnabled(false);
			}
		});

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long currentMills = pens[0].getModel().getFirstMillsElement()
						+ axisX.getMax();
				scope.setCurrentMills(currentMills);
				axisX.setCurrentMills(currentMills);
			}
		});

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long currentMills = scope.getCurrentMills() - axisX.getMax()
						/ cols;
				scope.setCurrentMills(currentMills);
				axisX.setCurrentMills(currentMills);
			}
		});

		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (scope.isPlay()) {
					scope.setPlay(false);
					button_4.setSelected(true);
				} else {
					scope.setPlay(true);
					button_4.setSelected(false);
				}
			}
		});

		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long currentMills = scope.getCurrentMills() + axisX.getMax()
						/ cols;
				scope.setCurrentMills(currentMills);
				axisX.setCurrentMills(currentMills);
			}
		});

		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long currentMills = pens[0].getModel().getLastMillsElement();
				scope.setCurrentMills(currentMills);
				axisX.setCurrentMills(currentMills);
			}
		});

		// Aggiungo l' asse x
		axisX = new Axis(200, 0, cols, true);
		panelSouth.add(axisX, BorderLayout.CENTER);

		// Aggiungo l' asse y
		axisY = new Axis(100, 0, rows, false);
		this.add(axisY, BorderLayout.WEST);
		axisY.setLayout(null);
		// Aggiungo lo scope
		scope = new Scope(this, cols, rows, axisY.getMax(), axisY.getMin(),
				axisX.getMax(), axisX.getMin());
		scope.setBackground(Color.black);
		this.add(scope, BorderLayout.CENTER);

		// Inizializzo il timer per aggiornare lo scope
		timerScopeUpdate = new Timer(DELAY_TIMER_UPDATE_SCOPE,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						scope.repaint();
						if (scope.isPlay()) {
							axisX.setCurrentMills(System.currentTimeMillis());
						}
					}

				});

	}

	/*
	 * ************* METODI OVERRIDE *************
	 */

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		flowLayoutPanelHgap.setHgap(axisY.getXDimension() / 2);
		panelHgap.revalidate();
	}

	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
		if (axisX != null)
			axisX.setBackground(color);
		if (axisY != null)
			axisY.setBackground(color);
		if (panelNorth != null)
			panelNorth.setBackground(color);
		if (panel_deltaBar != null)
			panel_deltaBar.setBackground(color);
		if (panel_Zoom != null)
			panel_Zoom.setBackground(color);
		if (button_deltaBar != null)
			button_deltaBar.setBackground(color);
		if (button_deltaBarSX != null)
			button_deltaBarSX.setBackground(color);
		if (button_deltaBarDX != null)
			button_deltaBarDX.setBackground(color);
		if (button_ZoomAdd != null)
			button_ZoomAdd.setBackground(color);
		if (button_ZoomSub != null)
			button_ZoomSub.setBackground(color);
		if (button_ZoomReset != null)
			button_ZoomReset.setBackground(color);
		if (panelSouthButton != null)
			panelSouthButton.setBackground(color);
		if (panelHgap != null)
			panelHgap.setBackground(color);
		if (button_0 != null)
			button_0.setBackground(color);
		if (button_1 != null)
			button_1.setBackground(color);
		if (button_2 != null)
			button_2.setBackground(color);
		if (button_3 != null)
			button_3.setBackground(color);
		if (button_4 != null)
			button_4.setBackground(color);
		if (button_5 != null)
			button_5.setBackground(color);
		if (button_6 != null)
			button_6.setBackground(color);
	}

	/*
	 * ************* METODI PUBBLICI *************
	 */

	/**
	 * public void addPen(Pen[] pens) </p>
	 * 
	 * Assegna l' array delle penne da controllare con lo scope del trend </p>
	 * 
	 * @param pens
	 *            - array di oggetti di tipo Pen
	 */
	public void addPens(Pen[] pens) {
		this.pens = pens;
		scope.addPen(pens);
	}

	/**
	 * public void close() </p>
	 * 
	 * Metodo da richiamare alla chiusura del programma per fermare tutti i
	 * thread
	 */
	public void close() {
		for (Pen pen : pens) {
			if (pen != null)
				pen.stopReg();
		}
		timerScopeUpdate.stop();
	}

	/**
	 * public String getTitolo()
	 * 
	 * @return il titolo del trend
	 */
	public String getTitolo() {
		if (lblTitolo != null) {
			return lblTitolo.getText();
		} else {
			return null;
		}
	}

	/**
	 * public void setTitolo(String titolo) </p>
	 * 
	 * Imposta il testo della JLabel che identifica il titolo del trend, se il
	 * titolo non c'� lo crea </p>
	 * 
	 * @param titolo
	 *            - oggetto di tipo String
	 */
	public void setTitolo(String titolo) {
		if (lblTitolo == null) {
			lblTitolo = new JLabel();
			lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(lblTitolo, BorderLayout.NORTH);
		}
		lblTitolo.setText(titolo);

	}

	/**
	 * public void setEnabledReg(boolean state)</p>
	 * 
	 * @param state
	 *            - true per abilitare il trend a registrare e false per
	 *            disabilitare
	 */
	public void setEnabledReg(boolean state) {
		if (state) {
			button_0.setEnabled(true);
		} else {
			for (Pen pen : pens) {
				if (pen != null)
					pen.stopReg();
			}
			timerScopeUpdate.stop();
			scope.setPlay(false);
			button_0.setEnabled(false);
			button_1.setEnabled(false);
			button_4.setEnabled(false);
		}
	}

	public void setCurrentMills(long mills) {
		scope.setCurrentMills(mills);
		axisX.setCurrentMills(mills);
	}

	/**
	 * public void setMillsAtFirstRecord()
	 * 
	 */
	public void setMillsAtFirstRecord() {
		long currentMills = pens[0].getModel().getFirstMillsElement()
				+ axisX.getMax();
		scope.setCurrentMills(currentMills);
		axisX.setCurrentMills(currentMills);
	}

	/**
	 * public TrendModel getModel()
	 * 
	 * @return the model
	 */
	public TrendModel getModel() {
		return new TrendModel(cols, rows, axisX.getMax(), axisY.getMin(),
				axisY.getMax(), scope.getTypeScaleAxisY());
	}

	/**
	 * public void setModel(TrendModel model)
	 * 
	 * @param model
	 *            - instanza di una classe TrendModel
	 */
	public void setModel(TrendModel model) {
		setCols(model.getCols());
		setRows(model.getRows());
		setScaleAxisX(model.getAxisX_Time());
		setScaleAxisY(model.getAxisY_Min(), model.getAxisY_Max());
		setTypeScaleAxisY(model.getTypeScaleAxisY());
	}

	/**
	 * @return numero di colonne dello scope
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols
	 *            - numero di colonne dello scope
	 */
	public void setCols(int cols) {
		if (cols > 0 && cols < 100)
			this.cols = cols;
		scope.setCols(cols);
		axisX.setDivisions(cols);
	}

	/**
	 * @return numero di righe dello scope
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            - numero di righe dello scope
	 */
	public void setRows(int rows) {
		if (rows > 0 && rows < 100)
			this.rows = rows;
		scope.setRows(rows);
		axisY.setDivisions(rows);
	}

	/**
	 * public void setScaleAxisX(int mills)</p>
	 * 
	 * Imposta l' asse x per avere sullo scope il trend dei millessecondi
	 * passati come parametro</p>
	 * 
	 * @param mills
	 *            - millesecondi
	 */
	public void setScaleAxisX(int mills) {
		axisX.setScale(0, mills);
		scope.setScaleAxisX(mills, 0);
	}

	/**
	 * public int getScaleAxisX()</p>
	 * 
	 * @return mills dell' asse x
	 */
	public int getScaleAxisX() {
		return axisX.getMax();
	}

	/**
	 * public void setScaleAxisY(int yMin, int yMax)</p>
	 * 
	 * Imposta i valori dell' asse y</p>
	 * 
	 * @param yMin
	 *            - valore minimo dell' asse y
	 * @param yMax
	 *            - valore massimo dell' asse x
	 */
	public void setScaleAxisY(int yMin, int yMax) {
		axisY.setScale(yMin, yMax);
		scope.setScaleAxisY(yMax, yMin);
	}

	/**
	 * public int getScaleAxisYMin()</p>
	 * 
	 * @return valore dell' inizio della scala dell' asse y
	 */
	public int getScaleAxisYMin() {
		return axisY.getMin();
	}

	/**
	 * public int getScaleAxisYMax()</p>
	 * 
	 * @return valore della fine della scala dell' asse y
	 */
	public int getScaleAxisYMax() {
		return axisY.getMax();
	}

	/**
	 * @return the typeScaleAxisY
	 */
	public TYPE_SCALE_AXIS_Y getTypeScaleAxisY() {
		return scope.getTypeScaleAxisY();
	}

	/**
	 * @param typeScaleAxisY
	 *            the typeScaleAxisY to set
	 */
	public void setTypeScaleAxisY(TYPE_SCALE_AXIS_Y typeScaleAxisY) {
		scope.setTypeScaleAxisY(typeScaleAxisY);
		if (typeScaleAxisY == TYPE_SCALE_AXIS_Y.PENS && pens[0] != null) {
			this.setScaleAxisY(pens[0].getMin(), pens[0].getMax());
		}
	}
	
	/**
	 * @param yMaxCustom
	 */
	 public int getYMaxCustom(){
		 return scope.getyMaxCustom();
	 }
	 
	 /**
	 * @param yMinCustom
	 */
	 public int getYMinCustom(){
		 return scope.getyMinCustom();
	 }
	 
	 public boolean isPlay(){
		 return scope.isPlay();
	 }
}
