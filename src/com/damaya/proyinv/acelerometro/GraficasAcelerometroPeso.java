/*
 *
 */
package com.damaya.proyinv.acelerometro;

import static com.damaya.proyinv.constantes.ConstantesGraficasAcelerometro.INTERVALO_RANGO_ACELEROMETRO_ALTO;
import static com.damaya.proyinv.constantes.ConstantesGraficasAcelerometro.INTERVALO_RANGO_ACELEROMETRO_BAJO;
import static com.damaya.proyinv.constantes.ConstantesGraficasAcelerometro.INTERVALO_RANGO_ACELEROMETRO_MEDIO;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.ValueDataset;
import com.damaya.proyinv.acelerometro.interfaces.IGraficaAcelerometro;

/**
 * The Class GraficasAcelerometroPeso.
 */
public class GraficasAcelerometroPeso implements IGraficaAcelerometro, Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.acelerometro.interfaces.IGraficaAcelerometro#crearGraficaParaAcelerometro(org.jfree.data.general.ValueDataset)
	 */
	@Override
	public JFreeChart crearGraficaParaAcelerometro(ValueDataset conjuntoDatos) {
		MeterPlot meterplot = new MeterPlot(conjuntoDatos);
		meterplot.setRange(new Range(0.0D, 10D));
		meterplot.addInterval(INTERVALO_RANGO_ACELEROMETRO_BAJO);
		meterplot.addInterval(INTERVALO_RANGO_ACELEROMETRO_MEDIO);
		meterplot.addInterval(INTERVALO_RANGO_ACELEROMETRO_ALTO);
		meterplot.setNeedlePaint(Color.darkGray);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Arial", 1, 14));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.gray);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));
		return new JFreeChart("Medici�n del Peso", JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
	}
}
