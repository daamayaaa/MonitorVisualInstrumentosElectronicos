/*
 *
 */
package com.damaya.proyinv.comserial;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.damaya.proyinv.comserial.interfaces.IComunicacionSerial;
import com.damaya.proyinv.constantes.ConstantesComunicacionSerial;
import com.damaya.proyinv.constantes.ConstantesGenerales;
import com.damaya.proyinv.dto.BalanzaDTO;
import com.damaya.proyinv.dto.EstadisticasBalanzaDTO;
import com.damaya.proyinv.estadisticas.ManejadorEstadisticasVariables;
import com.damaya.proyinv.gui.InterfazGraficaMonitoreoInstrumentosElectronicos;
import com.damaya.proyinv.utils.FuncionesUtilitarias;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * Clase encargada de manejar la comunicaci�n serial con un dispositivo de balanza.
 * @author Diego Alejandro Amaya Anaya Arjona.
 * @version 1.0
 * @since Marzo 01 de 2016.
 */
public class ComunicacionSerialBalanza implements IComunicacionSerial, SerialPortEventListener, Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The interfaz monitoreo gota. */
	private InterfazGraficaMonitoreoInstrumentosElectronicos interfazMonitoreoGota = null;
	/** The enum puertos. */
	@SuppressWarnings("rawtypes")
	private Enumeration enumPuertos = null;
	/** The mapa puertos. */
	private HashMap<String, CommPortIdentifier> mapaPuertos = new HashMap<String, CommPortIdentifier>();
	/** The identificador puerto. */
	private CommPortIdentifier identificadorPuerto = null;
	/** The puerto serial. */
	private SerialPort puertoSerial = null;
	/** The flujo serial entrada. */
	private InputStream flujoSerialEntrada = null;
	/** The puerto serial balanza conectado. */
	private boolean puertoSerialBalanzaConectado = false;
	// Traza de seguimiento para la comunicaci�n serial.
	/** The log comunicacion serial balanza. */
	private static Logger logComunicacionSerialBalanza = Logger.getLogger("comSerialBalanza");
	/** The peso dao. */
	// Datos que se obtienen desde el Arduino.
	public BalanzaDTO pesoDTO = new BalanzaDTO();
	/** The manejador estadisticas. */
	private ManejadorEstadisticasVariables manejadorEstadisticas = new ManejadorEstadisticasVariables();
	/** The lista tasas baudios. */
	private Integer[] listaTasasBaudios = new Integer[] { ConstantesComunicacionSerial.TASA_BAUDIOS_110.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_300.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_600.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_1200.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_2400.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_4800.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_9600.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_14400.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_19200.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_38400.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_57600.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_115200.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_230400.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_460800.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.TASA_BAUDIOS_921600.getConstanteComunicacionSerial() };
	/** The lista bits datos. */
	private Integer[] listaBitsDatos = new Integer[] { ConstantesComunicacionSerial.BITS_DATOS_5.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_DATOS_6.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_DATOS_7.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_DATOS_8.getConstanteComunicacionSerial() };
	/** The lista bits paridad. */
	private Integer[] listaBitsParidad = new Integer[] { ConstantesComunicacionSerial.SIN_PARIDAD.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.PARIDAD_IMPAR.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.PARIDAD_MARCA.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.PARIDAD_PAR.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.PARIDAD_ESPACIA.getConstanteComunicacionSerial() };
	/** The lista bits parada. */
	private Integer[] listaBitsParada = new Integer[] { ConstantesComunicacionSerial.SIN_PARIDAD.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_PARADA_1.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_PARADA_1_5.getConstanteComunicacionSerial(), ConstantesComunicacionSerial.BITS_PARADA_2.getConstanteComunicacionSerial() };
	private FuncionesUtilitarias funcionesUtilitarias;
	
	/**
	 * Instantiates a new comunicacion serial balanza.
	 * @param vistaModeloDatos the vista modelo datos
	 */
	public ComunicacionSerialBalanza(InterfazGraficaMonitoreoInstrumentosElectronicos vistaModeloDatos) {
		interfazMonitoreoGota = vistaModeloDatos;
		BasicConfigurator.configure();
	}
	
	/**
	 * Funcion que permite validar los puertos que se encuentran disponibles para conectar desde ARDUINO.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void verificarPuertoSerial() {
		interfazMonitoreoGota.cmbListaPuertosBalanza.removeAllItems();
		interfazMonitoreoGota.cmbListaTasasBaudiosBalanza.removeAllItems();
		interfazMonitoreoGota.cmbListaBitsDatosBalanza.removeAllItems();
		interfazMonitoreoGota.cmbListaParidadBalanza.removeAllItems();
		interfazMonitoreoGota.cmbListaBitsParadaBalanza.removeAllItems();
		enumPuertos = CommPortIdentifier.getPortIdentifiers();
		while (enumPuertos.hasMoreElements()) {
			CommPortIdentifier puertoActual = (CommPortIdentifier) enumPuertos.nextElement();
			if (puertoActual.getPortType() == ConstantesComunicacionSerial.COMUNICACION_PUERTO_SERIAL.getConstanteComunicacionSerial()) {
				interfazMonitoreoGota.cmbListaPuertosBalanza.addItem(puertoActual.getName());
				mapaPuertos.put(puertoActual.getName(), puertoActual);
			}
		}
		interfazMonitoreoGota.cmbListaTasasBaudiosBalanza.setModel(new DefaultComboBoxModel(listaTasasBaudios));
		interfazMonitoreoGota.cmbListaBitsDatosBalanza.setModel(new DefaultComboBoxModel(listaBitsDatos));
		interfazMonitoreoGota.cmbListaParidadBalanza.setModel(new DefaultComboBoxModel(listaBitsParidad));
		interfazMonitoreoGota.cmbListaBitsParadaBalanza.setModel(new DefaultComboBoxModel(listaBitsParada));
	}
	
	/**
	 * Checks if is conectado puerto serial balanza.
	 * @return true, if is conectado puerto serial balanza
	 */
	public boolean isConectadoPuertoSerialBalanza() {
		return puertoSerialBalanzaConectado;
	}
	
	/**
	 * Sets the conectado puerto serial balanza.
	 * @param puertoSerialBalanzaConectado the new conectado puerto serial balanza
	 */
	public void setConectadoPuertoSerialBalanza(boolean puertoSerialBalanzaConectado) {
		this.puertoSerialBalanzaConectado = puertoSerialBalanzaConectado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#conectarComponentePuertoSerial()
	 */
	@Override
	public void conectarComponentePuertoSerial() {
		String puertoSeleccionado = (String) interfazMonitoreoGota.cmbListaPuertosBalanza.getSelectedItem();
		Integer tasaBaudiosSeleccionada = (Integer) interfazMonitoreoGota.cmbListaTasasBaudiosBalanza.getSelectedItem();
		Integer bitDatosSeleccionado = (Integer) interfazMonitoreoGota.cmbListaBitsDatosBalanza.getSelectedItem();
		Integer bitParadaSeleccionado = (Integer) interfazMonitoreoGota.cmbListaBitsParadaBalanza.getSelectedItem();
		Integer paridadSeleccionada = (Integer) interfazMonitoreoGota.cmbListaParidadBalanza.getSelectedItem();
		identificadorPuerto = mapaPuertos.get(puertoSeleccionado);
		if (identificadorPuerto.isCurrentlyOwned()) {
			JOptionPane.showMessageDialog(null, "El puerto está siendo utilizado en este momento.");
		}
		else {
			CommPort puertoComunicacion = null;
			try {
				puertoComunicacion = identificadorPuerto.open(ConstantesGenerales.COMUNICACION_SERIAL_BALANZA_ID.getConstanteGeneral(), ConstantesComunicacionSerial.TIMEOUT.getConstanteComunicacionSerial());
				puertoSerial = (SerialPort) puertoComunicacion;
				puertoSerial.setSerialPortParams(tasaBaudiosSeleccionada, bitDatosSeleccionado, bitParadaSeleccionado, paridadSeleccionada);
				puertoSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
				puertoSerial.setRTS(true);
				puertoSerial.notifyOnBreakInterrupt(true);
				puertoSerial.notifyOnCarrierDetect(true);
				puertoSerial.notifyOnCTS(true);
				puertoSerial.notifyOnDataAvailable(true);
				puertoSerial.notifyOnDSR(true);
				puertoSerial.notifyOnFramingError(true);
				puertoSerial.notifyOnOutputEmpty(true);
				puertoSerial.notifyOnOverrunError(true);
				puertoSerial.notifyOnParityError(true);
				puertoSerial.notifyOnRingIndicator(true);
				setConectadoPuertoSerialBalanza(true);
				JOptionPane.showMessageDialog(null, puertoSeleccionado + " ha sido abierto exitosamente.");
				interfazMonitoreoGota.lblDesconectarPuertoSerialBalanza.setText("El puerto serial para la balanza ha sido conectado. ");
			}
			catch (UnsupportedCommOperationException e) {
				JOptionPane.showMessageDialog(null, "Ha fallado la comunicacion con los parametros seleccionadors: " + e.toString() + ")");
				logComunicacionSerialBalanza.error(e);
			}
			catch (PortInUseException e) {
				JOptionPane.showMessageDialog(null, puertoSeleccionado + " esta en uso. (" + e.toString() + ")");
				logComunicacionSerialBalanza.error(e);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ha fallado la conexi�n al puerto:  " + puertoSeleccionado + ".");
				logComunicacionSerialBalanza.error(e);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#desconectarComponentePuertoSerial()
	 */
	@Override
	public void desconectarComponentePuertoSerial() {
		try {
			puertoSerial.removeEventListener();
			puertoSerial.close();
			flujoSerialEntrada.close();
			setConectadoPuertoSerialBalanza(false);
			interfazMonitoreoGota.campoTextoPesoBalanza.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoMinimoPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoMaximoPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoMedianaPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoPromedioPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoTotalPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoGota.campoTextoModaPeso.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			JOptionPane.showMessageDialog(null, "El puerto ha sido cerrado exitosamente. ");
			interfazMonitoreoGota.lblDesconexionPuertoSerialSensorHumTemp.setText("El puerto serial ha sido desconectado. ");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cerrar el puerto " + puertoSerial.getName() + ".");
			logComunicacionSerialBalanza.error(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#inicializarFujoEntradaYSalida()
	 */
	@Override
	public boolean inicializarFujoEntradaYSalida() {
		try {
			flujoSerialEntrada = puertoSerial.getInputStream();
			return true;
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un error al abrir los flujos de E/S. ");
			logComunicacionSerialBalanza.error(e);
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#inicializarOyente()
	 */
	@Override
	public void inicializarOyente() {
		try {
			puertoSerial.addEventListener(this);
			puertoSerial.notifyOnDataAvailable(true);
		}
		catch (TooManyListenersException e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#serialEvent(gnu.io.SerialPortEvent)
	 */
	@Override
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == ConstantesComunicacionSerial.DATO_DISPONIBLE.getConstanteComunicacionSerial()) {
			try {
				// CacheManager administradorCacheBalanza = CacheManager.newInstance();
				// Cache cacheBalanza = administradorCacheBalanza.getCache("cacheManejoDatosBalanza");
				funcionesUtilitarias = new FuncionesUtilitarias();
				int contadorEjecuciones = 0;
				logComunicacionSerialBalanza.info("[INICIO] Se ha inicializado la conexi�n al dispositivo que realiza la toma de datos de la balanza.");
				byte[] dato = new byte[150];
				while (flujoSerialEntrada.available() > 0) {
					long start = System.currentTimeMillis();
					int datosPeso = flujoSerialEntrada.read(dato, 0, dato.length);
					String resultadoAsString = new String(dato, 0, datosPeso);
					String splitResultado = ConstantesGenerales.CADENA_VACIA.getConstanteGeneral();
					if (resultadoAsString.contains("S D")) {
						splitResultado = resultadoAsString.replace("S D", ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
					}
					if (resultadoAsString.contains("S S")) {
						splitResultado = resultadoAsString.replace("S S", ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
					}
					String splitResultadoGramos = splitResultado.replace("g", ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
					String resultadoFinal = splitResultadoGramos.replace(" ", ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
					pesoDTO.setPeso(NumberUtils.toFloat(resultadoFinal));
					contadorEjecuciones++;
					// CacheConfiguration config = cacheBalanza.getCacheConfiguration();
					// config.setTimeToIdleSeconds(60);
					// config.setTimeToLiveSeconds(120);
					// config.setMaxEntriesLocalHeap(10000);
					// config.setMaxEntriesLocalDisk(1000000);
					// cacheBalanza.put(new Element(contadorEjecuciones, pesoDAO.getPesoGota()));
					// Element ele = cacheBalanza.get(contadorEjecuciones);
					// String output = ele == null ? null : ele.getObjectValue().toString();
					// float salidaPesoObtenida = FloatW.parseFloat(output);
					interfazMonitoreoGota.campoTextoPesoBalanza.setText(String.valueOf(pesoDTO.getPeso()));
					String salidaEsperada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
					interfazMonitoreoGota.modeloBalanza.addRow(new Object[] { salidaEsperada, pesoDTO.getPeso() });
					interfazMonitoreoGota.campoTextolNumRegTblPeso.setText(String.valueOf(interfazMonitoreoGota.modeloBalanza.getRowCount()));
					List<String> listaSumatoriaRegistrosBalanza = new ArrayList<String>();
					manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosBalanza, interfazMonitoreoGota.modeloBalanza, 1);
					EstadisticasBalanzaDTO estadisticasBalanzaDTO = mapearEstadisticasBalanza(listaSumatoriaRegistrosBalanza);
					interfazMonitoreoGota.campoTextoTotalPeso.setText(String.valueOf(estadisticasBalanzaDTO.getTotalPeso()));
					interfazMonitoreoGota.campoTextoMaximoPeso.setText(String.valueOf(estadisticasBalanzaDTO.getMaximoPeso()));
					interfazMonitoreoGota.campoTextoMinimoPeso.setText(String.valueOf(estadisticasBalanzaDTO.getMinimoPeso()));
					interfazMonitoreoGota.campoTextoPromedioPeso.setText(String.valueOf(estadisticasBalanzaDTO.getPromedioPeso()));
					interfazMonitoreoGota.campoTextoMedianaPeso.setText(String.valueOf(estadisticasBalanzaDTO.getMedianaPeso()));
					interfazMonitoreoGota.campoTextoModaPeso.setText(String.valueOf(estadisticasBalanzaDTO.getModaPeso()));
					logComunicacionSerialBalanza.info("N�mero de Iteraciones: " + contadorEjecuciones);
					long end = System.currentTimeMillis();
					NumberFormat formatter = new DecimalFormat("#0.00000");
					String tiempoEjecucion = formatter.format((end - start) / 1000d);
					interfazMonitoreoGota.campoTextoTiempoTomaDatosRegPeso.setText(String.valueOf(tiempoEjecucion));
					logComunicacionSerialBalanza.info("Tiempo de ejecución: " + tiempoEjecucion + " segundos.");
					// administradorCacheBalanza.shutdown();
				}
			}
			catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Se ha presentado un error al realizar la transmisi�n de datos desde la balanza. ");
				logComunicacionSerialBalanza.error(ex);
			}
		}
	}

	/**
	 * 
	 * @param listaSumatoriaRegistrosBalanza
	 * @return
	 */
	public EstadisticasBalanzaDTO mapearEstadisticasBalanza(List<String> listaSumatoriaRegistrosBalanza) {
		EstadisticasBalanzaDTO estadisticasBalanzaDTO = new EstadisticasBalanzaDTO();
		estadisticasBalanzaDTO.setTotalPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosBalanza)), 5));
		estadisticasBalanzaDTO.setMaximoPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosBalanza)), 5));
		estadisticasBalanzaDTO.setMinimoPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosBalanza)), 5));
		estadisticasBalanzaDTO.setPromedioPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosBalanza)), 5));
		estadisticasBalanzaDTO.setMedianaPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosBalanza)), 5));
		estadisticasBalanzaDTO.setModaPeso(funcionesUtilitarias.Redondear(NumberUtils.toDouble(manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosBalanza)), 5));
		return estadisticasBalanzaDTO;
	}
	
	/**
	 * Gets the peso dao.
	 * @return the peso dao
	 */
	public BalanzaDTO getPesoDTO() {
		return pesoDTO;
	}
	
	/**
	 * Sets the peso dao.
	 * @param pesoDTO the new peso dao
	 */
	public void setPesoDTO(BalanzaDTO pesoDTO) {
		this.pesoDTO = pesoDTO;
	}
}