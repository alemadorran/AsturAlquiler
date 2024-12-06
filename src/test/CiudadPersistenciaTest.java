package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import excepciones.CiudadNoEncontradaException;
import excepciones.DatosObligatoriosNoPresentesException;
import logger.LoggerAplicacion;
import modelo.Ciudad;
import persistencia.GestorJDBC;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.impl.CiudadDAOimpl;

public class CiudadPersistenciaTest {
	
	public static ICiudadDAO ciudadDAO;
	
	public CiudadPersistenciaTest() {
		ciudadDAO = new CiudadDAOimpl();
	}
	
	@Test
	public void crearCiudadOk() {
		
		Ciudad ciudad = new Ciudad("323","Bilbao");
		boolean ciudadCreada = false;
		ciudadCreada = ciudadDAO.create(ciudad);
		
		assertTrue(ciudadCreada);
		
		//Limpiamos base de datos
		ciudadDAO.detele(ciudad);
		
	}
	
	@Test
	public void crearCiudadErrorCodigoYaExistente() {
		
		Ciudad ciudad = new Ciudad("443","Bilbao");
		ciudadDAO.create(ciudad);
		
		Ciudad ciudad2 = new Ciudad("443","Sevilla");
		
		boolean ciudadCreada = ciudadDAO.create(ciudad2);
		assertFalse(ciudadCreada);
		
		//Limpiamos base de datos
		ciudadDAO.detele(ciudad);
	}
	
	@Test 
	public void leerCiudadesOk(){
		
		Ciudad ciudad = new Ciudad("2324","Bilbao");
		ciudadDAO.create(ciudad);
		
		List<Ciudad>ciudades = ciudadDAO.readAll();		
		
		assertTrue(((ArrayList<Ciudad>)ciudades).size() >= 1);
		
		//Limpiamos base de datos
		ciudadDAO.detele(ciudad);
	}
	
	@Test
	public void leerCiudadOKTest() {
		//TODO
	}
	
	@Test
	public void borrarCiudadOk(){
		
		Ciudad ciudad = new Ciudad("23232","Bilbao");
		
		
		ciudadDAO.create(ciudad);
		
		boolean ciudadBorrada = false;
		
		ciudadBorrada = ciudadDAO.detele(ciudad);
		
		assertTrue(ciudadBorrada);
	}
	
	@Test
	public void borrarCiudadErrorCodigoIncorrectoOK() {
		
		String codigoCiudadInexistente = "999999999999";
		String codigoCiudadVacio = "";
		
		assertFalse(ciudadDAO.detele(new Ciudad (codigoCiudadInexistente, "Sevilla")));
		assertFalse(ciudadDAO.detele(new Ciudad (codigoCiudadVacio, "Sevilla")));

		
	}

}
