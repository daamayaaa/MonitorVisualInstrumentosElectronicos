package com.damaya.proyinv.controladores;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import com.damaya.proyinv.constantes.ConstantesConexionBD;

public class ProveedorDataSource {
	private static BasicDataSource origenDatos;
	
	public static BasicDataSource obtenerInstanciaBaseDatos() throws SQLException {
		if (origenDatos == null) {
			origenDatos = new BasicDataSource();
			origenDatos.setDriverClassName(ConstantesConexionBD.DRIVER_JDBC);
			origenDatos.setUrl("jdbc:mysql://" + ConstantesConexionBD.HostBD + ":" + ConstantesConexionBD.puertoBD + "/" + ConstantesConexionBD.MonitorBD);
			origenDatos.setUsername(ConstantesConexionBD.usuarioBD);
			origenDatos.setPassword(ConstantesConexionBD.passwordBD);
			origenDatos.setInitialSize(5); // Initial number of connections
			origenDatos.setMaxTotal(20); // Número máximo de conexiones.
			origenDatos.setMaxIdle(10); // Número máximo de conexiones inactivas.
			origenDatos.setMinIdle(5); // Número mínimo de conexiones inactivas.
		}
		return origenDatos;
	}
	
	public static Connection obtenerConexionBaseDatos() throws SQLException {
		return origenDatos.getConnection();
	}
}
