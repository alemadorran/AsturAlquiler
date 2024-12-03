package persistencia;

import java.util.ArrayList;
import java.util.List;

import excepciones.ConcesionarioNoEncontradoException;
import excepciones.ErrorConexionJDBCException;
import excepciones.MensajesError;

import java.sql.*;

import logger.LoggerAplicacion;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;

public class GestorJDBC {
	
    private static String useDB = "use asturalquiler";

	
	public static boolean crearCiudad(Ciudad c) {
		
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

	public static List<Ciudad> leerCiudades() {
		
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

	public static boolean borrarCiudad(String codigo) {
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
	    String sentencia = "DELETE FROM ciudad WHERE codigo = '" + codigo + "'";
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

	public static boolean crearConcesionario(Concesionario c) {
	
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

	public static ArrayList<Concesionario> leerConcesionarios() {
		
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

	public static boolean actualizarConcesionario(Concesionario c) throws ErrorConexionJDBCException {
		
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
			throw new ErrorConexionJDBCException("Un error inesperado se ha producido. Inténtelo más tarde.");
		}finally {
			conexionJDBC.closeConnection();
		}
		return true;
		
	}

	public static boolean borrarConcesionario(Concesionario c) throws ErrorConexionJDBCException {
		
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
			throw new ErrorConexionJDBCException(MensajesError.ERROR_CONEXION_BASE_DE_DATOS);
		}finally {
			conexionJDBC.closeConnection();
		}
		return true;
		
	}

	public static boolean crearCoche(Coche c) throws ErrorConexionJDBCException {
		
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "INSERT INTO ciudad (codigo, nombre) VALUES('" + c.getMatricula() + "', '" + c.getMarca() + "', '"
							+ c.getModelo() + "', '" + c.getCodigoConcesionario() + "')";
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
			throw new ErrorConexionJDBCException(MensajesError.ERROR_CONEXION_BASE_DE_DATOS);
		}finally {
			conexionJDBC.closeConnection();
		}
		
		return true;

	}
	
}
