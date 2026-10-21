/*
 * 
 */
package com.damaya.proyinv.reportes.pdf.interfaces;

import java.io.FileNotFoundException;
import java.text.ParseException;
import javax.swing.JTable;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import com.itextpdf.text.DocumentException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGeneradorReportePDF.
 */
public interface IGeneradorReportePDF {
	
	/**
	 * Generar reporte pdf.
	 *
	 * @param nombreArchivo the nombre archivo
	 * @param tblDatosInstrumento the tbl datos instrumento
	 * @throws DocumentException the document exception
	 * @throws FileNotFoundException the file not found exception
	 */
	public void generarReportePDF(String nombreArchivo, JTable tblDatosInstrumento) throws DocumentException, FileNotFoundException;

	/**
	 * Obtener datos desde tabla dinamica.
	 *
	 * @param tblDatosInstrumento the tbl datos instrumento
	 * @return the object[][]
	 */
	public Object[][] obtenerDatosDesdeTablaDinamica(JTable tblDatosInstrumento);

	/**
	 * Crear conjunto datos.
	 *
	 * @param tablaDatosComoMatriz the tabla datos como matriz
	 * @return the XY dataset
	 * @throws ParseException the parse exception
	 */
	public XYDataset crearConjuntoDatos(Object[][] tablaDatosComoMatriz) throws ParseException;

	/**
	 * Crear grafica.
	 *
	 * @param conjuntoDatos the conjunto datos
	 * @return the j free chart
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos);
}
