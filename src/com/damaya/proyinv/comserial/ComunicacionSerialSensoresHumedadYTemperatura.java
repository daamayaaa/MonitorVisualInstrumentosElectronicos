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
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_DATOS_5;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_DATOS_6;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_DATOS_7;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_DATOS_8;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_PARADA_1;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_PARADA_1_5;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.BITS_PARADA_2;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.COMUNICACION_PUERTO_SERIAL;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.PARIDAD_ESPACIA;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.PARIDAD_IMPAR;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.PARIDAD_MARCA;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.PARIDAD_PAR;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.SIN_PARIDAD;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_110;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_115200;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_1200;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_14400;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_19200;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_230400;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_2400;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_300;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_38400;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_460800;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_4800;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_57600;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_600;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_921600;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TASA_BAUDIOS_9600;
import static com.damaya.proyinv.constantes.ConstantesComunicacionSerial.TIMEOUT;
import static com.damaya.proyinv.constantes.ConstantesGenerales.CADENA_VACIA;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.damaya.proyinv.comserial.interfaces.IComunicacionSerial;
import com.damaya.proyinv.dto.HumedadTemperaturaDTO;
import com.damaya.proyinv.estadisticas.ManejadorEstadisticasVariables;
import com.damaya.proyinv.gui.InterfazGraficaMonitoreoInstrumentosElectronicos;

/**
 * The Class ComunicacionSerialSensoresHumedadYTemperatura.
 */
