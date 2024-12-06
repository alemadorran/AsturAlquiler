package persistenciaDAO.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import excepciones.ErrorConexionJDBCException;
import excepciones.MensajesError;
import logger.LoggerAplicacion;
import modelo.Concesionario;
import persistenciaDAO.ConcesionarioDAO;
import persistenciaDAO.ConexionJDBC;

public class ConcesionarioDAOimpl implements ConcesionarioDAO{

    private static String useDB = "use asturalquiler";
	
	@Override
	public boolean create(Concesionario c) {
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "INSERT INTO concesionario (codigo_concesionario, nombre, codigo_ciudad) VALUES('" + c.getCodigoConcesionario() + "', '" + c.getNombre() + "',"+ "'" 
							+  c.getCodigoCiudad() + "')";
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
	public Concesionario read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Concesionario> readAll() {
	ArrayList<Concesionario> listaConcesionarios = new ArrayList<>(); 

		
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "SELECT * FROM concesionario";
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			rs = st.executeQuery(sentencia);
			
			while (rs.next()) {
				
				Concesionario concesionario = new Concesionario(
						rs.getString(1), 
						rs.getString(2),
						rs.getString(3));
				listaConcesionarios.add(concesionario);
			}
			
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
		}finally {
			conexionJDBC.closeConnection();
		}
		
		return listaConcesionarios;
	}

	@Override
	public boolean update(Concesionario c) {
		
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "UPDATE concesionario SET nombre =" + " '" + c.getNombre() + "'" 
				            + " WHERE codigo_concesionario = " + "'" + c.getCodigoConcesionario() + "';";
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
			try {
				throw new ErrorConexionJDBCException("Un error inesperado se ha producido. Inténtelo más tarde.");
			} catch (ErrorConexionJDBCException e1) {
				System.out.println(e1.getMessage());
				LoggerAplicacion.logError(e1);
			}
		}finally {
			conexionJDBC.closeConnection();
		}
		return true;
	}

	@Override
	public boolean detele(Concesionario c) {
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
	    String sentencia = "DELETE FROM concesionario WHERE codigo_concesionario = '" + c.getCodigoConcesionario() + "'";
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
			try {
				throw new ErrorConexionJDBCException(MensajesError.ERROR_CONEXION_BASE_DE_DATOS);
			} catch (ErrorConexionJDBCException e1) {
				System.out.println(e1.getMessage());
				LoggerAplicacion.logError(e1);
			}
		}finally {
			conexionJDBC.closeConnection();
		}
		return true;
	}

}
