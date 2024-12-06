package interfaz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.CiudadNoEncontradaException;
import excepciones.CocheNoEncontradoException;
import excepciones.ConcesionarioNoEncontradoException;
import excepciones.DatosObligatoriosNoPresentesException;
import excepciones.ErrorConexionJDBCException;
import excepciones.ErrorInesperadoException;
import excepciones.MensajesError;
import logger.LoggerAplicacion;
import logica.GestorConcesionarios;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistencia.ConexionJDBC;


/**
 * 
 * Clase de inicio de la aplicación
 * 
 */
public class Inicio {
   /**
    * 
    * Brebe descripcion de la aplicacion.
    * 	
    */
	private static GestorConcesionarios gestorConcesionarios = new GestorConcesionarios();
    private static Scanner sc = new Scanner(System.in);	
    /**
     * 
     * Método de inicio de la aplicación
     * 
     * @param args
     */
	public static void main(String[] args) {
		try {
			iniciarBD();
			mostrarMenuInicio();
		}catch (Exception e) {
			LoggerAplicacion.logError(e);
			System.out.println(MensajesError.ERROR_INESPERADO);
		}
		
	}
	/**
	 * 
	 * Muestra el menú principal de la aplicación
	 * @throws ErrorConexionJDBCException 
	 * @throws ErrorInesperadoException 
	 * @throws DatosObligatoriosNoPresentesException 
	 * 
	 */
	public static void mostrarMenuInicio(){
		System.out.println("##########################################");
		System.out.println("###### BIENVENIDO A ASTURALQUILER.SA #####");
		System.out.println("##########################################");
		int opcion;
		 
		do {
			System.out.println("######################################");
			System.out.println("Accede al menú deseado:              #");
			System.out.println("1 - Ciudad                           #");
			System.out.println("2 - Concesionario                    #");
			System.out.println("3 - Coche                            #");
			System.out.println("0 - Salir de la aplicación           #");
			System.out.println("######################################");
			System.out.println();

			opcion = obtenerRespuesta();
			
			switch (opcion) {
			case 1:
			    menuCiudad();
				break;
			case 2: 
				try {
					menuConcesionario();
				} catch (ErrorConexionJDBCException | ErrorInesperadoException e) {
					System.out.println(e.getMessage());
					LoggerAplicacion.logError(e);
				}
				break;
			case 3: 
				try {
					menuCoche();
				} catch (ErrorConexionJDBCException e) {
					System.out.println(e.getMessage());
					LoggerAplicacion.logError(e);
				}
				break;
			case 0: 
				System.out.println("Saliendo de la aplicación...");
				break;
			default:
				System.out.println("Error la opcion no es correcta");
				break;
			}
			
		}while(opcion!=0);
		
		sc.close(); //Cerramos scanner
	}
	
