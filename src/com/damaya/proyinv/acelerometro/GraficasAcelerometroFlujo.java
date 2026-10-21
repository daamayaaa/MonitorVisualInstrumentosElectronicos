/*
 *
 */
package com.damaya.proyinv.acelerometro;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Point;
import java.io.Serializable;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer.Pointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import com.damaya.proyinv.constantes.ConstantesGraficasAcelerometro;

/**
 * The Class GraficasAcelerometroEW32907.
 */
public class GraficasAcelerometroFlujo implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Crear grafica para acelerometro.
	 * @param conjuntoDatosPresionEW32907 the conjunto datos presion e w32907
	 * @param conjuntoDatosTemperaturaEW32907 the conjunto datos temperatura e w32907
	 * @param conjuntoDatosVolumenMasaEW32907 the conjunto datos volumen masa e w32907
	 * @param conjuntoDatosFlujoMasicoEW32907 the conjunto datos flujo masico e w32907
	 * @param conjuntoDatosPuestaPuntoEW32907 the conjunto datos puesta punto e w32907
	 * @return the j free chart
	 */
	public JFreeChart crearGraficaParaAcelerometro(ValueDataset conjuntoDatosPresionEW32907, ValueDataset conjuntoDatosTemperaturaEW32907, ValueDataset conjuntoDatosVolumenMasaEW32907, ValueDataset conjuntoDatosFlujoMasicoEW32907, ValueDataset conjuntoDatosPuestaPuntoEW32907) {
		DialPlot plot = new DialPlot();
		plot.setView(0.0, 0.0, 1.0, 1.0);
		plot.setDataset(0, conjuntoDatosPresionEW32907);
		plot.setDataset(1, conjuntoDatosTemperaturaEW32907);
		plot.setDataset(2, conjuntoDatosVolumenMasaEW32907);
		plot.setDataset(3, conjuntoDatosFlujoMasicoEW32907);
		plot.setDataset(4, conjuntoDatosPuestaPuntoEW32907);
		StandardDialFrame dialFrame = new StandardDialFrame();
		dialFrame.setBackgroundPaint(ConstantesGraficasAcelerometro.COLOR_GRIS_SUAVE);
		dialFrame.setForegroundPaint(ConstantesGraficasAcelerometro.COLOR_GRIS_OSCURO);
		plot.setDialFrame(dialFrame);
		GradientPaint gp = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
		DialBackground db = new DialBackground(gp);
		db.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		plot.setBackground(db);
		DialTextAnnotation annotation1 = new DialTextAnnotation("EW32907");
		annotation1.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOGO_OSCURA_14);
		annotation1.setRadius(0.7);
		plot.addLayer(annotation1);
		DialValueIndicator indicador1 = new DialValueIndicator(0);
		indicador1.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		indicador1.setOutlinePaint(ConstantesGraficasAcelerometro.COLOR_AZUL);
		indicador1.setPaint(ConstantesGraficasAcelerometro.COLOR_AZUL);
		indicador1.setRadius(0.60);
		indicador1.setAngle(-103.0);
		plot.addLayer(indicador1);
		DialValueIndicator indicador2 = new DialValueIndicator(1);
		indicador2.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		indicador2.setOutlinePaint(ConstantesGraficasAcelerometro.COLOR_ROJO);
		indicador2.setPaint(ConstantesGraficasAcelerometro.COLOR_ROJO);
		indicador2.setRadius(0.60);
		indicador2.setAngle(-77.0);
		plot.addLayer(indicador2);
		DialValueIndicator indicador3 = new DialValueIndicator(2);
		indicador3.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		indicador3.setOutlinePaint(ConstantesGraficasAcelerometro.COLOR_VERDE);
		indicador3.setPaint(ConstantesGraficasAcelerometro.COLOR_VERDE);
		indicador3.setRadius(0.60);
		indicador3.setAngle(-51.0);
		plot.addLayer(indicador3);
		DialValueIndicator indicador4 = new DialValueIndicator(3);
		indicador4.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		indicador4.setOutlinePaint(ConstantesGraficasAcelerometro.COLOR_AMARILLO);
		indicador4.setPaint(ConstantesGraficasAcelerometro.COLOR_AMARILLO);
		indicador4.setRadius(0.60);
		indicador4.setAngle(-25.0);
		plot.addLayer(indicador4);
		DialValueIndicator indicador5 = new DialValueIndicator(4);
		indicador5.setFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		indicador5.setOutlinePaint(ConstantesGraficasAcelerometro.COLOR_MAGENTA);
		indicador5.setPaint(ConstantesGraficasAcelerometro.COLOR_MAGENTA);
		indicador5.setRadius(0.60);
		indicador5.setAngle(-25.0);
		plot.addLayer(indicador5);
		StandardDialScale escala = new StandardDialScale();
		escala.setLowerBound(-40);
		escala.setUpperBound(60);
		escala.setStartAngle(-120);
		escala.setExtent(-300);
		escala.setTickRadius(0.88);
		escala.setTickLabelOffset(0.15);
		escala.setTickLabelFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_14);
		escala.setMajorTickPaint(ConstantesGraficasAcelerometro.COLOR_AZUL);
		escala.setMinorTickPaint(ConstantesGraficasAcelerometro.COLOR_AZUL);
		escala.setTickLabelPaint(ConstantesGraficasAcelerometro.COLOR_AZUL);
		plot.addScale(0, escala);
		StandardDialScale scale2 = new StandardDialScale();
		scale2.setLowerBound(0);
		scale2.setUpperBound(100);
		scale2.setStartAngle(-120);
		scale2.setExtent(-300);
		scale2.setTickRadius(0.50);
		scale2.setTickLabelOffset(0.15);
		scale2.setTickLabelFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		scale2.setMajorTickPaint(ConstantesGraficasAcelerometro.COLOR_ROJO);
		scale2.setMinorTickPaint(ConstantesGraficasAcelerometro.COLOR_ROJO);
		scale2.setTickLabelPaint(ConstantesGraficasAcelerometro.COLOR_ROJO);
		plot.addScale(1, scale2);
		StandardDialScale scale3 = new StandardDialScale();
		scale3.setLowerBound(0);
		scale3.setUpperBound(100);
		scale3.setStartAngle(-120);
		scale3.setExtent(-300);
		scale3.setTickRadius(0.12);
		scale3.setTickLabelOffset(0.15);
		scale3.setTickLabelFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		scale3.setMajorTickPaint(ConstantesGraficasAcelerometro.COLOR_VERDE);
		scale3.setMinorTickPaint(ConstantesGraficasAcelerometro.COLOR_VERDE);
		scale3.setTickLabelPaint(ConstantesGraficasAcelerometro.COLOR_VERDE);
		plot.addScale(2, scale3);
		StandardDialScale scale4 = new StandardDialScale();
		scale4.setLowerBound(0);
		scale4.setUpperBound(100);
		scale4.setStartAngle(-120);
		scale4.setExtent(-300);
		scale4.setTickRadius(0.12);
		scale4.setTickLabelOffset(0.15);
		scale4.setTickLabelFont(ConstantesGraficasAcelerometro.FUENTE_DIALOG_PLANA_10);
		scale4.setMajorTickPaint(ConstantesGraficasAcelerometro.COLOR_AMARILLO);
		scale4.setMinorTickPaint(ConstantesGraficasAcelerometro.COLOR_AMARILLO);
		scale4.setTickLabelPaint(ConstantesGraficasAcelerometro.COLOR_AMARILLO);
		plot.addScale(3, scale4);
		plot.mapDatasetToScale(1, 1);
		Pointer needle5 = new Pointer(4);
		needle5.setFillPaint(Color.MAGENTA);
		needle5.setRadius(0.66);
		plot.addPointer(needle5);
		plot.addLayer(needle5);
		Pointer needle4 = new Pointer(3);
		needle4.setFillPaint(Color.YELLOW);
		needle4.setRadius(0.66);
		plot.addPointer(needle4);
		plot.addLayer(needle4);
		Pointer needle3 = new Pointer(2);
		needle3.setFillPaint(Color.BLUE);
		needle3.setRadius(0.66);
		plot.addPointer(needle3);
		plot.addLayer(needle3);
		Pointer needle2 = new Pointer(1);
		needle2.setFillPaint(Color.GREEN);
		needle2.setRadius(0.66);
		plot.addPointer(needle2);
		plot.addLayer(needle2);
		Pointer needle1 = new Pointer(0);
		needle1.setFillPaint(Color.RED);
		needle1.setRadius(0.66);
		plot.addPointer(needle1);
		plot.addLayer(needle1);
		DialCap cap = new DialCap();
		cap.setRadius(0.10);
		plot.setCap(cap);
		return new JFreeChart(plot);
	}
}