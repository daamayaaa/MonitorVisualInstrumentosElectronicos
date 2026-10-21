/*
 *
 */
package com.damaya.proyinv.constantes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.data.Range;

// TODO: Auto-generated Javadoc
/**
 * The Class ConstantesGraficasAcelerometro.
 */
public interface ConstantesGraficasAcelerometro {
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_BAJO. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_BAJO = new MeterInterval("Bajo", new Range(0.0D, 3.5D), Color.red, new BasicStroke(2.0F), new Color(255, 0, 0, 128));
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_MEDIO. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_MEDIO = new MeterInterval("Medio", new Range(3.5D, 7D), Color.yellow, new BasicStroke(2.0F), new Color(255, 255, 0, 64));
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_ALTO. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_ALTO = new MeterInterval("Alto", new Range(7D, 10.5D), Color.green, new BasicStroke(2.0F), new Color(0, 255, 0, 64));
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_BAJO_EW32907. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_BAJO_EW32907 = new MeterInterval("Bajo", new Range(0.0D, 20D), Color.red, new BasicStroke(2.0F), new Color(255, 0, 0, 128));
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_MEDIO_EW32907. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_MEDIO_EW32907 = new MeterInterval("Medio", new Range(20D, 40D), Color.yellow, new BasicStroke(2.0F), new Color(255, 255, 0, 64));
	/** The Constant INTERVALO_RANGO_ACELEROMETRO_ALTO_EW32907. */
	public static final MeterInterval INTERVALO_RANGO_ACELEROMETRO_ALTO_EW32907 = new MeterInterval("Alto", new Range(40D, 60D), Color.green, new BasicStroke(2.0F), new Color(0, 255, 0, 64));
	/** The Constant COLOR_GRIS_SUAVE. */
	public static final Color COLOR_GRIS_SUAVE = Color.lightGray;
	/** The Constant COLOR_GRIS_OSCURO. */
	public static final Color COLOR_GRIS_OSCURO = Color.darkGray;
	/** The Constant COLOR_AZUL. */
	public static final Color COLOR_AZUL = Color.blue;
	/** The Constant COLOR_ROJO. */
	public static final Color COLOR_ROJO = Color.red;
	/** The Constant COLOR_VERDE. */
	public static final Color COLOR_VERDE = Color.green;
	/** The Constant COLOR_AMARILLO. */
	public static final Color COLOR_AMARILLO = Color.yellow;
	/** The Constant COLOR_NEGRO. */
	public static final Color COLOR_NEGRO = Color.black;
	/** The Constant COLOR_GRIS. */
	public static final Color COLOR_GRIS = Color.gray;
	/** The Constant COLOR_BLANCO. */
	public static final Color COLOR_BLANCO = Color.white;
	/** The Constant COLOR_MAGENTA. */
	public static final Color COLOR_MAGENTA = Color.MAGENTA;
	/** The Constant FUENTE_DIALOG_PLANA_10. */
	public static final Font FUENTE_DIALOG_PLANA_10 = new Font("Dialog", Font.PLAIN, 10);
	/** The Constant FUENTE_DIALOGO_OSCURA_14. */
	public static final Font FUENTE_DIALOGO_OSCURA_14 = new Font("Dialog", Font.BOLD, 14);
	/** The Constant FUENTE_DIALOG_PLANA_14. */
	public static final Font FUENTE_DIALOG_PLANA_14 = new Font("Dialog", Font.PLAIN, 14);
}
