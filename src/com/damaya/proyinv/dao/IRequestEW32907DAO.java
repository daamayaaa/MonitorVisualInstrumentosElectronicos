package com.damaya.proyinv.dao;

import javax.swing.JTable;

public interface IRequestEW32907DAO {

	/**
	 * Metodo para ingresar el peso del instrumento electr�nico EW32907.
	 * 
	 * @param fechaHoraRegistro
	 * @param presionAbsolutaPSIA
	 * @param temperatura
	 * @param volumenFlujo
	 * @param flujoMasa
	 * @param valorPuestaPunto
	 */
	public void ingresarRegistroEW32907(String fechaHoraRegistro, float presionAbsolutaPSIA, float temperatura, float volumenFlujo, float flujoMasa, float valorPuestaPunto);
	
	/**
	 * Metodo para poder mirar el registro capturadod del instrumento EW32907.
	 * @param table
	 */
	public void consultarRegistroEW32907(JTable table);

}
