/*
 * 
 */
package com.damaya.proyinv.reportes.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.damaya.proyinv.reportes.txt.interfaces.IGeneradorReporteTexto;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneradorReporteTextoBalanza.
 */
public class GeneradorReporteTextoBalanza implements IGeneradorReporteTexto {
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.txt.interfaces.IGeneradorReporteTexto#generarReporteArhivoTexto(java.lang.String, javax.swing.JTable)
	 */
	public void generarReporteArhivoTexto(String nombreArchivo, JTable tblDatosPesosBalanza) {
		try {
			File file = new File(nombreArchivo);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < tblDatosPesosBalanza.getRowCount(); i++) {
				for (int j = 0; j < tblDatosPesosBalanza.getColumnCount(); j++) {
					bw.write(tblDatosPesosBalanza.getModel().getValueAt(i, j) + "\t");
				}
				bw.write("\n");
			}
			bw.write("\n");
			bw.close();
			fw.close();
			JOptionPane.showMessageDialog(null, "El archivo de texto ha sido generado exitosamente.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
