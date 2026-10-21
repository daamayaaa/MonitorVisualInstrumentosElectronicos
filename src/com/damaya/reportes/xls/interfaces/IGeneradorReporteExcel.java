/*
 * 
 */
package com.damaya.reportes.xls.interfaces;

import java.text.ParseException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGeneradorReporteExcel.
 */
public interface IGeneradorReporteExcel {
	
	/**
	 * Generar reporte en excel.
	 *
	 * @param datosEntrada the datos entrada
	 * @param modeloDataTable the modelo data table
	 * @param tablaGUI the tabla gui
	 */
	public void generarReporteEnExcel(String datosEntrada, DefaultTableModel modeloDataTable, JTable tablaGUI);

	/**
	 * Obtener datos desde tabla dinamica.
	 *
	 * @param tblDatosPesosInstrumento the tbl datos pesos instrumento
	 * @return the object[][]
	 */
	public Object[][] obtenerDatosDesdeTablaDinamica(JTable tblDatosPesosInstrumento);

	/**
	 * Recibe una matriz de entrada y la transforma en un conjunto de datos XYDataset para la gr�fica din�mica.
	 *
	 * @param tablaDatosComoMatriz the tabla datos como matriz
	 * @return the XY dataset
	 * @throws ParseException the parse exception
	 */
	public XYDataset crearConjuntoDatos(Object[][] tablaDatosComoMatriz) throws ParseException;

	/**
	 * Crear grafica.
	 *
	 * @param conjuntoDatosDinamica the conjunto datos dinamica
	 * @return the j free chart
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatosDinamica);
}
