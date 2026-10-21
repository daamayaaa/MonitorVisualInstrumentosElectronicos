/*
 * 
 */
package com.damaya.proyinv.graficas.interfaces;

import java.awt.event.WindowEvent;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGraficaDinamica.
 */
public interface IGraficaDinamica {
	
	/**
	 * Crear grafica.
	 *
	 * @param conjuntoDatos the conjunto datos
	 * @return the j free chart
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos);

	/**
	 * Obtener datos.
	 *
	 * @param x the x
	 */
	public void obtenerDatos(double x);

	/**
	 * Window closing.
	 *
	 * @param evt the evt
	 */
	public void windowClosing(final WindowEvent evt);
}
