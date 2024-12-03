package excepciones;

import java.sql.SQLException;

public class ErrorConexionJDBC extends SQLException{

	public ErrorConexionJDBC(String mensajeError) {
		super(mensajeError);
	}
	
}
