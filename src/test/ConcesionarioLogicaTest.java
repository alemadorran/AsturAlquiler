package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

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
	public void crearConcesionarioOKTest() throws DatosObligatoriosNoPresentesException, CiudadNoEncontradaException, ErrorConexionJDBCException, ConcesionarioNoEncontradoException {
		
		final String CODIGO_CIUDAD = "BB45";
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);
		
		assertTrue(concesionarioCreado);
		
		
		//Limpiamos base de datos
		gestor.borrarConcesionario(concesionario.getCodigoConcesionario());
		gestor.borrarCiudad(CODIGO_CIUDAD);	
	}
	
	@Test
	public void crearConcesionarioCiudadNoExistenteErrorTest() throws DatosObligatoriosNoPresentesException {
		
		final String CODIGO_CIUDAD = "XX78";
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);

		assertFalse(concesionarioCreado);
	}
	
	@Test
	public void crearConcesionarioParametrosVaciosTest() throws DatosObligatoriosNoPresentesException, ErrorConexionJDBCException, ConcesionarioNoEncontradoException, CiudadNoEncontradaException {
		
		final String CODIGO_CIUDAD = "LL89";
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		
		Concesionario concesionarioSinCodigo = new Concesionario(
				"",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		Concesionario concesionarioSinNombre = new Concesionario(
				"123",
				"",
				CODIGO_CIUDAD);
				
		assertThrows(DatosObligatoriosNoPresentesException.class, () -> {
			gestor.crearConcesionario(concesionarioSinCodigo);
		});
		
		assertThrows(DatosObligatoriosNoPresentesException.class, () -> {
			gestor.crearConcesionario(concesionarioSinNombre);
		});
		
		//Limpiamos base de datos
		gestor.borrarCiudad(CODIGO_CIUDAD);	
		
	}
	
	@Test 
	public void leerConcesionariosOkTest() throws DatosObligatoriosNoPresentesException, CiudadNoEncontradaException{
		
		final String CODIGO_CONCESIONARIO = "RT124";
		final String CODIGO_CIUDAD = "78JDL";
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		
		gestor.crearConcesionario( new Concesionario(
				CODIGO_CONCESIONARIO,
				"AutosMadrid",
				CODIGO_CIUDAD));
		
		ArrayList<Concesionario> listaConcesionarios = (ArrayList<Concesionario>) gestor.leerConcesionarios();
		
		assertTrue(listaConcesionarios.size() >= 1);
		//Limpiamos base de datos
		gestor.borrarCiudad(CODIGO_CIUDAD);
		
	}
	
	@Test
	public void actualizarConcesionariosOKTest() throws DatosObligatoriosNoPresentesException, ConcesionarioNoEncontradoException {
		
		final String CODIGO_CONCESIONARIO = "UIO48";
		final String CODIGO_CIUDAD = "098UJA";
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		gestor.crearConcesionario( new Concesionario(
				CODIGO_CONCESIONARIO,
				"AutosMadrid",
				CODIGO_CIUDAD));
		

		String nuevoNombreConcesionario = "NuevoAutosMadrid";
		
		boolean concesionarioActualizado = gestor.actualizarConcesionario(CODIGO_CONCESIONARIO, nuevoNombreConcesionario);
		ArrayList<Concesionario> listaConcesionario = (ArrayList<Concesionario>) gestor.leerConcesionarios();
		
		//Buscamos el concesionario actualizado
		Concesionario cActualizado = null; 
		for(Concesionario concesionario : listaConcesionario) {
			if(concesionario.getCodigoConcesionario().equals(CODIGO_CONCESIONARIO)) {
				cActualizado = concesionario;
			}
		}
		
		assertTrue(concesionarioActualizado);
		assertNotNull(cActualizado);
		assertEquals(nuevoNombreConcesionario, cActualizado.getNombre());
		
		//Limpiamos base de datos 
		gestor.borrarConcesionario(CODIGO_CONCESIONARIO);
		
	}
	
	@Test
	public void actualizarConcesionarioErrorCodigoVacioTest() throws DatosObligatoriosNoPresentesException {
		
		final String CODIGO_CONCESIONARIO = "872YU";
		final String CODIGO_CIUDAD = "227HIM";
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		gestor.crearConcesionario( new Concesionario(
				CODIGO_CONCESIONARIO,
				"AutosMadrid",
				CODIGO_CIUDAD));
		
		String nuevoNombreConcesionario = "";
		
		//boolean concesionarioActualizado = gestor.actualizarConcesionario(CODIGO_CONCESIONARIO, nuevoNombreConcesionario);

	}
	
	
	
	

}
