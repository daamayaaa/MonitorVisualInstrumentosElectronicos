/*
 *
 */
package com.damaya.proyinv.dto;

import java.io.Serializable;

/**
 * The Class EW32907DAO.
 */
public class EW32907DTO implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The presion absoluta psia. */
	private float presionAbsolutaPSIA;
	/** The temperatura. */
	private float temperatura;
	/** The volumen flujo. */
	private float volumenFlujo;
	/** The flujo masa. */
	private float flujoMasa;
	/** The valor puesta punto. */
	private float valorPuestaPunto;
	/** The gas. */
	private String gas;
	
	/**
	 * Instantiates a new e w32907 dao.
	 */
	public EW32907DTO() {
		super();
		this.presionAbsolutaPSIA = 0F;
		this.temperatura = 0F;
		this.volumenFlujo = 0F;
		this.flujoMasa = 0F;
		this.valorPuestaPunto = 0F;
		this.gas = "";
	}
	
	/**
	 * Instantiates a new e w32907 dao.
	 * @param presionAbsolutaPSIA the presion absoluta psia
	 * @param temperatura the temperatura
	 * @param volumenFlujo the volumen flujo
	 * @param flujoMasa the flujo masa
	 * @param valorPuestaPunto the valor puesta punto
	 * @param gas the gas
	 */
	public EW32907DTO(float presionAbsolutaPSIA, float temperatura, float volumenFlujo, float flujoMasa, float valorPuestaPunto, String gas) {
		super();
		this.presionAbsolutaPSIA = presionAbsolutaPSIA;
		this.temperatura = temperatura;
		this.volumenFlujo = volumenFlujo;
		this.flujoMasa = flujoMasa;
		this.valorPuestaPunto = valorPuestaPunto;
		this.gas = gas;
	}
	
	/**
	 * Gets the presion absoluta psia.
	 * @return the presion absoluta psia
	 */
	public float getPresionAbsolutaPSIA() {
		return this.presionAbsolutaPSIA;
	}
	
	/**
	 * Sets the presion absoluta psia.
	 * @param presionAbsolutaPSIA the new presion absoluta psia
	 */
	public void setPresionAbsolutaPSIA(float presionAbsolutaPSIA) {
		this.presionAbsolutaPSIA = presionAbsolutaPSIA;
	}
	
	/**
	 * Gets the temperatura.
	 * @return the temperatura
	 */
	public float getTemperatura() {
		return this.temperatura;
	}
	
	/**
	 * Sets the temperatura.
	 * @param temperatura the new temperatura
	 */
	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}
	
	/**
	 * Gets the volumen flujo.
	 * @return the volumen flujo
	 */
	public float getVolumenFlujo() {
		return this.volumenFlujo;
	}
	
	/**
	 * Sets the volumen flujo.
	 * @param volumenFlujo the new volumen flujo
	 */
	public void setVolumenFlujo(float volumenFlujo) {
		this.volumenFlujo = volumenFlujo;
	}
	
	/**
	 * Gets the flujo masa.
	 * @return the flujo masa
	 */
	public float getFlujoMasa() {
		return this.flujoMasa;
	}
	
	/**
	 * Sets the flujo masa.
	 * @param flujoMasa the new flujo masa
	 */
	public void setFlujoMasa(float flujoMasa) {
		this.flujoMasa = flujoMasa;
	}
	
	/**
	 * Gets the valor puesta punto.
	 * @return the valor puesta punto
	 */
	public float getValorPuestaPunto() {
		return this.valorPuestaPunto;
	}
	
	/**
	 * Sets the valor puesta punto.
	 * @param valorPuestaPunto the new valor puesta punto
	 */
	public void setValorPuestaPunto(float valorPuestaPunto) {
		this.valorPuestaPunto = valorPuestaPunto;
	}
	
	/**
	 * Gets the gas.
	 * @return the gas
	 */
	public String getGas() {
		return this.gas;
	}
	
	/**
	 * Sets the gas.
	 * @param gas the new gas
	 */
	public void setGas(String gas) {
		this.gas = gas;
	}
}
