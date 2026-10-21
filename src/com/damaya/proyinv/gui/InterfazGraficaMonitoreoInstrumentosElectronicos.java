package com.damaya.proyinv.gui;

import static com.damaya.proyinv.constantes.ConstantesPantallaPrincipal.DELAY_TEMPORIZADORES;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Timestamp;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;
import com.damaya.proyinv.acelerometro.GraficasAcelerometroFlujo;
import com.damaya.proyinv.acelerometro.GraficasAcelerometroPeso;
import com.damaya.proyinv.comserial.ComunicacionSerialBalanza;
import com.damaya.proyinv.comserial.ComunicacionSerialEW32907;
import com.damaya.proyinv.comserial.ComunicacionSerialSensoresHumedadYTemperatura;
import com.damaya.proyinv.constantes.ConstantesGraficasDinamicas;
import com.damaya.proyinv.constantes.ConstantesPantallaPrincipal;
import com.damaya.proyinv.graficas.balanza.GraficaDinamicaBalanza;
import com.damaya.proyinv.graficas.ew32907.GraficaDinamicaMasaFlujo;
import com.damaya.proyinv.graficas.ew32907.GraficaDinamicaPresion;
import com.damaya.proyinv.graficas.ew32907.GraficaDinamicaTemperatura;
import com.damaya.proyinv.graficas.ew32907.GraficaDinamicaVolumenFlujo;
import com.damaya.proyinv.graficas.tyh.GraficaDinamicaSensor1Temperatura;
import com.damaya.proyinv.graficas.tyh.GraficaDinamicaSensor2Temperatura;
import com.damaya.proyinv.graficas.tyh.GraficaDinamicaSensor3Humedad;
import com.damaya.proyinv.reportes.csv.GeneradorReporteCSVTemperaturaSensores;
import com.damaya.proyinv.reportes.pdf.GeneradorReportePDFBalanza;
import com.damaya.proyinv.reportes.pdf.GeneradorReportePDFHumedadTemperaturaSensores;
import com.damaya.proyinv.reportes.pdf.GeneradorReportePDFParaEW32907;
import com.damaya.proyinv.reportes.txt.GeneradorReporteTextoBalanza;
import com.damaya.proyinv.reportes.txt.GeneradorReporteTextoEW32907;
import com.damaya.proyinv.reportes.txt.GeneradorReporteTextoTemperaturaSensores;
import com.damaya.proyinv.reportes.xls.GeneradorReporteExcelBalanza;
import com.damaya.proyinv.reportes.xls.GeneradorReporteExcelEW32907;
import com.damaya.proyinv.reportes.xls.GeneradorReporteExcelHumedadTemperaturaSensores;
import com.damaya.proyinv.termometro.GraficaTermometroTemperatura1;
import com.damaya.proyinv.termometro.GraficaTermometroTemperatura2;
import com.damaya.proyinv.utils.CapturadorExcepcionesUtils;
import com.damaya.proyinv.utils.FuncionesUtilitarias;

/**
 * @author Diego Alejandro Amaya Arjona
 * @serial 1.0
 */
