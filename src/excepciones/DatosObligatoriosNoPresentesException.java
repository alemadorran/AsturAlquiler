package excepciones;

public class DatosObligatoriosNoPresentesException extends Exception{
	public DatosObligatoriosNoPresentesException(String mensajeError) {
		super(mensajeError);
	}

}
