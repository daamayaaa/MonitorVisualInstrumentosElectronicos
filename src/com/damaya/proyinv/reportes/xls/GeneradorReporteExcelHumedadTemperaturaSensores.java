/*
 * 
 */
package com.damaya.proyinv.reportes.xls;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import com.damaya.reportes.xls.interfaces.IGeneradorReporteExcel;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneradorReporteExcelHumedadTemperaturaSensores.
 */
public class GeneradorReporteExcelHumedadTemperaturaSensores implements IGeneradorReporteExcel, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#generarReporteEnExcel(java.lang.String, javax.swing.table.DefaultTableModel, javax.swing.JTable)
	 */
	public void generarReporteEnExcel(String datosEntrada, DefaultTableModel modeloTHT, JTable tblDatosHumedadTemperatura) {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		HSSFRow fila = hoja.createRow(0);
		HSSFFont fuenteTitulo = libro.createFont();
		HSSFCell celda = fila.createCell(0);
		HSSFCellStyle estiloCelda = libro.createCellStyle();
		fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		celda.setCellValue("Datos obtenidos de los Sensores de Temperatura y Humedad");
		fila = hoja.createRow(1);
		celda = fila.createCell(0);
		celda.setCellValue("Hora de Captura");
		celda = fila.createCell(1);
		celda.setCellValue("Temperatura - Sensor 1 (C)");
		celda = fila.createCell(2);
		celda.setCellValue("Temperatura - Sensor 2 (C)");
		celda = fila.createCell(3);
		celda.setCellValue("Humedad - Sensor 3 (%)");
		for (int i = 0; i < modeloTHT.getRowCount(); i++) {
			fila = hoja.createRow(i);
			for (int j = 0; j < modeloTHT.getColumnCount(); j++) {
				celda = fila.createCell(j + 3);
				celda.setCellValue(tblDatosHumedadTemperatura.getValueAt(i, j).toString());
				celda.setCellStyle(estiloCelda);
			}
		}
		try {
			int width = 850;
			int height = 480;
			float quality = 1;
			Object[][] matrizDatosPesosFlujoTemperatura = obtenerDatosDesdeTablaDinamica(tblDatosHumedadTemperatura);
			XYDataset conjuntoDatosFlujoTemperatura = crearConjuntoDatos(matrizDatosPesosFlujoTemperatura);
			JFreeChart chart = crearGrafica(conjuntoDatosFlujoTemperatura);
			ByteArrayOutputStream graficaSalida = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsJPEG(graficaSalida, quality, chart, width, height);
			int my_picture_id = libro.addPicture(graficaSalida.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
			graficaSalida.close();
			HSSFPatriarch drawing = hoja.createDrawingPatriarch();
			/* Create an anchor point */
			ClientAnchor my_anchor = new HSSFClientAnchor();
			my_anchor.setCol1(10);
			my_anchor.setRow1(5);
			HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
			my_picture.resize();
			FileOutputStream archivo = new FileOutputStream(datosEntrada);
			libro.write(archivo);
			archivo.close();
			JOptionPane.showMessageDialog(null, "El reporte en datos capturados de los sensores de humedad y temperatura en formato Excel ha sido creado exitosamente. ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
	 */
	public Object[][] obtenerDatosDesdeTablaDinamica(JTable tblDatosHumedadTemperatura) {
		DefaultTableModel modeloDatosHumedadTemperatura = (DefaultTableModel) tblDatosHumedadTemperatura.getModel();
		int nRow = modeloDatosHumedadTemperatura.getRowCount();
		int nCol = modeloDatosHumedadTemperatura.getColumnCount();
		Object[][] tablaDatos = new Object[nRow][nCol];
		for (int i = 0; i < nRow; i++) {
			for (int j = 0; j < nCol; j++) {
				tablaDatos[i][j] = modeloDatosHumedadTemperatura.getValueAt(i, j);
			}
		}
		return tablaDatos;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearConjuntoDatos(java.lang.Object[][])
	 */
	public XYDataset crearConjuntoDatos(Object[][] tblDatosHumedadTemperatura) throws ParseException {
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		TimePeriodValues series1 = new TimePeriodValues("Temperatura - Sensor 1 (�C)");
		TimePeriodValues series2 = new TimePeriodValues("Temperatura - Sensor 2 (�C)");
		TimePeriodValues series3 = new TimePeriodValues("Humedad - Sensor 3 (%)");
		for (int i = 0; i < tblDatosHumedadTemperatura.length; i++) {
			SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String string = tblDatosHumedadTemperatura[i][0].toString();
			Date calendario = standardDateFormat.parse(string);
			// Cargar datos primera serie.
			Object presionAbsoluta = tblDatosHumedadTemperatura[i][1];
			Double presionAbsolutaComoDouble = ((Float) presionAbsoluta).doubleValue();
			series1.add(new Millisecond(calendario), presionAbsolutaComoDouble);
			// Cargar datos segunda serie.
			Object temperatura = tblDatosHumedadTemperatura[i][2];
			Double temperaturaComoDouble = ((Float) temperatura).doubleValue();
			series2.add(new Millisecond(calendario), temperaturaComoDouble);
			// Cargar datos tercera serie.
			Object volumenFlujo = tblDatosHumedadTemperatura[i][3];
			Double volumenFlujoComoDouble = ((Float) volumenFlujo).doubleValue();
			series3.add(new Millisecond(calendario), volumenFlujoComoDouble);
		}
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		return dataset;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearGrafica(org.jfree.data.xy.XYDataset)
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Comportamiento de Datos - Arduino", "Tiempo", "Var", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		plot.setDomainAxis(new DateAxis());
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"));
		return graficaObtenida;
	}
}
