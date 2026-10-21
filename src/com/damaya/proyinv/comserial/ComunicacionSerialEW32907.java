/*
 *
 */
package com.damaya.proyinv.comserial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.damaya.proyinv.comserial.interfaces.IComunicacionSerial;
import com.damaya.proyinv.constantes.ConstantesComunicacionSerial;
import com.damaya.proyinv.constantes.ConstantesGenerales;
import com.damaya.proyinv.dto.EW32907DTO;
import com.damaya.proyinv.estadisticas.ManejadorEstadisticasVariables;
import com.damaya.proyinv.gui.InterfazGraficaMonitoreoInstrumentosElectronicos;

// TODO: Auto-generated Javadoc
/**
 * The Class ComunicacionSerialEW32907.
 */
public class ComunicacionSerialEW32907 implements IComunicacionSerial, SerialPortEventListener, Serializable {
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
	/** The puerto serial componente presion flujo masico y temperatura conectado. */
	private boolean puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado = false;
	// Traza de seguimiento para la comunicaci�n serial.
	/** The log comunicacion serial e w32907. */
	// private static Logger logger = Logger.getLogger(ComunicacionSerialEW32907.class);
	private static Logger logComunicacionSerialEW32907 = Logger.getLogger("comSerialEW32907");
	/** The datos e w2907 dao. */
	// Datos que se obtienen desde el Arduino.
	public EW32907DTO datosEW2907DAO = new EW32907DTO();
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
	
	/**
	 * Instantiates a new comunicacion serial e w32907.
	 * @param vistaModeloDatos the vista modelo datos
	 */
	public ComunicacionSerialEW32907(InterfazGraficaMonitoreoInstrumentosElectronicos vistaModeloDatos) {
		this.interfazMonitoreoGota = vistaModeloDatos;
	}
	
	/**
	 * Funcion que permite validar los puertos que se encuentran disponibles para conectar desde ARDUINO.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void verificarPuertoSerial() {
		this.interfazMonitoreoGota.cmbListaPuertosSenPresFluTemp.removeAllItems();
		this.interfazMonitoreoGota.cmbListaTasasBaudiosSenPresFluTemp.removeAllItems();
		this.interfazMonitoreoGota.cmbListaBitsDatosSenPresFluTemp.removeAllItems();
		this.interfazMonitoreoGota.cmbListaParidadSenPresFluTemp.removeAllItems();
		this.interfazMonitoreoGota.cmbListaBitsParadaSenPresFluTemp.removeAllItems();
		this.enumPuertos = CommPortIdentifier.getPortIdentifiers();
		while (this.enumPuertos.hasMoreElements()) {
			CommPortIdentifier puertoActual = (CommPortIdentifier) this.enumPuertos.nextElement();
			if (puertoActual.getPortType() == ConstantesComunicacionSerial.COMUNICACION_PUERTO_SERIAL.getConstanteComunicacionSerial()) {
				this.interfazMonitoreoGota.cmbListaPuertosSenPresFluTemp.addItem(puertoActual.getName());
				this.mapaPuertos.put(puertoActual.getName(), puertoActual);
			}
		}
		this.interfazMonitoreoGota.cmbListaTasasBaudiosSenPresFluTemp.setModel(new DefaultComboBoxModel(this.listaTasasBaudios));
		this.interfazMonitoreoGota.cmbListaBitsDatosSenPresFluTemp.setModel(new DefaultComboBoxModel(this.listaBitsDatos));
		this.interfazMonitoreoGota.cmbListaParidadSenPresFluTemp.setModel(new DefaultComboBoxModel(this.listaBitsParidad));
		this.interfazMonitoreoGota.cmbListaBitsParadaSenPresFluTemp.setModel(new DefaultComboBoxModel(this.listaBitsParada));
	}
	
	/**
	 * Checks if is conectado puerto serial componente presion flujo masico y temperatura.
	 * @return true, if is conectado puerto serial componente presion flujo masico y temperatura
	 */
	public boolean isConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura() {
		return this.puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado;
	}
	
