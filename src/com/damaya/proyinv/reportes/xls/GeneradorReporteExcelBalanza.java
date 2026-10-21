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
import com.damaya.proyinv.constantes.ConstantesGraficasDinamicas;
import com.damaya.reportes.xls.interfaces.IGeneradorReporteExcel;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneradorReporteExcelBalanza.
 */
public class GeneradorReporteExcelBalanza implements IGeneradorReporteExcel, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#generarReporteEnExcel(java.lang.String, javax.swing.table.DefaultTableModel, javax.swing.JTable)
	 */
	public void generarReporteEnExcel(String datosEntrada, DefaultTableModel modeloBalanza, JTable tblDatosPesosBalanza) {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("Reporte de Captura de Datos - Balanza");
		HSSFRow fila = hoja.createRow(0);
		HSSFFont fuenteTitulo = libro.createFont();
		HSSFCell celda = fila.createCell(0);
		HSSFCellStyle estiloCelda = libro.createCellStyle();
		fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		celda.setCellValue("Datos obtenidos de la Balanza");
		fila = hoja.createRow(1);
		celda = fila.createCell(0);
		celda.setCellValue("Fecha/Hora");
		celda = fila.createCell(1);
		celda.setCellValue("Peso (Kgs)");
		for (int i = 0; i < modeloBalanza.getRowCount(); i++) {
			fila = hoja.createRow(i);
			for (int j = 0; j < modeloBalanza.getColumnCount(); j++) {
				celda = fila.createCell(j + 3);
				celda.setCellValue(tblDatosPesosBalanza.getValueAt(i, j).toString());
				celda.setCellStyle(estiloCelda);
			}
		}
		try {
			int width = 850;
			int height = 480;
			float quality = 1;
			Object[][] matrizDatosPesoBalanza = obtenerDatosDesdeTablaDinamica(tblDatosPesosBalanza);
			XYDataset conjuntoDatosBalanza = crearConjuntoDatos(matrizDatosPesoBalanza);
			JFreeChart chart = crearGrafica(conjuntoDatosBalanza);
			ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsJPEG(chart_out, quality, chart, width, height);
			int cuadroAPintar = libro.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
			chart_out.close();
			HSSFPatriarch drawing = hoja.createDrawingPatriarch();
			ClientAnchor anchoFigura = new HSSFClientAnchor();
			anchoFigura.setCol1(10);
			anchoFigura.setRow1(5);
			HSSFPicture my_picture = drawing.createPicture(anchoFigura, cuadroAPintar);
			my_picture.resize();
			FileOutputStream archivo = new FileOutputStream(datosEntrada);
			libro.write(archivo);
			archivo.close();
			JOptionPane.showMessageDialog(null, "El reporte en datos capturados de la balanza en formato Excel ha sido creado exitosamente. ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
	 */
	public Object[][] obtenerDatosDesdeTablaDinamica(JTable tblDatosPesosBalanza) {
		DefaultTableModel modeloDatosPesoBalanza = (DefaultTableModel) tblDatosPesosBalanza.getModel();
		int nRow = modeloDatosPesoBalanza.getRowCount();
		int nCol = modeloDatosPesoBalanza.getColumnCount();
		Object[][] tablaDatos = new Object[nRow][nCol];
		for (int i = 0; i < nRow; i++) {
			for (int j = 0; j < nCol; j++) {
				tablaDatos[i][j] = modeloDatosPesoBalanza.getValueAt(i, j);
			}
		}
		return tablaDatos;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearConjuntoDatos(java.lang.Object[][])
	 */
	public XYDataset crearConjuntoDatos(Object[][] tablaDatos) throws ParseException {
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		TimePeriodValues series = new TimePeriodValues("Peso (kg)");
		for (int i = 0; i < tablaDatos.length; i++) {
			SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String string = tablaDatos[i][0].toString();
			Date calendario = standardDateFormat.parse(string);
			Object object = tablaDatos[i][1];
			Double datoMedicionComoDouble = ((Float) object).doubleValue();
			series.add(new Millisecond(calendario), datoMedicionComoDouble);
		}
		dataset.addSeries(series);
		return dataset;
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.xls.interfaces.IGeneradorReporteExcel#crearGrafica(org.jfree.data.xy.XYDataset)
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart(ConstantesGraficasDinamicas.TITULO_GRAFICA_COMPORTAMIENTO_PESO_BALANZA, "Tiempo", "Kg", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		plot.setDomainAxis(new DateAxis());
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"));
		return graficaObtenida;
	}
}
