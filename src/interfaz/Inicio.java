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
import persistenciaDAO.ConexionJDBC;


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
		// A eliminar -> iniciarBD();
		mostrarMenuInicio();
		
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
			
			System.out.println("Accede al menú deseado:              #");
			System.out.println("1 - Ciudad                           #");
			System.out.println("2 - Concesionario                    #");
			System.out.println("3 - Coche                            #");
			System.out.println("0 - Salir de la aplicación           #");
			System.out.println("######################################");

			//Esté método puede arrojar error si el usuario escribe: ex "dfa2"
			opcion = Integer.parseInt(sc.nextLine());
			
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
		
	
		respuestaUsuario= Integer.parseInt(sc.nextLine());
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
			
			respuestaUsuario= Integer.parseInt(sc.nextLine());
			
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
			System.out.println("3 Actualizar coche                       #");
			System.out.println("4 Borrar coche                           #");
			System.out.println("0 Salir del menu Coche                   #");
			
			respuestaUsuario= Integer.parseInt(sc.nextLine());
			
			switch (respuestaUsuario) {
			case 1:
				crearCoche();
				
				break;
			case 2:
				List<Coche> listaCoches = new ArrayList<Coche>();
				listaCoches = gestorConcesionarios.leerCoches();
				
				for(Coche coche: listaCoches) {
					System.out.println(coche.toString());
				}
				
				break;
			case 3:
				System.out.println("Actualización de coche");
				System.out.println("Indicamos la matricula del coche: ");
				String matriculaCoche = sc.nextLine();
				System.out.println("Indica el nuevo código de concesionario: ");
				String codigoConces = sc.nextLine();
				try {
					gestorConcesionarios.actualizarCoche(matriculaCoche, codigoConces);
				} catch (CocheNoEncontradoException e) {
					System.out.println(e.getMessage());
					LoggerAplicacion.logError(e);
				}
				
				break;
			case 4:
				System.out.println("Indica la matricula del coche a borrar: ");
				String matricula = sc.nextLine();
				try {
					gestorConcesionarios.borrarCoche(matricula);
				} catch (CocheNoEncontradoException e) {
					System.out.println(e.getMessage());
					LoggerAplicacion.logError(e);
				}
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
	private static void crearCoche() throws ErrorConexionJDBCException {
		
		System.out.println("Indica la marca: ");
		String marca = sc.nextLine();
		System.out.println("Indica el modelo: ");
		String modelo = sc.nextLine();
		System.out.println("Indica la matrícula: ");
		String matrícula = sc.nextLine();
		System.out.println("Indica su código de concesionario: ");
		String codigoConcesionario = sc.nextLine();
		
		Coche nuevoCoche = new Coche(marca, modelo, matrícula, codigoConcesionario);
		
		gestorConcesionarios.crearCocher(nuevoCoche);
		
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
		}
	}
}
