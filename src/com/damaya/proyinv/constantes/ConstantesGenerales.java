package com.damaya.proyinv.constantes;

public enum ConstantesGenerales {
	CADENA_VACIA(""), COMUNICACION_SERIAL_BALANZA_ID("IdComunicacionSerialBalanza");
	private String constanteGeneral;
	
	private ConstantesGenerales(String constanteGeneral) {
		this.constanteGeneral = constanteGeneral;
	}
	
	public String getConstanteGeneral() {
		return this.constanteGeneral;
	}
}