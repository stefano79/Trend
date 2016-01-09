package com.wordpress.blogste.trend.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import com.wordpress.blogste.trend.Trend;
import com.wordpress.blogste.trend.TrendModel.TYPE_SCALE_AXIS_Y;

public class ViewDialogPreference extends JDialog implements ActionListener,
		PropertyChangeListener {

	public static enum OPTION {
		OK, CANCEL
	};

	public OPTION option = OPTION.CANCEL;

	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnScaleCustom;
	private JLabel lblMax;
	private JFormattedTextField formattedTextField_ScaleCustomMax;
	private JLabel lblMin;
	private JFormattedTextField formattedTextField_ScaleCustomMin;
	private NumberFormatter numberFormatterInteger;
	private JFormattedTextField formattedTextField_cols;
	private JFormattedTextField formattedTextField_rows;
	private JFormattedTextField formattedTextField_AxisXTime;
	private final JPanel contentPanel = new JPanel();
	private Trend trend;
	private JRadioButton rdbtnScalePens;
	private JRadioButton rdbtnScaleAutomatic;

	private JButton okButton;

	private JButton cancelButton;

	/**
	 * @param owner
	 * @param title
	 * @wbp.parser.constructor
	 */
	public ViewDialogPreference(Frame owner, String title) {
		super(owner, title);
		inizialize();
	}

	/**
	 * @param owner
	 * @param title
	 * @param trend
	 */
	public ViewDialogPreference(Frame owner, String title, Trend trend) {
		super(owner, title);
		this.trend = trend;
		inizialize();
	}

	/**
	 * Create the dialog.
	 */
	public void inizialize() {
		setBounds(100, 100, 260, 400);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setModal(true);

		numberFormatterInteger = new NumberFormatter(
				NumberFormat.getIntegerInstance());
		numberFormatterInteger.setAllowsInvalid(false);
		numberFormatterInteger.setMinimum(Integer.MIN_VALUE);
		numberFormatterInteger.setMaximum(Integer.MAX_VALUE);
		numberFormatterInteger.setCommitsOnValidEdit(true);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 54, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		contentPanel.setLayout(gridBagLayout);

		JLabel lblPannelloTrend = new JLabel("Pannello Trend:");
		GridBagConstraints gbc_lblPannelloTrend = new GridBagConstraints();
		gbc_lblPannelloTrend.anchor = GridBagConstraints.WEST;
		gbc_lblPannelloTrend.gridwidth = 4;
		gbc_lblPannelloTrend.insets = new Insets(0, 0, 5, 5);
		gbc_lblPannelloTrend.gridx = 0;
		gbc_lblPannelloTrend.gridy = 0;
		contentPanel.add(lblPannelloTrend, gbc_lblPannelloTrend);

		JLabel lblCols = new JLabel("Colonne:");
		GridBagConstraints gbc_lblCols = new GridBagConstraints();
		gbc_lblCols.anchor = GridBagConstraints.EAST;
		gbc_lblCols.insets = new Insets(0, 0, 5, 5);
		gbc_lblCols.gridx = 1;
		gbc_lblCols.gridy = 1;
		contentPanel.add(lblCols, gbc_lblCols);

		formattedTextField_cols = new JFormattedTextField(
				numberFormatterInteger);
		GridBagConstraints gbc_formattedTextField_cols = new GridBagConstraints();
		gbc_formattedTextField_cols.gridwidth = 2;
		gbc_formattedTextField_cols.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_cols.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField_cols.gridx = 2;
		gbc_formattedTextField_cols.gridy = 1;
		contentPanel.add(formattedTextField_cols, gbc_formattedTextField_cols);

		JLabel lblRows = new JLabel("Righe:");
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.anchor = GridBagConstraints.EAST;
		gbc_lblRows.insets = new Insets(0, 0, 5, 5);
		gbc_lblRows.gridx = 1;
		gbc_lblRows.gridy = 2;
		contentPanel.add(lblRows, gbc_lblRows);

		formattedTextField_rows = new JFormattedTextField(
				numberFormatterInteger);
		GridBagConstraints gbc_formattedTextField_rows = new GridBagConstraints();
		gbc_formattedTextField_rows.gridwidth = 2;
		gbc_formattedTextField_rows.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_rows.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField_rows.gridx = 2;
		gbc_formattedTextField_rows.gridy = 2;
		contentPanel.add(formattedTextField_rows, gbc_formattedTextField_rows);

		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Separator.foreground"));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 4;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 3;
		contentPanel.add(separator, gbc_separator);

		JLabel lblAxisX = new JLabel("Asse X:");
		GridBagConstraints gbc_lblAxisX = new GridBagConstraints();
		gbc_lblAxisX.anchor = GridBagConstraints.WEST;
		gbc_lblAxisX.insets = new Insets(0, 0, 5, 5);
		gbc_lblAxisX.gridwidth = 3;
		gbc_lblAxisX.gridx = 0;
		gbc_lblAxisX.gridy = 4;
		contentPanel.add(lblAxisX, gbc_lblAxisX);

		JLabel lblTime = new JLabel("millisec:");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 1;
		gbc_lblTime.gridy = 5;
		contentPanel.add(lblTime, gbc_lblTime);

		formattedTextField_AxisXTime = new JFormattedTextField(
				numberFormatterInteger);
		GridBagConstraints gbc_formattedTextField_AxisXTime = new GridBagConstraints();
		gbc_formattedTextField_AxisXTime.gridwidth = 2;
		gbc_formattedTextField_AxisXTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_AxisXTime.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField_AxisXTime.gridx = 2;
		gbc_formattedTextField_AxisXTime.gridy = 5;
		contentPanel.add(formattedTextField_AxisXTime,
				gbc_formattedTextField_AxisXTime);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridwidth = 4;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 6;
		contentPanel.add(separator_1, gbc_separator_1);

		JLabel lblAxisY = new JLabel("Asse Y:");
		GridBagConstraints gbc_lblAxisY = new GridBagConstraints();
		gbc_lblAxisY.insets = new Insets(0, 0, 5, 5);
		gbc_lblAxisY.anchor = GridBagConstraints.WEST;
		gbc_lblAxisY.gridwidth = 3;
		gbc_lblAxisY.gridx = 0;
		gbc_lblAxisY.gridy = 7;
		contentPanel.add(lblAxisY, gbc_lblAxisY);

		JLabel lblScale = new JLabel("Scala:");
		GridBagConstraints gbc_lblScale = new GridBagConstraints();
		gbc_lblScale.anchor = GridBagConstraints.EAST;
		gbc_lblScale.insets = new Insets(0, 0, 5, 5);
		gbc_lblScale.gridx = 1;
		gbc_lblScale.gridy = 8;
		contentPanel.add(lblScale, gbc_lblScale);

		rdbtnScaleAutomatic = new JRadioButton("Automatica");
		rdbtnScaleAutomatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabledScaleCustom(false);
			}
		});
		rdbtnScaleAutomatic.setActionCommand("Scale_Auto");
		buttonGroup.add(rdbtnScaleAutomatic);
		GridBagConstraints gbc_rdbtnScaleAutomatic = new GridBagConstraints();
		gbc_rdbtnScaleAutomatic.gridwidth = 2;
		gbc_rdbtnScaleAutomatic.anchor = GridBagConstraints.WEST;
		gbc_rdbtnScaleAutomatic.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnScaleAutomatic.gridx = 2;
		gbc_rdbtnScaleAutomatic.gridy = 8;
		contentPanel.add(rdbtnScaleAutomatic, gbc_rdbtnScaleAutomatic);

		rdbtnScalePens = new JRadioButton("Penne");
		rdbtnScalePens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabledScaleCustom(false);
			}
		});
		rdbtnScalePens.setActionCommand("Scale_Pens");
		buttonGroup.add(rdbtnScalePens);
		GridBagConstraints gbc_rdbtnScalePens = new GridBagConstraints();
		gbc_rdbtnScalePens.gridwidth = 2;
		gbc_rdbtnScalePens.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnScalePens.anchor = GridBagConstraints.WEST;
		gbc_rdbtnScalePens.gridx = 2;
		gbc_rdbtnScalePens.gridy = 9;
		contentPanel.add(rdbtnScalePens, gbc_rdbtnScalePens);

		rdbtnScaleCustom = new JRadioButton("Personalizzata");
		rdbtnScaleCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnabledScaleCustom(true);
			}
		});
		rdbtnScaleCustom.setActionCommand("Scale_Custom");
		buttonGroup.add(rdbtnScaleCustom);
		GridBagConstraints gbc_rdbtnScaleCustom = new GridBagConstraints();
		gbc_rdbtnScaleCustom.gridwidth = 2;
		gbc_rdbtnScaleCustom.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnScaleCustom.gridx = 2;
		gbc_rdbtnScaleCustom.gridy = 10;
		contentPanel.add(rdbtnScaleCustom, gbc_rdbtnScaleCustom);

		lblMax = new JLabel("Max:");
		lblMax.setEnabled(false);
		GridBagConstraints gbc_lblMax = new GridBagConstraints();
		gbc_lblMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMax.anchor = GridBagConstraints.EAST;
		gbc_lblMax.gridx = 2;
		gbc_lblMax.gridy = 11;
		contentPanel.add(lblMax, gbc_lblMax);

		formattedTextField_ScaleCustomMax = new JFormattedTextField(
				numberFormatterInteger);
		formattedTextField_ScaleCustomMax.setEnabled(false);
		GridBagConstraints gbc_formattedTextField_ScaleCustomMax = new GridBagConstraints();
		gbc_formattedTextField_ScaleCustomMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_ScaleCustomMax.insets = new Insets(0, 0, 5, 0);
		gbc_formattedTextField_ScaleCustomMax.gridx = 3;
		gbc_formattedTextField_ScaleCustomMax.gridy = 11;
		contentPanel.add(formattedTextField_ScaleCustomMax,
				gbc_formattedTextField_ScaleCustomMax);

		lblMin = new JLabel("Min:");
		lblMin.setEnabled(false);
		GridBagConstraints gbc_lblMin = new GridBagConstraints();
		gbc_lblMin.anchor = GridBagConstraints.EAST;
		gbc_lblMin.insets = new Insets(0, 0, 0, 5);
		gbc_lblMin.gridx = 2;
		gbc_lblMin.gridy = 12;
		contentPanel.add(lblMin, gbc_lblMin);

		formattedTextField_ScaleCustomMin = new JFormattedTextField(
				numberFormatterInteger);
		formattedTextField_ScaleCustomMin.setEnabled(false);
		GridBagConstraints gbc_formattedTextField_ScaleCustomMin = new GridBagConstraints();
		gbc_formattedTextField_ScaleCustomMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_ScaleCustomMin.gridx = 3;
		gbc_formattedTextField_ScaleCustomMin.gridy = 12;
		contentPanel.add(formattedTextField_ScaleCustomMin,
				gbc_formattedTextField_ScaleCustomMin);

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}

		if (trend != null) {
			formattedTextField_cols.setValue(trend.getCols());
			formattedTextField_rows.setValue(trend.getRows());
			formattedTextField_AxisXTime.setValue(trend.getScaleAxisX());
			formattedTextField_ScaleCustomMax
					.setValue(trend.getYMaxCustom());
			formattedTextField_ScaleCustomMin
					.setValue(trend.getYMinCustom());

			switch (trend.getTypeScaleAxisY()) {
			case AUTOMATIC:
				rdbtnScaleAutomatic.setSelected(true);
				break;
			case PENS:
				rdbtnScalePens.setSelected(true);
				break;
			case CUSTOM:
				rdbtnScaleCustom.setSelected(true);
				setEnabledScaleCustom(true);
				break;
			}

		}
		formattedTextField_cols.addPropertyChangeListener(this);
		formattedTextField_rows.addPropertyChangeListener(this);
		formattedTextField_ScaleCustomMax.addPropertyChangeListener(this);
		formattedTextField_ScaleCustomMin.addPropertyChangeListener(this);
		setVisible(true);
	}

	private void setEnabledScaleCustom(boolean enabled) {
		lblMax.setEnabled(enabled);
		lblMin.setEnabled(enabled);
		formattedTextField_ScaleCustomMax.setEnabled(enabled);
		formattedTextField_ScaleCustomMin.setEnabled(enabled);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource().equals(formattedTextField_cols)) {
			int cols = (Integer) formattedTextField_cols.getValue();
			if (cols < 0 || cols > 100) {
				formattedTextField_cols.setForeground(Color.red);
				okButton.setEnabled(false);
			} else {
				formattedTextField_cols.setForeground(Color.black);
				okButton.setEnabled(true);
			}
		} else if (e.getSource().equals(formattedTextField_rows)) {
			int rows = (Integer) formattedTextField_rows.getValue();
			if (rows < 0 || rows > 100) {
				formattedTextField_rows.setForeground(Color.red);
				okButton.setEnabled(false);
			} else {
				formattedTextField_rows.setForeground(Color.black);
				okButton.setEnabled(true);
			}
		} else {
			int scaleCustomMax = (Integer) formattedTextField_ScaleCustomMax
					.getValue();
			int scaleCustomMin = (Integer) formattedTextField_ScaleCustomMin
					.getValue();
			if (scaleCustomMax < scaleCustomMin) {
				if (e.getSource().equals(formattedTextField_ScaleCustomMax)) {
					formattedTextField_ScaleCustomMax.setForeground(Color.red);
					okButton.setEnabled(false);
				}
				if (e.getSource().equals(formattedTextField_ScaleCustomMin)) {
					formattedTextField_ScaleCustomMin.setForeground(Color.red);
					okButton.setEnabled(false);
				}
			} else {
				formattedTextField_ScaleCustomMax.setForeground(Color.black);
				formattedTextField_ScaleCustomMin.setForeground(Color.black);
				okButton.setEnabled(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			trend.setCols((Integer) formattedTextField_cols.getValue());
			trend.setRows((Integer) formattedTextField_rows.getValue());
			trend.setScaleAxisX((Integer) formattedTextField_AxisXTime
					.getValue());

			if (rdbtnScaleAutomatic.isSelected()) {
				trend.setTypeScaleAxisY(TYPE_SCALE_AXIS_Y.AUTOMATIC);
			}

			if (rdbtnScalePens.isSelected()) {
				trend.setTypeScaleAxisY(TYPE_SCALE_AXIS_Y.PENS);
			}

			if (rdbtnScaleCustom.isSelected()) {
				trend.setTypeScaleAxisY(TYPE_SCALE_AXIS_Y.CUSTOM);
				trend.setScaleAxisY(
						(Integer) formattedTextField_ScaleCustomMin.getValue(),
						(Integer) formattedTextField_ScaleCustomMax.getValue());
			}
			option = OPTION.OK;
		}
		dispose();
	}

}
