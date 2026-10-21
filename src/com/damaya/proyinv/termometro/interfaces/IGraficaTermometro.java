package com.damaya.proyinv.termometro.interfaces;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.ValueDataset;

public interface IGraficaTermometro {
	public JFreeChart crearGraficaParaTermometro(ValueDataset conjuntoDatosSensor);
}
