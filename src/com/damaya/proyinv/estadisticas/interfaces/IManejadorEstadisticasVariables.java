/*
 * 
 */
package com.damaya.proyinv.estadisticas.interfaces;

import java.util.List;
import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface IManejadorEstadisticasVariables.
 */
public interface IManejadorEstadisticasVariables {
	
	/**
	 * Cargar listar modelo datos tabla.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @param modelo the modelo
	 * @param fila the fila
	 */
	public void cargarListarModeloDatosTabla(List<String> listaSumatoriaRegistros, DefaultTableModel modelo, int fila);
	
	/**
	 * Calcular sumatoria variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularSumatoriaVariable(List<String> listaSumatoriaRegistros);
	
	/**
	 * Calcular maximo variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularMaximoVariable(List<String> listaSumatoriaRegistros);
	
	/**
	 * Calcular minimo variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularMinimoVariable(List<String> listaSumatoriaRegistros);
	
	/**
	 * Calcular promedio variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularPromedioVariable(List<String> listaSumatoriaRegistros);
	
	/**
	 * Calcular mediana variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularMedianaVariable(List<String> listaSumatoriaRegistros);
	
	/**
	 * Calcular moda variable.
	 *
	 * @param listaSumatoriaRegistros the lista sumatoria registros
	 * @return the string
	 */
	public String calcularModaVariable(List<String> listaSumatoriaRegistros);
}
