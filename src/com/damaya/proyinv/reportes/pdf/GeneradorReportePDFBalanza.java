/*
 * 
 */
package com.damaya.proyinv.reportes.pdf;

import static com.damaya.proyinv.constantes.ConstantesGraficasDinamicas.TITULO_GRAFICA_COMPORTAMIENTO_PESO_BALANZA;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * The Class GeneradorReportePDFBalanza.
 */
public class GeneradorReportePDFBalanza implements IGeneradorReportePDF, Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#generarReportePDF(java.lang.String, javax.swing.JTable)
	 */
	public void generarReportePDF(String nombreArchivo, JTable tblDatosPesosBalanza) throws DocumentException {
		int contador = tblDatosPesosBalanza.getRowCount();
		Document documentoPdf = new Document();
		PdfWriter docWriter = null;
		try {
			docWriter = PdfWriter.getInstance(documentoPdf, new FileOutputStream(nombreArchivo));
			documentoPdf.addProducer();
			documentoPdf.addCreator(this.getClass().getName());
			documentoPdf.addTitle("Reporte de Datos generados para la Balanza");
			documentoPdf.setPageSize(PageSize.LETTER);
			documentoPdf.open();
			PdfPTable tablaDatos = new PdfPTable(2);
			tablaDatos.addCell("Fecha de Captura");
			tablaDatos.addCell("Peso (Kgs)");
			for (int i = 0; i < contador; i++) {
				String valor1 = tblDatosPesosBalanza.getModel().getValueAt(i, 0).toString();
				String valor2 = tblDatosPesosBalanza.getModel().getValueAt(i, 1).toString();
				tablaDatos.addCell(valor1);
				tablaDatos.addCell(valor2);
			}
			documentoPdf.add(tablaDatos);
			// documentoPdf.close();
			//
			Object[][] matrizDatosPesosBalanza = obtenerDatosDesdeTablaDinamica(tblDatosPesosBalanza);
			XYDataset conjuntoDatosBalanza = crearConjuntoDatos(matrizDatosPesosBalanza);
			JFreeChart chart = crearGrafica(conjuntoDatosBalanza);
			int width = 450;
			int height = 250;
			// get the direct pdf content
			PdfContentByte dc = docWriter.getDirectContent();
			// get a pdf template from the direct content
			PdfTemplate tp = dc.createTemplate(width, height);
			// create an AWT renderer from the pdf template
			@SuppressWarnings("deprecation")
			Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2, r2D, null);
			g2.dispose();
			// add the rendered pdf template to the direct content
			// you will have to play around with this because the chart is
			// absolutely positioned.
			// 38 is just a typical left margin
			// docWriter.getVerticalPosition(true) will approximate the position
			// that the content above the chart ended
			dc.addTemplate(tp, 38, docWriter.getVerticalPosition(true) - height);
		}
		catch (DocumentException dex) {
			dex.printStackTrace();
			throw new DocumentException(dex);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (documentoPdf != null && documentoPdf.isOpen()) {
				documentoPdf.close();
			}
			if (docWriter != null) {
				docWriter.close();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
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
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearConjuntoDatos(java.lang.Object[][])
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
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearGrafica(org.jfree.data.xy.XYDataset)
	 */
	public JFreeChart crearGrafica(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart(TITULO_GRAFICA_COMPORTAMIENTO_PESO_BALANZA, "Tiempo", "Kg", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		plot.setDomainAxis(new DateAxis());
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"));
		return graficaObtenida;
	}
}