package com.damaya.proyinv.reportes.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import com.damaya.proyinv.reportes.csv.interfaces.IGeneradorReporteCSV;

public class GeneradorReporteCSVTemperaturaSensores implements IGeneradorReporteCSV {
	@Override
	public void generarReporteArchivoCSV(String nombreArchivo, JTable tablaDatosDinamica) {
		try {
			TableModel model = tablaDatosDinamica.getModel();
			FileWriter csv = new FileWriter(new File(nombreArchivo));
			for (int i = 0; i < model.getColumnCount(); i++) {
				csv.write(model.getColumnName(i) + (i == model.getColumnCount() - 1 ? "" : ","));
			}
			csv.write("\n");
			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					Object cellValue = model.getValueAt(i, j);
					String value = (cellValue == null) ? "" : cellValue.toString();
					if (value.contains(",")) {
						value = "\"" + value.replace("\"", "\"\"") + "\"";
					}
					csv.write(value + (j == model.getColumnCount() - 1 ? "" : ","));
				}
				csv.write("\n"); // New line after each row
			}
			csv.close();
			JOptionPane.showMessageDialog(null, "El archivo CSV ha sido generado exitosamente.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