public class ComunicacionSerialSensoresHumedadYTemperatura implements IComunicacionSerial, SerialPortEventListener, Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The interfaz monitoreo gota. */
	private InterfazGraficaMonitoreoInstrumentosElectronicos interfazMonitoreoVisual = null;
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
	/** The puerto serial sensor hy t conectado. */
	private boolean puertoSerialSensorHyTConectado = false;
	/** The log comunicacion serial sen hy t. */
	// Traza de seguimiento para la comunicaci�n serial.
	private static Logger logComunicacionSerialSenHyT = Logger.getLogger("comSerialSenHyT");
	/** The entrada serial datos. */
	// Datos que se obtienen desde el Arduino.
	private String entradaSerialDatos = CADENA_VACIA.getConstanteGeneral();
	/** The bt. */
	// Bit de transmision de datos (2 -> Temperatura S1, 1 -> Temperatura S2, 0 -> Humedad Sensor 3).
	private int bt = 0;
	/** The humedad temperatura dao. */
	// DAO para manejar los datos de humedad y temperatura.
	public HumedadTemperaturaDTO humedadTemperaturaDTO = new HumedadTemperaturaDTO();
	/** The lista tasas baudios. */
	// Listas de valores para los datos seriales.
	private Integer[] listaTasasBaudios = new Integer[] { TASA_BAUDIOS_110.getConstanteComunicacionSerial(), TASA_BAUDIOS_300.getConstanteComunicacionSerial(), TASA_BAUDIOS_600.getConstanteComunicacionSerial(), TASA_BAUDIOS_1200.getConstanteComunicacionSerial(), TASA_BAUDIOS_2400.getConstanteComunicacionSerial(), TASA_BAUDIOS_4800.getConstanteComunicacionSerial(), TASA_BAUDIOS_9600.getConstanteComunicacionSerial(), TASA_BAUDIOS_14400.getConstanteComunicacionSerial(), TASA_BAUDIOS_19200.getConstanteComunicacionSerial(), TASA_BAUDIOS_38400.getConstanteComunicacionSerial(), TASA_BAUDIOS_57600.getConstanteComunicacionSerial(), TASA_BAUDIOS_115200.getConstanteComunicacionSerial(), TASA_BAUDIOS_230400.getConstanteComunicacionSerial(), TASA_BAUDIOS_460800.getConstanteComunicacionSerial(), TASA_BAUDIOS_921600.getConstanteComunicacionSerial() };
	/** The lista bits datos. */
	private Integer[] listaBitsDatos = new Integer[] { BITS_DATOS_5.getConstanteComunicacionSerial(), BITS_DATOS_6.getConstanteComunicacionSerial(), BITS_DATOS_7.getConstanteComunicacionSerial(), BITS_DATOS_8.getConstanteComunicacionSerial() };
	/** The lista bits paridad. */
	private Integer[] listaBitsParidad = new Integer[] { SIN_PARIDAD.getConstanteComunicacionSerial(), PARIDAD_IMPAR.getConstanteComunicacionSerial(), PARIDAD_MARCA.getConstanteComunicacionSerial(), PARIDAD_PAR.getConstanteComunicacionSerial(), PARIDAD_ESPACIA.getConstanteComunicacionSerial() };
	/** The lista bits parada. */
	private Integer[] listaBitsParada = new Integer[] { SIN_PARIDAD.getConstanteComunicacionSerial(), BITS_PARADA_1.getConstanteComunicacionSerial(), BITS_PARADA_1_5.getConstanteComunicacionSerial(), BITS_PARADA_2.getConstanteComunicacionSerial() };
	/** The manejador estadisticas. */
	// Manejador de Estad�sticas.
	private ManejadorEstadisticasVariables manejadorEstadisticas = new ManejadorEstadisticasVariables();
	
	/**
	 * Instantiates a new comunicacion serial sensores humedad y temperatura.
	 * @param vistaModeloDatos the vista modelo datos
	 */
	public ComunicacionSerialSensoresHumedadYTemperatura(InterfazGraficaMonitoreoInstrumentosElectronicos vistaModeloDatos) {
		interfazMonitoreoVisual = vistaModeloDatos;
		BasicConfigurator.configure();
	}
	
	/**
	 * Funcion que permite validar los puertos que se encuentran disponibles para conectar desde ARDUINO.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void verificarPuertoSerial() {
		interfazMonitoreoVisual.cmbListaPuertosSensorHT.removeAllItems();
		interfazMonitoreoVisual.cmbListaTasasBaudiosSensorHT.removeAllItems();
		interfazMonitoreoVisual.cmbListaBitsDatosSensorHT.removeAllItems();
		interfazMonitoreoVisual.cmbListaParidadSensorHT.removeAllItems();
		interfazMonitoreoVisual.cmbListaBitsParadaSensorHT.removeAllItems();
		enumPuertos = CommPortIdentifier.getPortIdentifiers();
		while (enumPuertos.hasMoreElements()) {
			CommPortIdentifier puertoActual = (CommPortIdentifier) enumPuertos.nextElement();
			if (puertoActual.getPortType() == COMUNICACION_PUERTO_SERIAL.getConstanteComunicacionSerial()) {
				interfazMonitoreoVisual.cmbListaPuertosSensorHT.addItem(puertoActual.getName());
				mapaPuertos.put(puertoActual.getName(), puertoActual);
			}
		}
		interfazMonitoreoVisual.cmbListaTasasBaudiosSensorHT.setModel(new DefaultComboBoxModel(listaTasasBaudios));
		interfazMonitoreoVisual.cmbListaBitsDatosSensorHT.setModel(new DefaultComboBoxModel(listaBitsDatos));
		interfazMonitoreoVisual.cmbListaParidadSensorHT.setModel(new DefaultComboBoxModel(listaBitsParidad));
		interfazMonitoreoVisual.cmbListaBitsParadaSensorHT.setModel(new DefaultComboBoxModel(listaBitsParada));
	}
	
	/**
	 * Checks if is conectado puerto serial sensor hy t.
	 * @return true, if is conectado puerto serial sensor hy t
	 */
	public boolean isConectadoPuertoSerialSensorHyT() {
		return puertoSerialSensorHyTConectado;
	}
	
	/**
	 * Sets the conectado puerto serial sensor hy t.
	 * @param puertoSerialSensorHyTConectado the new conectado puerto serial sensor hy t
	 */
	public void setConectadoPuertoSerialSensorHyT(boolean puertoSerialSensorHyTConectado) {
		this.puertoSerialSensorHyTConectado = puertoSerialSensorHyTConectado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.edu.unal.fing.diee.ingelect.proyinv.comserial.interfaces.IComunicacionSerial#conectarComponentePuertoSerial()
	 */
	@Override
	public void conectarComponentePuertoSerial() {
		String puertoSeleccionado = (String) interfazMonitoreoVisual.cmbListaPuertosSensorHT.getSelectedItem();
		Integer tasaBaudiosSeleccionada = (Integer) interfazMonitoreoVisual.cmbListaTasasBaudiosSensorHT.getSelectedItem();
		Integer bitDatosSeleccionado = (Integer) interfazMonitoreoVisual.cmbListaBitsDatosSensorHT.getSelectedItem();
		Integer bitParadaSeleccionado = (Integer) interfazMonitoreoVisual.cmbListaBitsParadaSensorHT.getSelectedItem();
		Integer paridadSeleccionada = (Integer) interfazMonitoreoVisual.cmbListaParidadSensorHT.getSelectedItem();
		identificadorPuerto = mapaPuertos.get(puertoSeleccionado);
		CommPort puertoComunicacion = null;
		try {
			logComunicacionSerialSenHyT.info("Se ha inicializado la conexi�n al dispositivo que realiza la toma de datos de los sensores de humedad y temperatura.");
			puertoComunicacion = identificadorPuerto.open("PuertoCapturaDatosSensoresHyY", TIMEOUT.getConstanteComunicacionSerial());
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
			setConectadoPuertoSerialSensorHyT(true);
			JOptionPane.showMessageDialog(null, puertoSeleccionado + " ha sido abierto exitosamente.");
			interfazMonitoreoVisual.lblDesconexionPuertoSerialSensorHumTemp.setText("El puerto serial ha sido conectado. ");
		}
		catch (UnsupportedCommOperationException e) {
			JOptionPane.showMessageDialog(null, "Ha fallado la comunicacion con los parametros seleccionadors: " + e.toString() + ")");
			logComunicacionSerialSenHyT.error(e);
		}
		catch (PortInUseException e) {
			JOptionPane.showMessageDialog(null, puertoSeleccionado + " esta en uso. (" + e.toString() + ")");
			logComunicacionSerialSenHyT.error(e);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha fallado la conexi�n al puerto:  " + puertoSeleccionado + ".");
			logComunicacionSerialSenHyT.error(e);
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
			setConectadoPuertoSerialSensorHyT(false);
			interfazMonitoreoVisual.campoTextoTemperaturaSensor1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoTemperaturaSensor2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoHumedadSensor3.setText(CADENA_VACIA.getConstanteGeneral());
			// Limpiar datos de conexi�n.
			interfazMonitoreoVisual.campoTextoTotalTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMaximoTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMinimoTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoPromedioTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.tfMedianaTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.tfModaTemperaturaS1.setText(CADENA_VACIA.getConstanteGeneral());
			// Limpiar datos de conexi�n.
			interfazMonitoreoVisual.campoTextoTotalTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMaximoTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMinimoTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoPromedioTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMedianaTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoModaTemperaturaS2.setText(CADENA_VACIA.getConstanteGeneral());
			// Limpiar datos de conexi�n.
			interfazMonitoreoVisual.campoTextoTotalHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMaximoHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMinimoHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoPromedioHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoMedianaHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			interfazMonitoreoVisual.campoTextoModaHumedadS3.setText(CADENA_VACIA.getConstanteGeneral());
			JOptionPane.showMessageDialog(null, "El puerto ha sido cerrado exitosamente. ");
			interfazMonitoreoVisual.lblDesconexionPuertoSerialSensorHumTemp.setText("El puerto serial ha sido desconectado. ");
			entradaSerialDatos = CADENA_VACIA.getConstanteGeneral();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al cerrar el puerto " + puertoSerial.getName() + ".");
			logComunicacionSerialSenHyT.error(e);
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
			logComunicacionSerialSenHyT.error(e);
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
			logComunicacionSerialSenHyT.error(e);
		}
	}
	
	/**
	 * Modificar el envio de datos serial de RS232.
	 * @param evt the evt
	 */
	@Override
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int contadorEjecuciones = 0;
				logComunicacionSerialSenHyT.info("[INICIO] Se ha inicializado la conexi�n al dispositivo que realiza la toma de datos de los sensores de humedad y temperatura.");
				long start = System.currentTimeMillis();
				char datoTomadoPorSerial = (char) flujoSerialEntrada.read();
				if (datoTomadoPorSerial == '\n') {
					if (bt == 0) {
						humedadTemperaturaDTO.setTemperaturaSensor1(Float.parseFloat(entradaSerialDatos));
						interfazMonitoreoVisual.campoTextoTemperaturaSensor1.setText(String.valueOf(humedadTemperaturaDTO.getTemperaturaSensor1()));
						bt = 1;
					}
					else if (bt == 1) {
						humedadTemperaturaDTO.setTemperaturaSensor2(Float.parseFloat(entradaSerialDatos));
						interfazMonitoreoVisual.campoTextoTemperaturaSensor2.setText(String.valueOf(humedadTemperaturaDTO.getTemperaturaSensor2()));
						bt = 2;
					}
					else if (bt == 2) {
						humedadTemperaturaDTO.setHumedadSensor3(Float.parseFloat(entradaSerialDatos));
						interfazMonitoreoVisual.campoTextoHumedadSensor3.setText(String.valueOf(humedadTemperaturaDTO.getHumedadSensor3()));
						bt = 0;
					}
					entradaSerialDatos = CADENA_VACIA.getConstanteGeneral();
					String salidaEsperada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
					interfazMonitoreoVisual.modeloTHT.addRow(new Object[] { salidaEsperada, humedadTemperaturaDTO.getTemperaturaSensor1(), humedadTemperaturaDTO.getTemperaturaSensor2(), humedadTemperaturaDTO.getHumedadSensor3() });
					interfazMonitoreoVisual.campoTextolNumRegTblHyT.setText(String.valueOf(interfazMonitoreoVisual.modeloBalanza.getRowCount()));
					List<String> listaSumatoriaRegistrosTemperaturaSensor1 = new ArrayList<String>();
					manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosTemperaturaSensor1, interfazMonitoreoVisual.modeloPFMyT, 1);
					interfazMonitoreoVisual.campoTextoTotalTemperaturaS1.setText(manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					interfazMonitoreoVisual.campoTextoMaximoTemperaturaS1.setText(manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					interfazMonitoreoVisual.campoTextoMinimoTemperaturaS1.setText(manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					interfazMonitoreoVisual.campoTextoPromedioTemperaturaS1.setText(manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					interfazMonitoreoVisual.tfMedianaTemperaturaS1.setText(manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					interfazMonitoreoVisual.tfModaTemperaturaS1.setText(manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosTemperaturaSensor1));
					List<String> listaSumatoriaRegistrosTemperaturaSensor2 = new ArrayList<String>();
					manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosTemperaturaSensor2, interfazMonitoreoVisual.modeloPFMyT, 2);
					interfazMonitoreoVisual.campoTextoTotalTemperaturaS2.setText(manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					interfazMonitoreoVisual.campoTextoMaximoTemperaturaS2.setText(manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					interfazMonitoreoVisual.campoTextoMinimoTemperaturaS2.setText(manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					interfazMonitoreoVisual.campoTextoPromedioTemperaturaS2.setText(manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					interfazMonitoreoVisual.campoTextoMedianaTemperaturaS2.setText(manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					interfazMonitoreoVisual.campoTextoModaTemperaturaS2.setText(manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosTemperaturaSensor2));
					List<String> listaSumatoriaRegistrosHumedadSensor3 = new ArrayList<String>();
					manejadorEstadisticas.cargarListarModeloDatosTabla(listaSumatoriaRegistrosHumedadSensor3, interfazMonitoreoVisual.modeloPFMyT, 3);
					interfazMonitoreoVisual.campoTextoTotalHumedadS3.setText(manejadorEstadisticas.calcularSumatoriaVariable(listaSumatoriaRegistrosHumedadSensor3));
					interfazMonitoreoVisual.campoTextoMaximoHumedadS3.setText(manejadorEstadisticas.calcularMaximoVariable(listaSumatoriaRegistrosHumedadSensor3));
					interfazMonitoreoVisual.campoTextoMinimoHumedadS3.setText(manejadorEstadisticas.calcularMinimoVariable(listaSumatoriaRegistrosHumedadSensor3));
					interfazMonitoreoVisual.campoTextoPromedioHumedadS3.setText(manejadorEstadisticas.calcularPromedioVariable(listaSumatoriaRegistrosHumedadSensor3));
					interfazMonitoreoVisual.campoTextoMedianaHumedadS3.setText(manejadorEstadisticas.calcularMedianaVariable(listaSumatoriaRegistrosHumedadSensor3));
					interfazMonitoreoVisual.campoTextoModaHumedadS3.setText(manejadorEstadisticas.calcularModaVariable(listaSumatoriaRegistrosHumedadSensor3));
					contadorEjecuciones++;
					logComunicacionSerialSenHyT.info("N�mero de Iteraciones: " + contadorEjecuciones);
					long end = System.currentTimeMillis();
					NumberFormat formatter = new DecimalFormat("#0.00000");
					String tiempoEjecucion = formatter.format((end - start) / 1000d);
					logComunicacionSerialSenHyT.info("Tiempo de ejecución: " + tiempoEjecucion + " segundos.");
				}
				else {
					entradaSerialDatos += String.valueOf(datoTomadoPorSerial);
				}
			}
			catch (IOException ex) {
				JOptionPane.showMessageDialog(null, ex.toString());
				logComunicacionSerialSenHyT.error(ex);
			}
		}
	}
}