package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logger.LoggerAplicacion;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistencia.GestionArchivoCSV;

/**
 * 
 * Clase para testear funcionamiento de lógica y persistencia de la aplicación.
 * 
 */
public class GestorConcesionariosTest {

	//Nombre de los archicos CVS que persisten los datos
	private static final String CSV_CIUDAD_TEST = "ciudadtest.cvs";
	private static final String CSV_CONCESIONARIO_TEST = "concesionariotest.cvs";
	private static final String CSV_COCHE_TEST = "cochetest.cvs";

	/**
	 * 
	 * Método para la creación de los archivos de prueba
	 * 
	 * @param archivo
	 */
	private void crearArchivos(String archivo) {
		File file = new File (archivo);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("No se ha podido crear el archivo: " + archivo);
				LoggerAplicacion.logError(e);
			}
		}
	}
	/**
	 * 
	 * Método para borrar archivos después de cada test
	 * 
	 * @param archivo
	 */
	private void borrarArchivos(String archivo) {
	
		File archivoABorrar = new File(archivo);
		archivoABorrar.delete();
		
	}

	/**
	 * 
	 * Método de configuración inicial de tests
	 * 
	 */
	@BeforeEach
	private void setUp() {
		// Configuración inicial, elimina archivos de prueba si ya existen
		crearArchivos(CSV_CIUDAD_TEST);
		crearArchivos(CSV_CONCESIONARIO_TEST);
		crearArchivos(CSV_COCHE_TEST);
	}
	/**
	 * 
	 * Método para eliminar todos los archivos al finalizar el test
	 * 
	 */
	@AfterEach
	private void finalizarTest() {
		
		borrarArchivos(CSV_CIUDAD_TEST);
		borrarArchivos(CSV_CONCESIONARIO_TEST);
		borrarArchivos(CSV_COCHE_TEST);
	}
	/**
	 * 
	 * Test para comprobar la creación de ciudades y lectura
	 * 
	 */
	@Test
	public void testCrearYLeerCiudades() {
		
		//Given
		List<Ciudad> ciudad = new ArrayList<>();
		ciudad.add(new Ciudad("1","Madrid"));
		
		//When
		try {
			GestionArchivoCSV.crearCiudad(ciudad, CSV_CIUDAD_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Ciudad> ciudadRecuperada = null;
		try {
			ciudadRecuperada = GestionArchivoCSV.leerCiudades(CSV_CIUDAD_TEST,CSV_CONCESIONARIO_TEST, CSV_COCHE_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Then
		assertEquals(ciudad.get(0).getListaConcesionarios().size(), 
				ciudadRecuperada.get(0).getListaConcesionarios().size());
		assertEquals(ciudad.get(0).getNombre(),
				ciudadRecuperada.get(0).getNombre());
		
	}
	/**
	 * 
	 * Método para testear crear y leer concesionarios.
	 * 
	 */
	@Test
	public void testCrearYLeerConcesionarios() {
		
		//Given
		List<Concesionario> concesionarios = new ArrayList<>();
		concesionarios.add(new Concesionario("1","MadridAutos","1"));
		
		//When
		try {
			GestionArchivoCSV.crearConcesionario(concesionarios, CSV_CONCESIONARIO_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Concesionario> concesionarioRecuperado = null;
		try {
			concesionarioRecuperado = GestionArchivoCSV.leerConcesionarios(CSV_CONCESIONARIO_TEST, CSV_COCHE_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Then
		assertEquals(concesionarioRecuperado.get(0).getNombre(),
				     concesionarios.get(0).getNombre());
		assertEquals(concesionarioRecuperado.get(0).getNombre(),
			     "MadridAutos");
	}
	/**
	 * 
	 * Método para testear crear y leer coches.
	 * 
	 */
	@Test
	public void testCrearYLeerCoches() {
		//Given
		List<Coche> coches = new ArrayList<>();
		coches.add(new Coche("Seat", "Ibiza","XCY8767", "1" ));
		
		//When
		try {
			GestionArchivoCSV.crearCoche(coches, CSV_COCHE_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Coche> cocheRecuperado = null;
		try {
			cocheRecuperado = GestionArchivoCSV.leerCoches(CSV_COCHE_TEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Then
		assertEquals( "Seat", cocheRecuperado.get(0).getMarca());
		
	}
	
	
	
}
