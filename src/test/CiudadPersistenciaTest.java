package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import excepciones.CiudadNoEncontradaException;
import excepciones.DatosObligatoriosNoPresentesException;
import logger.LoggerAplicacion;
import modelo.Ciudad;
import persistencia.GestorJDBC;

public class CiudadPersistenciaTest {
	
	@Test
	public void crearCiudadOk() {
		
		Ciudad ciudad = new Ciudad("323","Bilbao");
		boolean ciudadCreada = false;
		ciudadCreada = GestorJDBC.crearCiudad(ciudad);
		
		assertTrue(ciudadCreada);
		
		GestorJDBC.borrarCiudad(ciudad.getCodigo());
		
	}
	
	@Test
	public void crearCiudadErrorCodigoYaExistente() {
		
		Ciudad ciudad = new Ciudad("443","Bilbao");
		GestorJDBC.crearCiudad(ciudad);
		
		Ciudad ciudad2 = new Ciudad("443","Sevilla");
		
		boolean ciudadCreada = GestorJDBC.crearCiudad(ciudad2);
		assertFalse(ciudadCreada);
		
		//Limpiamos base de datos
		GestorJDBC.borrarCiudad(ciudad.getCodigo());
	}
	
	@Test 
	public void leerCiudadesOk(){
		
		Ciudad ciudad = new Ciudad("2324","Bilbao");
		GestorJDBC.crearCiudad(ciudad);
		
		List<Ciudad>ciudades = GestorJDBC.leerCiudades();		
		
		assertTrue(((ArrayList<Ciudad>)ciudades).size() >= 1);
		
		//Limpiamos base de datos
		GestorJDBC.borrarCiudad(ciudad.getCodigo());
	}
	
	@Test
	public void borrarCiudadOk(){
		
		Ciudad ciudad = new Ciudad("23232","Bilbao");
		
		
		GestorJDBC.crearCiudad(ciudad);
		
		boolean ciudadBorrada = false;
		
		ciudadBorrada = GestorJDBC.borrarCiudad(ciudad.getCodigo());
		
		
		assertTrue(ciudadBorrada);
	}
	
	@Test
	public void borrarCiudadErrorCodigoIncorrectoOK() {
		
		String codigoCiudadInexistente = "999999999999";
		String codigoCiudadVacio = "";
		
		assertFalse(GestorJDBC.borrarCiudad(codigoCiudadInexistente));
		assertFalse(GestorJDBC.borrarCiudad(codigoCiudadVacio));

		
	}

}
