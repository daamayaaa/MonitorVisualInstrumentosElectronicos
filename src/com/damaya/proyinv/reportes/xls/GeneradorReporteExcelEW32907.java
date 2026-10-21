/*
 * 
 */
package com.damaya.proyinv.reportes.xls;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * The Class GeneradorReporteExcelEW32907.
 */
public class GeneradorReporteExcelEW32907 implements IGeneradorReporteExcel {
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#generarReporteEnExcel(java.lang.String, javax.swing.table.DefaultTableModel, javax.swing.JTable)
	 */
	public void generarReporteEnExcel(String datosEntrada, DefaultTableModel modeloPFMyT, JTable tblDatosPresionFlujoTemperatura) {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("Reporte de Captura de Datos - EW32907");
		HSSFRow fila = hoja.createRow(0);
		HSSFFont fuenteTitulo = libro.createFont();
		HSSFCell celda = fila.createCell(0);
		HSSFCellStyle estiloCelda = libro.createCellStyle();
		fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		celda.setCellValue("Datos obtenidos de la medicion del flujo masico");
		fila = hoja.createRow(1);
		celda = fila.createCell(0);
		celda.setCellValue("Fecha/Hora");
		celda = fila.createCell(1);
		celda.setCellValue("Presion Absoluta");
		celda = fila.createCell(2);
		celda.setCellValue("Temperatura (C)");
		celda = fila.createCell(3);
		celda.setCellValue("Volumen de Flujo");
		celda = fila.createCell(4);
		celda.setCellValue("Flujo Masico");
		celda = fila.createCell(5);
		celda.setCellValue("Set Point");
		celda = fila.createCell(6);
		celda.setCellValue("Gas");
		for (int i = 0; i < modeloPFMyT.getRowCount(); i++) {
			fila = hoja.createRow(i);
			for (int j = 0; j < modeloPFMyT.getColumnCount(); j++) {
				celda = fila.createCell(j + 3);
				celda.setCellValue(tblDatosPresionFlujoTemperatura.getValueAt(i, j).toString());
				celda.setCellStyle(estiloCelda);
			}
		}
		try {
			int width = 850;
			int height = 480;
			float quality = 1;
			Object[][] matrizDatosPesosFlujoTemperatura = obtenerDatosDesdeTablaDinamica(tblDatosPresionFlujoTemperatura);
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
			JOptionPane.showMessageDialog(null, "El reporte en datos capturados para el flujo masico en formato Excel ha sido creado exitosamente. ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
	 */
	public Object[][] obtenerDatosDesdeTablaDinamica(JTable tblDatosPresionFlujoTemperatura) {
		DefaultTableModel modeloDatosPresionFlujoTemperatura = (DefaultTableModel) tblDatosPresionFlujoTemperatura.getModel();
		int nRow = modeloDatosPresionFlujoTemperatura.getRowCount();
		int nCol = modeloDatosPresionFlujoTemperatura.getColumnCount();
		Object[][] tablaDatos = new Object[nRow][nCol];
		for (int i = 0; i < nRow; i++) {
			for (int j = 0; j < nCol; j++) {
				tablaDatos[i][j] = modeloDatosPresionFlujoTemperatura.getValueAt(i, j);
			}
		}
		return tablaDatos;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearConjuntoDatos(java.lang.Object[][])
	 */
	public XYDataset crearConjuntoDatos(Object[][] tablaDatos) throws ParseException {
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		TimePeriodValues series1 = new TimePeriodValues("Presion Absoluta (PSIA)");
		TimePeriodValues series2 = new TimePeriodValues("Temperatura (C)");
		TimePeriodValues series3 = new TimePeriodValues("Volumen de Flujo");
		TimePeriodValues series4 = new TimePeriodValues("Flujo de Masa");
		for (int i = 0; i < tablaDatos.length; i++) {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String today = formatter.format(date);
			SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String string = today + " " + tablaDatos[i][0].toString();
			Date calendario = standardDateFormat.parse(string);
			// Cargar datos primera serie.
			Object presionAbsoluta = tablaDatos[i][1];
			Double presionAbsolutaComoDouble = ((Float) presionAbsoluta).doubleValue();
			series1.add(new Millisecond(calendario), presionAbsolutaComoDouble);
			// Cargar datos segunda serie.
			Object temperatura = tablaDatos[i][2];
			Double temperaturaComoDouble = ((Float) temperatura).doubleValue();
			series2.add(new Millisecond(calendario), temperaturaComoDouble);
			// Cargar datos tercera serie.
			Object volumenFlujo = tablaDatos[i][3];
			Double volumenFlujoComoDouble = ((Float) volumenFlujo).doubleValue();
			series3.add(new Millisecond(calendario), volumenFlujoComoDouble);
			// Cargar datos cuarta serie.
			Object flujoMasa = tablaDatos[i][4];
			Double flujoMasaComoDouble = ((Float) flujoMasa).doubleValue();
			series4.add(new Millisecond(calendario), flujoMasaComoDouble);
		}
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		dataset.addSeries(series4);
		return dataset;
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearGrafica(org.jfree.data.xy.XYDataset)
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Comportamiento de Datos - Sensor EW32907", "Tiempo", "Var", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		plot.setDomainAxis(new DateAxis());
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"));
		return graficaObtenida;
	}
}
