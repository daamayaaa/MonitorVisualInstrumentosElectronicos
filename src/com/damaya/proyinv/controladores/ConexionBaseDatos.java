package com.damaya.proyinv.controladores;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ConexionBaseDatos {
	final static Logger logger = Logger.getLogger(ConexionBaseDatos.class);
	
	@SuppressWarnings("finally")
	public Connection establecerConexionBD() {
		Connection conexion = null;
		try {
			logger.info("Conexion ha sido exitosa");
			conexion = ProveedorDataSource.obtenerConexionBaseDatos();
			return conexion;
		}
		catch (SQLException e) {
			logger.error("Se ha presentado un error al establecer la conexión con la base de datos: " + e.getMessage());
		}
		finally {
			if (conexion != null) {
				try {
					conexion.close(); // Returns the connection to the pool
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}