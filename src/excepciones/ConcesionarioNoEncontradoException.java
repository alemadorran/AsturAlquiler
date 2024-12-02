package excepciones;

public class ConcesionarioNoEncontradoException extends Exception{
	
	public ConcesionarioNoEncontradoException(String mensajeError) {
		super(mensajeError);
	}
	
}
