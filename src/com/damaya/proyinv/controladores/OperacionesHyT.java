package com.damaya.proyinv.controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.log4j.Logger;
import com.damaya.proyinv.dao.IRequestHyTDAO;

public class OperacionesHyT implements IRequestHyTDAO {
	ConexionBaseDatos conexionBD = new ConexionBaseDatos();
	Connection connection = conexionBD.establecerConexionBD();
	final static Logger logger = Logger.getLogger(OperacionesHyT.class);
	
	@Override
	public void ingresarRegistroHyT(String fechaHoraRegistro, float temperaturaSensor1, float temperaturaSensor2, float humedadSensor3) {
		try {
			String sql = "INSERT INTO datos_hyt(fecha_hora_registro, temperatura_sensor_1, temperatura_sensor_2, humedad_sensor_3) VALUES(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, fechaHoraRegistro);
			ps.setFloat(2, temperaturaSensor1);
			ps.setFloat(3, temperaturaSensor2);
			ps.setFloat(4, humedadSensor3);
			ps.executeUpdate();
			logger.info("El los datos tomados de la balanza se han a�adido correctamente a la base de datos");
		}
		catch (SQLException e) {
			logger.error("Ha ocurrido un error en el controlador " + e.getMessage());
		}
	}
	
	public void ingresarRegistrosHyT(JTable table) {
		PreparedStatement prepareStatement = null;
		try {
			int numeroFilas = table.getRowCount();
			connection.setAutoCommit(false);
			String sql = "INSERT INTO datos_hyt(fecha_hora_registro, temperatura_sensor_1, temperatura_sensor_2, humedad_sensor_3) VALUES(?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			for (int row = 0; row < numeroFilas; row++) {
				String fechaHoraRegistro = String.valueOf(table.getValueAt(row, 0));
				float temperatura_sensor_1 = Float.parseFloat(table.getValueAt(row, 1).toString());
				float temperatura_sensor_2 = Float.parseFloat(table.getValueAt(row, 2).toString());
				float humedad_sensor_3 = Float.parseFloat(table.getValueAt(row, 3).toString());
				prepareStatement.setString(1, fechaHoraRegistro);
				prepareStatement.setFloat(2, temperatura_sensor_1);
				prepareStatement.setFloat(3, temperatura_sensor_2);
				prepareStatement.setFloat(4, humedad_sensor_3);
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
			connection.commit();
			logger.info("Los datos han sido guardados exitosamente. ");
		}
		catch (Exception e) {
			logger.error("Ha ocurrido un error en el controlador " + e.getMessage());
		}
		finally {
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null)
					connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void consultarRegistroHyT(JTable tablaDatosHyT) {
		DefaultTableModel model = new DefaultTableModel();
		TableRowSorter<TableModel> orderTable = new TableRowSorter<TableModel>(model);
		tablaDatosHyT.setRowSorter(orderTable);
		String sql = "SELECT * FROM datos_hyt";
		model.addColumn("ID");
		model.addColumn("Fecha y Hora");
		model.addColumn("Temperatura Sensor 1");
		model.addColumn("Temperatura Sensor 2");
		model.addColumn("Humedad Sensor 3");
		tablaDatosHyT.setModel(model);
		String[] data = new String[5];
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				data[0] = rs.getString(1);
				data[1] = rs.getString(2);
				data[2] = rs.getString(3);
				data[3] = rs.getString(4);
				data[4] = rs.getString(5);
				model.addRow(data);
			}
		}
		catch (SQLException e) {
			logger.error("Ha ocurrido un error en el controlador " + e.getMessage());
		}
	}
}