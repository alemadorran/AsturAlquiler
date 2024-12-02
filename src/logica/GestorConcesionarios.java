package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excepciones.CiudadNoEncontradaException;
import excepciones.CocheNoEncontradoException;
import excepciones.ConcesionarioNoEncontradoException;
import logger.LoggerAplicacion;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistencia.GestionArchivoCSV;
import persistencia.GestorJDBC;

/**
 * 
 * Clase con lógica de negocio
 * 
 */
public class GestorConcesionarios {
	
	//Base de datos intermedia (en memoria)
	private List<Ciudad> listaCiudades;
	private List<Concesionario> listaConcesionarios;
	private List<Coche> listaCoches;

	//TODO A BORRAR
	String CSV_CONCESIONARIO = "";
	String CSV_CIUDAD = "";
	String CSV_COCHE = "";

	
    /**
     * 
     * Constructor de la clase GestorConcesionarios
     * 
     */
	public GestorConcesionarios() {
		listaCiudades = new ArrayList<>();
		listaConcesionarios = new ArrayList<>();
		listaCoches = new ArrayList<>();
	}
	/**
	 * 
	 * Añadimos una nueva ciudad 
	 * 
	 * @param ciudad
	 */
	public boolean crearCiudad(Ciudad ciudad) {
		//Actualizamos memoria dinámica
		listaCiudades.add(ciudad);
		//Actualizamos base de datos
		return GestorJDBC.crearCiudad(ciudad);
	}

	/**
	 * 
	 * Leemos todas las ciudades
	 * 
	 * @return : list
	 */
	public List<Ciudad> leerCiudades() {
		
		List<Ciudad> listaCiudades = GestorJDBC.leerCiudades();		
		return listaCiudades;
		
	}

	/**
	 * 
	 * Borramos una ciudad por su código
	 * 
	 * @param codigo : String
	 * @throws CiudadNoEncontradaException
	 */
	public void borrarCiudad(String codigo) throws CiudadNoEncontradaException {
		
		boolean ciudadBorrada = true;
		ciudadBorrada = GestorJDBC.borrarCiudad(codigo);
		
		if(ciudadBorrada == false) {
			throw new CiudadNoEncontradaException("Ciudad no encontrada con el código: " + codigo);	
		}
		
	}

	/**
	 * 
	 * Añadimos un nuevo concesionario.
	 * 
	 * @param nuevoConcesionario
	 */
	public void crearConcesionario(Concesionario nuevoConcesionario) {
		
		this.listaConcesionarios.add(nuevoConcesionario);
		/*
		try {
			GestionArchivoCSV.crearConcesionario(listaConcesionarios, CSV_CONCESIONARIO);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_CONCESIONARIO);
			LoggerAplicacion.logError(e);
		}
		*/
		
	}

	/**
	 * 
	 * Recuperamos todos los concesionarios.
	 * 
	 * @return
	 */
	public List<Concesionario> leerConcesionario() {

		List<Concesionario> listaconcesionario = null; 
		/*
		try {
			//Actualiza la lista en memoria 
			this.listaConcesionarios = GestionArchivoCSV.leerConcesionarios(CSV_CONCESIONARIO, CSV_COCHE);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_CONCESIONARIO);
			LoggerAplicacion.logError(e);
		}
		*/
		listaconcesionario = this.listaConcesionarios;
		
		return listaconcesionario;

	}
	/**
	 * 
	 * Actualizamos el nombre del concesionario
	 * 
	 * @param codigoC
	 * @param nombreConcecionario
	 * @throws ConcesionarioNoEncontradoException 
	 */
	public void actualizarConcesionario(String codigoC, String nombreConcecionario) throws ConcesionarioNoEncontradoException {
		
		//Sincronizar la lista en memoria
		try {
			this.listaConcesionarios = GestionArchivoCSV.leerConcesionarios(CSV_CONCESIONARIO, CSV_COCHE);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_CONCESIONARIO);
			LoggerAplicacion.logError(e);
		}
		
		Concesionario concesionarioAActualizar = null; 
		
		//Buscamos el concesionario con el mismo código
		for(Concesionario concesionario : listaConcesionarios) {
			if(concesionario.getCodigoConcesionario().equals(codigoC)) {
				concesionarioAActualizar = concesionario;
			}
		}
		
