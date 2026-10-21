package com.damaya.proyinv.controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import com.damaya.proyinv.dao.IRequestEW32907DAO;

public class OperacionesEW32907 implements IRequestEW32907DAO {
	final static Logger logger = Logger.getLogger(OperacionesEW32907.class);
	
	@Override
	public void ingresarRegistroEW32907(String fechaHoraRegistro, float presionAbsolutaPSIA, float temperatura, float volumenFlujo, float flujoMasa, float valorPuestaPunto) {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			ConexionBaseDatos conexionBD = new ConexionBaseDatos();
			connection = conexionBD.establecerConexionBD();
			String sql = "INSERT INTO datos_EW32907(fecha_hora_registro, presion_Absoluta_PSIA, temperatura, volumen_flujo, flujo_masa, valor_puesta_punto) VALUES(?,?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, fechaHoraRegistro);
			ps.setFloat(2, presionAbsolutaPSIA);
			ps.setFloat(3, temperatura);
			ps.setFloat(4, volumenFlujo);
			ps.setFloat(5, flujoMasa);
			ps.setFloat(6, valorPuestaPunto);
			ps.executeUpdate();
			int numeroColumnasAfectadas = ps.executeUpdate();
			logger.info("El los datos tomados del instrumento EW32907 se han a�adido correctamente a la base de datos, " + numeroColumnasAfectadas + " registro insertado.");
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
	public void consultarRegistroEW32907(JTable table) {
		// TODO Auto-generated method stub
	}
}
