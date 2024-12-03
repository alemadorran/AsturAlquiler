package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import excepciones.CiudadNoEncontradaException;
import excepciones.ConcesionarioNoEncontradoException;
import excepciones.DatosObligatoriosNoPresentesException;
import excepciones.ErrorConexionJDBCException;
import logica.GestorConcesionarios;
import modelo.Ciudad;
import modelo.Concesionario;

public class ConcesionarioLogicaTest {
	
	GestorConcesionarios gestor = new GestorConcesionarios();
	
	@Test
	public void crearConcesionarioOK() throws DatosObligatoriosNoPresentesException, CiudadNoEncontradaException, ErrorConexionJDBCException, ConcesionarioNoEncontradoException {
		
		gestor.crearCiudad(new Ciudad ("AA45", "Santander"));
		
		
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				"AA45");
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);
		
		assertTrue(concesionarioCreado);
		
		
		//Limpiamos base de datos
		gestor.borrarConcesionario(concesionario.getCodigoConcesionario());
		gestor.borrarCiudad("AA45");	
	}
	
	@Test
	public void crearConcesionarioCiudadNoExistenteError() {
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				"AA45");
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);

		assertFalse(concesionarioCreado);
	}
	
	@Test
	public void crearConcesionarioParamtrosVacios() {
		
		Concesionario concesionario = new Concesionario(
				"",
				"AutosMadrid",
				"AA45");
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);

		assertFalse(concesionarioCreado);
		
	}
	
	
	
	

}
