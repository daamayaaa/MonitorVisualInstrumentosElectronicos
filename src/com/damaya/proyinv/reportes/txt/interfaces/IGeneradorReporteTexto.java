/*
 * 
 */
package com.damaya.proyinv.reportes.txt.interfaces;

import javax.swing.JTable;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGeneradorReporteTexto.
 */
public interface IGeneradorReporteTexto {
	
	/**
	 * Generar reporte arhivo texto.
	 *
	 * @param nombreArchivo the nombre archivo
	 * @param tablaDatosDinamica the tabla datos dinamica
	 */
	public void generarReporteArhivoTexto(String nombreArchivo, JTable tablaDatosDinamica);
}