	/**
	 * Sets the conectado puerto serial componente presion flujo masico y temperatura.
	 * @param puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado the new conectado puerto serial componente presion flujo masico y temperatura
	 */
	public void setConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura(boolean puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado) {
		this.puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado = puertoSerialComponentePresionFlujoMasicoYTemperaturaConectado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#conectarComponentePuertoSerial()
	 */
	@Override
	public void conectarComponentePuertoSerial() {
		String puertoSeleccionado = (String) this.interfazMonitoreoGota.cmbListaPuertosSenPresFluTemp.getSelectedItem();
		Integer tasaBaudiosSeleccionada = (Integer) this.interfazMonitoreoGota.cmbListaTasasBaudiosSenPresFluTemp.getSelectedItem();
		Integer bitDatosSeleccionado = (Integer) this.interfazMonitoreoGota.cmbListaBitsDatosSenPresFluTemp.getSelectedItem();
		Integer bitParadaSeleccionado = (Integer) this.interfazMonitoreoGota.cmbListaBitsParadaSenPresFluTemp.getSelectedItem();
		Integer paridadSeleccionada = (Integer) this.interfazMonitoreoGota.cmbListaParidadSenPresFluTemp.getSelectedItem();
		this.identificadorPuerto = this.mapaPuertos.get(puertoSeleccionado);
		CommPort puertoComunicacion = null;
		try {
			puertoComunicacion = this.identificadorPuerto.open("PuertoCapturaDatosEW32907", ConstantesComunicacionSerial.TIMEOUT.getConstanteComunicacionSerial());
			this.puertoSerial = (SerialPort) puertoComunicacion;
			this.puertoSerial.setSerialPortParams(tasaBaudiosSeleccionada, bitDatosSeleccionado, bitParadaSeleccionado, paridadSeleccionada);
			this.puertoSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
			this.puertoSerial.setRTS(true);
			setConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura(true);
			JOptionPane.showMessageDialog(null, puertoSeleccionado + " ha sido abierto exitosamente.");
			this.interfazMonitoreoGota.lblDesconectarPuertoSerialPresFluTemp.setText("El puerto serial ha sido conectado. ");
		}
		catch (UnsupportedCommOperationException e) {
			JOptionPane.showMessageDialog(null, "Ha fallado la comunicacion con los parametros seleccionadors: " + e.toString() + ")");
			e.printStackTrace();
		}
		catch (PortInUseException e) {
			JOptionPane.showMessageDialog(null, puertoSeleccionado + " esta en uso. (" + e.toString() + ")");
			logComunicacionSerialEW32907.error(e);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha fallado la conexi�n al puerto:  " + puertoSeleccionado + ".");
			logComunicacionSerialEW32907.error(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#desconectarComponentePuertoSerial()
	 */
	@Override
	public void desconectarComponentePuertoSerial() {
		try {
			this.puertoSerial.removeEventListener();
			this.puertoSerial.close();
			this.flujoSerialEntrada.close();
			setConectadoPuertoSerialComponentePresionFlujoMasicoYTemperatura(false);
			// Limpia los campos de textos b�sicos.
			this.interfazMonitoreoGota.campoTextoPresion.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoTemperaturaC.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoVolumenFlujo.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoFlujoMasico.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			// Limpia las estad�sticas.
			this.interfazMonitoreoGota.campoTextoMaximoPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMinimoPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoPromedioPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoTotalPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMedianaPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoModaPresionAbsoluta.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			// Limpia las estad�sticas.
			this.interfazMonitoreoGota.campoTextoMaximoTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMinimoTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoPromedioTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoTotalTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMedianaTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoModaTemperaturaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			// Limpia las estad�sticas.
			this.interfazMonitoreoGota.campoTextoMaximoVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMinimoVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoPromedioVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoTotalVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMedianaVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoModaVolumenMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			// Limpia las estad�sticas.
			this.interfazMonitoreoGota.campoTextoMaximoFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMinimoFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoPromedioFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoTotalFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoMedianaFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			this.interfazMonitoreoGota.campoTextoModaFlujoMasaEW2907.setText(ConstantesGenerales.CADENA_VACIA.getConstanteGeneral());
			// Fin de limpieza.
			JOptionPane.showMessageDialog(null, "El puerto ha sido cerrado exitosamente. ");
			this.interfazMonitoreoGota.lblDesconectarPuertoSerialPresFluTemp.setText("El puerto serial ha sido desconectado. ");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cerrar el puerto " + this.puertoSerial.getName() + "(" + e.toString() + ")");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#inicializarFujoEntradaYSalida()
	 */
	@Override
	public boolean inicializarFujoEntradaYSalida() {
		try {
			this.flujoSerialEntrada = this.puertoSerial.getInputStream();
			return true;
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Se ha presentado un error al abrir los flujos de E/S. (" + e.toString() + ")");
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
			this.puertoSerial.addEventListener(this);
			this.puertoSerial.notifyOnDataAvailable(true);
		}
		catch (TooManyListenersException e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
	
	/**
	 * Split string2 single alpha array.
	 * @param s the s
	 * @return the string[]
	 */
	public String[] splitString2SingleAlphaArray(String s) {
		return s.split("[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#serialEvent(gnu.io.SerialPortEvent)
	 */
	@Override
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == ConstantesComunicacionSerial.DATO_DISPONIBLE.getConstanteComunicacionSerial()) {
			try {
				logComunicacionSerialEW32907.info("[INICIO] Se ha inicializado la conexi�n al dispositivo que realiza la toma de datos del sensor de flujo masico.");
				byte[] buffer = new byte[19200];
				int data;
				String resultadoAsString = "";
				int cola = 0;
				while ((data = this.flujoSerialEntrada.read()) != -1) {
					if (data == '\n') {
						resultadoAsString = new String(buffer, 0, cola);
						logComunicacionSerialEW32907.info("[SAL1]:" + resultadoAsString);
						cola = 0;
					}
					else {
						buffer[cola] = (byte) data;
						cola++;
					}
				}
				if (cola != 0) {
					resultadoAsString = new String(buffer, 0, cola);
					logComunicacionSerialEW32907.info("[SAL2]:" + resultadoAsString);
					cola = 0;
				}
				String[] stringFlujoMasico = splitString2SingleAlphaArray(resultadoAsString);
				String cadena1 = stringFlujoMasico[0];
				String cadena2 = stringFlujoMasico[1];
				String cadena3 = stringFlujoMasico[2];
				String cadena4 = stringFlujoMasico[3];
				String cadena5 = stringFlujoMasico[4];
				String cadena6 = stringFlujoMasico[5];
				this.datosEW2907DAO.setPresionAbsolutaPSIA(Float.parseFloat(cadena1));
				this.datosEW2907DAO.setTemperatura(Float.parseFloat(cadena2));
				this.datosEW2907DAO.setVolumenFlujo(Float.parseFloat(cadena3));
				this.datosEW2907DAO.setFlujoMasa(Float.parseFloat(cadena4));
				this.datosEW2907DAO.setValorPuestaPunto(Float.parseFloat(cadena5));
				this.datosEW2907DAO.setGas(cadena6);
				this.interfazMonitoreoGota.campoTextoPresion.setText(String.valueOf(this.datosEW2907DAO.getPresionAbsolutaPSIA()));
				this.interfazMonitoreoGota.campoTextoTemperaturaC.setText(String.valueOf(this.datosEW2907DAO.getTemperatura()));
				this.interfazMonitoreoGota.campoTextoVolumenFlujo.setText(String.valueOf(this.datosEW2907DAO.getVolumenFlujo()));
				this.interfazMonitoreoGota.campoTextoFlujoMasico.setText(String.valueOf(this.datosEW2907DAO.getFlujoMasa()));
				String salidaEsperada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
				this.interfazMonitoreoGota.modeloPFMyT.addRow(new Object[] { salidaEsperada, this.datosEW2907DAO.getPresionAbsolutaPSIA(), this.datosEW2907DAO.getTemperatura(), this.datosEW2907DAO.getVolumenFlujo(), this.datosEW2907DAO.getFlujoMasa(), this.datosEW2907DAO.getValorPuestaPunto(), this.datosEW2907DAO.getGas() });
				List<String> listaSumatoriaRegistrosPAEW2907 = new ArrayList<String>();
				this.manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosPAEW2907, this.interfazMonitoreoGota.modeloPFMyT, 1);
				this.interfazMonitoreoGota.campoTextoTotalPresionAbsoluta.setText(this.manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosPAEW2907));
				this.interfazMonitoreoGota.campoTextoMaximoPresionAbsoluta.setText(this.manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosPAEW2907));
				this.interfazMonitoreoGota.campoTextoMinimoPresionAbsoluta.setText(this.manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosPAEW2907));
				this.interfazMonitoreoGota.campoTextoPromedioPresionAbsoluta.setText(this.manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosPAEW2907));
				this.interfazMonitoreoGota.campoTextoMedianaPresionAbsoluta.setText(this.manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosPAEW2907));
				this.interfazMonitoreoGota.campoTextoModaPresionAbsoluta.setText(this.manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosPAEW2907));
				List<String> listaSumatoriaRegistrosTempEW2907 = new ArrayList<String>();
				this.manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosTempEW2907, this.interfazMonitoreoGota.modeloPFMyT, 2);
				this.interfazMonitoreoGota.campoTextoTotalTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosTempEW2907));
				this.interfazMonitoreoGota.campoTextoMaximoTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosTempEW2907));
				this.interfazMonitoreoGota.campoTextoMinimoTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosTempEW2907));
				this.interfazMonitoreoGota.campoTextoPromedioTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosTempEW2907));
				this.interfazMonitoreoGota.campoTextoMedianaTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosTempEW2907));
				this.interfazMonitoreoGota.campoTextoModaTemperaturaEW2907.setText(this.manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosTempEW2907));
				List<String> listaSumatoriaRegistrosVolumenMasaEW2907 = new ArrayList<String>();
				this.manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosVolumenMasaEW2907, this.interfazMonitoreoGota.modeloPFMyT, 3);
				this.interfazMonitoreoGota.campoTextoTotalVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMaximoVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMinimoVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				this.interfazMonitoreoGota.campoTextoPromedioVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMedianaVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				this.interfazMonitoreoGota.campoTextoModaVolumenMasaEW2907.setText(this.manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosVolumenMasaEW2907));
				List<String> listaSumatoriaRegistrosFlujoMasaEW2907 = new ArrayList<String>();
				this.manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosFlujoMasaEW2907, this.interfazMonitoreoGota.modeloPFMyT, 4);
				this.interfazMonitoreoGota.campoTextoTotalFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMaximoFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMinimoFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
				this.interfazMonitoreoGota.campoTextoPromedioFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
				this.interfazMonitoreoGota.campoTextoMedianaFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
				this.interfazMonitoreoGota.campoTextoModaFlujoMasaEW2907.setText(this.manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosFlujoMasaEW2907));
			}
			catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Se ha presentado un error al realizar la transmisi�n de datos para el sensor de flujo masico. ");
				logComunicacionSerialEW32907.error(ex);
			}
		}
	}
}