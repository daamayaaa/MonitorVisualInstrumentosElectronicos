package com.damaya.proyinv.excepciones;

public class ComunicacionPuertoSerialExcepcion extends Exception {
	private static final long serialVersionUID = 1L;

	public ComunicacionPuertoSerialExcepcion() {
		super();
	}

	public ComunicacionPuertoSerialExcepcion(String mensaje) {
		super(mensaje);
	}

	public ComunicacionPuertoSerialExcepcion(Throwable causa) {
		super(causa);
	}
	
	public ComunicacionPuertoSerialExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}