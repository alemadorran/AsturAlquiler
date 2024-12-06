package persistenciaDAO.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import excepciones.ErrorConexionJDBCException;
import excepciones.MensajesError;
import logger.LoggerAplicacion;
import modelo.Coche;
import modelo.Concesionario;
import persistenciaDAO.CocheDAO;
import persistenciaDAO.ConexionJDBC;

public class CocheDAOimpl implements CocheDAO {

    private static String useDB = "use asturalquiler";
	
	@Override
	public boolean create(Coche c) {
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
			try {
				throw new ErrorConexionJDBCException(MensajesError.ERROR_CONEXION_BASE_DE_DATOS);
			} catch (ErrorConexionJDBCException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public List<Coche> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Coche coche) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean detele(Coche coche) {
		// TODO Auto-generated method stub
		return false;
	}

}
