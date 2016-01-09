package com.wordpress.blogste.trend;

import java.io.Serializable;

public class TrendModel implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8235171493533724239L;

	public static enum TYPE_SCALE_AXIS_Y {
		AUTOMATIC, PENS, CUSTOM
	};
	
	private int cols = 5;
	private int rows = 8;
	private int axisX_Time;
	private int axisY_Min;
	private int axisY_Max;
	private TYPE_SCALE_AXIS_Y typeScaleAxisY = TYPE_SCALE_AXIS_Y.AUTOMATIC;
	
	/**
	 * @param cols
	 * @param rows
	 * @param axisX_Time
	 * @param axisY_Min
	 * @param axisY_Max
	 * @param typeScaleAxisY
	 */
	public TrendModel(int cols, int rows, int axisX_Time, int axisY_Min,
			int axisY_Max, TYPE_SCALE_AXIS_Y typeScaleAxisY) {
		super();
		this.cols = cols;
		this.rows = rows;
		this.axisX_Time = axisX_Time;
		this.axisY_Min = axisY_Min;
		this.axisY_Max = axisY_Max;
		this.typeScaleAxisY = typeScaleAxisY;
	}

	int getCols() {
		return cols;
	}

	void setCols(int cols) {
		this.cols = cols;
	}

	int getRows() {
		return rows;
	}

	void setRows(int rows) {
		this.rows = rows;
	}

	int getAxisX_Time() {
		return axisX_Time;
	}

	void setAxisX_Time(int axisX_Time) {
		this.axisX_Time = axisX_Time;
	}

	int getAxisY_Min() {
		return axisY_Min;
	}

	void setAxisY_Min(int axisY_Min) {
		this.axisY_Min = axisY_Min;
	}

	int getAxisY_Max() {
		return axisY_Max;
	}

	void setAxisY_Max(int axisY_Max) {
		this.axisY_Max = axisY_Max;
	}

	TYPE_SCALE_AXIS_Y getTypeScaleAxisY() {
		return typeScaleAxisY;
	}

	void setTypeScaleAxisY(TYPE_SCALE_AXIS_Y typeScaleAxisY) {
		this.typeScaleAxisY = typeScaleAxisY;
	}
	
	

}
