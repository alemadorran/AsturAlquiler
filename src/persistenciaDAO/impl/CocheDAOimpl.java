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
import modelo.Coche;
import modelo.Concesionario;
import persistencia.ConexionJDBC;
import persistenciaDAO.ICocheDAO;

public class CocheDAOimpl implements ICocheDAO {

    private static String useDB = "use asturalquiler";
	
	@Override
	public boolean create(Coche c) {
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		
		String sentencia = "INSERT INTO coche (matricula, marca, modelo, codigo_concesionario) VALUES('" + c.getMatricula() + "', '" + c.getMarca() + "', '"
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
			gestionErrorPersistencia();
			return false;
		}finally {
			conexionJDBC.closeConnection();
		}
		
		return true;
	}


	@Override
	public Coche read(String matricula) {
		//TODO
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		ResultSet rs = null;
		Coche coche = null;
		String sentencia = "SELECT *  FROM coche WHERE matricula = " + "'" + matricula + "';";
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			rs = st.executeQuery(sentencia);
			
			while (rs.next()) {
				coche = new Coche(
						rs.getString(1), 
						rs.getString(2),
						rs.getString(3),
						rs.getString(4));
			}
			return coche;
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			gestionErrorPersistencia();
		}
		return null;
	}

	@Override
	public List<Coche> readAll() {
		
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		ResultSet rs = null;
		List<Coche>listaCoches = new ArrayList<Coche>();
		String sentencia = "SELECT * FROM coche";
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			st.execute(useDB);
			rs = st.executeQuery(sentencia);
			
			while (rs.next()) {
				
				Coche coche = new Coche(
						rs.getString(1), 
						rs.getString(2),
						rs.getString(3),
						rs.getString(4));
				listaCoches.add(coche);
			}
			
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			gestionErrorPersistencia();
			return null;
		}
		return listaCoches;
	}

	@Override
	public boolean update(Coche coche) {
		
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "UPDATE coche SET codigo_concesionario = " + "'" + coche.getCodigoConcesionario() + "'" 
							+ "WHERE matricula = " + "'" + coche.getMatricula() + "';";
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			int resultado = st.executeUpdate(sentencia);
			if(resultado == 0) {
				return false;
			}
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			gestionErrorPersistencia();
			return false;
		}
		return true;
	}

	@Override
	public boolean detele(Coche coche) {
		
		Connection conexion = null;
		Statement st = null;
		ConexionJDBC conexionJDBC = null;
		String sentencia = "DELETE FROM coche WHERE matricula = " + "'" + coche.getMatricula() + "'";
		
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexion = conexionJDBC.getConnection();
			st = conexion.createStatement(); 
			int resultado = st.executeUpdate(sentencia);
			if(resultado == 0) {
				return false;
			}
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
			gestionErrorPersistencia();
			return false;
		}
		return true;
	}
	
	//Método utilitario para lanzar excepcion personalizada en clase persistencia
	private void gestionErrorPersistencia() {
		try {
			throw new ErrorConexionJDBCException(MensajesError.ERROR_EN_BASE_DE_DATOS);
		} catch (ErrorConexionJDBCException e1) {
			System.out.println(e1.getMessage());
			LoggerAplicacion.logError(e1);
		}
	}

}
