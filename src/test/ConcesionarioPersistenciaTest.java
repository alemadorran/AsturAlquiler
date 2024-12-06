package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Ciudad;
import modelo.Concesionario;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.IConcesionarioDAO;
import persistenciaDAO.impl.CiudadDAOimpl;
import persistenciaDAO.impl.ConcesionarioDAOimpl;

public class ConcesionarioPersistenciaTest {

	private static IConcesionarioDAO concesionarioDAO;
	private static ICiudadDAO ciudadDAO;
	
	final String CODIGO_CIUDAD = "BB45";
	final String NOMBRE_CIUDAD = "Santander";

	
	public ConcesionarioPersistenciaTest() {
		concesionarioDAO = new ConcesionarioDAOimpl();
		ciudadDAO = new CiudadDAOimpl();

	}
	
	@BeforeEach
	public void setUpCrearCiudad() {
		ciudadDAO.create(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
	}
	
	@AfterEach
	public void setUpBorrarCiudad() {
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
	}
	
	
	@Test
	public void crearConcesionarioOkTest() {
		
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = concesionarioDAO.create(concesionario);
		
		assertTrue(concesionarioCreado);
		
	}
	
	@Test
	public void leerConcesionarioOKTest() {
		
		
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = concesionarioDAO.create(concesionario);
		
		assertTrue(concesionarioCreado);
		
		ArrayList<Concesionario> listaConcesionarios = (ArrayList<Concesionario>) concesionarioDAO.readAll();
		
		assertTrue(listaConcesionarios.size() >= 1);
	
	}
	
	@Test
	public void leerConcesionarioOkTest() {
		//TODO
	}
	
	@Test
	public void actualizarConcesionarioOkTest() {
		
				
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = concesionarioDAO.create(concesionario);
		
		assertTrue(concesionarioCreado);
		
		Concesionario concesionarioAActualizar = new Concesionario(
				"31231",
				"NuevosAutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioActualizado = concesionarioDAO.update(concesionarioAActualizar);
		
		assertTrue(concesionarioActualizado);
	
	}
	
	@Test
	public void borrarConcesionarioOkTest() {
				
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = concesionarioDAO.create(concesionario);
		
		assertTrue(concesionarioCreado);
		
		boolean concesionarioBorrado = concesionarioDAO.detele(concesionario);
		
		assertTrue(concesionarioBorrado);

	}
	
	
	
}
