package com.damaya.proyinv.reportes.csv.interfaces;

import javax.swing.JTable;

/**
 * The Interface /**
 * The Interface IGeneradorReporteTexto.
 */
public interface IGeneradorReporteCSV {
	/**
	 * Generar reporte arhivo CSV.
	 * @param nombreArchivo the nombre archivo
	 * @param tablaDatosDinamica the tabla datos dinamica
	 */
	public void generarReporteArchivoCSV(String nombreArchivo, JTable tablaDatosDinamica);
}