public class InterfazGraficaMonitoreoInstrumentosElectronicos extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logInterfazGraficaMonitoreo = Logger.getLogger("interfazGraficaMonitoreo");
	private JPanel panelPrincipal;
	public JTextField campoTextoTemperaturaSensor1;
	public JTextField campoTextoTemperaturaSensor2;
	public JTextField campoTextoHumedadSensor3;
	public JTextField campoTextoPesoBalanza;
	public JTextField campoTextoPresion;
	public JTextField campoTextoTemperaturaC;
	public JTextField campoTextoVolumenFlujo;
	public JTextField campoTextoFlujoMasico;
	// Campos de Texto de Estad�sticas.
	public JTextField campoTextoMaximoPeso;
	public JTextField campoTextoMinimoPeso;
	public JTextField campoTextoPromedioPeso;
	public JTextField campoTextoTotalPeso;
	public JTextField campoTextoMedianaPeso;
	public JTextField campoTextoModaPeso;
	// Campos de Texto de Estad�sticas (Presion Absoluta).
	public JTextField campoTextoMinimoPresionAbsoluta;
	public JTextField campoTextoMaximoPresionAbsoluta;
	public JTextField campoTextoMedianaPresionAbsoluta;
	public JTextField campoTextoModaPresionAbsoluta;
	public JTextField campoTextoTotalPresionAbsoluta;
	public JTextField campoTextoPromedioPresionAbsoluta;
	// Campos de Texto de Estad�sticas (Temperatura).
	public JTextField campoTextoTotalTemperaturaEW2907;
	public JTextField campoTextoPromedioTemperaturaEW2907;
	public JTextField campoTextoMinimoTemperaturaEW2907;
	public JTextField campoTextoMaximoTemperaturaEW2907;
	public JTextField campoTextoModaTemperaturaEW2907;
	public JTextField campoTextoMedianaTemperaturaEW2907;
	// Campos de Texto de Estad�sticas (Volumen de Masa).
	public JTextField campoTextoTotalVolumenMasaEW2907;
	public JTextField campoTextoPromedioVolumenMasaEW2907;
	public JTextField campoTextoMinimoVolumenMasaEW2907;
	public JTextField campoTextoMaximoVolumenMasaEW2907;
	public JTextField campoTextoModaVolumenMasaEW2907;
	public JTextField campoTextoMedianaVolumenMasaEW2907;
	// Campos de Texto de Estad�sticas (Flujo de Masa).
	public JTextField campoTextoTotalFlujoMasaEW2907;
	public JTextField campoTextoPromedioFlujoMasaEW2907;
	public JTextField campoTextoMinimoFlujoMasaEW2907;
	public JTextField campoTextoMaximoFlujoMasaEW2907;
	public JTextField campoTextoModaFlujoMasaEW2907;
	public JTextField campoTextoMedianaFlujoMasaEW2907;
	// Campos de Texto de Estad�sticas (Sensor 1).
	public JTextField campoTextoTotalTemperaturaS1;
	public JTextField campoTextoPromedioTemperaturaS1;
	public JTextField campoTextoMinimoTemperaturaS1;
	public JTextField campoTextoMaximoTemperaturaS1;
	public JTextField tfModaTemperaturaS1;
	public JTextField tfMedianaTemperaturaS1;
	// Campos de Texto de Estad�sticas (Sensor 2).
	public JTextField campoTextoTotalTemperaturaS2;
	public JTextField campoTextoPromedioTemperaturaS2;
	public JTextField campoTextoMinimoTemperaturaS2;
	public JTextField campoTextoMaximoTemperaturaS2;
	public JTextField campoTextoModaTemperaturaS2;
	public JTextField campoTextoMedianaTemperaturaS2;
	// Campos de Texto de Estad�sticas (Sensor 3).
	public JTextField campoTextoTotalHumedadS3;
	public JTextField campoTextoPromedioHumedadS3;
	public JTextField campoTextoMinimoHumedadS3;
	public JTextField campoTextoMaximoHumedadS3;
	public JTextField campoTextoModaHumedadS3;
	public JTextField campoTextoMedianaHumedadS3;
	public JTable tblDatosHumedadTemperatura;
	public JTable tblDatosPesosBalanza;
	public JTable tblDatosPresionFlujoTemperatura;
	private JMenuBar menuPrincipal;
	private JMenu submenuOpciones;
	private JMenu submenuAyuda;
	private JMenuItem miAcercaDelProyecto;
	private JMenuItem miSalir;
	private JTabbedPane panelPestanasPrograma;
	private JPanel panelVisualTemperatura;
	private JPanel pnlDatosConexionPuertoSerialTempHum;
	private JPanel pnlResultadosSensores;
	private JPanel panelDatosTablaFlujoMasico;
	private JPanel panelDatosTablaTemperatura;
	private JPanel panelVisualPeso;
	private JPanel pnlDatosConexionPuertoSerialPeso;
	private JPanel pnlResultadosBalanza;
	private JPanel panelDatosTablaPeso;
	private JPanel panelVisualFlujoMasico;
	private JPanel pnlDatosConexionPuertoSerialPresFlujoTemp;
	private JPanel pnlResultadosPresFluTemp;
	private JLabel lblNomPuertoSensorHT;
	private JLabel lblTemperaturaSensor1;
	private JLabel lblTemperaturaSensor2;
	private JLabel lblHumedadSensor3;
	private JLabel lblPesokg;
	private JLabel lblNomPuertoBalanza;
	private JLabel lblNomSensorPresFluTemp;
	public JLabel lblDesconexionPuertoSerialSensorHumTemp;
	public JLabel lblDesconectarPuertoSerialBalanza;
	public JLabel lblDesconectarPuertoSerialPresFluTemp;
	private JLabel lblTemperaturaC;
	private JLabel lblFlujoMasico;
	private JLabel lblPresion;
	public JComboBox<String> cmbListaPuertosSensorHT;
	public JComboBox<String> cmbListaTasasBaudiosSensorHT;
	public JComboBox<String> cmbListaBitsDatosSensorHT;
	public JComboBox<String> cmbListaParidadSensorHT;
	public JComboBox<String> cmbListaBitsParadaSensorHT;
	// Combos para la parametrizaci�n de los puertos seriales (Balanza).
	public JComboBox<String> cmbListaPuertosBalanza;
	public JComboBox<String> cmbListaTasasBaudiosBalanza;
	public JComboBox<String> cmbListaBitsDatosBalanza;
	public JComboBox<String> cmbListaParidadBalanza;
	public JComboBox<String> cmbListaBitsParadaBalanza;
	// Combos para la parametrizaci�n de los puertos seriales (Flujo Masico).
	public JComboBox<String> cmbListaPuertosSenPresFluTemp;
	public JComboBox<String> cmbListaTasasBaudiosSenPresFluTemp;
	public JComboBox<String> cmbListaBitsDatosSenPresFluTemp;
	public JComboBox<String> cmbListaParidadSenPresFluTemp;
	public JComboBox<String> cmbListaBitsParadaSenPresFluTemp;
	private JButton btnValidarPuertoSensorHT;
	private JButton btnConectarSensorHT;
	private JButton btnDesconectarSensorHT;
	private JButton btnPintarGraficaSensor1;
	private JButton btnGraficaTemperatura;
	private JButton btnGraficaDeFl;
	private JButton btnGraficaDePresion;
	private JButton btnPintarGraficaSensor2;
	private JButton btnGraficaHumSensor;
	private JButton btnValidarPuertoBalanza;
	private JButton btnConectarBalanza;
	private JButton btnDesconectarBalanza;
	private JButton btnGraficaPesoDe;
	private JButton btnValidarPuertoSenPresFluTemp;
	private JButton btnConectarSenPresFluTemp;
	private JButton btnDesconectarSenPresFluTemp;
	private JButton btnExportarDatosHyTAPDF;
	private GroupLayout glPanelDatosConexionPuertoSerialTempHum;
	private GroupLayout glPanelDatosTablaFlujoMasico;
	private GroupLayout glPanelResultadosSensores;
	private GroupLayout glPanelVisualTemperatura;
	private GroupLayout glPanelDatosTablaTemperatura;
	private GroupLayout glPanelDatosConexionPuertoSerialPeso;
	private GroupLayout glPanelResultadosBalanza;
	private GroupLayout glPanelVisualPeso;
	private GroupLayout glPanelDatosTablaPeso;
	private GroupLayout glPanelDatosConexionPuertoSerialPresFlujoTemp;
	private JScrollPane pnlDespTablaPrsFlujTemp;
	private JScrollPane pnlDespTablaTempHum;
	private JScrollPane pnlDespTablaPesosBalanza;
	// Componentes de Humedad y Temperatura.
	private ComunicacionSerialSensoresHumedadYTemperatura varComunicacionSerialHyT;
	private GraficaDinamicaSensor1Temperatura graficaDinamicaSensor1Temperatura;
	private GraficaDinamicaSensor2Temperatura graficaDinamicaSensor2Temperatura;
	private GraficaDinamicaSensor3Humedad graficaDinamicaSensor3Humedad;
	//
	private Timer temporizadorSensor1Temperatura;
	private Timer temporizadorSensor2Temperatura;
	private Timer temporizadorSensor3Humedad;
	// Componentes de Balanza.
	private ComunicacionSerialBalanza varComunicacionSerialBalanza;
	private GraficaDinamicaBalanza graficaDinamicaBalanza;
	private Timer temporizadorBalanza;
	// Componentes de Presion, Flujo y Temperatura.
	public ComunicacionSerialEW32907 varComunicacionSerialPFMyT;
	private GraficaDinamicaPresion graficaDinamicaPresionAbsoluta;
	private GraficaDinamicaTemperatura graficaDinamicaTemperatura;
	private GraficaDinamicaVolumenFlujo graficaDinamicaVolumenFlujo;
	private GraficaDinamicaMasaFlujo graficaDinamicaMasaFlujo;
	private Timer temporizadorPresion;
	private Timer temporizadorTemperatura;
	private Timer temporizadorVolumenFlujo;
	private Timer temporizadorMasaFlujo;
	// Componentes para el Modelo de Datos de los componentes.
	public DefaultTableModel modeloTHT;
	public DefaultTableModel modeloBalanza;
	public DefaultTableModel modeloPFMyT;
	private JButton btnExportarDatosHyTAExcel;
	private JButton btnExportarDatosHyTATexto;
	@SuppressWarnings("unused")
	private JButton btnExportarDatosHyTACSV;
	private JButton btnExportarDatosDe;
	private JButton btnExportarDatosFlujoMasicoAExcel;
	private ChartPanel chartPanelSensorTemperatura1;
	private ChartPanel chartPanelSensorTemperatura2;
	private ChartPanel chartPanelSensorHumedad;
	private ChartPanel chartPanelPesoBalanza;
	private ChartPanel chartPanelAcelerometroPesoBalanza;
	private ChartPanel chartPanelTermometroTemperaturaTermometro1;
	private ChartPanel chartPanelTermometroTemperaturaTermometro2;
	private Timer temporizadorTermometroTemperatura1;
	private Timer temporizadorTermometroTemperatura2;
	private TimePeriodValues serieTiempoSensorTemperatura1;
	private double ultimoValorSensorTemperatura1 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoSensorTemperatura2;
	private double ultimoValorSensorTemperatura2 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoSensorHumedad;
	private double ultimoValorSensorHumedad = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	//
	private Timer tempSensor1Temperatura;
	private Timer tempSensor2Temperatura;
	private Timer tempSensor3Humedad;
	private TimePeriodValues serieTiempoPesoBalanza;
	private double ultimoValorPezoBalanza = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	//
	private Timer tempPesoBalanza;
	private ChartPanel chartPanelPresionEW32907;
	// ----------------------------------------------------------------------------------------- //
	// SERIES DE TIEMPO PARA LAS VARIABLES DEL PROGRAMA EW32907. //
	// ----------------------------------------------------------------------------------------- //
	private TimePeriodValues serieTiempoPresionAbsolutaEW32907;
	private double ultimoValorPresionAbsolutaEW32907 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoTemperaturaEW32907;
	private double ultimoValorTemperaturaEW32907 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoVolumenFlujoEW32907;
	private double ultimoValorVolumenFlujoEW32907 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoFlujoMasaFlujoEW32907;
	private double ultimoValorFlujoMasaEW32907 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private TimePeriodValues serieTiempoPuestaPuntoEW32907;
	private double ultimoValorPuestaPuntoEW32907 = ConstantesPantallaPrincipal.VALOR_DEFECTO_100;
	private Timer tempEW32907;
	private JLabel lblImagenHumedad;
	// Datos requeridos para los acelerometros (Conjunto de datos, temporizadores),
	private DefaultValueDataset conjuntoDatosAcelerometroBalanza = new DefaultValueDataset();
	private Timer temporizadorAcelerometroBalanza;
	private ChartPanel chartPanelAcelerometroEW32907;
	private DefaultValueDataset conjuntoDatosAcelerometroPresionEW32907 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosAcelerometroTemperaturaEW32907 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosAcelerometroVolumenMasicoEW32907 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosAcelerometroFlujoMasicoEW32907 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosAcelerometroSetPointEW32907 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosSensorTermometroTemperaturaSensor1 = new DefaultValueDataset();
	private DefaultValueDataset conjuntoDatosSensorTermometroTemperaturaSensor2 = new DefaultValueDataset();
	private Timer temporizadorAcelerometroEW32907;
	private JLabel lblMaximoPeso;
	private JLabel lblMnimoPeso;
	private JLabel lblBitsDatosBalanza;
	private JLabel lblTasBaudiosSensorHT;
	private JLabel lblBitsDatosSensorHT;
	private JLabel lblBitsParadaSensorHT;
	private JLabel lblParidadSensorHT;
	private JPanel pnlEstadisticasTablaFlujoMasaEW2907;
	private JLabel lblTotalFlujoMasaEW2907;
	private JLabel lblPromedioFlujoMasaEW2907;
	private JLabel lblMinimoFlujoMasaEW2907;
	private JLabel lblMaximoFlujoMasaEW2907;
	private JLabel lblMedianaFlujoMasaEW2907;
	private JLabel lblModaFlujoMasaEW2907;
	private JPanel pnlEstadisticasTablaHumedadS3;
	private JLabel lblTotalHumedadS3;
	private JLabel lblPromedioHumedadS3;
	private JLabel lblMinimoHumedadS3;
	private JLabel lblMaximoHumedadS3;
	private JLabel lblMedianaHumedadS3;
	private JLabel lblModaHumedadS3;
	@SuppressWarnings("unused")
	private JPanel pnlTermometroTemperaturaTermometro1;
	@SuppressWarnings("unused")
	private JPanel pnlTermometroTemperaturaTermometro2;
	private CapturadorExcepcionesUtils capturadorExcepcion;
	private JLabel lblHoraCapturaDatosTmpHum;
	public JLabel lblTimeOperacionHumTemp;
	private JLabel lblHoraCapturaDatosPeso;
	public JLabel lblTimeOperacionPeso;
	private JLabel lblTimeOperacionFlujoMasico;
	public JLabel lblHoraCapturaDatosFlujoMasico;
	private JButton btnExportarDatosBalanzaAPDF;
	private JButton btnExportarDatosBalanzaATexto;
	private JButton btnVolFlujo;
	private JButton btnExportarDatosFlujoMasicoAPDF;
	private JButton btnExportarDatosFlujoMasicoATXT;
	private TableRowSorter<TableModel> ordenadorFilasTablaHyT;
	private TableRowSorter<TableModel> ordenadorFilasTablaPeso;
	private TableRowSorter<TableModel> ordenadorFilasTablaFlujoMasico;
	private JTextField tfRegBusTblHyT;
	private JLabel lblRegBusTblPeso;
	private JTextField tfRegBusTblPeso;
	private JLabel lblRegBusTblFlujoMasico;
	private JTextField tfRegBusFlujoMasico;
	private JMenuItem miAyudaProyecto;
	public JTextField campoTextolNumRegTblHyT;
	public JTextField campoTextolNumRegTblPeso;
	public JTextField campoTextoTiempoTomaDatosRegPeso;
	
	/**
	 * Create the frame.
	 */
	public InterfazGraficaMonitoreoInstrumentosElectronicos() {
		varComunicacionSerialHyT = new ComunicacionSerialSensoresHumedadYTemperatura(this);
		varComunicacionSerialBalanza = new ComunicacionSerialBalanza(this);
		varComunicacionSerialPFMyT = new ComunicacionSerialEW32907(this);
		setTitle("Monitoreo del Comportamiento de los Datos Obtenidos de Varios Instrumentos Electrónicos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Object opciones[] = { "SI", "NO" };
				int cerrar = JOptionPane.showOptionDialog(e.getComponent(), "Desea cerrar la aplicación en este momento?\n", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
				if (cerrar == JOptionPane.YES_OPTION) {
					if (varComunicacionSerialHyT.isConectadoPuertoSerialSensorHyT()) {
						varComunicacionSerialHyT.desconectarComponentePuertoSerial();
					}
					if (varComunicacionSerialBalanza.isConectadoPuertoSerialBalanza()) {
						varComunicacionSerialBalanza.desconectarComponentePuertoSerial();
					}
					if (varComunicacionSerialPFMyT.isConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura()) {
						varComunicacionSerialPFMyT.desconectarComponentePuertoSerial();
					}
					((JFrame) e.getSource()).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose();
					System.exit(0);
					setVisible(false);
				}
				else {
					((JFrame) e.getSource()).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		setBounds(100, 100, 695, 798);
		menuPrincipal = new JMenuBar();
		setJMenuBar(menuPrincipal);
		submenuOpciones = new JMenu("Opciones");
		menuPrincipal.add(submenuOpciones);
		miSalir = new JMenuItem("Salir");
		miSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
				setVisible(false);
			}
		});
		submenuOpciones.add(miSalir);
		submenuAyuda = new JMenu("Ayuda");
		menuPrincipal.add(submenuAyuda);
		miAyudaProyecto = new JMenuItem("Manual de Usuario");
		submenuAyuda.add(miAyudaProyecto);
		miAcercaDelProyecto = new JMenuItem("Acerca del Proyecto");
		miAcercaDelProyecto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(submenuAyuda, "Aplicación visual que facilita la captura de datos de un instrumento electrónico.", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		submenuAyuda.add(miAcercaDelProyecto);
		cargarAyuda();
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		setContentPane(panelPrincipal);
		panelPestanasPrograma = new JTabbedPane(JTabbedPane.TOP);
		panelPrincipal.add(panelPestanasPrograma, BorderLayout.CENTER);
		panelVisualTemperatura = new JPanel();
		panelPestanasPrograma.addTab("Temp - Hum", null, panelVisualTemperatura, null);
		pnlDatosConexionPuertoSerialTempHum = new JPanel();
		pnlDatosConexionPuertoSerialTempHum.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos de Entrada - Sensores de Humedad y Temperatura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblNomPuertoSensorHT = new JLabel("Nombre del Puerto: ");
		lblNomPuertoSensorHT.setVerticalAlignment(SwingConstants.TOP);
		cmbListaPuertosSensorHT = new JComboBox<String>();
		btnValidarPuertoSensorHT = new JButton("");
		btnValidarPuertoSensorHT.setToolTipText("Validar");
		try {
			Image imagenRS232 = ImageIO.read(new File("imagenes/RS232.png"));
			btnValidarPuertoSensorHT.setIcon(new ImageIcon(imagenRS232));
		}
		catch (IOException e1) {
			capturadorExcepcion = new CapturadorExcepcionesUtils();
			capturadorExcepcion.getCustomStackTrace(e1);
		}
		btnValidarPuertoSensorHT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialHyT.verificarPuertoSerial();
			}
		});
		btnConectarSensorHT = new JButton("");
		btnConectarSensorHT.setToolTipText("Conectar");
		btnConectarSensorHT.setEnabled(true);
		try {
			Image imagenFlechaArriba2 = ImageIO.read(new File("imagenes/FlechaArriba2.png"));
			btnConectarSensorHT.setIcon(new ImageIcon(imagenFlechaArriba2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnConectarSensorHT.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialHyT.conectarComponentePuertoSerial();
				if (varComunicacionSerialHyT.isConectadoPuertoSerialSensorHyT()) {
					if (varComunicacionSerialHyT.inicializarFujoEntradaYSalida()) {
						varComunicacionSerialHyT.inicializarOyente();
					}
				}
				if (!isSuspendidoHiloHyT) {// si no esta suspendido o pausado
					hiloCronometroHyT.start();// el hilo empieza
				}
				else {// de lo contrario
					hiloCronometroHyT.resume();// el hilo se reanuda
					isSuspendidoHiloHyT = false;// el hilo ya no esta suspendido
				}
				btnDesconectarSensorHT.setEnabled(false);
				btnExportarDatosHyTAPDF.setEnabled(true);
				btnExportarDatosHyTAExcel.setEnabled(true);
				btnExportarDatosHyTATexto.setEnabled(true);
				btnPintarGraficaSensor1.setEnabled(true);
				btnGraficaHumSensor.setEnabled(true);
				btnPintarGraficaSensor2.setEnabled(true);
			}
		});
		btnDesconectarSensorHT = new JButton("");
		btnDesconectarSensorHT.setToolTipText("Desconectar");
		btnDesconectarSensorHT.setEnabled(false);
		try {
			Image imagenFlechaAbajo2 = ImageIO.read(new File("imagenes/FlechaAbajo2.png"));
			btnDesconectarSensorHT.setIcon(new ImageIcon(imagenFlechaAbajo2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnDesconectarSensorHT.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				temporizadorSensor1Temperatura.stop();
				temporizadorSensor2Temperatura.stop();
				temporizadorSensor3Humedad.stop();
				if (varComunicacionSerialHyT.isConectadoPuertoSerialSensorHyT()) {
					varComunicacionSerialHyT.desconectarComponentePuertoSerial();
					varComunicacionSerialHyT.humedadTemperaturaDTO.setTemperaturaSensor1(ConstantesPantallaPrincipal.VALOR_CERO_FLOAT_DEFECTO_FLOAT);
					varComunicacionSerialHyT.humedadTemperaturaDTO.setTemperaturaSensor2(ConstantesPantallaPrincipal.VALOR_CERO_FLOAT_DEFECTO_FLOAT);
					varComunicacionSerialHyT.humedadTemperaturaDTO.setHumedadSensor3(ConstantesPantallaPrincipal.VALOR_CERO_FLOAT_DEFECTO_FLOAT);
				}
				hiloCronometroHyT.suspend();
				dsHyT = 0;
				segHyT = 0;
				minHyT = 0;
				horaHyT = 0;
				lblTimeOperacionHumTemp.setText("0:0:0:0");// cronometro en cero
				isSuspendidoHiloHyT = true;// el hilo esta suspendido
			}
		});
		lblTasBaudiosSensorHT = new JLabel("Tasa de Baudios: ");
		cmbListaTasasBaudiosSensorHT = new JComboBox<String>();
		lblBitsDatosSensorHT = new JLabel("Bit de Datos:");
		cmbListaBitsDatosSensorHT = new JComboBox<String>();
		lblBitsParadaSensorHT = new JLabel("Bit de Parada:");
		cmbListaBitsParadaSensorHT = new JComboBox<String>();
		lblParidadSensorHT = new JLabel("Paridad: ");
		cmbListaParidadSensorHT = new JComboBox<String>();
		lblHoraCapturaDatosTmpHum = new JLabel("Tmp. Operaci\u00F3n:");
		lblTimeOperacionHumTemp = new JLabel();
		lblTimeOperacionHumTemp.setText("0:0:0:0");
		glPanelDatosConexionPuertoSerialTempHum = new GroupLayout(pnlDatosConexionPuertoSerialTempHum);
		glPanelDatosConexionPuertoSerialTempHum.setHorizontalGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addContainerGap().addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.TRAILING, false).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addComponent(btnValidarPuertoSensorHT).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnConectarSensorHT).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDesconectarSensorHT).addContainerGap()).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.TRAILING, false).addComponent(lblTasBaudiosSensorHT).addComponent(lblNomPuertoSensorHT, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblBitsDatosSensorHT, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE).addComponent(lblHoraCapturaDatosTmpHum, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addComponent(cmbListaBitsDatosSensorHT, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addComponent(cmbListaPuertosSensorHT, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(lblBitsParadaSensorHT, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addComponent(cmbListaTasasBaudiosSensorHT, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(lblParidadSensorHT))).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING, false).addComponent(cmbListaParidadSensorHT, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbListaBitsParadaSensorHT, 0, 104, Short.MAX_VALUE))).addComponent(lblTimeOperacionHumTemp, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)).addGap(150)))));
		glPanelDatosConexionPuertoSerialTempHum.setVerticalGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addContainerGap().addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addGap(3).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaPuertosSensorHT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblNomPuertoSensorHT))).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.BASELINE).addComponent(lblBitsParadaSensorHT).addComponent(cmbListaBitsParadaSensorHT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cmbListaParidadSensorHT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosConexionPuertoSerialTempHum.createSequentialGroup().addGap(45).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaTasasBaudiosSensorHT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTasBaudiosSensorHT).addComponent(lblParidadSensorHT)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaBitsDatosSensorHT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblBitsDatosSensorHT)))).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.BASELINE).addComponent(lblHoraCapturaDatosTmpHum).addComponent(lblTimeOperacionHumTemp)).addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE).addGroup(glPanelDatosConexionPuertoSerialTempHum.createParallelGroup(Alignment.LEADING).addComponent(btnDesconectarSensorHT, Alignment.TRAILING).addComponent(btnConectarSensorHT, Alignment.TRAILING).addComponent(btnValidarPuertoSensorHT, Alignment.TRAILING)).addContainerGap()));
		pnlDatosConexionPuertoSerialTempHum.setLayout(glPanelDatosConexionPuertoSerialTempHum);
		pnlResultadosSensores = new JPanel();
		pnlResultadosSensores.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultados Obtenidos - Humedad y Temperatura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblTemperaturaSensor1 = new JLabel("Temperatura - Sensor 1 (°C): ");
		campoTextoTemperaturaSensor1 = new JTextField();
		campoTextoTemperaturaSensor1.setEnabled(false);
		campoTextoTemperaturaSensor1.setEditable(false);
		campoTextoTemperaturaSensor1.setColumns(10);
		// Evento para disparar la gráfica del sensor 1 de humedad.
		varComunicacionSerialHyT.verificarPuertoSerial();
		// Configuración de la Gráfica para el Sensor 1.
		configuracionGraficaDinamicaSensor1();
		lblDesconexionPuertoSerialSensorHumTemp = new JLabel("Desconectar Puerto Serial para el Sensores de Humedad y Temperatura.");
		lblTemperaturaSensor2 = new JLabel("Temperatura - Sensor 2 (°C): ");
		campoTextoTemperaturaSensor2 = new JTextField();
		campoTextoTemperaturaSensor2.setEnabled(false);
		campoTextoTemperaturaSensor2.setEditable(false);
		campoTextoTemperaturaSensor2.setColumns(10);
		// Configuración de la gráfica para el Sensor 2.
		configuracionGraficaDinamicaSensor2();
		lblHumedadSensor3 = new JLabel("Humedad - Sensor 3 (%): ");
		campoTextoHumedadSensor3 = new JTextField();
		campoTextoHumedadSensor3.setEnabled(false);
		campoTextoHumedadSensor3.setEditable(false);
		campoTextoHumedadSensor3.setColumns(10);
		// Configuración de la Gráfica para el Sensor 3.
		configuracionGraficaDinamicaSensor3();
		lblImagenHumedad = new JLabel("");
		try {
			Image imagenHumedad = ImageIO.read(new File("imagenes/humedad.png"));
			lblImagenHumedad.setIcon(new ImageIcon(imagenHumedad));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		JLabel lblImagenTemperatura = new JLabel("");
		try {
			Image imagenTemperatura = ImageIO.read(new File("imagenes/temperatura.png"));
			lblImagenTemperatura.setIcon(new ImageIcon(imagenTemperatura));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		pnlTermometroTemperaturaTermometro1 = new JPanel();
		pnlTermometroTemperaturaTermometro1 = crearPanelTermometroTemperatura1();
		pnlTermometroTemperaturaTermometro2 = new JPanel();
		pnlTermometroTemperaturaTermometro2 = crearPanelTermometroTemperatura2();
		glPanelResultadosSensores = new GroupLayout(pnlResultadosSensores);
		glPanelResultadosSensores.setHorizontalGroup(glPanelResultadosSensores.createParallelGroup(Alignment.TRAILING).addGroup(glPanelResultadosSensores.createSequentialGroup().addContainerGap().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosSensores.createSequentialGroup().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addComponent(lblImagenTemperatura).addComponent(lblImagenHumedad)).addGap(22).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING, false).addComponent(lblHumedadSensor3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblTemperaturaSensor2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblTemperaturaSensor1, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING, false).addGroup(glPanelResultadosSensores.createSequentialGroup().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTemperaturaSensor2).addComponent(campoTextoTemperaturaSensor1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING, false).addComponent(btnGraficaHumSensor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnPintarGraficaSensor1, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))).addGroup(glPanelResultadosSensores.createSequentialGroup().addComponent(campoTextoHumedadSensor3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnPintarGraficaSensor2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chartPanelTermometroTemperaturaTermometro1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chartPanelTermometroTemperaturaTermometro2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)).addComponent(lblDesconexionPuertoSerialSensorHumTemp)).addContainerGap(122, Short.MAX_VALUE)));
		glPanelResultadosSensores.setVerticalGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosSensores.createSequentialGroup().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosSensores.createSequentialGroup().addContainerGap().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addComponent(lblImagenTemperatura).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.BASELINE).addComponent(lblTemperaturaSensor1).addComponent(campoTextoTemperaturaSensor1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosSensores.createSequentialGroup().addGap(24).addComponent(lblImagenHumedad)).addGroup(glPanelResultadosSensores.createSequentialGroup().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.BASELINE).addComponent(lblTemperaturaSensor2).addComponent(campoTextoTemperaturaSensor2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.BASELINE).addComponent(lblHumedadSensor3).addComponent(campoTextoHumedadSensor3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))).addGap(29)).addGroup(glPanelResultadosSensores.createSequentialGroup().addGroup(glPanelResultadosSensores.createParallelGroup(Alignment.LEADING).addComponent(chartPanelTermometroTemperaturaTermometro2, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addComponent(chartPanelTermometroTemperaturaTermometro1, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addGroup(glPanelResultadosSensores.createSequentialGroup().addComponent(btnPintarGraficaSensor1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnGraficaHumSensor, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnPintarGraficaSensor2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))).addGap(18))).addGap(13).addComponent(lblDesconexionPuertoSerialSensorHumTemp).addContainerGap()));
		pnlResultadosSensores.setLayout(glPanelResultadosSensores);
		JPanel pnlGraficosTmpHum = new JPanel();
		pnlGraficosTmpHum = crearPanelTemperaturaSensor1();
		JPanel pnlGraficosTmpHum2 = new JPanel();
		pnlGraficosTmpHum2 = crearPanelTemperaturaSensor2();
		JPanel pnlGraficosTmpHum3 = new JPanel();
		pnlGraficosTmpHum3 = crearPanelHumedadSensor3();
		pnlGraficosTmpHum2.setPreferredSize(new Dimension(75, 75));
		GroupLayout gl_chartPanelSensorTemperatura2 = new GroupLayout(pnlGraficosTmpHum2);
		gl_chartPanelSensorTemperatura2.setHorizontalGroup(gl_chartPanelSensorTemperatura2.createParallelGroup(Alignment.LEADING).addGap(0, 231, Short.MAX_VALUE).addGap(0, 231, Short.MAX_VALUE));
		gl_chartPanelSensorTemperatura2.setVerticalGroup(gl_chartPanelSensorTemperatura2.createParallelGroup(Alignment.LEADING).addGap(0, 227, Short.MAX_VALUE).addGap(0, 227, Short.MAX_VALUE));
		pnlGraficosTmpHum2.setLayout(gl_chartPanelSensorTemperatura2);
		pnlGraficosTmpHum3.setPreferredSize(new Dimension(75, 75));
		GroupLayout gl_pnlGraficosTmpHum3 = new GroupLayout(pnlGraficosTmpHum3);
		gl_pnlGraficosTmpHum3.setHorizontalGroup(gl_pnlGraficosTmpHum3.createParallelGroup(Alignment.LEADING).addGap(0, 207, Short.MAX_VALUE).addGap(0, 231, Short.MAX_VALUE));
		gl_pnlGraficosTmpHum3.setVerticalGroup(gl_pnlGraficosTmpHum3.createParallelGroup(Alignment.LEADING).addGap(0, 227, Short.MAX_VALUE).addGap(0, 227, Short.MAX_VALUE));
		pnlGraficosTmpHum3.setLayout(gl_pnlGraficosTmpHum3);
		glPanelVisualTemperatura = new GroupLayout(panelVisualTemperatura);
		glPanelVisualTemperatura.setHorizontalGroup(glPanelVisualTemperatura.createParallelGroup(Alignment.LEADING).addGroup(glPanelVisualTemperatura.createSequentialGroup().addContainerGap().addGroup(glPanelVisualTemperatura.createParallelGroup(Alignment.LEADING).addComponent(pnlResultadosSensores, GroupLayout.PREFERRED_SIZE, 644, Short.MAX_VALUE).addGroup(glPanelVisualTemperatura.createSequentialGroup().addComponent(chartPanelSensorTemperatura1, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chartPanelSensorTemperatura2, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chartPanelSensorHumedad, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)).addComponent(pnlDatosConexionPuertoSerialTempHum, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)).addContainerGap()));
		glPanelVisualTemperatura.setVerticalGroup(glPanelVisualTemperatura.createParallelGroup(Alignment.TRAILING).addGroup(glPanelVisualTemperatura.createSequentialGroup().addContainerGap().addComponent(pnlDatosConexionPuertoSerialTempHum, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(pnlResultadosSensores, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelVisualTemperatura.createParallelGroup(Alignment.LEADING, false).addComponent(chartPanelSensorTemperatura2, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE).addComponent(chartPanelSensorHumedad, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE).addComponent(chartPanelSensorTemperatura1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		GroupLayout gl_chartPanelSensorTemperatura1 = new GroupLayout(pnlGraficosTmpHum);
		gl_chartPanelSensorTemperatura1.setHorizontalGroup(gl_chartPanelSensorTemperatura1.createParallelGroup(Alignment.LEADING).addGap(0, 624, Short.MAX_VALUE));
		gl_chartPanelSensorTemperatura1.setVerticalGroup(gl_chartPanelSensorTemperatura1.createParallelGroup(Alignment.LEADING).addGap(0, 204, Short.MAX_VALUE));
		pnlGraficosTmpHum.setLayout(gl_chartPanelSensorTemperatura1);
		panelVisualTemperatura.setLayout(glPanelVisualTemperatura);
		panelDatosTablaTemperatura = new JPanel();
		panelPestanasPrograma.addTab("Datos Temp - Hum", null, panelDatosTablaTemperatura, null);
		pnlDespTablaTempHum = new JScrollPane();
		btnExportarDatosHyTAExcel = new JButton("");
		btnExportarDatosHyTAExcel.setToolTipText("Exportar a Excel");
		try {
			Image imagenExcel = ImageIO.read(new File("imagenes/excel.png"));
			btnExportarDatosHyTAExcel.setIcon(new ImageIcon(imagenExcel));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosHyTAExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FuncionesUtilitarias funcionesUtilitarias = new FuncionesUtilitarias();
				JFileChooser selectorArchivo = new JFileChooser();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteExcelHumedadTemperaturaSensores generadorReporteExcel = new GeneradorReporteExcelHumedadTemperaturaSensores();
						generadorReporteExcel.generarReporteEnExcel(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls", modeloTHT, tblDatosHumedadTemperatura);
						funcionesUtilitarias.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls");
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		btnExportarDatosHyTAPDF = new JButton("");
		btnExportarDatosHyTAPDF.setToolTipText("Exportar a PDF");
		try {
			Image imagenPDF = ImageIO.read(new File("imagenes/acrobat.png"));
			btnExportarDatosHyTAPDF.setIcon(new ImageIcon(imagenPDF));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosHyTAPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilidades = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReportePDFHumedadTemperaturaSensores generadorReportePDF = new GeneradorReportePDFHumedadTemperaturaSensores();
						generadorReportePDF.generarReportePDF(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF, tblDatosHumedadTemperatura);
						utilidades.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		JPanel pnlEstadisticasTablaTemperaturaS1 = new JPanel();
		pnlEstadisticasTablaTemperaturaS1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Temperatura Sensor 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblTotalTemperaturaS1 = new JLabel("Total:");
		JLabel lblPromedioTemperaturaS1 = new JLabel("Promedio:");
		JLabel lblMinimoTemperaturaS1 = new JLabel("M\u00EDnimo: ");
		JLabel lblMaximoTemperaturaS1 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalTemperaturaS1 = new JTextField();
		campoTextoTotalTemperaturaS1.setEnabled(false);
		campoTextoTotalTemperaturaS1.setEditable(false);
		campoTextoTotalTemperaturaS1.setColumns(10);
		campoTextoPromedioTemperaturaS1 = new JTextField();
		campoTextoPromedioTemperaturaS1.setEnabled(false);
		campoTextoPromedioTemperaturaS1.setEditable(false);
		campoTextoPromedioTemperaturaS1.setColumns(10);
		campoTextoMinimoTemperaturaS1 = new JTextField();
		campoTextoMinimoTemperaturaS1.setEnabled(false);
		campoTextoMinimoTemperaturaS1.setEditable(false);
		campoTextoMinimoTemperaturaS1.setColumns(10);
		campoTextoMaximoTemperaturaS1 = new JTextField();
		campoTextoMaximoTemperaturaS1.setEnabled(false);
		campoTextoMaximoTemperaturaS1.setEditable(false);
		campoTextoMaximoTemperaturaS1.setColumns(10);
		JLabel lblMedianaTemperaturaS1 = new JLabel("Mediana:");
		JLabel lblModaTemperaturaS1 = new JLabel("Moda:");
		tfModaTemperaturaS1 = new JTextField();
		tfModaTemperaturaS1.setEnabled(false);
		tfModaTemperaturaS1.setEditable(false);
		tfModaTemperaturaS1.setColumns(10);
		tfMedianaTemperaturaS1 = new JTextField();
		tfMedianaTemperaturaS1.setEnabled(false);
		tfMedianaTemperaturaS1.setEditable(false);
		tfMedianaTemperaturaS1.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaTemperaturaS1 = new GroupLayout(pnlEstadisticasTablaTemperaturaS1);
		gl_pnlEstadisticasTablaTemperaturaS1.setHorizontalGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalTemperaturaS1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioTemperaturaS1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoTemperaturaS1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoTemperaturaS1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalTemperaturaS1).addComponent(campoTextoPromedioTemperaturaS1).addComponent(campoTextoMinimoTemperaturaS1).addComponent(campoTextoMaximoTemperaturaS1, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaTemperaturaS1).addComponent(lblModaTemperaturaS1)).addGap(18).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING).addComponent(tfModaTemperaturaS1, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(tfMedianaTemperaturaS1, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaTemperaturaS1.setVerticalGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoTemperaturaS1).addComponent(campoTextoMaximoTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoTemperaturaS1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioTemperaturaS1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalTemperaturaS1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaTemperaturaS1).addComponent(tfMedianaTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS1.createParallelGroup(Alignment.BASELINE).addComponent(lblModaTemperaturaS1).addComponent(tfModaTemperaturaS1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaTemperaturaS1.setLayout(gl_pnlEstadisticasTablaTemperaturaS1);
		JPanel pnlEstadisticasTablaTemperaturaS2 = new JPanel();
		pnlEstadisticasTablaTemperaturaS2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Temperatura Sensor 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblTotalTemperaturaS2 = new JLabel("Total:");
		JLabel lblPromedioTemperaturaS2 = new JLabel("Promedio:");
		JLabel lblMinimoTemperaturaS2 = new JLabel("M\u00EDnimo: ");
		JLabel lblMaximoTemperaturaS2 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalTemperaturaS2 = new JTextField();
		campoTextoTotalTemperaturaS2.setEnabled(false);
		campoTextoTotalTemperaturaS2.setEditable(false);
		campoTextoTotalTemperaturaS2.setColumns(10);
		campoTextoPromedioTemperaturaS2 = new JTextField();
		campoTextoPromedioTemperaturaS2.setEnabled(false);
		campoTextoPromedioTemperaturaS2.setEditable(false);
		campoTextoPromedioTemperaturaS2.setColumns(10);
		campoTextoMinimoTemperaturaS2 = new JTextField();
		campoTextoMinimoTemperaturaS2.setEnabled(false);
		campoTextoMinimoTemperaturaS2.setEditable(false);
		campoTextoMinimoTemperaturaS2.setColumns(10);
		campoTextoMaximoTemperaturaS2 = new JTextField();
		campoTextoMaximoTemperaturaS2.setEnabled(false);
		campoTextoMaximoTemperaturaS2.setEditable(false);
		campoTextoMaximoTemperaturaS2.setColumns(10);
		JLabel lblMedianaTemperaturaS2 = new JLabel("Mediana:");
		JLabel lblModaTemperaturaS2 = new JLabel("Moda:");
		campoTextoModaTemperaturaS2 = new JTextField();
		campoTextoModaTemperaturaS2.setEnabled(false);
		campoTextoModaTemperaturaS2.setEditable(false);
		campoTextoModaTemperaturaS2.setColumns(10);
		campoTextoMedianaTemperaturaS2 = new JTextField();
		campoTextoMedianaTemperaturaS2.setEnabled(false);
		campoTextoMedianaTemperaturaS2.setEditable(false);
		campoTextoMedianaTemperaturaS2.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaTemperaturaS2 = new GroupLayout(pnlEstadisticasTablaTemperaturaS2);
		gl_pnlEstadisticasTablaTemperaturaS2.setHorizontalGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalTemperaturaS2, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioTemperaturaS2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoTemperaturaS2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoTemperaturaS2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalTemperaturaS2).addComponent(campoTextoPromedioTemperaturaS2).addComponent(campoTextoMinimoTemperaturaS2).addComponent(campoTextoMaximoTemperaturaS2, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaTemperaturaS2).addComponent(lblModaTemperaturaS2)).addGap(18).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaTemperaturaS2, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaTemperaturaS2, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaTemperaturaS2.setVerticalGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoTemperaturaS2).addComponent(campoTextoMaximoTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoTemperaturaS2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioTemperaturaS2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalTemperaturaS2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaTemperaturaS2).addComponent(campoTextoMedianaTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaS2.createParallelGroup(Alignment.BASELINE).addComponent(lblModaTemperaturaS2).addComponent(campoTextoModaTemperaturaS2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaTemperaturaS2.setLayout(gl_pnlEstadisticasTablaTemperaturaS2);
		pnlEstadisticasTablaHumedadS3 = new JPanel();
		pnlEstadisticasTablaHumedadS3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Humedad Sensor 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblTotalHumedadS3 = new JLabel("Total:");
		lblPromedioHumedadS3 = new JLabel("Promedio:");
		lblMinimoHumedadS3 = new JLabel("M\u00EDnimo: ");
		lblMaximoHumedadS3 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalHumedadS3 = new JTextField();
		campoTextoTotalHumedadS3.setEnabled(false);
		campoTextoTotalHumedadS3.setEditable(false);
		campoTextoTotalHumedadS3.setColumns(10);
		campoTextoPromedioHumedadS3 = new JTextField();
		campoTextoPromedioHumedadS3.setEnabled(false);
		campoTextoPromedioHumedadS3.setEditable(false);
		campoTextoPromedioHumedadS3.setColumns(10);
		campoTextoMinimoHumedadS3 = new JTextField();
		campoTextoMinimoHumedadS3.setEnabled(false);
		campoTextoMinimoHumedadS3.setEditable(false);
		campoTextoMinimoHumedadS3.setColumns(10);
		campoTextoMaximoHumedadS3 = new JTextField();
		campoTextoMaximoHumedadS3.setEnabled(false);
		campoTextoMaximoHumedadS3.setEditable(false);
		campoTextoMaximoHumedadS3.setColumns(10);
		lblMedianaHumedadS3 = new JLabel("Mediana:");
		lblModaHumedadS3 = new JLabel("Moda:");
		campoTextoModaHumedadS3 = new JTextField();
		campoTextoModaHumedadS3.setEnabled(false);
		campoTextoModaHumedadS3.setEditable(false);
		campoTextoModaHumedadS3.setColumns(10);
		campoTextoMedianaHumedadS3 = new JTextField();
		campoTextoMedianaHumedadS3.setEnabled(false);
		campoTextoMedianaHumedadS3.setEditable(false);
		campoTextoMedianaHumedadS3.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaHumedadS3 = new GroupLayout(pnlEstadisticasTablaHumedadS3);
		gl_pnlEstadisticasTablaHumedadS3.setHorizontalGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaHumedadS3.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaHumedadS3.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalHumedadS3, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioHumedadS3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoHumedadS3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoHumedadS3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalHumedadS3).addComponent(campoTextoPromedioHumedadS3).addComponent(campoTextoMinimoHumedadS3).addComponent(campoTextoMaximoHumedadS3, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaHumedadS3.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaHumedadS3).addComponent(lblModaHumedadS3)).addGap(18).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaHumedadS3, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaHumedadS3, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaHumedadS3.setVerticalGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaHumedadS3.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoHumedadS3).addComponent(campoTextoMaximoHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoHumedadS3)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioHumedadS3)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalHumedadS3)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaHumedadS3).addComponent(campoTextoMedianaHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaHumedadS3.createParallelGroup(Alignment.BASELINE).addComponent(lblModaHumedadS3).addComponent(campoTextoModaHumedadS3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaHumedadS3.setLayout(gl_pnlEstadisticasTablaHumedadS3);
		btnExportarDatosHyTATexto = new JButton("");
		btnExportarDatosHyTATexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteTextoTemperaturaSensores generadorReporteTXTTemperaturaSensores = new GeneradorReporteTextoTemperaturaSensores();
						generadorReporteTXTTemperaturaSensores.generarReporteArhivoTexto(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_TXT, tblDatosHumedadTemperatura);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		try {
			Image imagenArchivoTXT = ImageIO.read(new File("imagenes/archivo.png"));
			btnExportarDatosHyTATexto.setIcon(new ImageIcon(imagenArchivoTXT));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosHyTATexto.setToolTipText("Exportar a Archivo de Texto");
		JLabel lblRegBusTblHyT = new JLabel("Registro a buscar:");
		tfRegBusTblHyT = new JTextField();
		tfRegBusTblHyT.setColumns(10);
		tfRegBusTblHyT.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = tfRegBusTblHyT.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaHyT.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaHyT.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = tfRegBusTblHyT.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaHyT.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaHyT.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
			}
		});
		JLabel lblNumRegTblHyT = new JLabel("No. Registros: ");
		campoTextolNumRegTblHyT = new JTextField();
		campoTextolNumRegTblHyT.setEnabled(false);
		campoTextolNumRegTblHyT.setEditable(false);
		campoTextolNumRegTblHyT.setColumns(10);
		JButton btnExportarDatosHyTACSV = new JButton("");
		try {
			Image imagenArchivoCSV = ImageIO.read(new File("imagenes/csv.png"));
			btnExportarDatosHyTACSV.setIcon(new ImageIcon(imagenArchivoCSV));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosHyTACSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarias = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteCSVTemperaturaSensores generadorReporteCSVSensores = new GeneradorReporteCSVTemperaturaSensores();
						generadorReporteCSVSensores.generarReporteArchivoCSV(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_CSV, tblDatosHumedadTemperatura);
						utilitarias.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_CSV);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		btnExportarDatosHyTACSV.setToolTipText("Exportar a Archivo CSV");
		glPanelDatosTablaTemperatura = new GroupLayout(panelDatosTablaTemperatura);
		glPanelDatosTablaTemperatura.setHorizontalGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addGap(47).addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.TRAILING, false).addComponent(pnlDespTablaTempHum, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING).addComponent(pnlEstadisticasTablaHumedadS3, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING, false).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addComponent(lblRegBusTblHyT, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tfRegBusTblHyT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addComponent(lblNumRegTblHyT, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(campoTextolNumRegTblHyT))).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExportarDatosHyTACSV).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExportarDatosHyTAPDF, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExportarDatosHyTAExcel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExportarDatosHyTATexto, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(pnlEstadisticasTablaTemperaturaS1, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(pnlEstadisticasTablaTemperaturaS2, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED))).addContainerGap(47, Short.MAX_VALUE)));
		glPanelDatosTablaTemperatura.setVerticalGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addGap(41).addComponent(pnlDespTablaTempHum, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaTemperatura.createSequentialGroup().addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.BASELINE).addComponent(lblRegBusTblHyT, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(tfRegBusTblHyT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.LEADING).addComponent(lblNumRegTblHyT).addComponent(campoTextolNumRegTblHyT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18)).addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.TRAILING, false).addComponent(btnExportarDatosHyTACSV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosHyTAPDF, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosHyTATexto, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosHyTAExcel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))).addGap(15).addGroup(glPanelDatosTablaTemperatura.createParallelGroup(Alignment.BASELINE).addComponent(pnlEstadisticasTablaTemperaturaS1, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE).addComponent(pnlEstadisticasTablaTemperaturaS2, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(pnlEstadisticasTablaHumedadS3, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		tblDatosHumedadTemperatura = new JTable();
		tblDatosHumedadTemperatura.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Hora de Captura", "Temperatura - Sensor 1 (�C)", "Temperatura - Sensor 2 (�C)", "Humedad - Sensor 3 (%)" }));
		tblDatosHumedadTemperatura.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				tblDatosHumedadTemperatura.scrollRectToVisible(tblDatosHumedadTemperatura.getCellRect(tblDatosHumedadTemperatura.getRowCount() - 1, 0, true));
			}
		});
		pnlDespTablaTempHum.setViewportView(tblDatosHumedadTemperatura);
		modeloTHT = (DefaultTableModel) tblDatosHumedadTemperatura.getModel();
		panelDatosTablaTemperatura.setLayout(glPanelDatosTablaTemperatura);
		panelVisualPeso = new JPanel();
		panelPestanasPrograma.addTab("Peso", null, panelVisualPeso, null);
		pnlDatosConexionPuertoSerialPeso = new JPanel();
		pnlDatosConexionPuertoSerialPeso.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos de Entrada - Balanza por Puerto Serial", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblNomPuertoBalanza = new JLabel("Nombre del Puerto: ");
		lblNomPuertoBalanza.setVerticalAlignment(SwingConstants.TOP);
		cmbListaPuertosBalanza = new JComboBox<String>();
		btnValidarPuertoBalanza = new JButton("");
		btnValidarPuertoBalanza.setToolTipText("Validar");
		try {
			Image imagenRS232 = ImageIO.read(new File("imagenes/RS232.png"));
			btnValidarPuertoBalanza.setIcon(new ImageIcon(imagenRS232));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnValidarPuertoBalanza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialBalanza.verificarPuertoSerial();
			}
		});
		btnConectarBalanza = new JButton("");
		btnConectarBalanza.setToolTipText("Conectar");
		try {
			Image imagenFlechaArriba2 = ImageIO.read(new File("imagenes/FlechaArriba2.png"));
			btnConectarBalanza.setIcon(new ImageIcon(imagenFlechaArriba2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnConectarBalanza.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialBalanza.conectarComponentePuertoSerial();
				if (varComunicacionSerialBalanza.isConectadoPuertoSerialBalanza() && varComunicacionSerialBalanza.inicializarFujoEntradaYSalida()) {
					varComunicacionSerialBalanza.inicializarOyente();
				}
				if (!isSuspendidoHiloPeso) {
					hiloCronometroPeso.start();
				}
				else {// de lo contrario
					hiloCronometroPeso.resume();
					isSuspendidoHiloPeso = false;
				}
				btnConectarBalanza.setEnabled(false);
				btnDesconectarBalanza.setEnabled(true);
				btnGraficaPesoDe.setEnabled(true);
				btnExportarDatosDe.setEnabled(true);
				btnExportarDatosBalanzaAPDF.setEnabled(true);
				btnExportarDatosBalanzaATexto.setEnabled(true);
			}
		});
		btnDesconectarBalanza = new JButton("");
		btnDesconectarBalanza.setToolTipText("Desconectar");
		try {
			Image imagenFlechaAbajo2 = ImageIO.read(new File("imagenes/FlechaAbajo2.png"));
			btnDesconectarBalanza.setIcon(new ImageIcon(imagenFlechaAbajo2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnDesconectarBalanza.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				temporizadorBalanza.stop();
				if (varComunicacionSerialBalanza.isConectadoPuertoSerialBalanza()) {
					varComunicacionSerialBalanza.desconectarComponentePuertoSerial();
					varComunicacionSerialBalanza.pesoDTO.setPeso(0);
					// serieTiempoPesoBalanza.clear();
					modeloBalanza.setRowCount(0);
				}
				hiloCronometroPeso.suspend();
				dsPeso = 0;
				segPeso = 0;
				minPeso = 0;
				horaPeso = 0;
				lblTimeOperacionPeso.setText("0:0:0:0");
				isSuspendidoHiloPeso = true;
				btnConectarBalanza.setEnabled(true);
				btnDesconectarBalanza.setEnabled(false);
				btnGraficaPesoDe.setEnabled(false);
				btnExportarDatosDe.setEnabled(false);
				btnExportarDatosBalanzaAPDF.setEnabled(false);
				btnExportarDatosBalanzaATexto.setEnabled(false);
			}
		});
		JLabel lblTasBaudiosBalanza = new JLabel("Tasa de Baudios:");
		cmbListaTasasBaudiosBalanza = new JComboBox<String>();
		lblBitsDatosBalanza = new JLabel("Bit de Datos:");
		cmbListaBitsDatosBalanza = new JComboBox<String>();
		JLabel lblBitsParadaBalanza = new JLabel("Bit de Parada:");
		JLabel lblParidadBalanza = new JLabel("Paridad:");
		cmbListaBitsParadaBalanza = new JComboBox<String>();
		cmbListaParidadBalanza = new JComboBox<String>();
		lblHoraCapturaDatosPeso = new JLabel("Tmp. Operaci\u00F3n:");
		lblTimeOperacionPeso = new JLabel("");
		lblTimeOperacionPeso.setText("0:0:0:0");
		glPanelDatosConexionPuertoSerialPeso = new GroupLayout(pnlDatosConexionPuertoSerialPeso);
		glPanelDatosConexionPuertoSerialPeso.setHorizontalGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addGap(75).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.TRAILING, false).addComponent(lblHoraCapturaDatosPeso, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(Alignment.LEADING, glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.TRAILING, false).addComponent(lblTasBaudiosBalanza, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblNomPuertoBalanza, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addComponent(lblBitsDatosBalanza, Alignment.LEADING)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING, false).addComponent(cmbListaBitsDatosBalanza, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbListaTasasBaudiosBalanza, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbListaPuertosBalanza, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)).addGap(36).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addComponent(lblBitsParadaBalanza).addComponent(lblParidadBalanza)).addGap(18).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addComponent(btnValidarPuertoBalanza).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnConectarBalanza).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDesconectarBalanza)).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING, false).addComponent(cmbListaParidadBalanza, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(cmbListaBitsParadaBalanza, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))).addComponent(lblTimeOperacionPeso, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)).addContainerGap(119, Short.MAX_VALUE)));
		glPanelDatosConexionPuertoSerialPeso.setVerticalGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addContainerGap().addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblNomPuertoBalanza).addComponent(cmbListaPuertosBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblTasBaudiosBalanza).addComponent(cmbListaTasasBaudiosBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblBitsDatosBalanza).addComponent(cmbListaBitsDatosBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGroup(glPanelDatosConexionPuertoSerialPeso.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaBitsParadaBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblBitsParadaBalanza)).addGap(18).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblParidadBalanza).addComponent(cmbListaParidadBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))).addGap(18).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblHoraCapturaDatosPeso).addComponent(lblTimeOperacionPeso)).addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialPeso.createParallelGroup(Alignment.BASELINE).addComponent(btnValidarPuertoBalanza).addComponent(btnConectarBalanza)).addComponent(btnDesconectarBalanza)).addContainerGap()));
		pnlDatosConexionPuertoSerialPeso.setLayout(glPanelDatosConexionPuertoSerialPeso);
		pnlResultadosBalanza = new JPanel();
		pnlResultadosBalanza.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultados Obtenidos - Peso de la Balanza", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblDesconectarPuertoSerialBalanza = new JLabel("Desconectar Puerto Serial para la Balanza.");
		lblPesokg = new JLabel("Peso (Kg): ");
		campoTextoPesoBalanza = new JTextField();
		campoTextoPesoBalanza.setEnabled(false);
		campoTextoPesoBalanza.setEditable(false);
		campoTextoPesoBalanza.setColumns(10);
		// Evento para disparar la gráfica del sensor 1 de humedad.
		varComunicacionSerialBalanza.verificarPuertoSerial();
		// Configuraci�n de la gráfica para la balanza.
		graficaDinamicaBalanza = new GraficaDinamicaBalanza("Ver gráfica de Comportamiento para la Balanza (kilogramos)");
		graficaDinamicaBalanza.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaBalanza);
		graficaDinamicaBalanza.setVisible(false);
		ActionListener oyenteBalanza = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaBalanza.obtenerDatos(varComunicacionSerialBalanza.pesoDTO.getPeso());
			}
		};
		temporizadorBalanza = new Timer(DELAY_TEMPORIZADORES, oyenteBalanza);
		btnGraficaPesoDe = new JButton("");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnGraficaPesoDe.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnGraficaPesoDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaBalanza.setVisible(true);
				graficaDinamicaBalanza.obtenerDatos(0.0);
				temporizadorBalanza.start();
			}
		});
		JPanel pnlGraficosPesoBalanza = new JPanel();
		pnlGraficosPesoBalanza = crearPanelPesoBalanza();
		pnlGraficosPesoBalanza.setPreferredSize(new Dimension(75, 75));
		JLabel lblImgBalanza = new JLabel("");
		try {
			Image imagenBalanza = ImageIO.read(new File("imagenes/balanza.png"));
			lblImgBalanza.setIcon(new ImageIcon(imagenBalanza));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		JPanel pnlAcelerometroPesoBalanza = new JPanel();
		pnlAcelerometroPesoBalanza = crearPanelAcelerometroPesoBalanza();
		pnlAcelerometroPesoBalanza.setPreferredSize(new Dimension(75, 75));
		GroupLayout gl_pnlAcelerometroPesoBalanza = new GroupLayout(pnlAcelerometroPesoBalanza);
		gl_pnlAcelerometroPesoBalanza.setHorizontalGroup(gl_pnlAcelerometroPesoBalanza.createParallelGroup(Alignment.LEADING).addGap(0, 264, Short.MAX_VALUE).addGap(0, 612, Short.MAX_VALUE));
		gl_pnlAcelerometroPesoBalanza.setVerticalGroup(gl_pnlAcelerometroPesoBalanza.createParallelGroup(Alignment.LEADING).addGap(0, 248, Short.MAX_VALUE).addGap(0, 248, Short.MAX_VALUE));
		pnlAcelerometroPesoBalanza.setLayout(gl_pnlAcelerometroPesoBalanza);
		glPanelResultadosBalanza = new GroupLayout(pnlResultadosBalanza);
		glPanelResultadosBalanza.setHorizontalGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosBalanza.createSequentialGroup().addGap(31).addGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.LEADING).addComponent(lblDesconectarPuertoSerialBalanza).addGroup(glPanelResultadosBalanza.createSequentialGroup().addComponent(chartPanelPesoBalanza, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(chartPanelAcelerometroPesoBalanza, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelResultadosBalanza.createSequentialGroup().addComponent(lblImgBalanza).addGap(61).addComponent(lblPesokg).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(campoTextoPesoBalanza, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(btnGraficaPesoDe))).addGap(55)));
		glPanelResultadosBalanza.setVerticalGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosBalanza.createSequentialGroup().addContainerGap().addGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.TRAILING).addGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.LEADING).addGroup(glPanelResultadosBalanza.createSequentialGroup().addGap(21).addComponent(lblImgBalanza)).addGroup(glPanelResultadosBalanza.createSequentialGroup().addGap(29).addGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.BASELINE).addComponent(lblPesokg).addComponent(campoTextoPesoBalanza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))).addComponent(btnGraficaPesoDe)).addGap(43).addComponent(lblDesconectarPuertoSerialBalanza).addGap(18).addGroup(glPanelResultadosBalanza.createParallelGroup(Alignment.LEADING, false).addComponent(chartPanelPesoBalanza, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chartPanelAcelerometroPesoBalanza, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)).addContainerGap(111, Short.MAX_VALUE)));
		GroupLayout gl_pnlGraficosPesoBalanza = new GroupLayout(pnlGraficosPesoBalanza);
		gl_pnlGraficosPesoBalanza.setHorizontalGroup(gl_pnlGraficosPesoBalanza.createParallelGroup(Alignment.LEADING).addGap(0, 207, Short.MAX_VALUE));
		gl_pnlGraficosPesoBalanza.setVerticalGroup(gl_pnlGraficosPesoBalanza.createParallelGroup(Alignment.LEADING).addGap(0, 227, Short.MAX_VALUE));
		pnlGraficosPesoBalanza.setLayout(gl_pnlGraficosPesoBalanza);
		pnlResultadosBalanza.setLayout(glPanelResultadosBalanza);
		glPanelVisualPeso = new GroupLayout(panelVisualPeso);
		glPanelVisualPeso.setHorizontalGroup(glPanelVisualPeso.createParallelGroup(Alignment.TRAILING).addGroup(glPanelVisualPeso.createSequentialGroup().addGap(20).addComponent(pnlResultadosBalanza, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(Alignment.LEADING, glPanelVisualPeso.createSequentialGroup().addGap(8).addComponent(pnlDatosConexionPuertoSerialPeso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(10)));
		glPanelVisualPeso.setVerticalGroup(glPanelVisualPeso.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, glPanelVisualPeso.createSequentialGroup().addGap(5).addComponent(pnlDatosConexionPuertoSerialPeso, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE).addGap(18).addComponent(pnlResultadosBalanza, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)));
		panelVisualPeso.setLayout(glPanelVisualPeso);
		panelDatosTablaPeso = new JPanel();
		panelPestanasPrograma.addTab("Datos Peso", null, panelDatosTablaPeso, null);
		pnlDespTablaPesosBalanza = new JScrollPane();
		btnExportarDatosDe = new JButton("");
		try {
			Image imagenExcel = ImageIO.read(new File("imagenes/excel.png"));
			btnExportarDatosDe.setIcon(new ImageIcon(imagenExcel));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarios = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteExcelBalanza generadorReporteExcel = new GeneradorReporteExcelBalanza();
						generadorReporteExcel.generarReporteEnExcel(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls", modeloBalanza, tblDatosPesosBalanza);
						utilitarios.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls");
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		btnExportarDatosBalanzaAPDF = new JButton("");
		try {
			Image imagenPDF = ImageIO.read(new File("imagenes/acrobat.png"));
			btnExportarDatosBalanzaAPDF.setIcon(new ImageIcon(imagenPDF));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosBalanzaAPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarios = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReportePDFBalanza generadorReportePDFBalanza = new GeneradorReportePDFBalanza();
						generadorReportePDFBalanza.generarReportePDF(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF, tblDatosPesosBalanza);
						utilitarios.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		btnExportarDatosBalanzaATexto = new JButton("");
		try {
			Image imagenArchivoTXT = ImageIO.read(new File("imagenes/archivo.png"));
			btnExportarDatosBalanzaATexto.setIcon(new ImageIcon(imagenArchivoTXT));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosBalanzaATexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarias = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteTextoBalanza generadorReporteTXTBalanza = new GeneradorReporteTextoBalanza();
						generadorReporteTXTBalanza.generarReporteArhivoTexto(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_TXT, tblDatosPesosBalanza);
						utilitarias.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_TXT);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		JPanel pnlEstadisticasTablaPeso = new JPanel();
		pnlEstadisticasTablaPeso.setBorder(new TitledBorder(null, "Estad\u00EDsticas de Medici\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblRegBusTblPeso = new JLabel("Registro a Buscar:");
		tfRegBusTblPeso = new JTextField();
		tfRegBusTblPeso.setColumns(10);
		tfRegBusTblPeso.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = tfRegBusTblPeso.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaPeso.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaPeso.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = tfRegBusTblPeso.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaPeso.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaPeso.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});
		JLabel lblNumRegTblPeso = new JLabel("No. Registros: ");
		campoTextolNumRegTblPeso = new JTextField();
		campoTextolNumRegTblPeso.setEnabled(false);
		campoTextolNumRegTblPeso.setEditable(false);
		campoTextolNumRegTblPeso.setColumns(10);
		JLabel lblTiempoTomaDatosRegPeso = new JLabel("Tiempo Est (seg): ");
		campoTextoTiempoTomaDatosRegPeso = new JTextField();
		campoTextoTiempoTomaDatosRegPeso.setEnabled(false);
		campoTextoTiempoTomaDatosRegPeso.setEditable(false);
		campoTextoTiempoTomaDatosRegPeso.setColumns(10);
		glPanelDatosTablaPeso = new GroupLayout(panelDatosTablaPeso);
		glPanelDatosTablaPeso.setHorizontalGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaPeso.createSequentialGroup().addGap(32).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaPeso.createSequentialGroup().addComponent(lblTiempoTomaDatosRegPeso, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE).addContainerGap()).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaPeso.createSequentialGroup().addComponent(lblNumRegTblPeso, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE).addContainerGap()).addGroup(Alignment.TRAILING, glPanelDatosTablaPeso.createSequentialGroup().addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.TRAILING).addComponent(pnlEstadisticasTablaPeso, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE).addComponent(pnlDespTablaPesosBalanza, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE).addGroup(Alignment.LEADING, glPanelDatosTablaPeso.createSequentialGroup().addComponent(lblRegBusTblPeso).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addComponent(campoTextolNumRegTblPeso, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE).addGroup(glPanelDatosTablaPeso.createSequentialGroup().addComponent(tfRegBusTblPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(119).addComponent(btnExportarDatosDe, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(btnExportarDatosBalanzaAPDF, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE).addGap(18).addComponent(btnExportarDatosBalanzaATexto, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)).addComponent(campoTextoTiempoTomaDatosRegPeso, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))).addGap(66))))));
		glPanelDatosTablaPeso.setVerticalGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaPeso.createSequentialGroup().addGap(31).addComponent(pnlDespTablaPesosBalanza, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE).addGap(18).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.LEADING, false).addComponent(btnExportarDatosBalanzaATexto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosBalanzaAPDF, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosDe, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblRegBusTblPeso).addComponent(tfRegBusTblPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblNumRegTblPeso).addComponent(campoTextolNumRegTblPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(glPanelDatosTablaPeso.createParallelGroup(Alignment.TRAILING).addComponent(lblTiempoTomaDatosRegPeso).addComponent(campoTextoTiempoTomaDatosRegPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(42).addComponent(pnlEstadisticasTablaPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(130)));
		lblMaximoPeso = new JLabel("M\u00E1ximo: ");
		lblMnimoPeso = new JLabel("M\u00EDnimo: ");
		JLabel lblPromedioPeso = new JLabel("Promedio:");
		JLabel lblTotalPeso = new JLabel("Total:");
		campoTextoMaximoPeso = new JTextField();
		campoTextoMaximoPeso.setEditable(false);
		campoTextoMaximoPeso.setEnabled(false);
		campoTextoMaximoPeso.setColumns(10);
		campoTextoMinimoPeso = new JTextField();
		campoTextoMinimoPeso.setEnabled(false);
		campoTextoMinimoPeso.setEditable(false);
		campoTextoMinimoPeso.setColumns(10);
		campoTextoPromedioPeso = new JTextField();
		campoTextoPromedioPeso.setEnabled(false);
		campoTextoPromedioPeso.setEditable(false);
		campoTextoPromedioPeso.setColumns(10);
		campoTextoTotalPeso = new JTextField();
		campoTextoTotalPeso.setEnabled(false);
		campoTextoTotalPeso.setEditable(false);
		campoTextoTotalPeso.setColumns(10);
		JLabel lblMedianaPeso = new JLabel("Mediana:");
		campoTextoMedianaPeso = new JTextField();
		campoTextoMedianaPeso.setEnabled(false);
		campoTextoMedianaPeso.setEditable(false);
		campoTextoMedianaPeso.setColumns(10);
		JLabel lblModaPeso = new JLabel("Moda:");
		campoTextoModaPeso = new JTextField();
		campoTextoModaPeso.setEnabled(false);
		campoTextoModaPeso.setEditable(false);
		campoTextoModaPeso.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaPeso = new GroupLayout(pnlEstadisticasTablaPeso);
		gl_pnlEstadisticasTablaPeso.setHorizontalGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlEstadisticasTablaPeso.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaPeso.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING).addComponent(lblMaximoPeso).addComponent(lblMnimoPeso)).addGap(18).addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoMinimoPeso).addComponent(campoTextoMaximoPeso, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaPeso.createSequentialGroup().addComponent(lblMedianaPeso).addGap(18).addComponent(campoTextoMedianaPeso))).addGap(62).addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING).addComponent(lblPromedioPeso).addComponent(lblTotalPeso).addComponent(lblModaPeso)).addGap(18).addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoModaPeso).addComponent(campoTextoTotalPeso).addComponent(campoTextoPromedioPeso, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)).addContainerGap(37, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaPeso.setVerticalGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlEstadisticasTablaPeso.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoPeso).addComponent(lblPromedioPeso).addComponent(campoTextoMaximoPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(campoTextoPromedioPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(27).addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblMnimoPeso).addComponent(lblTotalPeso).addComponent(campoTextoMinimoPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(campoTextoTotalPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaPeso.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaPeso).addComponent(campoTextoMedianaPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblModaPeso).addComponent(campoTextoModaPeso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		pnlEstadisticasTablaPeso.setLayout(gl_pnlEstadisticasTablaPeso);
		tblDatosPesosBalanza = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component c = super.prepareRenderer(renderer, row, col);
				if (col == 1) {
					Float floatValue = Float.parseFloat(String.valueOf(getValueAt(row, col)));
					c.setForeground(getColor(floatValue));
				}
				else {
					c.setForeground(getForeground());
				}
				return c;
			}
			
			private Color getColor(float floatValue) {
				Color color = null;
				if (floatValue > 0) {
					color = Color.GREEN;
				}
				else if (floatValue < 0) {
					color = Color.RED;
				}
				else {
					color = getForeground();
				}
				return color;
			}
		};
		tblDatosPesosBalanza.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Hora de Captura", "Peso (Kgs)" }));
		tblDatosPesosBalanza.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				tblDatosPesosBalanza.scrollRectToVisible(tblDatosPesosBalanza.getCellRect(tblDatosPesosBalanza.getRowCount() - 1, 0, true));
			}
		});
		pnlDespTablaPesosBalanza.setViewportView(tblDatosPesosBalanza);
		modeloBalanza = (DefaultTableModel) tblDatosPesosBalanza.getModel();
		panelDatosTablaPeso.setLayout(glPanelDatosTablaPeso);
		panelVisualFlujoMasico = new JPanel();
		panelPestanasPrograma.addTab("EW-32907-77", null, panelVisualFlujoMasico, null);
		pnlDatosConexionPuertoSerialPresFlujoTemp = new JPanel();
		pnlDatosConexionPuertoSerialPresFlujoTemp.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos de Entrada - Sensor de Presion - Flujo - Temperatura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblNomSensorPresFluTemp = new JLabel("Nombre del Puerto: ");
		lblNomSensorPresFluTemp.setVerticalAlignment(SwingConstants.TOP);
		cmbListaPuertosSenPresFluTemp = new JComboBox<String>();
		btnValidarPuertoSenPresFluTemp = new JButton("");
		btnValidarPuertoSenPresFluTemp.setToolTipText("Validar Puerto");
		try {
			Image imagenRS232 = ImageIO.read(new File("imagenes/RS232.png"));
			btnValidarPuertoSenPresFluTemp.setIcon(new ImageIcon(imagenRS232));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnValidarPuertoSenPresFluTemp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialPFMyT.verificarPuertoSerial();
			}
		});
		btnConectarSenPresFluTemp = new JButton("");
		try {
			Image imagenFlechaArriba2 = ImageIO.read(new File("imagenes/FlechaArriba2.png"));
			btnConectarSenPresFluTemp.setIcon(new ImageIcon(imagenFlechaArriba2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnConectarSenPresFluTemp.setToolTipText("Conectar");
		btnConectarSenPresFluTemp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				varComunicacionSerialPFMyT.conectarComponentePuertoSerial();
				if (varComunicacionSerialPFMyT.isConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura()) {
					if (varComunicacionSerialPFMyT.inicializarFujoEntradaYSalida()) {
						varComunicacionSerialPFMyT.inicializarOyente();
					}
				}
				if (!isSuspendidoHiloFlujoMasico) {
					hiloCronometroFlujoMasico.start();
				}
				else {// de lo contrario
					hiloCronometroFlujoMasico.resume();
					isSuspendidoHiloFlujoMasico = false;
				}
				btnConectarSenPresFluTemp.setEnabled(false);
				btnDesconectarSenPresFluTemp.setEnabled(true);
				btnGraficaDePresion.setEnabled(true);
				btnGraficaTemperatura.setEnabled(true);
				btnVolFlujo.setEnabled(true);
				btnGraficaDeFl.setEnabled(true);
				btnExportarDatosFlujoMasicoAPDF.setEnabled(true);
				btnExportarDatosFlujoMasicoAExcel.setEnabled(true);
				btnExportarDatosFlujoMasicoATXT.setEnabled(true);
			}
		});
		btnDesconectarSenPresFluTemp = new JButton("");
		try {
			Image imagenFlechaAbajo2 = ImageIO.read(new File("imagenes/FlechaAbajo2.png"));
			btnDesconectarSenPresFluTemp.setIcon(new ImageIcon(imagenFlechaAbajo2));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnDesconectarSenPresFluTemp.setToolTipText("Desconectar");
		btnDesconectarSenPresFluTemp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				temporizadorPresion.stop();
				temporizadorVolumenFlujo.stop();
				temporizadorTemperatura.stop();
				temporizadorVolumenFlujo.stop();
				if (varComunicacionSerialPFMyT.isConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura()) {
					varComunicacionSerialPFMyT.desconectarComponentePuertoSerial();
					varComunicacionSerialPFMyT.datosEW2907DAO.setPresionAbsolutaPSIA(0.0F);
					varComunicacionSerialPFMyT.datosEW2907DAO.setTemperatura(0.0F);
					varComunicacionSerialPFMyT.datosEW2907DAO.setVolumenFlujo(0.0F);
					varComunicacionSerialPFMyT.datosEW2907DAO.setFlujoMasa(0.0F);
					varComunicacionSerialPFMyT.datosEW2907DAO.setValorPuestaPunto(0.0F);
					varComunicacionSerialPFMyT.datosEW2907DAO.setGas("");
					// serieTiempoTemperaturaEW32907.clear();
					// serieTiempoVolumenFlujoEW32907.clear();
					// serieTiempoFlujoMasaFlujoEW32907.clear();
					// serieTiempoPresionAbsolutaEW32907.clear();
					// serieTiempoPuestaPuntoEW32907.clear();
				}
				hiloCronometroFlujoMasico.suspend();
				dsFlujoMasico = 0;
				segFlujoMasico = 0;
				minFlujoMasico = 0;
				horaFlujoMasico = 0;
				lblTimeOperacionFlujoMasico.setText("0:0:0:0");
				isSuspendidoHiloFlujoMasico = true;
				btnConectarSenPresFluTemp.setEnabled(true);
				btnDesconectarSenPresFluTemp.setEnabled(false);
				btnGraficaDePresion.setEnabled(false);
				btnGraficaTemperatura.setEnabled(false);
				btnVolFlujo.setEnabled(false);
				btnGraficaDeFl.setEnabled(false);
				btnExportarDatosFlujoMasicoAPDF.setEnabled(false);
				btnExportarDatosFlujoMasicoAExcel.setEnabled(false);
				btnExportarDatosFlujoMasicoATXT.setEnabled(false);
			}
		});
		JLabel lblBitsParadaSensorPresFluTemp = new JLabel("Bit de Parada:");
		cmbListaBitsParadaSenPresFluTemp = new JComboBox<String>();
		JLabel lblParidadSensorPresFluTemp = new JLabel("Paridad:");
		cmbListaParidadSenPresFluTemp = new JComboBox<String>();
		JLabel lblTasBaudiosSensorPresFluTemp = new JLabel("Tasa de Baudios:");
		cmbListaTasasBaudiosSenPresFluTemp = new JComboBox<String>();
		JLabel lblBitsDatosSensorPresFluTemp = new JLabel("Bit de Datos:");
		cmbListaBitsDatosSenPresFluTemp = new JComboBox<String>();
		lblHoraCapturaDatosFlujoMasico = new JLabel("Tmp. Operaci\u00F3n:");
		lblTimeOperacionFlujoMasico = new JLabel("");
		lblTimeOperacionFlujoMasico.setText("0:0:0:0");
		glPanelDatosConexionPuertoSerialPresFlujoTemp = new GroupLayout(pnlDatosConexionPuertoSerialPresFlujoTemp);
		glPanelDatosConexionPuertoSerialPresFlujoTemp.setHorizontalGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addContainerGap().addComponent(btnValidarPuertoSenPresFluTemp).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnConectarSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGap(58).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING, false).addComponent(lblTasBaudiosSensorPresFluTemp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblNomSensorPresFluTemp, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)).addComponent(lblBitsDatosSensorPresFluTemp, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE).addComponent(lblHoraCapturaDatosFlujoMasico, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING).addComponent(lblTimeOperacionFlujoMasico, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING).addComponent(cmbListaPuertosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addComponent(cmbListaTasasBaudiosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING, false).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addComponent(lblParidadSensorPresFluTemp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cmbListaParidadSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addComponent(lblBitsParadaSensorPresFluTemp, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cmbListaBitsParadaSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))).addComponent(cmbListaBitsDatosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDesconectarSenPresFluTemp).addContainerGap()));
		glPanelDatosConexionPuertoSerialPresFlujoTemp.setVerticalGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGap(16).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblNomSensorPresFluTemp).addComponent(cmbListaPuertosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblTasBaudiosSensorPresFluTemp).addComponent(cmbListaTasasBaudiosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaBitsParadaSenPresFluTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblBitsParadaSensorPresFluTemp)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.BASELINE).addComponent(cmbListaParidadSenPresFluTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblParidadSensorPresFluTemp)))).addGap(11).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.TRAILING).addComponent(btnConectarSenPresFluTemp).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING).addComponent(btnValidarPuertoSenPresFluTemp, Alignment.TRAILING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addComponent(lblBitsDatosSensorPresFluTemp).addGap(18).addComponent(lblHoraCapturaDatosFlujoMasico)).addGroup(glPanelDatosConexionPuertoSerialPresFlujoTemp.createSequentialGroup().addComponent(cmbListaBitsDatosSenPresFluTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblTimeOperacionFlujoMasico).addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE).addComponent(btnDesconectarSenPresFluTemp)))))).addContainerGap()));
		pnlDatosConexionPuertoSerialPresFlujoTemp.setLayout(glPanelDatosConexionPuertoSerialPresFlujoTemp);
		pnlResultadosPresFluTemp = new JPanel();
		pnlResultadosPresFluTemp.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Resultados Obtenidos - Humedad y Temperatura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblDesconectarPuertoSerialPresFluTemp = new JLabel("Desconectar Puerto Serial para el Sensor de Presion, Flujo Masico y Temperatura");
		lblTemperaturaC = new JLabel("Temperatura (C):");
		lblFlujoMasico = new JLabel("Flujo Masico (mg):");
		lblPresion = new JLabel("Presion Abs(PSIA):");
		//
		campoTextoTemperaturaC = new JTextField();
		campoTextoTemperaturaC.setEnabled(false);
		campoTextoTemperaturaC.setEditable(false);
		campoTextoTemperaturaC.setColumns(10);
		campoTextoFlujoMasico = new JTextField();
		campoTextoFlujoMasico.setEnabled(false);
		campoTextoFlujoMasico.setEditable(false);
		campoTextoFlujoMasico.setColumns(10);
		campoTextoPresion = new JTextField();
		campoTextoPresion.setEnabled(false);
		campoTextoPresion.setEditable(false);
		campoTextoPresion.setColumns(10);
		// Evento para disparar las gráficas de presion, flujo m�sico y temperatura.
		varComunicacionSerialPFMyT.verificarPuertoSerial();
		// Configuraci�n de la gráfica para el Sensor 1.
		graficaDinamicaPresionAbsoluta = new GraficaDinamicaPresion("Ver gráfica de Comportamiento de la Presi�n Absoluta");
		graficaDinamicaPresionAbsoluta.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaPresionAbsoluta);
		graficaDinamicaPresionAbsoluta.setVisible(false);
		ActionListener oyenteSensorPresion = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaPresionAbsoluta.obtenerDatos(varComunicacionSerialPFMyT.datosEW2907DAO.getPresionAbsolutaPSIA());
			}
		};
		temporizadorPresion = new Timer(DELAY_TEMPORIZADORES, oyenteSensorPresion);
		// Configuraci�n de la gráfica para el Volumen de Flujo.
		graficaDinamicaVolumenFlujo = new GraficaDinamicaVolumenFlujo("Ver gráfica de Comportamiento del Volumen de Flujo");
		graficaDinamicaVolumenFlujo.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaVolumenFlujo);
		graficaDinamicaVolumenFlujo.setVisible(false);
		ActionListener oyenteVolumenFlujo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaVolumenFlujo.obtenerDatos(varComunicacionSerialPFMyT.datosEW2907DAO.getVolumenFlujo());
			}
		};
		temporizadorVolumenFlujo = new Timer(DELAY_TEMPORIZADORES, oyenteVolumenFlujo);
		// Configuraci�n de la gráfica para la Temperatura.
		graficaDinamicaTemperatura = new GraficaDinamicaTemperatura("Ver gráfica de Comportamiento de la Temperatura en Grados Celsius");
		graficaDinamicaTemperatura.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaTemperatura);
		graficaDinamicaTemperatura.setVisible(false);
		ActionListener oyenteTemperatura = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaTemperatura.obtenerDatos(varComunicacionSerialPFMyT.datosEW2907DAO.getTemperatura());
			}
		};
		temporizadorTemperatura = new Timer(DELAY_TEMPORIZADORES, oyenteTemperatura);
		// Configuraci�n de la gráfica para el Flujo de Masa.
		graficaDinamicaMasaFlujo = new GraficaDinamicaMasaFlujo("Ver gráfica de Comportamiento de la Masa de Flujo");
		graficaDinamicaMasaFlujo.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaMasaFlujo);
		graficaDinamicaMasaFlujo.setVisible(false);
		ActionListener oyenteMasaFlujo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaMasaFlujo.obtenerDatos(varComunicacionSerialPFMyT.datosEW2907DAO.getFlujoMasa());
			}
		};
		temporizadorMasaFlujo = new Timer(DELAY_TEMPORIZADORES, oyenteMasaFlujo);
		btnGraficaTemperatura = new JButton("");
		btnGraficaTemperatura.setToolTipText("Grafica para monitorear la temperatura de flujo.");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnGraficaTemperatura.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnGraficaTemperatura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaTemperatura.setVisible(true);
				graficaDinamicaTemperatura.obtenerDatos(0.0);
				temporizadorTemperatura.start();
			}
		});
		btnGraficaDeFl = new JButton("");
		btnGraficaDeFl.setToolTipText("Grafica para monitorear el flujo.");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnGraficaDeFl.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnGraficaDeFl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaMasaFlujo.setVisible(true);
				graficaDinamicaMasaFlujo.obtenerDatos(0.0);
				temporizadorMasaFlujo.start();
			}
		});
		btnGraficaDePresion = new JButton("");
		btnGraficaDePresion.setToolTipText("Grafica para monitorear la presi\u00F3n absoluta.");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnGraficaDePresion.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnGraficaDePresion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaPresionAbsoluta.setVisible(true);
				graficaDinamicaPresionAbsoluta.obtenerDatos(0.0);
				temporizadorPresion.start();
			}
		});
		JPanel pnlGraficosPresionEW32907 = new JPanel();
		pnlGraficosPresionEW32907 = crearPanelEW32907();
		pnlGraficosPresionEW32907.setPreferredSize(new Dimension(75, 75));
		JLabel lblVolumenFlujo = new JLabel("Volumen de Flujo:");
		campoTextoVolumenFlujo = new JTextField();
		campoTextoVolumenFlujo.setEnabled(false);
		campoTextoVolumenFlujo.setEditable(false);
		campoTextoVolumenFlujo.setColumns(10);
		btnVolFlujo = new JButton("");
		btnVolFlujo.setToolTipText("Grafica para monitorear el volumen de flujo.");
		btnVolFlujo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaVolumenFlujo.setVisible(true);
				graficaDinamicaVolumenFlujo.obtenerDatos(0.0);
				temporizadorVolumenFlujo.start();
			}
		});
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnVolFlujo.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		GroupLayout gl_pnlResultadosPresFluTemp = new GroupLayout(pnlResultadosPresFluTemp);
		gl_pnlResultadosPresFluTemp.setHorizontalGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addGap(10).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addComponent(lblPresion).addComponent(lblVolumenFlujo, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.TRAILING).addComponent(campoTextoVolumenFlujo, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addComponent(campoTextoPresion, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.TRAILING).addComponent(btnVolFlujo, 0, 0, Short.MAX_VALUE).addComponent(btnGraficaDePresion, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addComponent(lblFlujoMasico).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(campoTextoFlujoMasico, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addComponent(lblTemperaturaC, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(campoTextoTemperaturaC, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.TRAILING).addComponent(btnGraficaTemperatura, GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE).addComponent(btnGraficaDeFl, GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE))).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addContainerGap().addComponent(lblDesconectarPuertoSerialPresFluTemp))).addContainerGap()));
		gl_pnlResultadosPresFluTemp.setVerticalGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addContainerGap().addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblPresion).addComponent(campoTextoPresion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addComponent(btnGraficaDePresion).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addContainerGap().addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblTemperaturaC).addComponent(campoTextoTemperaturaC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addComponent(btnGraficaTemperatura)).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addGap(11).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblVolumenFlujo).addComponent(campoTextoVolumenFlujo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnVolFlujo, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)).addGroup(gl_pnlResultadosPresFluTemp.createParallelGroup(Alignment.BASELINE).addComponent(lblFlujoMasico).addComponent(campoTextoFlujoMasico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))).addGroup(gl_pnlResultadosPresFluTemp.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(btnGraficaDeFl))).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblDesconectarPuertoSerialPresFluTemp).addGap(162)));
		pnlResultadosPresFluTemp.setLayout(gl_pnlResultadosPresFluTemp);
		GroupLayout gl_pnlGraficosPresionEW32907 = new GroupLayout(pnlGraficosPresionEW32907);
		gl_pnlGraficosPresionEW32907.setHorizontalGroup(gl_pnlGraficosPresionEW32907.createParallelGroup(Alignment.LEADING).addGap(0, 207, Short.MAX_VALUE));
		gl_pnlGraficosPresionEW32907.setVerticalGroup(gl_pnlGraficosPresionEW32907.createParallelGroup(Alignment.LEADING).addGap(0, 227, Short.MAX_VALUE));
		pnlGraficosPresionEW32907.setLayout(gl_pnlGraficosPresionEW32907);
		JPanel pnlAcelerometroEW32907 = new JPanel();
		pnlAcelerometroEW32907 = crearPanelAcelerometroEW32907();
		GroupLayout gl_panelVisualFlujoMasico = new GroupLayout(panelVisualFlujoMasico);
		gl_panelVisualFlujoMasico.setHorizontalGroup(gl_panelVisualFlujoMasico.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelVisualFlujoMasico.createSequentialGroup().addGroup(gl_panelVisualFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(gl_panelVisualFlujoMasico.createSequentialGroup().addContainerGap().addComponent(chartPanelPresionEW32907, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(chartPanelAcelerometroEW32907, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)).addGroup(gl_panelVisualFlujoMasico.createSequentialGroup().addContainerGap().addComponent(pnlResultadosPresFluTemp, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))).addGap(21)).addGroup(Alignment.LEADING, gl_panelVisualFlujoMasico.createSequentialGroup().addContainerGap().addComponent(pnlDatosConexionPuertoSerialPresFlujoTemp, 0, 0, Short.MAX_VALUE).addGap(25)));
		gl_panelVisualFlujoMasico.setVerticalGroup(gl_panelVisualFlujoMasico.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelVisualFlujoMasico.createSequentialGroup().addGap(5).addComponent(pnlDatosConexionPuertoSerialPresFlujoTemp, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE).addGap(18).addComponent(pnlResultadosPresFluTemp, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelVisualFlujoMasico.createParallelGroup(Alignment.TRAILING).addComponent(chartPanelAcelerometroEW32907, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE).addComponent(chartPanelPresionEW32907, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)).addContainerGap()));
		GridBagLayout gbl_pnlAcelerometroEW32907 = new GridBagLayout();
		gbl_pnlAcelerometroEW32907.columnWidths = new int[] { 0 };
		gbl_pnlAcelerometroEW32907.rowHeights = new int[] { 0 };
		gbl_pnlAcelerometroEW32907.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_pnlAcelerometroEW32907.rowWeights = new double[] { Double.MIN_VALUE };
		pnlAcelerometroEW32907.setLayout(gbl_pnlAcelerometroEW32907);
		panelVisualFlujoMasico.setLayout(gl_panelVisualFlujoMasico);
		panelDatosTablaFlujoMasico = new JPanel();
		panelPestanasPrograma.addTab("Datos EW-32907-77", null, panelDatosTablaFlujoMasico, null);
		pnlDespTablaPrsFlujTemp = new JScrollPane();
		btnExportarDatosFlujoMasicoAExcel = new JButton("");
		try {
			Image imagenExcel = ImageIO.read(new File("imagenes/excel.png"));
			btnExportarDatosFlujoMasicoAExcel.setIcon(new ImageIcon(imagenExcel));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosFlujoMasicoAExcel.setToolTipText("Exportar Datos de Flujo Masico a Excel");
		btnExportarDatosFlujoMasicoAExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarios = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteExcelEW32907 generadorReporteExcel = new GeneradorReporteExcelEW32907();
						generadorReporteExcel.generarReporteEnExcel(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls", modeloPFMyT, tblDatosPresionFlujoTemperatura);
						utilitarios.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ".xls");
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		JPanel pnlEstadisticasTablaPresionAbsoluta = new JPanel();
		pnlEstadisticasTablaPresionAbsoluta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Presi\u00F3n Absoluta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblMaximoPresionAbsoluta = new JLabel("M\u00E1ximo: ");
		JLabel lblMinimoPresionAbsoluta = new JLabel("M\u00EDnimo: ");
		campoTextoMinimoPresionAbsoluta = new JTextField();
		campoTextoMinimoPresionAbsoluta.setEnabled(false);
		campoTextoMinimoPresionAbsoluta.setEditable(false);
		campoTextoMinimoPresionAbsoluta.setColumns(10);
		campoTextoMaximoPresionAbsoluta = new JTextField();
		campoTextoMaximoPresionAbsoluta.setEnabled(false);
		campoTextoMaximoPresionAbsoluta.setEditable(false);
		campoTextoMaximoPresionAbsoluta.setColumns(10);
		JLabel lblMedianaPresionAbsoluta = new JLabel("Mediana:");
		campoTextoMedianaPresionAbsoluta = new JTextField();
		campoTextoMedianaPresionAbsoluta.setEnabled(false);
		campoTextoMedianaPresionAbsoluta.setEditable(false);
		campoTextoMedianaPresionAbsoluta.setColumns(10);
		JLabel lblPromedioPresionAbsoluta = new JLabel("Promedio:");
		JLabel lblTotalPresionAbsoluta = new JLabel("Total:");
		JLabel lblModaPresionAbsoluta = new JLabel("Moda:");
		campoTextoModaPresionAbsoluta = new JTextField();
		campoTextoModaPresionAbsoluta.setEnabled(false);
		campoTextoModaPresionAbsoluta.setEditable(false);
		campoTextoModaPresionAbsoluta.setColumns(10);
		campoTextoTotalPresionAbsoluta = new JTextField();
		campoTextoTotalPresionAbsoluta.setEnabled(false);
		campoTextoTotalPresionAbsoluta.setEditable(false);
		campoTextoTotalPresionAbsoluta.setColumns(10);
		campoTextoPromedioPresionAbsoluta = new JTextField();
		campoTextoPromedioPresionAbsoluta.setEnabled(false);
		campoTextoPromedioPresionAbsoluta.setEditable(false);
		campoTextoPromedioPresionAbsoluta.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaPresionAbsoluta = new GroupLayout(pnlEstadisticasTablaPresionAbsoluta);
		gl_pnlEstadisticasTablaPresionAbsoluta.setHorizontalGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalPresionAbsoluta, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioPresionAbsoluta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoPresionAbsoluta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoPresionAbsoluta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalPresionAbsoluta).addComponent(campoTextoPromedioPresionAbsoluta).addComponent(campoTextoMinimoPresionAbsoluta).addComponent(campoTextoMaximoPresionAbsoluta, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaPresionAbsoluta).addComponent(lblModaPresionAbsoluta)).addGap(18).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaPresionAbsoluta, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaPresionAbsoluta, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(19, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaPresionAbsoluta.setVerticalGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoPresionAbsoluta).addComponent(campoTextoMaximoPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoPresionAbsoluta)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioPresionAbsoluta)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalPresionAbsoluta)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaPresionAbsoluta).addComponent(campoTextoMedianaPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaPresionAbsoluta.createParallelGroup(Alignment.BASELINE).addComponent(lblModaPresionAbsoluta).addComponent(campoTextoModaPresionAbsoluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaPresionAbsoluta.setLayout(gl_pnlEstadisticasTablaPresionAbsoluta);
		JPanel pnlEstadisticasTablaTemperaturaEW2907 = new JPanel();
		pnlEstadisticasTablaTemperaturaEW2907.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Temperatura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblTotalTemperaturaEW2907 = new JLabel("Total:");
		JLabel lblPromedioTemperaturaEW2907 = new JLabel("Promedio:");
		JLabel lblMinimoTemperaturaEW2907 = new JLabel("M\u00EDnimo: ");
		JLabel lblMaximoTemperaturaEW2907 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalTemperaturaEW2907 = new JTextField();
		campoTextoTotalTemperaturaEW2907.setEnabled(false);
		campoTextoTotalTemperaturaEW2907.setEditable(false);
		campoTextoTotalTemperaturaEW2907.setColumns(10);
		campoTextoPromedioTemperaturaEW2907 = new JTextField();
		campoTextoPromedioTemperaturaEW2907.setEnabled(false);
		campoTextoPromedioTemperaturaEW2907.setEditable(false);
		campoTextoPromedioTemperaturaEW2907.setColumns(10);
		campoTextoMinimoTemperaturaEW2907 = new JTextField();
		campoTextoMinimoTemperaturaEW2907.setEnabled(false);
		campoTextoMinimoTemperaturaEW2907.setEditable(false);
		campoTextoMinimoTemperaturaEW2907.setColumns(10);
		campoTextoMaximoTemperaturaEW2907 = new JTextField();
		campoTextoMaximoTemperaturaEW2907.setEnabled(false);
		campoTextoMaximoTemperaturaEW2907.setEditable(false);
		campoTextoMaximoTemperaturaEW2907.setColumns(10);
		JLabel lblMedianaTemperaturaEW2907 = new JLabel("Mediana:");
		JLabel lblModaTemperaturaEW2907 = new JLabel("Moda:");
		campoTextoModaTemperaturaEW2907 = new JTextField();
		campoTextoModaTemperaturaEW2907.setEnabled(false);
		campoTextoModaTemperaturaEW2907.setEditable(false);
		campoTextoModaTemperaturaEW2907.setColumns(10);
		campoTextoMedianaTemperaturaEW2907 = new JTextField();
		campoTextoMedianaTemperaturaEW2907.setEnabled(false);
		campoTextoMedianaTemperaturaEW2907.setEditable(false);
		campoTextoMedianaTemperaturaEW2907.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaTemperaturaEW2907 = new GroupLayout(pnlEstadisticasTablaTemperaturaEW2907);
		gl_pnlEstadisticasTablaTemperaturaEW2907.setHorizontalGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalTemperaturaEW2907).addComponent(campoTextoPromedioTemperaturaEW2907).addComponent(campoTextoMinimoTemperaturaEW2907).addComponent(campoTextoMaximoTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaTemperaturaEW2907).addComponent(lblModaTemperaturaEW2907)).addGap(18).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaTemperaturaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaTemperaturaEW2907.setVerticalGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoTemperaturaEW2907).addComponent(campoTextoMaximoTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoTemperaturaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioTemperaturaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalTemperaturaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaTemperaturaEW2907).addComponent(campoTextoMedianaTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaTemperaturaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblModaTemperaturaEW2907).addComponent(campoTextoModaTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaTemperaturaEW2907.setLayout(gl_pnlEstadisticasTablaTemperaturaEW2907);
		JPanel pnlEstadisticasTablaVolumenMasaEW2907 = new JPanel();
		pnlEstadisticasTablaVolumenMasaEW2907.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Volumen de Masa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel lblTotalVolumenMasaEW2907 = new JLabel("Total:");
		JLabel lblPromedioVolumenMasaEW2907 = new JLabel("Promedio:");
		JLabel lblMinimoVolumenMasaEW2907 = new JLabel("M\u00EDnimo: ");
		JLabel lblMaximoVolumenMasaEW2907 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalVolumenMasaEW2907 = new JTextField();
		campoTextoTotalVolumenMasaEW2907.setEnabled(false);
		campoTextoTotalVolumenMasaEW2907.setEditable(false);
		campoTextoTotalVolumenMasaEW2907.setColumns(10);
		campoTextoPromedioVolumenMasaEW2907 = new JTextField();
		campoTextoPromedioVolumenMasaEW2907.setEnabled(false);
		campoTextoPromedioVolumenMasaEW2907.setEditable(false);
		campoTextoPromedioVolumenMasaEW2907.setColumns(10);
		campoTextoMinimoVolumenMasaEW2907 = new JTextField();
		campoTextoMinimoVolumenMasaEW2907.setEnabled(false);
		campoTextoMinimoVolumenMasaEW2907.setEditable(false);
		campoTextoMinimoVolumenMasaEW2907.setColumns(10);
		campoTextoMaximoVolumenMasaEW2907 = new JTextField();
		campoTextoMaximoVolumenMasaEW2907.setEnabled(false);
		campoTextoMaximoVolumenMasaEW2907.setEditable(false);
		campoTextoMaximoVolumenMasaEW2907.setColumns(10);
		JLabel lblMedianaVolumenMasaEW2907 = new JLabel("Mediana:");
		JLabel lblModaVolumenMasaEW2907 = new JLabel("Moda:");
		campoTextoModaVolumenMasaEW2907 = new JTextField();
		campoTextoModaVolumenMasaEW2907.setEnabled(false);
		campoTextoModaVolumenMasaEW2907.setEditable(false);
		campoTextoModaVolumenMasaEW2907.setColumns(10);
		campoTextoMedianaVolumenMasaEW2907 = new JTextField();
		campoTextoMedianaVolumenMasaEW2907.setEnabled(false);
		campoTextoMedianaVolumenMasaEW2907.setEditable(false);
		campoTextoMedianaVolumenMasaEW2907.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaVolumenMasaEW2907 = new GroupLayout(pnlEstadisticasTablaVolumenMasaEW2907);
		gl_pnlEstadisticasTablaVolumenMasaEW2907.setHorizontalGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalVolumenMasaEW2907).addComponent(campoTextoPromedioVolumenMasaEW2907).addComponent(campoTextoMinimoVolumenMasaEW2907).addComponent(campoTextoMaximoVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaVolumenMasaEW2907).addComponent(lblModaVolumenMasaEW2907)).addGap(18).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaVolumenMasaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaVolumenMasaEW2907.setVerticalGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoVolumenMasaEW2907).addComponent(campoTextoMaximoVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoVolumenMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioVolumenMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalVolumenMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaVolumenMasaEW2907).addComponent(campoTextoMedianaVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaVolumenMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblModaVolumenMasaEW2907).addComponent(campoTextoModaVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaVolumenMasaEW2907.setLayout(gl_pnlEstadisticasTablaVolumenMasaEW2907);
		pnlEstadisticasTablaFlujoMasaEW2907 = new JPanel();
		pnlEstadisticasTablaFlujoMasaEW2907.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estad\u00EDsticas - Flujo de Masa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblTotalFlujoMasaEW2907 = new JLabel("Total:");
		lblPromedioFlujoMasaEW2907 = new JLabel("Promedio:");
		lblMinimoFlujoMasaEW2907 = new JLabel("M\u00EDnimo: ");
		lblMaximoFlujoMasaEW2907 = new JLabel("M\u00E1ximo: ");
		campoTextoTotalFlujoMasaEW2907 = new JTextField();
		campoTextoTotalFlujoMasaEW2907.setEnabled(false);
		campoTextoTotalFlujoMasaEW2907.setEditable(false);
		campoTextoTotalFlujoMasaEW2907.setColumns(10);
		campoTextoPromedioFlujoMasaEW2907 = new JTextField();
		campoTextoPromedioFlujoMasaEW2907.setEnabled(false);
		campoTextoPromedioFlujoMasaEW2907.setEditable(false);
		campoTextoPromedioFlujoMasaEW2907.setColumns(10);
		campoTextoMinimoFlujoMasaEW2907 = new JTextField();
		campoTextoMinimoFlujoMasaEW2907.setEnabled(false);
		campoTextoMinimoFlujoMasaEW2907.setEditable(false);
		campoTextoMinimoFlujoMasaEW2907.setColumns(10);
		campoTextoMaximoFlujoMasaEW2907 = new JTextField();
		campoTextoMaximoFlujoMasaEW2907.setEnabled(false);
		campoTextoMaximoFlujoMasaEW2907.setEditable(false);
		campoTextoMaximoFlujoMasaEW2907.setColumns(10);
		lblMedianaFlujoMasaEW2907 = new JLabel("Mediana:");
		lblModaFlujoMasaEW2907 = new JLabel("Moda:");
		campoTextoModaFlujoMasaEW2907 = new JTextField();
		campoTextoModaFlujoMasaEW2907.setEnabled(false);
		campoTextoModaFlujoMasaEW2907.setEditable(false);
		campoTextoModaFlujoMasaEW2907.setColumns(10);
		campoTextoMedianaFlujoMasaEW2907 = new JTextField();
		campoTextoMedianaFlujoMasaEW2907.setEnabled(false);
		campoTextoMedianaFlujoMasaEW2907.setEditable(false);
		campoTextoMedianaFlujoMasaEW2907.setColumns(10);
		GroupLayout gl_pnlEstadisticasTablaFlujoMasaEW2907 = new GroupLayout(pnlEstadisticasTablaFlujoMasaEW2907);
		gl_pnlEstadisticasTablaFlujoMasaEW2907.setHorizontalGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 246, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING, false).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(lblTotalFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE).addComponent(lblPromedioFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMinimoFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblMaximoFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING, false).addComponent(campoTextoTotalFlujoMasaEW2907).addComponent(campoTextoPromedioFlujoMasaEW2907).addComponent(campoTextoMinimoFlujoMasaEW2907).addComponent(campoTextoMaximoFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createSequentialGroup().addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING).addComponent(lblMedianaFlujoMasaEW2907).addComponent(lblModaFlujoMasaEW2907)).addGap(18).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING).addComponent(campoTextoModaFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE).addComponent(campoTextoMedianaFlujoMasaEW2907, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))).addContainerGap(21, Short.MAX_VALUE)));
		gl_pnlEstadisticasTablaFlujoMasaEW2907.setVerticalGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.LEADING).addGap(0, 188, Short.MAX_VALUE).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createSequentialGroup().addContainerGap().addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMaximoFlujoMasaEW2907).addComponent(campoTextoMaximoFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoMinimoFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblMinimoFlujoMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoPromedioFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPromedioFlujoMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(campoTextoTotalFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTotalFlujoMasaEW2907)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblMedianaFlujoMasaEW2907).addComponent(campoTextoMedianaFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_pnlEstadisticasTablaFlujoMasaEW2907.createParallelGroup(Alignment.BASELINE).addComponent(lblModaFlujoMasaEW2907).addComponent(campoTextoModaFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlEstadisticasTablaFlujoMasaEW2907.setLayout(gl_pnlEstadisticasTablaFlujoMasaEW2907);
		btnExportarDatosFlujoMasicoAPDF = new JButton("");
		btnExportarDatosFlujoMasicoAPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarios = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReportePDFParaEW32907 generadorReportePDFEW32907 = new GeneradorReportePDFParaEW32907();
						generadorReportePDFEW32907.generarReportePDF(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF, tblDatosPresionFlujoTemperatura);
						utilitarios.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_PDF);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		try {
			Image imagenPDF = ImageIO.read(new File("imagenes/acrobat.png"));
			btnExportarDatosFlujoMasicoAPDF.setIcon(new ImageIcon(imagenPDF));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosFlujoMasicoAPDF.setToolTipText("Exportar Datos de Flujo Masico a PDF");
		btnExportarDatosFlujoMasicoATXT = new JButton("");
		btnExportarDatosFlujoMasicoATXT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selectorArchivo = new JFileChooser();
				FuncionesUtilitarias utilitarios = new FuncionesUtilitarias();
				try {
					if (selectorArchivo.showSaveDialog(null) == ConstantesGraficasDinamicas.OPCION_SELECCION_ARCHIVO) {
						GeneradorReporteTextoEW32907 generadorReporteTXTEW32907 = new GeneradorReporteTextoEW32907();
						generadorReporteTXTEW32907.generarReporteArhivoTexto(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_TXT, tblDatosPresionFlujoTemperatura);
						utilitarios.abrirArchivoGenerado(selectorArchivo.getSelectedFile().getAbsolutePath() + ConstantesPantallaPrincipal.EXTENSION_REPORTE_TXT);
					}
				}
				catch (Exception ex) {
					logInterfazGraficaMonitoreo.error(ex);
				}
			}
		});
		try {
			Image imagenArchivoTXT = ImageIO.read(new File("imagenes/archivo.png"));
			btnExportarDatosFlujoMasicoATXT.setIcon(new ImageIcon(imagenArchivoTXT));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnExportarDatosFlujoMasicoATXT.setToolTipText("Exportar Datos de Flujo Masico a Texto");
		lblRegBusTblFlujoMasico = new JLabel("Registro a Buscar:");
		tfRegBusFlujoMasico = new JTextField();
		tfRegBusFlujoMasico.setColumns(10);
		tfRegBusFlujoMasico.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = tfRegBusFlujoMasico.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaFlujoMasico.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaFlujoMasico.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = tfRegBusFlujoMasico.getText();
				if (text.trim().length() == 0) {
					ordenadorFilasTablaFlujoMasico.setRowFilter(null);
				}
				else {
					ordenadorFilasTablaFlujoMasico.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
			}
		});
		glPanelDatosTablaFlujoMasico = new GroupLayout(panelDatosTablaFlujoMasico);
		glPanelDatosTablaFlujoMasico.setHorizontalGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addContainerGap().addComponent(pnlEstadisticasTablaPresionAbsoluta, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(pnlEstadisticasTablaVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addContainerGap().addComponent(pnlEstadisticasTablaTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(pnlEstadisticasTablaFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addGap(30).addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addComponent(lblRegBusTblFlujoMasico).addPreferredGap(ComponentPlacement.RELATED).addComponent(tfRegBusFlujoMasico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(32).addComponent(btnExportarDatosFlujoMasicoAPDF, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnExportarDatosFlujoMasicoAExcel).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnExportarDatosFlujoMasicoATXT)).addComponent(pnlDespTablaPrsFlujTemp, GroupLayout.PREFERRED_SIZE, 592, GroupLayout.PREFERRED_SIZE)))).addContainerGap(50, Short.MAX_VALUE)));
		glPanelDatosTablaFlujoMasico.setVerticalGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaFlujoMasico.createSequentialGroup().addGap(28).addComponent(pnlDespTablaPrsFlujTemp, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING, false).addComponent(btnExportarDatosFlujoMasicoATXT, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosFlujoMasicoAPDF, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnExportarDatosFlujoMasicoAExcel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addComponent(lblRegBusTblFlujoMasico).addComponent(tfRegBusFlujoMasico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE).addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addComponent(pnlEstadisticasTablaPresionAbsoluta, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE).addComponent(pnlEstadisticasTablaVolumenMasaEW2907, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)).addGap(7).addGroup(glPanelDatosTablaFlujoMasico.createParallelGroup(Alignment.LEADING).addComponent(pnlEstadisticasTablaFlujoMasaEW2907, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE).addComponent(pnlEstadisticasTablaTemperaturaEW2907, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		tblDatosPresionFlujoTemperatura = new JTable();
		tblDatosPresionFlujoTemperatura.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Hora de Captura", "Presion Absoluta (PSIA)", "Temperatura (�C)", "Volumen de Flujo", "Flujo de Masa", "Set Point", "Gas" }));
		tblDatosPresionFlujoTemperatura.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				tblDatosPresionFlujoTemperatura.scrollRectToVisible(tblDatosPresionFlujoTemperatura.getCellRect(tblDatosPresionFlujoTemperatura.getRowCount() - 1, 0, true));
			}
		});
		pnlDespTablaPrsFlujTemp.setViewportView(tblDatosPresionFlujoTemperatura);
		modeloPFMyT = (DefaultTableModel) tblDatosPresionFlujoTemperatura.getModel();
		panelDatosTablaFlujoMasico.setLayout(glPanelDatosTablaFlujoMasico);
		ordenadorFilasTablaHyT = new TableRowSorter<TableModel>(tblDatosHumedadTemperatura.getModel());
		ordenadorFilasTablaPeso = new TableRowSorter<TableModel>(tblDatosPesosBalanza.getModel());
		ordenadorFilasTablaFlujoMasico = new TableRowSorter<TableModel>(tblDatosPresionFlujoTemperatura.getModel());
		tblDatosHumedadTemperatura.setRowSorter(ordenadorFilasTablaHyT);
		tblDatosPesosBalanza.setRowSorter(ordenadorFilasTablaPeso);
		tblDatosPresionFlujoTemperatura.setRowSorter(ordenadorFilasTablaFlujoMasico);
	}
	
	public void configuracionGraficaDinamicaSensor3() {
		graficaDinamicaSensor3Humedad = new GraficaDinamicaSensor3Humedad("Ver gráfica de Comportamiento para el Sensor 3 - Humedad");
		graficaDinamicaSensor3Humedad.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaSensor2Temperatura);
		graficaDinamicaSensor1Temperatura.setVisible(false);
		ActionListener oyenteSensorTemperatura3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor3Humedad.obtenerDatos(varComunicacionSerialHyT.humedadTemperaturaDTO.getHumedadSensor3());
			}
		};
		temporizadorSensor3Humedad = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperatura3);
		//
		btnGraficaHumSensor = new JButton("");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnGraficaHumSensor.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnGraficaHumSensor.setSelectedIcon(null);
		btnGraficaHumSensor.setToolTipText("Grafica Hum. Sensor 3");
		btnGraficaHumSensor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor3Humedad.setVisible(true);
				graficaDinamicaSensor3Humedad.obtenerDatos(0.0);
				temporizadorSensor3Humedad.start();
			}
		});
	}
	
	public void configuracionGraficaDinamicaSensor2() {
		graficaDinamicaSensor2Temperatura = new GraficaDinamicaSensor2Temperatura("Ver gráfica de Comportamiento para el Sensor 2 - Temperatura");
		graficaDinamicaSensor2Temperatura.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaSensor2Temperatura);
		graficaDinamicaSensor2Temperatura.setVisible(false);
		ActionListener oyenteSensorTemperatura2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor2Temperatura.obtenerDatos(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor2());
			}
		};
		temporizadorSensor2Temperatura = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperatura2);
		//
		btnPintarGraficaSensor2 = new JButton("");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnPintarGraficaSensor2.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnPintarGraficaSensor2.setToolTipText("Grafica Temp. Sensor 2");
		btnPintarGraficaSensor2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor2Temperatura.setVisible(true);
				graficaDinamicaSensor2Temperatura.obtenerDatos(0.0);
				temporizadorSensor2Temperatura.start();
			}
		});
	}
	
	public void configuracionGraficaDinamicaSensor1() {
		graficaDinamicaSensor1Temperatura = new GraficaDinamicaSensor1Temperatura("Ver gráfica de Comportamiento para el Sensor 1 - Temperatura");
		graficaDinamicaSensor1Temperatura.pack();
		RefineryUtilities.centerFrameOnScreen(graficaDinamicaSensor1Temperatura);
		graficaDinamicaSensor1Temperatura.setVisible(false);
		ActionListener oyenteSensorTemperatura1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor1Temperatura.obtenerDatos(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor1());
			}
		};
		temporizadorSensor1Temperatura = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperatura1);
		btnPintarGraficaSensor1 = new JButton("");
		try {
			Image imagenGrafica = ImageIO.read(new File("imagenes/grafica.png"));
			btnPintarGraficaSensor1.setIcon(new ImageIcon(imagenGrafica));
		}
		catch (IOException e1) {
			logInterfazGraficaMonitoreo.error(e1);
		}
		btnPintarGraficaSensor1.setToolTipText("Grafica Temp. Sensor 1");
		btnPintarGraficaSensor1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graficaDinamicaSensor1Temperatura.setVisible(true);
				graficaDinamicaSensor1Temperatura.obtenerDatos(0.0);
				temporizadorSensor1Temperatura.start();
			}
		});
	}
	
	public JPanel crearPanelTemperaturaSensor1() {
		ActionListener oyenteSensorTemperaturaSensor1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerDatosSensor1(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor1());
			}
		};
		tempSensor1Temperatura = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperaturaSensor1);
		serieTiempoSensorTemperatura1 = new TimePeriodValues("Temperatura Medida para el Sensor 1");
		chartPanelSensorTemperatura1 = new ChartPanel(crearGraficaSensor1(new TimePeriodValuesCollection(serieTiempoSensorTemperatura1)));
		chartPanelSensorTemperatura1.setPreferredSize(new Dimension(75, 75));
		tempSensor1Temperatura.start();
		return chartPanelSensorTemperatura1;
	}
	
	public JFreeChart crearGraficaSensor1(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gráfica de Comportamiento del Sensor 1 - Temperatura", "Tiempo", "Celcius", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	public void obtenerDatosSensor1(double x) {
		ultimoValorSensorTemperatura1 = x;
		serieTiempoSensorTemperatura1.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorSensorTemperatura1);
	}
	
	// Eventos para la humedad del Sensor de Temperatura 2
	public JPanel crearPanelTemperaturaSensor2() {
		ActionListener oyenteSensorTemperaturaSensor2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerDatosSensor2(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor2());
			}
		};
		tempSensor2Temperatura = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperaturaSensor2);
		serieTiempoSensorTemperatura2 = new TimePeriodValues("Temperatura Medida para el Sensor 2");
		chartPanelSensorTemperatura2 = new ChartPanel(crearGraficaSensor2(new TimePeriodValuesCollection(serieTiempoSensorTemperatura2)));
		chartPanelSensorTemperatura2.setPreferredSize(new Dimension(75, 75));
		tempSensor2Temperatura.start();
		return chartPanelSensorTemperatura2;
	}
	
	public JFreeChart crearGraficaSensor2(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gráfica de Comportamiento del Sensor 2 - Temperatura", "Tiempo", "Celcius", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	public void obtenerDatosSensor2(double x) {
		ultimoValorSensorTemperatura2 = x;
		serieTiempoSensorTemperatura2.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorSensorTemperatura2);
	}
	
	// Eventos para la humedad del Sensor de Temperatura 2
	public JPanel crearPanelHumedadSensor3() {
		ActionListener oyenteSensorTemperaturaSensor3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerDatosHumedad(varComunicacionSerialHyT.humedadTemperaturaDTO.getHumedadSensor3());
			}
		};
		tempSensor3Humedad = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTemperaturaSensor3);
		serieTiempoSensorHumedad = new TimePeriodValues("Humedad Medida para el Sensor 3");
		chartPanelSensorHumedad = new ChartPanel(crearGraficaSensor3(new TimePeriodValuesCollection(serieTiempoSensorHumedad)));
		chartPanelSensorHumedad.setPreferredSize(new Dimension(75, 75));
		tempSensor3Humedad.start();
		return chartPanelSensorHumedad;
	}
	
	public JFreeChart crearGraficaSensor3(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gráfica de Comportamiento del Sensor 3 - Humedad", "Tiempo", "%", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	public void obtenerDatosHumedad(double x) {
		ultimoValorSensorHumedad = x;
		serieTiempoSensorHumedad.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorSensorHumedad);
	}
	
	// Peso Balanza
	public JPanel crearPanelPesoBalanza() {
		ActionListener oyenteSensorPesoBalanza = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerDatosPesoBalanza(varComunicacionSerialBalanza.pesoDTO.getPeso());
			}
		};
		tempPesoBalanza = new Timer(DELAY_TEMPORIZADORES, oyenteSensorPesoBalanza);
		serieTiempoPesoBalanza = new TimePeriodValues("Peso Obtenido para la Balanza");
		chartPanelPesoBalanza = new ChartPanel(crearGraficaPesoBalanza(new TimePeriodValuesCollection(serieTiempoPesoBalanza)));
		chartPanelPesoBalanza.setPreferredSize(new Dimension(75, 75));
		tempPesoBalanza.start();
		return chartPanelPesoBalanza;
	}
	
	public JPanel crearPanelAcelerometroPesoBalanza() {
		ActionListener oyenteSensorPesoBalanzaAcelerometro = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarDatosAcelerometroPeso(varComunicacionSerialBalanza.pesoDTO.getPeso());
			}
		};
		conjuntoDatosAcelerometroBalanza = new DefaultValueDataset(varComunicacionSerialBalanza.pesoDTO.getPeso());
		GraficasAcelerometroPeso acelerometroPeso = new GraficasAcelerometroPeso();
		chartPanelAcelerometroPesoBalanza = new ChartPanel(acelerometroPeso.crearGraficaParaAcelerometro(conjuntoDatosAcelerometroBalanza)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}
		};
		temporizadorAcelerometroBalanza = new Timer(DELAY_TEMPORIZADORES, oyenteSensorPesoBalanzaAcelerometro);
		temporizadorAcelerometroBalanza.start();
		return chartPanelAcelerometroPesoBalanza;
	}
	
	public void actualizarDatosAcelerometroPeso(float data) {
		conjuntoDatosAcelerometroBalanza.setValue(data);
	}
	
	//
	public JPanel crearPanelAcelerometroEW32907() {
		ActionListener oyenteSensorEW32907Acelerometro = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarDatosAcelerometroEW32907(varComunicacionSerialPFMyT.datosEW2907DAO.getPresionAbsolutaPSIA(), varComunicacionSerialPFMyT.datosEW2907DAO.getTemperatura(), varComunicacionSerialPFMyT.datosEW2907DAO.getVolumenFlujo(), varComunicacionSerialPFMyT.datosEW2907DAO.getFlujoMasa(), varComunicacionSerialPFMyT.datosEW2907DAO.getValorPuestaPunto());
			}
		};
		conjuntoDatosAcelerometroPresionEW32907 = new DefaultValueDataset(varComunicacionSerialPFMyT.datosEW2907DAO.getPresionAbsolutaPSIA());
		conjuntoDatosAcelerometroTemperaturaEW32907 = new DefaultValueDataset(varComunicacionSerialPFMyT.datosEW2907DAO.getTemperatura());
		conjuntoDatosAcelerometroVolumenMasicoEW32907 = new DefaultValueDataset(varComunicacionSerialPFMyT.datosEW2907DAO.getVolumenFlujo());
		conjuntoDatosAcelerometroFlujoMasicoEW32907 = new DefaultValueDataset(varComunicacionSerialPFMyT.datosEW2907DAO.getFlujoMasa());
		conjuntoDatosAcelerometroSetPointEW32907 = new DefaultValueDataset(varComunicacionSerialPFMyT.datosEW2907DAO.getValorPuestaPunto());
		GraficasAcelerometroFlujo acelerometroEW32907 = new GraficasAcelerometroFlujo();
		chartPanelAcelerometroEW32907 = new ChartPanel(acelerometroEW32907.crearGraficaParaAcelerometro(conjuntoDatosAcelerometroPresionEW32907, conjuntoDatosAcelerometroTemperaturaEW32907, conjuntoDatosAcelerometroVolumenMasicoEW32907, conjuntoDatosAcelerometroFlujoMasicoEW32907, conjuntoDatosAcelerometroSetPointEW32907)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}
		};
		temporizadorAcelerometroEW32907 = new Timer(DELAY_TEMPORIZADORES, oyenteSensorEW32907Acelerometro);
		temporizadorAcelerometroEW32907.start();
		return chartPanelAcelerometroEW32907;
	}
	
	public void actualizarDatosAcelerometroEW32907(float presion, float temperatura, float volumenFlujo, float flujoMasico, float puestaAPunto) {
		conjuntoDatosAcelerometroPresionEW32907.setValue(presion);
		conjuntoDatosAcelerometroTemperaturaEW32907.setValue(temperatura);
		conjuntoDatosAcelerometroVolumenMasicoEW32907.setValue(volumenFlujo);
		conjuntoDatosAcelerometroFlujoMasicoEW32907.setValue(flujoMasico);
		conjuntoDatosAcelerometroSetPointEW32907.setValue(puestaAPunto);
	}
	
	public JPanel crearPanelTermometroTemperatura1() {
		ActionListener oyenteSensorTermometroTemperaturaSensor1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarDatosTermometroTemperatura1(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor1());
			}
		};
		conjuntoDatosSensorTermometroTemperaturaSensor1 = new DefaultValueDataset(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor1());
		GraficaTermometroTemperatura1 termometroTemperatura1 = new GraficaTermometroTemperatura1();
		chartPanelTermometroTemperaturaTermometro1 = new ChartPanel(termometroTemperatura1.crearGraficaParaTermometro(conjuntoDatosSensorTermometroTemperaturaSensor1)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}
		};
		temporizadorTermometroTemperatura1 = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTermometroTemperaturaSensor1);
		temporizadorTermometroTemperatura1.start();
		return chartPanelTermometroTemperaturaTermometro1;
	}
	
	public void actualizarDatosTermometroTemperatura1(float data) {
		conjuntoDatosSensorTermometroTemperaturaSensor1.setValue(data);
	}
	
	public JPanel crearPanelTermometroTemperatura2() {
		ActionListener oyenteSensorTermometroTemperaturaSensor2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarDatosTermometroTemperatura2(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor2());
			}
		};
		conjuntoDatosSensorTermometroTemperaturaSensor2 = new DefaultValueDataset(varComunicacionSerialHyT.humedadTemperaturaDTO.getTemperaturaSensor2());
		GraficaTermometroTemperatura2 termometroTemperatura2 = new GraficaTermometroTemperatura2();
		chartPanelTermometroTemperaturaTermometro2 = new ChartPanel(termometroTemperatura2.crearGraficaParaTermometro(conjuntoDatosSensorTermometroTemperaturaSensor1)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}
		};
		temporizadorTermometroTemperatura2 = new Timer(DELAY_TEMPORIZADORES, oyenteSensorTermometroTemperaturaSensor2);
		temporizadorTermometroTemperatura2.start();
		return chartPanelTermometroTemperaturaTermometro2;
	}
	
	public void actualizarDatosTermometroTemperatura2(float data) {
		conjuntoDatosSensorTermometroTemperaturaSensor2.setValue(data);
	}
	
	//
	public JFreeChart crearGraficaPesoBalanza(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gráfica de Comportamiento del la Balanza", "Tiempo", "Peso (kgs)", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	public void obtenerDatosPesoBalanza(double x) {
		ultimoValorPezoBalanza = x;
		serieTiempoPesoBalanza.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorPezoBalanza);
	}
	
	// --------------------------------------------------------------------------------------------
	// Datos del Sensor EW32907
	// --------------------------------------------------------------------------------------------
	public JPanel crearPanelEW32907() {
		ActionListener oyenteSensorEW32907 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerDatosEW32907(varComunicacionSerialPFMyT.datosEW2907DAO.getPresionAbsolutaPSIA(), varComunicacionSerialPFMyT.datosEW2907DAO.getTemperatura(), varComunicacionSerialPFMyT.datosEW2907DAO.getVolumenFlujo(), varComunicacionSerialPFMyT.datosEW2907DAO.getFlujoMasa(), varComunicacionSerialPFMyT.datosEW2907DAO.getValorPuestaPunto());
			}
		};
		tempEW32907 = new Timer(DELAY_TEMPORIZADORES, oyenteSensorEW32907);
		serieTiempoPresionAbsolutaEW32907 = new TimePeriodValues("Presion Absoluta");
		serieTiempoTemperaturaEW32907 = new TimePeriodValues("Temperatura");
		serieTiempoVolumenFlujoEW32907 = new TimePeriodValues("Volumen de Flujo");
		serieTiempoFlujoMasaFlujoEW32907 = new TimePeriodValues("Flujo de Masa");
		serieTiempoPuestaPuntoEW32907 = new TimePeriodValues("Puesta a Punto");
		TimePeriodValuesCollection conjuntoDatos = new TimePeriodValuesCollection();
		conjuntoDatos.addSeries(serieTiempoPresionAbsolutaEW32907);
		conjuntoDatos.addSeries(serieTiempoTemperaturaEW32907);
		conjuntoDatos.addSeries(serieTiempoVolumenFlujoEW32907);
		conjuntoDatos.addSeries(serieTiempoFlujoMasaFlujoEW32907);
		conjuntoDatos.addSeries(serieTiempoPuestaPuntoEW32907);
		chartPanelPresionEW32907 = new ChartPanel(crearGraficaEW32907(conjuntoDatos));
		chartPanelPresionEW32907.setPreferredSize(new Dimension(75, 75));
		tempEW32907.start();
		return chartPanelPresionEW32907;
	}
	
	public JFreeChart crearGraficaEW32907(XYDataset conjuntoDatos) {
		final JFreeChart graficaObtenida = ChartFactory.createTimeSeriesChart("Gráfica de Comportamiento del Sensor EW-32907", "Tiempo", "Intervalo", conjuntoDatos, true, true, false);
		final XYPlot plot = graficaObtenida.getXYPlot();
		ValueAxis valoresEjes = plot.getDomainAxis();
		valoresEjes.setAutoRange(true);
		valoresEjes.setFixedAutoRange(10000.0);
		valoresEjes = plot.getRangeAxis();
		valoresEjes.setRange(0.0, 100.0);
		return graficaObtenida;
	}
	
	public void obtenerDatosEW32907(double v, double w, double x, double y, double z) {
		ultimoValorPresionAbsolutaEW32907 = v;
		ultimoValorTemperaturaEW32907 = w;
		ultimoValorVolumenFlujoEW32907 = x;
		ultimoValorFlujoMasaEW32907 = y;
		ultimoValorPuestaPuntoEW32907 = z;
		serieTiempoPresionAbsolutaEW32907.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorPresionAbsolutaEW32907);
		serieTiempoTemperaturaEW32907.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorTemperaturaEW32907);
		serieTiempoVolumenFlujoEW32907.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorVolumenFlujoEW32907);
		serieTiempoFlujoMasaFlujoEW32907.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorFlujoMasaEW32907);
		serieTiempoPuestaPuntoEW32907.add(new Millisecond(obtenerTiempoRealTransaccion()), ultimoValorPuestaPuntoEW32907);
	}
	
	public Timestamp obtenerTiempoRealTransaccion() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public boolean isSuspendidoHiloPeso = false;// para saber si el hilo esta suspendido o pausado
	public int horaPeso = 0;
	public int minPeso = 0;
	public int segPeso = 0;
	public int dsPeso = 0;// unidades de medida
	Thread hiloCronometroPeso = new Thread() {// declaramos el hilo
		@Override
		public void run() {
			try {
				while (true) {// ciclo infinito
					if (dsPeso == 99) {// si los decisegundos son iguales a 99
						dsPeso = 0;// decisegundo vuelve a empezar en cero
						segPeso++;// y aumenta un segundo
					}
					if (segPeso == 59) {// si los segundos son iguales a 59
						segPeso = 0;// segundo vuelve a empezar en cero
						minPeso++;// y aumenta un minuto
					}
					if (minPeso == 59) {// si los minutos son iguales a 59
						minPeso = 0;// minuto vuelve a empezar en cero
						horaPeso++;// y aumenta una hora
					}
					dsPeso++;// aumentan las decimas de segundo
					lblTimeOperacionPeso.setText(horaPeso + ":" + minPeso + ":" + segPeso + ":" + dsPeso);// se muestra en el jlabel
					hiloCronometroPeso.sleep(10);// que duerma una decima de segundo
				}
			}
			catch (InterruptedException ie) {
				logInterfazGraficaMonitoreo.error(ie);
			}
		}
	};
	public boolean isSuspendidoHiloHyT = false;// para saber si el hilo esta suspendido o pausado
	public int horaHyT = 0;
	public int minHyT = 0;
	public int segHyT = 0;
	public int dsHyT = 0;// unidades de medida
	Thread hiloCronometroHyT = new Thread() {// declaramos el hilo
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				while (true) {// ciclo infinito
					if (dsHyT == 99) {// si los decisegundos son iguales a 99
						dsHyT = 0;// decisegundo vuelve a empezar en cero
						segHyT++;// y aumenta un segundo
					}
					if (segHyT == 59) {// si los segundos son iguales a 59
						segHyT = 0;// segundo vuelve a empezar en cero
						minHyT++;// y aumenta un minuto
					}
					if (minHyT == 59) {// si los minutos son iguales a 59
						minHyT = 0;// minuto vuelve a empezar en cero
						horaHyT++;// y aumenta una hora
					}
					dsHyT++;// aumentan las decimas de segundo
					lblTimeOperacionHumTemp.setText(horaHyT + ":" + minHyT + ":" + segHyT + ":" + dsHyT);// se muestra en el jlabel
					hiloCronometroHyT.sleep(10);// que duerma una decima de segundo
				}
			}
			catch (InterruptedException ie) {
				logInterfazGraficaMonitoreo.error(ie);
			}
		}
	};
	public boolean isSuspendidoHiloFlujoMasico = false;
	public int horaFlujoMasico = 0;
	public int minFlujoMasico = 0;
	public int segFlujoMasico = 0;
	public int dsFlujoMasico = 0;
	Thread hiloCronometroFlujoMasico = new Thread() {// declaramos el hilo
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				while (true) {// ciclo infinito
					if (dsFlujoMasico == 99) {// si los decisegundos son iguales a 99
						dsFlujoMasico = 0;// decisegundo vuelve a empezar en cero
						segFlujoMasico++;// y aumenta un segundo
					}
					if (segFlujoMasico == 59) {// si los segundos son iguales a 59
						segFlujoMasico = 0;// segundo vuelve a empezar en cero
						minFlujoMasico++;// y aumenta un minuto
					}
					if (minFlujoMasico == 59) {// si los minutos son iguales a 59
						minFlujoMasico = 0;// minuto vuelve a empezar en cero
						horaFlujoMasico++;// y aumenta una hora
					}
					dsFlujoMasico++;// aumentan las decimas de segundo
					lblTimeOperacionFlujoMasico.setText(horaFlujoMasico + ":" + minFlujoMasico + ":" + segFlujoMasico + ":" + dsFlujoMasico);
					hiloCronometroFlujoMasico.sleep(10);// que duerma una decima de segundo
				}
			}
			catch (InterruptedException ie) {
				logInterfazGraficaMonitoreo.error(ie);
			}
		}
	};
	
	// M�todo llamado al cargar la ayuda.
	private void cargarAyuda() {
		try {
			// Carga el fichero de ayuda
			File fichero = new File("help/help_set.hs");
			URL hsURL = fichero.toURI().toURL();
			// Crea el HelpSet y el HelpBroker
			ClassLoader loader = InterfazGraficaMonitoreoInstrumentosElectronicos.class.getClassLoader();
			HelpSet helpset = new HelpSet(loader, hsURL);
			HelpBroker hb = helpset.createHelpBroker();
			hb.enableHelpOnButton(miAyudaProyecto, "monitorVisual", helpset);
			hb.enableHelpKey(getContentPane(), "ventana_hyt", helpset);
			hb.enableHelpKey(getContentPane(), "ventana_peso", helpset);
		}
		catch (Exception e) {
			logInterfazGraficaMonitoreo.error(e);
		}
	}
}