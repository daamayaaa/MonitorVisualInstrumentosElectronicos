package com.damaya.proyinv.termometro;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.Serializable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.ValueDataset;
import com.damaya.proyinv.termometro.interfaces.IGraficaTermometro;

public class GraficaTermometroTemperatura1 implements IGraficaTermometro, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public JFreeChart crearGraficaParaTermometro(ValueDataset conjuntoDatosSensor) {
		ThermometerPlot plot = new ThermometerPlot(conjuntoDatosSensor);
		plot.setThermometerStroke(new BasicStroke(2.0f));
		plot.setThermometerPaint(Color.lightGray);
		plot.setSubrange(ThermometerPlot.CRITICAL, 70.0, 100.0);
		plot.setSubrangeInfo(1, 19.0, 22.0, -10.0, 22.0);
		plot.setSubrangeInfo(0, 22.0, 24.0, 10.0, 26.0);
		plot.setMercuryPaint(Color.red.darker());
		plot.setSubrangePaint(1, Color.green);
		return new JFreeChart("Temp. Sensor 1", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	}
}