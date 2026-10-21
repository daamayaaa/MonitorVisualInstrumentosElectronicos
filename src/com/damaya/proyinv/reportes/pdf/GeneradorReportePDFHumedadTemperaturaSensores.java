/*
 * 
 */
package com.damaya.proyinv.reportes.pdf;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import com.damaya.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneradorReportePDFHumedadTemperaturaSensores.
 */
public class GeneradorReportePDFHumedadTemperaturaSensores implements IGeneradorReportePDF, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#generarReportePDF(java.lang.String, javax.swing.JTable)
	 */
	@SuppressWarnings("deprecation")
	public void generarReportePDF(String nombreArchivo, JTable tblDatosHumedadTemperatura) throws DocumentException, FileNotFoundException {
		int contador = tblDatosHumedadTemperatura.getRowCount();
		Document documentoPdf = new Document();
		PdfWriter docWriter = null;
		try {
			docWriter = PdfWriter.getInstance(documentoPdf, new FileOutputStream(nombreArchivo));
			documentoPdf.addProducer();
			documentoPdf.addCreator(this.getClass().getName());
			documentoPdf.addTitle("Reporte de Datos generados para la Balanza");
			documentoPdf.setPageSize(PageSize.LETTER);
			documentoPdf.open();
			PdfPTable tablaDatos = new PdfPTable(4);
			tablaDatos.addCell("Fecha de Captura");
			tablaDatos.addCell("Temperatura - Sensor 1 (�C)");
			tablaDatos.addCell("Temperatura - Sensor 2 (�C)");
			tablaDatos.addCell("Humedad - Sensor 3 (%)");
			for (int i = 0; i < contador; i++) {
				String valor1 = tblDatosHumedadTemperatura.getModel().getValueAt(i, 0).toString();
				String valor2 = tblDatosHumedadTemperatura.getModel().getValueAt(i, 1).toString();
				String valor3 = tblDatosHumedadTemperatura.getModel().getValueAt(i, 2).toString();
				String valor4 = tblDatosHumedadTemperatura.getModel().getValueAt(i, 3).toString();
				tablaDatos.addCell(valor1);
				tablaDatos.addCell(valor2);
				tablaDatos.addCell(valor3);
				tablaDatos.addCell(valor4);
			}
			documentoPdf.add(tablaDatos);
			Object[][] matrizDatosPesosFlujoTemperatura = obtenerDatosDesdeTablaDinamica(tblDatosHumedadTemperatura);
			XYDataset conjuntoDatosFlujoTemperatura = crearConjuntoDatos(matrizDatosPesosFlujoTemperatura);
			JFreeChart chart = crearGrafica(conjuntoDatosFlujoTemperatura);
			int width = 450;
			int height = 250;
			PdfContentByte dc = docWriter.getDirectContent();
			PdfTemplate tp = dc.createTemplate(width, height);
			Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2, r2D, null);
			g2.dispose();
			dc.addTemplate(tp, 38, docWriter.getVerticalPosition(true) - height);
		} catch (DocumentException dex) {
			dex.printStackTrace();
			throw new DocumentException(dex);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (documentoPdf != null && documentoPdf.isOpen()) {
				documentoPdf.close();
			}
			if (docWriter != null) {
				docWriter.close();
				JOptionPane.showMessageDialog(null, "El reporte en datos capturados de la balanza en formato PDF ha sido creado exitosamente. ");
			}
		}
	}

	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
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
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearConjuntoDatos(java.lang.Object[][])
	 */
	public XYDataset crearConjuntoDatos(Object[][] tblDatosHumedadTemperatura) throws ParseException {
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		TimePeriodValues series1 = new TimePeriodValues("Temperatura - Sensor 1 (�C)");
		TimePeriodValues series2 = new TimePeriodValues("Temperatura - Sensor 2 (�C)");
		TimePeriodValues series3 = new TimePeriodValues("Humedad - Sensor 3 (%)");
		for (int i = 0; i < tblDatosHumedadTemperatura.length; i++) {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String today = formatter.format(date);
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
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearGrafica(org.jfree.data.xy.XYDataset)
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
