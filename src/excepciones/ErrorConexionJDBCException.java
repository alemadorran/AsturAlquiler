package excepciones;

import java.sql.SQLException;

public class ErrorConexionJDBCException extends SQLException{

	public ErrorConexionJDBCException(String mensajeError) {
		super(mensajeError);
	}
	
}