	//############################################################################################### 
	//###################################  MENU CIUDAD  #############################################
	//############################################################################################### 
	/**
	 * 
	 * Menu de acciones con Ciudad
	 * @throws DatosObligatoriosNoPresentesException 
	 * 
	 */
	public static void menuCiudad() {
		int respuestaUsuario;
		do {
		System.out.println("##########################################");	
		System.out.println("    Selecciona una de las opciones:       ");
		System.out.println("##########################################");	
		System.out.println("1 Crear ciudad                           #");
		System.out.println("2 Leer ciudades                          #");
		System.out.println("3 Borrar ciudad                          #");
		System.out.println("0 Salir al menu principal                #");
		
		respuestaUsuario = obtenerRespuesta();
		
		switch (respuestaUsuario) {
		case 1:
			try {
				crearCiudad();
			} catch (DatosObligatoriosNoPresentesException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 2:
			 leerCiudades();
			break;
		case 3:
			borrarCiudad();
			break;
		case 0:
			System.out.println("Saliendo del menu ciudad");
			break;
		default:
			System.out.println("La opcion no es correcta");
			break;
			
		}
		}while(respuestaUsuario != 0);
	}
	
	private static void borrarCiudad() {
		System.out.println("Indica el código de la ciudad a borrar: ");
		String codigo = sc.nextLine();
		Ciudad ciudadABorrar = new Ciudad(codigo, "");
		try {
			gestorConcesionarios.borrarCiudad(ciudadABorrar);
			System.out.println("Ciudad con código " + codigo + " borrada correctamente.");
		} catch (CiudadNoEncontradaException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
	}
	/**
	 * Método para crear ciudad
	 * @throws DatosObligatoriosNoPresentesException 
	 * 
	 */
	private static void crearCiudad() throws DatosObligatoriosNoPresentesException {
		
		System.out.println("Indica el código de la ciudad: ");
		String codigoCiudad = sc.nextLine();
		System.out.println("Indica el nombre de la ciudad: ");
		String nombreCiudad = sc.nextLine();
		Ciudad nuevaCiudad = new Ciudad(codigoCiudad, nombreCiudad);

		if (gestorConcesionarios.crearCiudad(nuevaCiudad)) {
			System.out.println("Ciudad insertada correctamente");
		}else { //No se insertó
			System.out.println("Ocurrió un error inesperado durante la inserción en BD");
		}
	}
	/**
	 * Método para leer todas las ciudades
	 * 
	 */
	private static void leerCiudades() {
		List<Ciudad> listaCiudades = gestorConcesionarios.leerCiudades();
		
		if(listaCiudades.size() == 0) {
			System.out.println("No hay ciudades registradas en base de datos");
		}else {
			System.out.println("*********************************");
			System.out.println("********** LISTA CIUDADES *******");
			System.out.println("*********************************");
			for(Ciudad ciudad : listaCiudades) {
				System.out.println(ciudad.toString());
			}
			System.out.println("*********************************");
			System.out.println("******** FIN LISTA CIUDADES *****");
			System.out.println("*********************************");
		}
	}
	
	//############################################################################################### 
	//###################################  MENU CONCESIONARIO #######################################
	//############################################################################################### 
	
	/**
	 * 
	 * Mostrar menu Concesionario
	 * @throws ErrorConexionJDBCException 
	 * @throws ErrorInesperadoException 
	 * 
	 */
	public static void menuConcesionario() throws ErrorConexionJDBCException, ErrorInesperadoException {
		int respuestaUsuario;
		do {
			System.out.println("##########################################");	
			System.out.println("    Selecciona una de las opciones:       ");
			System.out.println("##########################################");
			System.out.println("1 Crear concesionario                    #");
			System.out.println("2 Leer concesionarios                    #");
			System.out.println("3 Actualizar concesionarios              #");
			System.out.println("4 Borrar concesionario                   #");
			System.out.println("0 Salir al menu principal                #");
			
			respuestaUsuario = obtenerRespuesta();
			
			switch (respuestaUsuario) {
			case 1:
				try {
					crearConcesionario();
				} catch (DatosObligatoriosNoPresentesException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				leerConcesionarios();
				break;
			case 3:
				actualizarConcesionario();
				break;
			case 4:
				borrarConcesionario();
				break;
			case 0:
				System.out.println("Saliendo del menu concesionario");
				break;
			default:
				System.out.println("La opcion no es correcta");
				break;
			}
		}while(respuestaUsuario != 0);
	}

	private static void borrarConcesionario() throws ErrorInesperadoException {
		System.out.println("Indica el código del concesionario a borrar: ");
		String codigo = sc.nextLine();
		try {
			if (gestorConcesionarios.borrarConcesionario(codigo)) System.out.println("Concesionario borrado correctamente");
			else throw new ErrorInesperadoException(MensajesError.ERROR_INESPERADO);
		} catch (ConcesionarioNoEncontradoException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
	}
	private static void actualizarConcesionario() throws ErrorInesperadoException {
		System.out.println();
		System.out.println("#### Actualización de concesionarios ####");
		System.out.print("Indica el codigo del concesionario: ");
		String codigoC = sc.nextLine();
		System.out.print("Indica el nuevo nombre del concesionario: ");
		String nombreConcesionario = sc.nextLine();
		
		try {
			if(gestorConcesionarios.actualizarConcesionario(codigoC, nombreConcesionario)) System.out.println("Concesionario modificado correctamente");
			else throw new ErrorInesperadoException(MensajesError.ERROR_INESPERADO);
		}catch (ConcesionarioNoEncontradoException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		} catch (DatosObligatoriosNoPresentesException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
		
	}
	private static void leerConcesionarios() {
		
		List<Concesionario> listaConcesionario = gestorConcesionarios.leerConcesionarios();
		if(listaConcesionario.size() == 0) {
			System.out.println("No ha concesionarios registrados");
		}else {
			for(Concesionario concesionario : listaConcesionario) {
				System.out.println(concesionario.toString());
			}
		}
		
		
	}
	private static void crearConcesionario() throws ErrorInesperadoException, DatosObligatoriosNoPresentesException {
		System.out.println();
		System.out.print("Indica el código del concesionario: ");
		String codigoCoche = sc.nextLine();
		System.out.print("Indica el nombre del concesionario: ");
		String nombreConcecionario = sc.nextLine();
		System.out.print("Indica el codigo de la ciudad: ");
		String codigoCiudad = sc.nextLine();
;		Concesionario nuevoConcesionario = new Concesionario(codigoCoche, nombreConcecionario, codigoCiudad);
		
		if( gestorConcesionarios.crearConcesionario(nuevoConcesionario)) System.out.println("Concesionario creado correctamente");
		else throw new ErrorInesperadoException(MensajesError.ERROR_INESPERADO);
		
	}
	
	//############################################################################################### 
	//###################################  MENU COCHE   #############################################
	//############################################################################################### 
	
	/**
	 * 
	 * Mostrar menu Coche
	 * @throws ErrorConexionJDBCException 
	 * 
	 */
	private static void menuCoche() throws ErrorConexionJDBCException {
		int respuestaUsuario;
		do {
			
			System.out.println("##########################################");	
			System.out.println("    Selecciona una de las opciones:       ");
			System.out.println("##########################################");			
			System.out.println("1 Crear coche                            #");
			System.out.println("2 Leer coches                            #");
			System.out.println("3 Leer un coche                          #");
			System.out.println("4 Actualizar coche                       #");
			System.out.println("5 Borrar coche                           #");
			System.out.println("0 Salir del menu Coche                   #");
			
			respuestaUsuario= obtenerRespuesta();
			
			switch (respuestaUsuario) {
			case 1:
				crearCoche();
				break;
			case 2:
				leerCoches();
				break;	
			case 3: 
				leerCoche();
				break;
			case 4:
				actualizarCoche();
				break;
			case 5:
				borrarCoche();
				break;
			case 0:
				System.out.println("Saliendo del menu coche");
				break;
			default:
				System.out.println("La opcion no es correcta");
				break;
			}
		}while(respuestaUsuario != 0);
		
	}
	private static void borrarCoche() {
		System.out.println("###########  BORRADO DE COCHE ###########");
		System.out.println("Indica la matricula del coche a borrar: ");
		String matricula = sc.nextLine();
		try {
			gestorConcesionarios.borrarCoche(matricula);
		} catch (CocheNoEncontradoException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
	}
	private static void actualizarCoche() {
		System.out.println("###########  ACTUALIZACIÓN DE COCHE ###########");
		System.out.print("Indicamos la matricula del coche: ");
		String matriculaCoche = sc.nextLine();
		System.out.print("Indica el nuevo código de concesionario: ");
		String codigoConces = sc.nextLine();
		try {
			gestorConcesionarios.actualizarCoche(matriculaCoche, codigoConces);
		} catch (CocheNoEncontradoException | DatosObligatoriosNoPresentesException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		} 
	}
	private static void leerCoche() {
		String matricula = "";
		System.out.print("Introduce la matrícula del coche a buscar: ");
		matricula = sc.nextLine();
		try {
			gestorConcesionarios.leerCoche(matricula);
		} catch (DatosObligatoriosNoPresentesException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
		
	}
	private static void leerCoches() {
		List<Coche> listaCoches = new ArrayList<Coche>();
		listaCoches = gestorConcesionarios.leerCoches();
		
		for(Coche coche: listaCoches) {
			System.out.println(coche.toString());
		}
		
	}
	private static void crearCoche() throws ErrorConexionJDBCException {

		System.out.println("Indica la matrícula: ");
		String matricula = sc.nextLine();
		System.out.println("Indica la marca: ");
		String marca = sc.nextLine();
		System.out.println("Indica el modelo: ");
		String modelo = sc.nextLine();
		System.out.println("Indica su código de concesionario: ");
		String codigoConcesionario = sc.nextLine();
		
		Coche nuevoCoche = new Coche(matricula, marca, modelo, codigoConcesionario);
		
		try {
			gestorConcesionarios.crearCoche(nuevoCoche);
		} catch (DatosObligatoriosNoPresentesException e) {
			System.out.println(e.getMessage());
			LoggerAplicacion.logError(e);
		}
		
	}
	
	//TODO a eliminar?
	/**
	 * Método para crear la base de datos
	 */
	private static void iniciarBD() {
		ConexionJDBC conexionJDBC;
		try {
			conexionJDBC = ConexionJDBC.instace();
			conexionJDBC.crearBD();
			conexionJDBC.closeConnection();
		} catch (SQLException e) {
			LoggerAplicacion.logError(e);
		}catch (NullPointerException e1) {
			LoggerAplicacion.logError(e1);
			System.out.println(MensajesError.BASE_DATOS_NO_CREADA);
		}catch (Exception e2) {
			LoggerAplicacion.logError(e2);
			System.out.println(MensajesError.BASE_DATOS_NO_CREADA);
		}
	}
	
	private static int obtenerRespuesta() {
		try {
			return Integer.parseInt(sc.nextLine());
		}catch (Exception e) {
			System.out.println(MensajesError.FORMATO_NUMERO_INCORRECTO);
		}
		return -1;
	}
}
