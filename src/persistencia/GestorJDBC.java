package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import logger.LoggerAplicacion;
import modelo.Ciudad;

public class GestorJDBC {
	
    private static String useDB = "use asturalquiler";

	
	public static boolean crearCiudad(Ciudad c) {
		
		Connection conexion = null;
		Statement st = null;
		String sentencia = "INSERT INTO ciudad (codigo, nombre) VALUES('" + c.getCodigo() + "', '" + c.getNombre() + "')";
		try {
			conexion = ConexionJDBC.instace().getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			int resultado = st.executeUpdate(sentencia);
			if(resultado == 0) {
				return false;
			}
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
		}
		
		return true;
	}

	public static List<Ciudad> leerCiudades() {
		
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		String sentencia = "SELECT * FROM ciudad";
		ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		try {
			conexion = ConexionJDBC.instace().getConnection();
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
		}
		
		return ciudades;
	}

	public static boolean borrarCiudad(String codigo) {
		Connection conexion = null;
		Statement st = null;
	    String sentencia = "DELETE FROM ciudad WHERE codigo = '" + codigo + "'";
	    try {
			conexion = ConexionJDBC.instace().getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			
	        int resultado = st.executeUpdate(sentencia);
	        if(resultado == 0) {
	        	return false; 
	        }
			
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
		}
		return true;
	}
	
}
