package excepciones;
/**
 * Clase con mensajes de error
 */
public class MensajesError {
	/**
	 * Concesionario no encontrado en BD
	 */
	public static final String CONCESIONARIO_NO_ENCONTRADO = 
	        "\u001B[31m" + // Iniciar texto en rojo
	        "##########################################################################\n" +
	        "#########    NO SE HA ENCONTRADO EL CONCESIONARIO CON EL CÓDIGO: #########\n" +
	        "##########################################################################\n" +
	        "\u001B[0m"; // Restablecer el color al predeterminado
	/**
	 * Error conexión base de datos
	 */
	public static final String ERROR_EN_BASE_DE_DATOS = 
		    "\u001B[31m" + // Iniciar texto en rojo
		    "########################################################################\n" +
		    "#########    SE HA PRODUCIDO UN ERROR                          #########\n" +
		    "#########    PUEDE ESTAR RELACIONADO CON CODIGOS DUPLICADOS    #########\n" +
		    "#########    O CON CODIGOS CIUDAD O CONCESIONARIO              #########\n" +
		    "#########    NO EXISTENTES EN BASE DE DATOS                    #########\n" +
		    "########################################################################\n" +
		    "\u001B[0m"; // Restablecer el color al predeterminado
	
	public static final String PARAMETROS_OBLIGATORIOS_NO_PRESENTES = 
	        "\u001B[31m" + // Iniciar texto en rojo
	        "#######################################################################\n" +
	        "#########    NO SE HA INTRODUCIDO CORRECTAMENTE EL PARÁMETRO: #########\n" +
	        "#######################################################################\n" +
	        "\u001B[0m"; // Restablecer el color al predeterminado
	
	 public static final String FORMATO_NUMERO_INCORRECTO = 
		        "\u001B[31m" + // Iniciar texto en rojo
		        "#####################################################################################\n" +
		        "#########    INSERTE CORRECTAMENTE EL NÚMERO COMPRENDIDO ENTRE LAS OPCIONES #########\n" +
		        "#####################################################################################\n" +
		        "\u001B[0m"; // Restablecer el color al predeterminado	
	 
	 public static final StringBuilder BASE_DATOS_NO_CREADA = 
			    new StringBuilder()
			    .append("\u001B[31m") // Iniciar texto en rojo
			    .append("################################################################\n")
			    .append("#########    BASE DE DATOS NO CREADA                   #########\n")
			    .append("#########    AVISO:                                    #########\n")
			    .append("#########    LA APLICACION NO FUNCIONARÁ CORRECTAMENTE #########\n")
			    .append("################################################################\n")
			    .append("\u001B[0m"); // Restablecer el color al predeterminado
	/**
	 * Error inesperado
	 */
	public static final String ERROR_INESPERADO = 
	        "\u001B[31m" + // Iniciar texto en rojo
	        "#####################################################################\n" +
	        "#########    SE HA PRODUCIDO UN ERROR INESPERADO            #########\n" +
	        "#####################################################################\n" +
	        "\u001B[0m"; // Restablecer el color al predeterminado
	

	
}
