/*
 * 
 */
package com.damaya.proyinv.graficas.ew32907;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import com.damaya.proyinv.constantes.ConstantesGraficasDinamicas;
import com.damaya.proyinv.graficas.interfaces.IGraficaDinamica;

// TODO: Auto-generated Javadoc
/**
 * The Class GraficaDinamicaTemperatura.
 */
public class GraficaDinamicaTemperatura extends ApplicationFrame implements IGraficaDinamica, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The serie tiempo. */
	private TimePeriodValues serieTiempo;
	
	/** The ultimo valor. */
	private double ultimoValor;
	
	/** The chart panel. */
	private ChartPanel chartPanel;
	
	/**
	 * Instantiates a new grafica dinamica temperatura.
	 *
	 * @param tituloGrafica the titulo grafica
	 */
	public GraficaDinamicaTemperatura(String tituloGrafica) {
		super(tituloGrafica);
		ultimoValor = ConstantesGraficasDinamicas.VALOR_DEFECTO_GRAFICAS_100;
		serieTiempo = new TimePeriodValues(ConstantesGraficasDinamicas.TITULO_SERIE_TEMPERATURA_GOTA);
		chartPanel = new ChartPanel(crearGrafica(new TimePeriodValuesCollection(serieTiempo)));
		JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new Dimension(700, 500));
		setContentPane(content);
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.graficas.interfaces.IGraficaDinamica#crearGrafica(org.jfree.data.xy.XYDataset)
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gr�fica de Comportamiento de la Temperatura de la Gota", "Tiempo", "Kg", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.graficas.interfaces.IGraficaDinamica#obtenerDatos(double)
	 */
	public void obtenerDatos(double x) {
		ultimoValor = x;
		serieTiempo.add(new Millisecond(new Timestamp(System.currentTimeMillis())), ultimoValor);
	}
	
	/* (non-Javadoc)
	 * @see org.jfree.ui.ApplicationFrame#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(final WindowEvent evt) {
		if (evt.getWindow() == this) {
			dispose();
		}
	}
}