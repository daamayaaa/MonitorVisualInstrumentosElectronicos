/*
 *
 */
package com.damaya.proyinv.comserial.interfaces;

import gnu.io.SerialPortEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface IComunicacionSerial.
 */
public interface IComunicacionSerial {
	/**
	 * Verificar puerto serial.
	 */
	public void verificarPuertoSerial();

	/**
	 * Inicializar fujo entrada y salida.
	 * @return true, if successful
	 */
	public boolean inicializarFujoEntradaYSalida();

	/**
	 * Conectar componente puerto serial.
	 */
	public void conectarComponentePuertoSerial();

	/**
	 * Desconectar componente puerto serial.
	 */
	public void desconectarComponentePuertoSerial();

	/**
	 * Inicializar oyente.
	 */
	public void inicializarOyente();

	/**
	 * Serial event.
	 * @param evt the evt
	 */
	public void serialEvent(SerialPortEvent evt);
}
