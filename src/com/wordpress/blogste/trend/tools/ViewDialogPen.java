package com.wordpress.blogste.trend.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.wordpress.blogste.trend.Pen;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class ViewDialogPen extends JDialog implements ActionListener,
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	public static enum OPTION {
		OK, CANCEL
	};

	public OPTION option = OPTION.CANCEL;

	private String[] nameArray;
	private Color[] colors = { Color.red, Color.green, Color.yellow,
			Color.blue, Color.orange, Color.magenta, Color.cyan };
	private String[] nameColors = { "Rosso", "Verde", "Giallo", "Blu",
			"Arancio", "Magenta", "Ciano" };

	private String name;
	private Color color = Color.RED;
	private int valueMax = 100;
	private int valueMin = 0;
	private String units;
	private String description;
	private boolean scale = false;
	private int scaleInMax = 0;
	private int scaleInMin = 0;
	private int scaleOutMax = 0;
	private int scaleOutMin = 0;

	private JComboBox comboBoxName;

	private JComboBox comboBoxColor;

	private JFormattedTextField valueMax_TextField;

	private JFormattedTextField valueMin_TextField;

	private NumberFormatter numberFormatterInteger;

	// private NumberFormatter numberFormatterValueMin;

	private JButton okButton;

	private JButton cancelButton;
	private JTextField units_textField;
	private JTextField description_textField;

	private JFormattedTextField scaleOutMax_TextField;

	private JFormattedTextField scaleOutMin_TextField;

	private JCheckBox chckbxScalatura;

	private JLabel lbl_outMax;

	private JLabel lbl_outMin;
	private JLabel lbl_inMax;
	private JLabel lbl_inMin;
	private JFormattedTextField scaleInMax_TextField;
	private JFormattedTextField scaleInMin_TextField;

	/**
	 * Costruttore del JDialog per inserire una nuova penna
	 * 
	 * @param owner
	 *            - frame da bloccare
	 * @param title
	 *            - titolo del JDialog
	 * @param nameArray
	 *            - Lista dei nomi che si possono assegnare alla penna
	 * @wbp.parser.constructor
	 */
	public ViewDialogPen(Frame owner, String title, String[] nameArray) {
		super(owner, title);
		this.nameArray = nameArray;
		inizialize();
	}

	/**
	 * Costruttore del JDialog per modificare penna
	 * 
	 * @param owner
	 *            - frame da bloccare
	 * @param title
	 *            - titolo del JDialog
	 * @param nameArray
	 *            - Lista dei nomi che si possono assegnare alla penna
	 * @param pen
	 *            - penna da modificare
	 */
	public ViewDialogPen(Frame owner, String title, String[] nameArray, Pen pen) {
		super(owner, title);
		this.nameArray = nameArray;
		this.name = pen.getName();
		this.description = pen.getDescription();
		this.units = pen.getUnits();
		this.color = pen.getColor();
		this.valueMax = pen.getMax();
		this.valueMin = pen.getMin();
		this.scale = pen.isScale();
		this.scaleInMax = pen.getScaleInMax();
		this.scaleInMin = pen.getScaleInMin();
		this.scaleOutMax = pen.getScaleOutMax();
		this.scaleOutMin = pen.getScaleOutMin();

		inizialize();

	}

	/**
	 * Create the dialog.
	 */
	public void inizialize() {
		setResizable(false);
		setBounds(100, 100, 320, 419);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);

		JPanel panel_Value = new JPanel();
		panel_Value.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel_Value, BorderLayout.CENTER);

		numberFormatterInteger = new NumberFormatter(
				NumberFormat.getIntegerInstance());
		numberFormatterInteger.setAllowsInvalid(false);
		numberFormatterInteger.setMinimum(Integer.MIN_VALUE);
		numberFormatterInteger.setMaximum(Integer.MAX_VALUE);
		numberFormatterInteger.setCommitsOnValidEdit(true);

		GridBagLayout gbl_panel_Value = new GridBagLayout();
		gbl_panel_Value.columnWidths = new int[] { 0, 0, 254, 0 };
		gbl_panel_Value.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_Value.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_Value.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_Value.setLayout(gbl_panel_Value);

		JLabel lblIndirizzoVariabile = new JLabel("Indirizzo variabile:");
		GridBagConstraints gbc_lblIndirizzoVariabile = new GridBagConstraints();
		gbc_lblIndirizzoVariabile.anchor = GridBagConstraints.EAST;
		gbc_lblIndirizzoVariabile.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndirizzoVariabile.gridx = 0;
		gbc_lblIndirizzoVariabile.gridy = 0;
		panel_Value.add(lblIndirizzoVariabile, gbc_lblIndirizzoVariabile);

		comboBoxName = new JComboBox(nameArray);
		GridBagConstraints gbc_comboBoxName = new GridBagConstraints();
		gbc_comboBoxName.gridwidth = 2;
		gbc_comboBoxName.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxName.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxName.gridx = 1;
		gbc_comboBoxName.gridy = 0;
		panel_Value.add(comboBoxName, gbc_comboBoxName);

		JLabel lblColore = new JLabel("Colore:");
		GridBagConstraints gbc_lblColore = new GridBagConstraints();
		gbc_lblColore.anchor = GridBagConstraints.EAST;
		gbc_lblColore.insets = new Insets(0, 0, 5, 5);
		gbc_lblColore.gridx = 0;
		gbc_lblColore.gridy = 1;
		panel_Value.add(lblColore, gbc_lblColore);

		comboBoxColor = new JComboBox(colors);
		comboBoxColor.setRenderer(new RenderColorCombobox());
		GridBagConstraints gbc_comboBoxColor = new GridBagConstraints();
		gbc_comboBoxColor.gridwidth = 2;
		gbc_comboBoxColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxColor.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxColor.gridx = 1;
		gbc_comboBoxColor.gridy = 1;
		panel_Value.add(comboBoxColor, gbc_comboBoxColor);

		JLabel lblValoreMax = new JLabel("Valore max:");
		GridBagConstraints gbc_lblValoreMax = new GridBagConstraints();
		gbc_lblValoreMax.anchor = GridBagConstraints.EAST;
		gbc_lblValoreMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblValoreMax.gridx = 0;
		gbc_lblValoreMax.gridy = 2;
		panel_Value.add(lblValoreMax, gbc_lblValoreMax);

		valueMax_TextField = new JFormattedTextField(numberFormatterInteger);
		valueMax_TextField.setValue(valueMax);
		valueMax_TextField.setColumns(10);
		valueMax_TextField.addPropertyChangeListener(this);

		valueMax_TextField.addPropertyChangeListener(this);
		GridBagConstraints gbc_valueMax_TextField = new GridBagConstraints();
		gbc_valueMax_TextField.gridwidth = 2;
		gbc_valueMax_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_valueMax_TextField.insets = new Insets(0, 0, 5, 0);
		gbc_valueMax_TextField.gridx = 1;
		gbc_valueMax_TextField.gridy = 2;
		panel_Value.add(valueMax_TextField, gbc_valueMax_TextField);

		JLabel lblValoreMin = new JLabel("Valore min:");
		GridBagConstraints gbc_lblValoreMin = new GridBagConstraints();
		gbc_lblValoreMin.anchor = GridBagConstraints.EAST;
		gbc_lblValoreMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblValoreMin.gridx = 0;
		gbc_lblValoreMin.gridy = 3;
		panel_Value.add(lblValoreMin, gbc_lblValoreMin);

		valueMin_TextField = new JFormattedTextField(numberFormatterInteger);
		valueMin_TextField.setValue(valueMin);
		valueMin_TextField.setColumns(10);
		valueMin_TextField.addPropertyChangeListener(this);
		GridBagConstraints gbc_valueMin_TextField = new GridBagConstraints();
		gbc_valueMin_TextField.gridwidth = 2;
		gbc_valueMin_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_valueMin_TextField.insets = new Insets(0, 0, 5, 0);
		gbc_valueMin_TextField.gridx = 1;
		gbc_valueMin_TextField.gridy = 3;
		panel_Value.add(valueMin_TextField, gbc_valueMin_TextField);

		JLabel lblUnitaIng = new JLabel("Unit\u00E0 ing.:");
		GridBagConstraints gbc_lblUnitaIng = new GridBagConstraints();
		gbc_lblUnitaIng.anchor = GridBagConstraints.EAST;
		gbc_lblUnitaIng.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnitaIng.gridx = 0;
		gbc_lblUnitaIng.gridy = 4;
		panel_Value.add(lblUnitaIng, gbc_lblUnitaIng);

		units_textField = new JTextField(units);
		units_textField.setColumns(10);
		GridBagConstraints gbc_units_textField = new GridBagConstraints();
		gbc_units_textField.gridwidth = 2;
		gbc_units_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_units_textField.insets = new Insets(0, 0, 5, 0);
		gbc_units_textField.gridx = 1;
		gbc_units_textField.gridy = 4;
		panel_Value.add(units_textField, gbc_units_textField);

		JLabel lblDescrizione = new JLabel("Descrizione:");
		GridBagConstraints gbc_lblDescrizione = new GridBagConstraints();
		gbc_lblDescrizione.anchor = GridBagConstraints.EAST;
		gbc_lblDescrizione.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescrizione.gridx = 0;
		gbc_lblDescrizione.gridy = 5;
		panel_Value.add(lblDescrizione, gbc_lblDescrizione);

		description_textField = new JTextField(description);
		description_textField.setColumns(10);
		GridBagConstraints gbc_description_textField = new GridBagConstraints();
		gbc_description_textField.gridwidth = 2;
		gbc_description_textField.insets = new Insets(0, 0, 5, 0);
		gbc_description_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_description_textField.gridx = 1;
		gbc_description_textField.gridy = 5;
		panel_Value.add(description_textField, gbc_description_textField);
		
		JLabel lbl_scale = new JLabel("Scalatura:");
		GridBagConstraints gbc_lbl_scale = new GridBagConstraints();
		gbc_lbl_scale.anchor = GridBagConstraints.EAST;
		gbc_lbl_scale.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_scale.gridx = 0;
		gbc_lbl_scale.gridy = 6;
		panel_Value.add(lbl_scale, gbc_lbl_scale);
		
		chckbxScalatura = new JCheckBox("");
		chckbxScalatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = chckbxScalatura.isSelected();
				scaleInMax_TextField.setEnabled(state);
				scaleInMin_TextField.setEnabled(state);
				lbl_inMax.setEnabled(state);
				lbl_inMin.setEnabled(state);
				scaleOutMax_TextField.setEnabled(state);
				scaleOutMin_TextField.setEnabled(state);
				lbl_outMax.setEnabled(state);
				lbl_outMin.setEnabled(state);
			}
		});
		chckbxScalatura.setSelected(scale);
		chckbxScalatura.addPropertyChangeListener(this);
		GridBagConstraints gbc_chckbxScalatura = new GridBagConstraints();
		gbc_chckbxScalatura.anchor = GridBagConstraints.WEST;
		gbc_chckbxScalatura.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxScalatura.gridx = 1;
		gbc_chckbxScalatura.gridy = 6;
		panel_Value.add(chckbxScalatura, gbc_chckbxScalatura);
		
		scaleOutMin_TextField = new JFormattedTextField(numberFormatterInteger);
		scaleOutMin_TextField.setEnabled(scale);
		scaleOutMin_TextField.setValue(scaleOutMin);
		scaleOutMin_TextField.setColumns(10);
		
		scaleOutMax_TextField = new JFormattedTextField(numberFormatterInteger);
		scaleOutMax_TextField.setEnabled(scale);
		scaleOutMax_TextField.setValue(scaleOutMax);
		scaleOutMax_TextField.setColumns(10);
		
		lbl_inMax = new JLabel("In Max:");
		lbl_inMax.setEnabled(scale);
		GridBagConstraints gbc_lbl_inMax = new GridBagConstraints();
		gbc_lbl_inMax.anchor = GridBagConstraints.EAST;
		gbc_lbl_inMax.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_inMax.gridx = 1;
		gbc_lbl_inMax.gridy = 7;
		panel_Value.add(lbl_inMax, gbc_lbl_inMax);
		
		scaleInMax_TextField = new JFormattedTextField(numberFormatterInteger);
		scaleInMax_TextField.setEnabled(scale);
		scaleInMax_TextField.setValue(scaleInMax);
		scaleInMax_TextField.setColumns(10);
		GridBagConstraints gbc_scaleInMax_TextField = new GridBagConstraints();
		gbc_scaleInMax_TextField.insets = new Insets(0, 0, 5, 0);
		gbc_scaleInMax_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scaleInMax_TextField.gridx = 2;
		gbc_scaleInMax_TextField.gridy = 7;
		panel_Value.add(scaleInMax_TextField, gbc_scaleInMax_TextField);
		
		lbl_inMin = new JLabel("In Min:");
		lbl_inMin.setEnabled(scale);
		GridBagConstraints gbc_lbl_inMin = new GridBagConstraints();
		gbc_lbl_inMin.anchor = GridBagConstraints.EAST;
		gbc_lbl_inMin.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_inMin.gridx = 1;
		gbc_lbl_inMin.gridy = 8;
		panel_Value.add(lbl_inMin, gbc_lbl_inMin);
		
		scaleInMin_TextField = new JFormattedTextField(numberFormatterInteger);
		scaleInMin_TextField.setEnabled(scale);
		scaleInMin_TextField.setValue(scaleInMin);
		scaleInMin_TextField.setColumns(10);
		GridBagConstraints gbc_scaleInMin_TextField = new GridBagConstraints();
		gbc_scaleInMin_TextField.insets = new Insets(0, 0, 5, 0);
		gbc_scaleInMin_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scaleInMin_TextField.gridx = 2;
		gbc_scaleInMin_TextField.gridy = 8;
		panel_Value.add(scaleInMin_TextField, gbc_scaleInMin_TextField);
		
		lbl_outMax = new JLabel("Out Max:");
		lbl_outMax.setEnabled(scale);
		GridBagConstraints gbc_lbl_outMax = new GridBagConstraints();
		gbc_lbl_outMax.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_outMax.anchor = GridBagConstraints.EAST;
		gbc_lbl_outMax.gridx = 1;
		gbc_lbl_outMax.gridy = 9;
		panel_Value.add(lbl_outMax, gbc_lbl_outMax);
		GridBagConstraints gbc_scaleOutMax_TextField = new GridBagConstraints();
		gbc_scaleOutMax_TextField.insets = new Insets(0, 0, 5, 0);
		gbc_scaleOutMax_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scaleOutMax_TextField.gridx = 2;
		gbc_scaleOutMax_TextField.gridy = 9;
		panel_Value.add(scaleOutMax_TextField, gbc_scaleOutMax_TextField);
		
		lbl_outMin = new JLabel("Out Min:");
		lbl_outMin.setEnabled(scale);
		GridBagConstraints gbc_lbl_outMin = new GridBagConstraints();
		gbc_lbl_outMin.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_outMin.anchor = GridBagConstraints.EAST;
		gbc_lbl_outMin.gridx = 1;
		gbc_lbl_outMin.gridy = 10;
		panel_Value.add(lbl_outMin, gbc_lbl_outMin);
		GridBagConstraints gbc_scaleOutMin_TextField = new GridBagConstraints();
		gbc_scaleOutMin_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scaleOutMin_TextField.gridx = 2;
		gbc_scaleOutMin_TextField.gridy = 10;
		panel_Value.add(scaleOutMin_TextField, gbc_scaleOutMin_TextField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}

		for (int i = 0; i < comboBoxName.getItemCount(); i++) {
			if (comboBoxName.getItemAt(i).equals(name))
				comboBoxName.setSelectedIndex(i);
		}
		for (int i = 0; i < comboBoxColor.getItemCount(); i++) {
			if (comboBoxColor.getItemAt(i).equals(color))
				comboBoxColor.setSelectedIndex(i);
		}

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			name = (String) comboBoxName.getSelectedItem();
			color = (Color) comboBoxColor.getSelectedItem();
			valueMax = (Integer) valueMax_TextField.getValue();
			valueMin = (Integer) valueMin_TextField.getValue();
			units = units_textField.getText();
			description = description_textField.getText();
			scale = chckbxScalatura.isSelected();
			scaleInMax = (Integer) scaleInMax_TextField.getValue();
			scaleInMin = (Integer) scaleInMin_TextField.getValue();
			scaleOutMax = (Integer) scaleOutMax_TextField.getValue();
			scaleOutMin = (Integer) scaleOutMin_TextField.getValue();
			option = OPTION.OK;
		}
		dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		int max = (Integer) valueMax_TextField.getValue();
		int min = (Integer) valueMin_TextField.getValue();
		if (max < min) {
			if (e.getSource().equals(valueMax_TextField))
				valueMax_TextField.setForeground(Color.red);
			if (e.getSource().equals(valueMin_TextField))
				valueMin_TextField.setForeground(Color.red);
			okButton.setEnabled(false);
		} else {
			valueMax_TextField.setForeground(Color.black);
			valueMin_TextField.setForeground(Color.black);
			okButton.setEnabled(true);
		}

	}

	/**
	 * @return una penna
	 */
	public Pen getPen() {
		Pen pen = new Pen(name, description, units, color, valueMin, valueMax);
		pen.setScale(scale);
		pen.setScaleValue(scaleInMin, scaleInMax, scaleOutMin, scaleOutMax);
		return pen;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setAddress(String name) {
		this.name = name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the valueMax
	 */
	public int getValueMax() {
		return valueMax;
	}

	/**
	 * @param valueMax
	 *            the valueMax to set
	 */
	public void setValueMax(int valueMax) {
		this.valueMax = valueMax;
	}

	/**
	 * @return the valueMin
	 */
	public int getValueMin() {
		return valueMin;
	}

	/**
	 * @param valueMin
	 *            the valueMin to set
	 */
	public void setValueMin(int valueMin) {
		this.valueMin = valueMin;
	}

	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	/*
	 * *********** Classi interne per Combobox del colore *************
	 */

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
	 * @return the scaleOutMax
	 */
	public int getScaleOutMax() {
		return scaleOutMax;
	}

	/**
	 * @param scaleOutMax the scaleOutMax to set
	 */
	public void setScaleOutMax(int scaleOutMax) {
		this.scaleOutMax = scaleOutMax;
	}

	/**
	 * @return the scaleOutMin
	 */
	public int getScaleOutMin() {
		return scaleOutMin;
	}

	/**
	 * @param scaleOutMin the scaleOutMin to set
	 */
	public void setScaleOutMin(int scaleOutMin) {
		this.scaleOutMin = scaleOutMin;
	}



	private class RenderColorCombobox extends BasicComboBoxRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object item,
				int index, boolean isSelected, boolean hasFocus) {
			Color color = (Color) item;
			IconColor icon = new IconColor(color);
			setIcon(icon);
			for (int i = 0; i < colors.length; i++) {
				if (color.equals(colors[i])) {
					setText(nameColors[i]);
					break;
				}
			}
			setIconTextGap(15);
			return this;
		}

		private class IconColor implements Icon {

			Color color;
			static final int HEIGHT_RECT = 5;
			static final int WIDTH_RECT = 25;
			static final int V_GAP = 0;
			static final int H_GAP = 5;

			public IconColor(Color color) {
				super();
				this.color = color;
			}

			@Override
			public int getIconHeight() {
				return HEIGHT_RECT;
			}

			@Override
			public int getIconWidth() {
				return WIDTH_RECT + H_GAP;
			}

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {

				g.setColor(color);
				g.fillRoundRect(x + H_GAP, y + V_GAP, WIDTH_RECT, HEIGHT_RECT,
						0, 0);
			}

		}

	}
}