		//Verificamos si se ha encontrado el concesionario 
		if(concesionarioAActualizar == null) {
			throw new ConcesionarioNoEncontradoException("El concecionario con el código " + codigoC + " no ha sido encontrado");
		}else {
			//Modificamos nombre del concesionario
			concesionarioAActualizar.setNombre(nombreConcecionario);
			
			try {
				GestionArchivoCSV.crearConcesionario(this.listaConcesionarios, CSV_CONCESIONARIO);
			} catch (IOException e) {
				System.out.println("No se ha podido encontrar el archivo: " + CSV_CONCESIONARIO);
				LoggerAplicacion.logError(e);
			}
			
		}
		
	}

	/**
	 * 
	 * Borramos concesionario por su código
	 * 
	 * @param codigo
	 * @throws ConcesionarioNoEncontradoException 
	 */
	public void borrarConcesionario(String codigo) throws ConcesionarioNoEncontradoException {
		
		Concesionario concesionarioABorrar = null;
		
		for(Concesionario concesionario: listaConcesionarios) {
			
			if(concesionario.getCodigoConcesionario().equals(codigo)) {
				concesionarioABorrar = concesionario;
			}
			
		}
		
		if(concesionarioABorrar == null) {
			throw new ConcesionarioNoEncontradoException("Concesionario no encontrado con el código: " + codigo);
		}else {
			//La borramos de la lista
			listaConcesionarios.remove(concesionarioABorrar);
			try {
				//Lo que hacemos es sobreescribir la lista de ciudades 
				GestionArchivoCSV.crearConcesionario(listaConcesionarios, CSV_CONCESIONARIO);
				
			} catch (IOException e) {
				System.out.println("No se ha podido encontrar el archivo: " + CSV_CONCESIONARIO);
				LoggerAplicacion.logError(e);
			}
		}
		
	}

	/**
	 * 
	 * Añadimos un nuevo coche
	 * 
	 * @param nuevoCoche
	 */
	public void crearCocher(Coche nuevoCoche) {
		
		this.listaCoches.add(nuevoCoche);
		try {
			GestionArchivoCSV.crearCoche(listaCoches, CSV_COCHE);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_COCHE);
			LoggerAplicacion.logError(e);
		}
		
	}

	/**
	 * 
	 * Recuperamos todos los coches
	 * 
	 * @return
	 */
	public List<Coche> leerCoches() {
		
		List<Coche> listaCoches = null;
		try {
			this.listaCoches = GestionArchivoCSV.leerCoches(CSV_COCHE);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_COCHE);
			LoggerAplicacion.logError(e);
		}
		listaCoches = this.listaCoches;
		return listaCoches;
	}

	/**
	 * 
	 * Este método actualiza el código del consionario asignado al coche
	 * 
	 * @param matriculaCoche
	 * @param codigoConces
	 * @throws CocheNoEncontradoException 
	 */
	public void actualizarCoche(String matriculaCoche, String codigoConces) throws CocheNoEncontradoException {
		//Sincronizar lista en memoria con la persistencia
		try {
			this.listaCoches = GestionArchivoCSV.leerCoches(CSV_COCHE);
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar el archivo: " + CSV_COCHE);
			LoggerAplicacion.logError(e);
		}
		
		Coche cocheAActualizar = null; 
		
		//Vamos a encontrar el coche a actualizar
		for(Coche coche: this.listaCoches) {
			if(coche.getMatricula().equals(matriculaCoche)) {
				cocheAActualizar = coche;
			}
		}
		
		//Con este if comprobamos que se haya encontrado un coche con esa matrícula
		if(cocheAActualizar == null) {
			
			throw new CocheNoEncontradoException("Matricula " + matriculaCoche + " no encontrada.");
			
		}else {
			//Actualizar con el nuevo código concesionario
			cocheAActualizar.setCodigoConcesionario(codigoConces);
			
			try {
				//Sincronizamos el archivo CSV 
				GestionArchivoCSV.crearCoche(this.listaCoches, CSV_COCHE);
			} catch (IOException e) {
				System.out.println("No se ha podido encontrar el archivo: " + CSV_COCHE);
				LoggerAplicacion.logError(e);

			}
		}
		
	}

	/**
	 * 
	 * Borramos coche por su matrícula
	 * 
	 * @param matricula
	 * @throws CocheNoEncontradoException 
	 */
	public void borrarCoche(String matricula) throws CocheNoEncontradoException {
		
		//Sincronizar lista en memoria con la persistencia
		try {
			this.listaCoches = GestionArchivoCSV.leerCoches(CSV_COCHE);
		} catch (IOException e) {
					e.printStackTrace();
		}
				
		Coche cocheABorrar = null; 
				
		//Vamos a encontrar el coche a actualizar
		for(Coche coche: this.listaCoches) {
			if(coche.getMatricula().equals(matricula)) {
				cocheABorrar = coche;
			}
		}
		
		if(cocheABorrar == null) {
			throw new CocheNoEncontradoException("Matricula " + matricula + " no encontrada.");
		}else {
			//Borramos el objeto en la lista en memoria
			this.listaCoches.remove(cocheABorrar);
			try {
				//Sincronizamos el archivo CSV 
				GestionArchivoCSV.crearCoche(this.listaCoches, CSV_COCHE);
			} catch (IOException e) {
				System.out.println("No se ha podido encontrar el archivo: " + CSV_COCHE);
				LoggerAplicacion.logError(e);
			}

		}
		
	}

	
}
