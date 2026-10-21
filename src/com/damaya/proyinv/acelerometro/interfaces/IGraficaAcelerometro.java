/*
 * 
 */
package com.damaya.proyinv.acelerometro.interfaces;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.ValueDataset;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGraficaAcelerometro.
 */
public interface IGraficaAcelerometro {
	
	/**
	 * Crear grafica para acelerometro.
	 *
	 * @param conjuntoDatos the conjunto datos
	 * @return the j free chart
	 */
	public JFreeChart crearGraficaParaAcelerometro(ValueDataset conjuntoDatos);
}
