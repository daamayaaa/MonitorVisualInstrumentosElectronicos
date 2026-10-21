/*
 * 
 */
package com.damaya.proyinv.reportes.pdf;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * The Class GeneradorReportePDFParaEW32907.
 */
public class GeneradorReportePDFParaEW32907 implements IGeneradorReportePDF, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#generarReportePDF(java.lang.String, javax.swing.JTable)
	 */
	@SuppressWarnings("deprecation")
	public void generarReportePDF(String nombreArchivo, JTable tblDatosPresionFlujoTemperatura) throws DocumentException {
		int contador = tblDatosPresionFlujoTemperatura.getRowCount();
		Document documentoPdf = new Document();
		PdfWriter docWriter = null;
		try {
			docWriter = PdfWriter.getInstance(documentoPdf, new FileOutputStream(nombreArchivo));
			documentoPdf.addProducer();
			documentoPdf.addCreator(this.getClass().getName());
			documentoPdf.addTitle("Reporte de Datos generados para la Balanza");
			documentoPdf.setPageSize(PageSize.LETTER);
			documentoPdf.open();
			PdfPTable tablaDatos = new PdfPTable(7);
			tablaDatos.addCell("Fecha de Captura");
			tablaDatos.addCell("Presion Absoluta (PSIA)");
			tablaDatos.addCell("Temperatura (C)");
			tablaDatos.addCell("Volumen de Flujo");
			tablaDatos.addCell("Flujo de Masa");
			tablaDatos.addCell("Set Point");
			tablaDatos.addCell("Gas");
			for (int i = 0; i < contador; i++) {
				String valor1 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 0).toString();
				String valor2 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 1).toString();
				String valor3 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 2).toString();
				String valor4 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 3).toString();
				String valor5 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 4).toString();
				String valor6 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 5).toString();
				String valor7 = tblDatosPresionFlujoTemperatura.getModel().getValueAt(i, 6).toString();
				tablaDatos.addCell(valor1);
				tablaDatos.addCell(valor2);
				tablaDatos.addCell(valor3);
				tablaDatos.addCell(valor4);
				tablaDatos.addCell(valor5);
				tablaDatos.addCell(valor6);
				tablaDatos.addCell(valor7);
			}
			documentoPdf.add(tablaDatos);
			// documentoPdf.close();
			//
			Object[][] matrizDatosPesosFlujoTemperatura = obtenerDatosDesdeTablaDinamica(tblDatosPresionFlujoTemperatura);
			XYDataset conjuntoDatosFlujoTemperatura = crearConjuntoDatos(matrizDatosPesosFlujoTemperatura);
			JFreeChart chart = crearGrafica(conjuntoDatosFlujoTemperatura);
			int width = 450;
			int height = 250;
			// get the direct pdf content
			PdfContentByte dc = docWriter.getDirectContent();
			// get a pdf template from the direct content
			PdfTemplate tp = dc.createTemplate(width, height);
			// create an AWT renderer from the pdf template
			Graphics2D g2 = tp.createGraphics(width, height, new DefaultFontMapper());
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2, r2D, null);
			g2.dispose();
			// add the rendered pdf template to the direct content
			// you will have to play around with this because the chart is absolutely positioned.
			// 38 is just a typical left margin
			// docWriter.getVerticalPosition(true) will approximate the position that the content above the chart ended
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
				JOptionPane.showMessageDialog(null, "El reporte en datos capturados de la balanza en formato PDF ha sido creado exitosamente. ");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#obtenerDatosDesdeTablaDinamica(javax.swing.JTable)
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
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearConjuntoDatos(java.lang.Object[][])
	 */
	public XYDataset crearConjuntoDatos(Object[][] tablaDatos) throws ParseException {
		TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
		TimePeriodValues series1 = new TimePeriodValues("Presion Absoluta (PSIA)");
		TimePeriodValues series2 = new TimePeriodValues("Temperatura (C)");
		TimePeriodValues series3 = new TimePeriodValues("Volumen de Flujo");
		TimePeriodValues series4 = new TimePeriodValues("Flujo de Masa");
		for (int i = 0; i < tablaDatos.length; i++) {
			SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String string = tablaDatos[i][0].toString();
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
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.reportes.pdf.interfaces.IGeneradorReportePDF#crearGrafica(org.jfree.data.xy.XYDataset)
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
