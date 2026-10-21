/*
 *
 */
package com.damaya.proyinv.constantes;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface ConstantesComunicacionSerial.
 */
public enum ConstantesComunicacionSerial {
	TIMEOUT(2000), COMUNICACION_PUERTO_SERIAL(CommPortIdentifier.PORT_SERIAL), DATO_DISPONIBLE(SerialPortEvent.DATA_AVAILABLE), TASA_BAUDIOS_110(110), TASA_BAUDIOS_300(300), TASA_BAUDIOS_600(600), TASA_BAUDIOS_1200(1200), TASA_BAUDIOS_2400(2400), TASA_BAUDIOS_4800(4800), TASA_BAUDIOS_9600(9600), TASA_BAUDIOS_14400(14400), TASA_BAUDIOS_19200(19200), TASA_BAUDIOS_38400(38400), TASA_BAUDIOS_57600(57600), TASA_BAUDIOS_115200(115200), TASA_BAUDIOS_230400(230400), TASA_BAUDIOS_460800(460800), TASA_BAUDIOS_921600(921600), BITS_DATOS_5(SerialPort.DATABITS_5), BITS_DATOS_6(SerialPort.DATABITS_6), BITS_DATOS_7(SerialPort.DATABITS_7), BITS_DATOS_8(SerialPort.DATABITS_8), BITS_PARADA_1(SerialPort.STOPBITS_1), BITS_PARADA_1_5(SerialPort.STOPBITS_1_5), BITS_PARADA_2(SerialPort.STOPBITS_2), SIN_PARIDAD(SerialPort.PARITY_NONE), PARIDAD_IMPAR(SerialPort.PARITY_EVEN), PARIDAD_MARCA(SerialPort.PARITY_MARK), PARIDAD_PAR(SerialPort.PARITY_ODD), PARIDAD_ESPACIA(SerialPort.PARITY_SPACE);
	private int constanteComunicacionSerial;

	private ConstantesComunicacionSerial(int constanteComunicacionSerial) {
		this.constanteComunicacionSerial = constanteComunicacionSerial;
	}

	public int getConstanteComunicacionSerial() {
		return this.constanteComunicacionSerial;
	}
}
