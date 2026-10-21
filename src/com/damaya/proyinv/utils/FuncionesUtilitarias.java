package com.damaya.proyinv.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FuncionesUtilitarias implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String getNombrePc() {
		String nombrePC = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			nombrePC = address.getHostName();
		}
		catch (Exception e) {
			Logger.getLogger(FuncionesUtilitarias.class.getName()).log(Level.SEVERE, null, e);
		}
		return nombrePC;
	}
	
	public static String getIpPc() {
		String direccionIP = null;
		try {
			direccionIP = InetAddress.getLocalHost().getHostAddress();
		}
		catch (Exception ex) {
			Logger.getLogger(FuncionesUtilitarias.class.getName()).log(Level.SEVERE, null, ex);
		}
		return direccionIP;
	}
	
	public void abrirArchivoGenerado(String ruta) {
		String archivoLocal = new String(ruta);
		try {
			File archivo = new File(archivoLocal);
			if (archivo.exists()) {
				Desktop.getDesktop().open(archivo);
			}
			else {
				JOptionPane.showMessageDialog(null, "No se pudo encontrar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (IllegalArgumentException ae) {
			ae.printStackTrace();
		}
	}
	
	public double Redondear(double numero, int digitos) {
		int cifras = (int) Math.pow(10, digitos);
		return Math.rint(numero * cifras) / cifras;
	}
}
