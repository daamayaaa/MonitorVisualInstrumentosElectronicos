package com.damaya.proyinv.dao;

import javax.swing.JTable;

public interface IRequestHyTDAO {
	
	/**
	 * Metodo para ingresar los datos de humedad y temperatura del sensor DHT11.
	 * 
	 * @param fechaHoraRegistro
	 * @param temperaturaSensor1
	 * @param temperaturaSensor2
	 * @param humedadSensor3
	 */
	public void ingresarRegistroHyT(String fechaHoraRegistro, float temperaturaSensor1, float temperaturaSensor2, float humedadSensor3);
	
	/**
	 * Metodo para poder mirar el registro capturadod del instrumento EW32907.
	 * @param table
	 */
	public void consultarRegistroHyT(JTable tablaDatosHyT);
}
