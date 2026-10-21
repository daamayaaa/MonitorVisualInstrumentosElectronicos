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
 * The Class GraficaDinamicaPresion.
 */
public class GraficaDinamicaPresion extends ApplicationFrame implements IGraficaDinamica, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The serie tiempo. */
	private TimePeriodValues serieTiempo;
	
	/** The ultimo valor. */
	private double ultimoValor;
	
	/** The chart panel. */
	private ChartPanel chartPanel;
	
	/**
	 * Instantiates a new grafica dinamica presion.
	 *
	 * @param tituloGrafica the titulo grafica
	 */
	public GraficaDinamicaPresion(String tituloGrafica) {
		super(tituloGrafica);
		ultimoValor = ConstantesGraficasDinamicas.VALOR_DEFECTO_GRAFICAS_100;
		serieTiempo = new TimePeriodValues("Presi�n de la Gota");
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
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gr�fica de Comportamiento de la presi�n de una gota", "Tiempo", "pascales", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		// XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// plot.setRenderer(renderer);
		// plot.setOutlinePaint(Color.BLUE);
		// plot.setOutlineStroke(new BasicStroke(4.0f));
		// plot.setBackgroundPaint(Color.DARK_GRAY);
		// plot.setRangeGridlinesVisible(true);
		// plot.setRangeGridlinePaint(Color.BLACK);
		// plot.setDomainGridlinesVisible(true);
		// plot.setDomainGridlinePaint(Color.BLACK);
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