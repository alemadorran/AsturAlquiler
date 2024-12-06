package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Ciudad;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.impl.CiudadDAOimpl;

public class CiudadPersistenciaTest {
	
	private static ICiudadDAO ciudadDAO;
	
	private final String CODIGO_CIUDAD = "AABG78";
	private final String NOMBRE_CIUDAD = "Santander";
	
	public CiudadPersistenciaTest() {
		ciudadDAO = new CiudadDAOimpl();
	}
	
	@Test
	public void crearCiudadOk() {
		
		Ciudad ciudad = new Ciudad(CODIGO_CIUDAD,NOMBRE_CIUDAD);
		boolean ciudadCreada = false;
		ciudadCreada = ciudadDAO.create(ciudad);
		
		assertTrue(ciudadCreada);
		
		borrarCiudadUtilitario();
		
	}
	
	@Test
	public void crearCiudadErrorCodigoYaExistente() {
		
		Ciudad ciudad = new Ciudad(CODIGO_CIUDAD,NOMBRE_CIUDAD);
		ciudadDAO.create(ciudad);
		
		Ciudad ciudad2 = new Ciudad(CODIGO_CIUDAD,NOMBRE_CIUDAD);
		
		boolean ciudadCreada = ciudadDAO.create(ciudad2);
		assertFalse(ciudadCreada);
		
		borrarCiudadUtilitario();

	}
	
	@Test 
	public void leerCiudadesOk(){
		
		Ciudad ciudad = new Ciudad(CODIGO_CIUDAD,NOMBRE_CIUDAD);
		ciudadDAO.create(ciudad);
		
		List<Ciudad>ciudades = ciudadDAO.readAll();		
		
		assertTrue(((ArrayList<Ciudad>)ciudades).size() >= 1);
		
		borrarCiudadUtilitario();

	}
	
	@Test
	public void leerCiudadOKTest() {
		crearCiudadUtilitario();
		
		Ciudad ciudadRecuperada = ciudadDAO.read(CODIGO_CIUDAD);
		
		assertNotNull(ciudadRecuperada);
		
		borrarCiudadUtilitario();
	}

	@Test
	public void borrarCiudadOk(){
		
		Ciudad ciudad = new Ciudad(CODIGO_CIUDAD,NOMBRE_CIUDAD);
		
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
	
	public void crearCiudadUtilitario() {
		ciudadDAO.create(new Ciudad(CODIGO_CIUDAD, NOMBRE_CIUDAD));
	}

	
	private void borrarCiudadUtilitario() {
		ciudadDAO.detele(new Ciudad(CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
	}

}
