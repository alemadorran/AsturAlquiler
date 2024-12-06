package persistenciaDAO.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logger.LoggerAplicacion;
import modelo.Ciudad;
import persistencia.ConexionJDBC;
import persistenciaDAO.ICiudadDAO;

public class CiudadDAOimpl implements ICiudadDAO{

    private static String useDB = "use asturalquiler";
	
	@Override
	public boolean create(Ciudad c) {

		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "INSERT INTO ciudad (codigo, nombre) VALUES('" + c.getCodigo() + "', '" + c.getNombre() + "')";
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			int resultado = st.executeUpdate(sentencia);
			if(resultado == 0) {
				return false;
			}
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			return false;
		}finally {
			conexionJDBC.closeConnection();
		}
		
		return true;
	}

	@Override
	public Ciudad read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ciudad> readAll() {
		
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "SELECT * FROM ciudad";
		ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			rs = st.executeQuery(sentencia);
			
			while (rs.next()) {
				
				Ciudad ciudad = new Ciudad(
						rs.getString(1), //Código
						rs.getString(2) // Nombre
						);
				ciudades.add(ciudad);
			}
			
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
		}finally {
			conexionJDBC.closeConnection();
		}
		
		return ciudades;
	}

	@Override
	public boolean update(Ciudad ciudad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean detele(Ciudad c) {
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
	    String sentencia = "DELETE FROM ciudad WHERE codigo = '" + c.getCodigo() + "'";
	    try {
	    	conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			
	        int resultado = st.executeUpdate(sentencia);
	        if(resultado == 0) {
	        	return false; 
	        }
			
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			return false;
		}finally {
			conexionJDBC.closeConnection();
		}
		return true;
	}

}
