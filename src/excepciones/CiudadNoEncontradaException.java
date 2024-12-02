package excepciones;

/**
 * 
 * Clase de ciudad no encontrada
 * 
 */
public class CiudadNoEncontradaException extends Exception {
	
	/**
	 * Constructor que recibe un mensaje
	 * @param mensajeError : String
	 */

	public CiudadNoEncontradaException(String mensajeError) {
		super(mensajeError);
	}
	
}
