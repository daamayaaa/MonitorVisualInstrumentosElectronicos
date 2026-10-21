/*
 * 
 */
package com.damaya.proyinv.dto;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BalanzaDAO.
 */
public class BalanzaDTO implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The peso gota. */
	private float peso;

	/**
	 * Instantiates a new balanza dao.
	 */
	public BalanzaDTO() {
		super();
		peso = 0F;
	}

	/**
	 * Instantiates a new balanza dao.
	 *
	 * @param peso the peso
	 */
	public BalanzaDTO(float peso) {
		super();
		this.peso = peso;
	}

	/**
	 * Gets the peso.
	 *
	 * @return the peso
	 */
	public float getPeso() {
		return peso;
	}

	/**
	 * Sets the peso gota.
	 *
	 * @param peso the new peso 
	 */
	public void setPeso(float peso) {
		this.peso = peso;
	}
}
