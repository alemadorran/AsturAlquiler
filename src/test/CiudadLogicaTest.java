package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import excepciones.CiudadNoEncontradaException;
import excepciones.DatosObligatoriosNoPresentesException;
import logger.LoggerAplicacion;
import logica.GestorConcesionarios;
import modelo.Ciudad;
import persistenciaDAO.CiudadDAO;
import persistenciaDAO.impl.CiudadDAOimpl;

public class CiudadLogicaTest {
	
	GestorConcesionarios gestor = new GestorConcesionarios();
	
	public static CiudadDAO ciudadDAO;
	
	public CiudadLogicaTest() {
		ciudadDAO = new CiudadDAOimpl();
	}
	
	
	@Test
	public void crearCiudadOk() {
		
		Ciudad ciudad = new Ciudad("9999","Bilbao");
		boolean ciudadCreada = false;
		try {
			ciudadCreada = gestor.crearCiudad(ciudad);
		} catch (DatosObligatoriosNoPresentesException e) {
			LoggerAplicacion.logError(e);
		}
		
		assertTrue(ciudadCreada);
		
		//Limpiamos base de datos
		try {
			gestor.borrarCiudad(ciudad);
		} catch (CiudadNoEncontradaException e) {
			LoggerAplicacion.logError(e);
		}
		
	}
	
	@Test
	public void crearCiudadErrorCodigoVacio() {
		Ciudad ciudad = new Ciudad("","Bilbao");
		
		assertThrows(DatosObligatoriosNoPresentesException.class, () -> {
			gestor.crearCiudad(ciudad);
		});
	}
	@Test
	public void crearCiudadErrorNombreVacio() {
		Ciudad ciudad = new Ciudad("","Bilbao");
		
		assertThrows(DatosObligatoriosNoPresentesException.class, () -> {
			gestor.crearCiudad(ciudad);
		});
	}
	
	@Test 
	public void leerCiudadesOk(){
		
		Ciudad ciudad = new Ciudad("8888","Bilbao");
		try {
			gestor.crearCiudad(ciudad);
		} catch (DatosObligatoriosNoPresentesException e) {
			LoggerAplicacion.logError(e);
		}
		
		List<Ciudad>ciudades = gestor.leerCiudades();		
		
		assertTrue(((ArrayList<Ciudad>)ciudades).size() >= 1);
		//Limpiamos base de datos
		try {
			gestor.borrarCiudad(ciudad);
		} catch (CiudadNoEncontradaException e) {
			LoggerAplicacion.logError(e);
		}
	}
	
	@Test
	public void borrarCiudadOk(){
		
		Ciudad ciudad = new Ciudad("9999","Bilbao");
		
		try {
			boolean ciudadCreada = gestor.crearCiudad(ciudad);
		} catch (DatosObligatoriosNoPresentesException e) {
			LoggerAplicacion.logError(e);
		}
		boolean ciudadBorrada = false;
		try {
			ciudadBorrada = gestor.borrarCiudad(ciudad);
		} catch (CiudadNoEncontradaException e) {
			LoggerAplicacion.logError(e);
		}
		
		assertTrue(ciudadBorrada);
	}
	
	@Test
	public void borrarCiudadErrorCodigoIncorrectoOK() {
		
		String codigoCiudadInexistente = "9999999999999999";
		String codigoCiudadVacio = "";
		
		assertThrows(CiudadNoEncontradaException.class, () -> {
			gestor.borrarCiudad(codigoCiudadInexistente);
		});
		assertThrows(CiudadNoEncontradaException.class, () -> {
			gestor.borrarCiudad(codigoCiudadVacio);
		});
		
		
	}

}
