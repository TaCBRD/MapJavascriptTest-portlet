package com.omi.mapservice.portlet;

import java.awt.Color;

/**
 * Class to mirror the way OpenLayers does WKT layer styling.
 * @author gdonarum
 *
 */
public class LayerFeature {
	
	// Default values from http://docs.openlayers.org/library/feature_styling.html
	private String geometry;
	private String label;
	private String fillColor = "#ee9900";
	private String fillOpacity = "0.4";
	private String strokeColor = "#ee9900";
	private String strokeOpacity = "1";
	private double strokeWidth = 1;
	private STROKE_LINE_CAP strokeLinecap = STROKE_LINE_CAP.round;
	private STROKE_DASH_STYLE strokeDashstyle = STROKE_DASH_STYLE.solid;
	private String pointRadius = "6";

	public enum STROKE_DASH_STYLE {
		dot, dash, dashdot, longdash, longdashdot, solid
	}
	public enum STROKE_LINE_CAP {
		butt, round, square
	}
	
	/**
	 * @return the fillOpacity
	 */
	public String getFillOpacity() {
		return fillOpacity;
	}

	/**
	 * @param fillOpacity the fillOpacity to set
	 */
	public void setFillOpacity(String fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	/**
	 * @return the strokeColor
	 */
	public String getStrokeColor() {
		return strokeColor;
	}

	/**
	 * @param strokeColor the strokeColor to set
	 */
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	public void setStrokeColor(Color color) {
		this.strokeColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());		
	}

	/**
	 * @return the strokeOpacity
	 */
	public String getStrokeOpacity() {
		return strokeOpacity;
	}

	/**
	 * @param strokeOpacity the strokeOpacity to set
	 */
	public void setStrokeOpacity(String strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}

	/**
	 * @return the strokeWidth
	 */
	public double getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * @param strokeWidth the strokeWidth to set
	 */
	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * @return the strokeLinecap
	 */
	public STROKE_LINE_CAP getStrokeLinecap() {
		return strokeLinecap;
	}

	/**
	 * @param strokeLinecap the strokeLinecap to set
	 */
	public void setStrokeLinecap(STROKE_LINE_CAP strokeLinecap) {
		this.strokeLinecap = strokeLinecap;
	}

	/**
	 * @return the strokeDashstyle
	 */
	public STROKE_DASH_STYLE getStrokeDashstyle() {
		return strokeDashstyle;
	}

	/**
	 * @param strokeDashstyle the strokeDashstyle to set
	 */
	public void setStrokeDashstyle(STROKE_DASH_STYLE strokeDashstyle) {
		this.strokeDashstyle = strokeDashstyle;
	}

	/**
	 * @return the pointRadius
	 */
	public String getPointRadius() {
		return pointRadius;
	}

	/**
	 * @param pointRadius the pointRadius to set
	 */
	public void setPointRadius(String pointRadius) {
		this.pointRadius = pointRadius;
	}

	/**
	 * 
	 * @return The WKT string
	 */
	public String getGeometry() {
		return this.geometry;
	}
	
	public void setGeometry(String geometry) {
		this.geometry=geometry;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the fillColor
	 */
	public String getFillColor() {
		return fillColor;
	}

	/**
	 * @param fillColor the fillColor to set
	 */
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setFillColor(Color color) {
		this.fillColor = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());		
	}

}
