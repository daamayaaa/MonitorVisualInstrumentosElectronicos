package com.damaya.proyinv.dao;

import javax.swing.JTable;

public interface IRequestBalanzaDAO {

	/**
	 * Metodo para ingresar el peso de la balanza.
	 * @param pesoBalanza
	 */
	public void ingresarRegistroBalanza(String fechaHoraRegistro, float pesoBalanza);
	
	/**
	 * Metodo para poder mirar el registro de la balanza
	 * @param table
	 */
	public void consultarRegistroBalanza(JTable table);
	
	/**
	 * Metodo para poder actualizar el registro de la balanza.
	 * @param pesoGota
	 * @param id
	 */
	public void actualizarRegistroBalanza(float pesoGota, int id);
	
	/**
	 * Metodo para poder eliminar los productos
	 * @param id
	 */
	public void eliminarRegistroBalanza(int id);
}
