package com.damaya.proyinv.controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import com.damaya.proyinv.dao.IRequestBalanzaDAO;

public class OperacionesBalanza implements IRequestBalanzaDAO {
	final static Logger logger = Logger.getLogger(OperacionesBalanza.class);
	
	@Override
	public void ingresarRegistroBalanza(String fechaHoraRegistro, float pesoBalanza) {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			ConexionBaseDatos conexionBD = new ConexionBaseDatos();
			connection = conexionBD.establecerConexionBD();
			String sql = "INSERT INTO datos_balanza(fecha_hora_registro, peso_balanza) VALUES(?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, fechaHoraRegistro);
			ps.setFloat(2, pesoBalanza);
			int numeroColumnasAfectadas = ps.executeUpdate();
			logger.info("Los datos tomados de la balanza se han añadido correctamente a la base de datos, " + numeroColumnasAfectadas + " registro insertado.");
			if (numeroColumnasAfectadas > 0) {
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						generatedKeys.getInt(1);
						logger.info("ID generado obtenido: " + generatedKeys.getInt(1));
					}
				}
				catch (SQLException e) {
					logger.error("Se ha presentado un error insertando el registro: " + e.getMessage());
				}
			}
		}
		catch (SQLException e) {
			logger.error("Ha ocurrido un error en el controlador " + e.getMessage());
		}
		finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close(); // Returns connection to the pool
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void consultarRegistroBalanza(JTable table) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actualizarRegistroBalanza(float pesoGota, int id) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void eliminarRegistroBalanza(int id) {
		// TODO Auto-generated method stub
	}
}
