/*
 *
 */
package com.damaya.proyinv.estadisticas;

import java.io.Serializable;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.damaya.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables;

/**
 * The Class ManejadorEstadisticasVariables.
 */
public class ManejadorEstadisticasVariables implements IManejadorEstadisticasVariables, Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#cargarListarModeloDatosTabla(java.util.List, javax.swing.table.DefaultTableModel, int)
	 */
	@Override
	public void cargarListarModeloDatosTabla(List<String> listaSumatoriaRegistros, DefaultTableModel modelo, int fila) {
		for (int count = 0; count < modelo.getRowCount(); count++) {
			listaSumatoriaRegistros.add(modelo.getValueAt(count, fila).toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularSumatoriaVariable(java.util.List)
	 */
	@Override
	public String calcularSumatoriaVariable(List<String> listaSumatoriaRegistros) {
		double sum = 0;
		for (String d : listaSumatoriaRegistros) {
			sum += Double.parseDouble(d);
		}
		return Double.toString(sum);
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularMaximoVariable(java.util.List)
	 */
	@Override
	public String calcularMaximoVariable(List<String> listaSumatoriaRegistros) {
		double max = Double.MIN_VALUE;
		for (String d : listaSumatoriaRegistros) {
			if (Double.parseDouble(d) > max) {
				max = Double.parseDouble(d);
			}
		}
		return Double.toString(max);
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularMinimoVariable(java.util.List)
	 */
	@Override
	public String calcularMinimoVariable(List<String> listaSumatoriaRegistros) {
		double min = Double.MAX_VALUE;
		for (String d : listaSumatoriaRegistros) {
			if (Double.parseDouble(d) < min) {
				min = Double.parseDouble(d);
			}
		}
		return Double.toString(min);
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularPromedioVariable(java.util.List)
	 */
	@Override
	public String calcularPromedioVariable(List<String> listaSumatoriaRegistros) {
		if (listaSumatoriaRegistros == null || listaSumatoriaRegistros.isEmpty()) {
			return Double.toString(0D);
		}
		double sumaElementos = 0;
		for (String d : listaSumatoriaRegistros) {
			sumaElementos += Double.parseDouble(d);
		}
		return Double.toString(sumaElementos / listaSumatoriaRegistros.size());
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularMedianaVariable(java.util.List)
	 */
	@Override
	public String calcularMedianaVariable(List<String> listaSumatoriaRegistros) {
		if (listaSumatoriaRegistros == null || listaSumatoriaRegistros.isEmpty()) {
			return Double.toString(0D);
		}
		try	{
			return Double.toString(listaSumatoriaRegistros.size() % 2 == 1 ? Double.parseDouble(listaSumatoriaRegistros.get(listaSumatoriaRegistros.size() / 2)) : Double.parseDouble(listaSumatoriaRegistros.get(listaSumatoriaRegistros.size() / 2 - 1) + Double.parseDouble(listaSumatoriaRegistros.get(listaSumatoriaRegistros.size() / 2)) / 2.0));
		}
		catch (NumberFormatException e) {
			return Double.toString(0D);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.estadisticas.interfaces.IManejadorEstadisticasVariables#calcularModaVariable(java.util.List)
	 */
	@Override
	public String calcularModaVariable(List<String> listaSumatoriaRegistros) {
		double valorMaximo = 0;
		double contadorMaximo = 0;
		for (int i = 0; i < listaSumatoriaRegistros.size(); ++i) {
			int contador = 0;
			for (int j = 0; j < listaSumatoriaRegistros.size(); ++j) {
				if (listaSumatoriaRegistros.get(j) == listaSumatoriaRegistros.get(i)) {
					++contador;
				}
			}
			if (contador > contadorMaximo) {
				contadorMaximo = contador;
				valorMaximo = Double.parseDouble(listaSumatoriaRegistros.get(i));
			}
		}
		return Double.toString(valorMaximo);
	}
}
