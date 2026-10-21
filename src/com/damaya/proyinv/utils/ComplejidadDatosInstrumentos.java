/*
 *
 */
package com.damaya.proyinv.utils;

import java.io.Serializable;

/**
 * The Class ComplejidadDatosInstrumentos.
 */
public class ComplejidadDatosInstrumentos implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Recibe los elementos del arreglo que fueron ordenados.
	 *
	 * <Pre>
	 * Se dej� para nlogn pero para ver otros c�lculos de complejidad
	 * </Pre>
	 * @param n the n
	 */
	public void hallarComplejidad(int n) {
		double tiempoEjecucion = n;
		double log2n = obtenerOrdenLog2N(n);
		double log2log2n = obtenerOrdenLog2LogN(log2n);
		System.out.println("C�lculo de complejidad O(n)");
		System.out.println("n: " + n);
		System.out.println("T(n)/n: " + tiempoEjecucion / n);
		System.out.println("T(n)/n2logn: " + tiempoEjecucion / (n * log2log2n));
		System.out.println("T(n)/nlogn: " + tiempoEjecucion / (n * log2n));
		System.out.println("T(n)/n^2: " + tiempoEjecucion / obtenerOrdenLog2LogN(n));
	}

	/**
	 * Obtiene la complejidad del programa el arreglo n.
	 * @param log2n the log2n
	 * @return the double
	 */
	private double obtenerOrdenLog2LogN(double log2n) {
		return Math.log(log2n) / Math.log(2.0);
	}

	/**
	 * Obtiene la complejidad del programa el arreglo n.
	 * @param n the n
	 * @return the double
	 */
	private double obtenerOrdenLog2N(int n) {
		return Math.log(n) / Math.log(2.0);
	}
}
