package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.CocheNoEncontradoException;
import excepciones.DatosObligatoriosNoPresentesException;
import excepciones.ErrorConexionJDBCException;
import logica.GestorConcesionarios;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.ICocheDAO;
import persistenciaDAO.IConcesionarioDAO;
import persistenciaDAO.impl.CiudadDAOimpl;
import persistenciaDAO.impl.CocheDAOimpl;
import persistenciaDAO.impl.ConcesionarioDAOimpl;

public class CocheLogicaTest {
	
	private final ICiudadDAO ciudadDAO; 
	private final IConcesionarioDAO concesionarioDAO;
	private final ICocheDAO cocheDAO;
	
	private final GestorConcesionarios gestor;
	
	final String CODIGO_CIUDAD = "BB45";
	final String NOMBRE_CIUDAD = "Santander";
	
	final String CODIGO_CONCESIONARIO = "UIO928";
	final String NOMBRE_CONCESIONARIO = "Santander Autos";
	
	final String MATRICULA = "3482GUL";
	final String MARCA = "Seat";
	final String MODELO = "Ibiza";
	
	CocheLogicaTest(){
		ciudadDAO = new CiudadDAOimpl();
		concesionarioDAO = new ConcesionarioDAOimpl();
		cocheDAO = new CocheDAOimpl();
		gestor = new GestorConcesionarios();
	}
	
	@BeforeEach 
	public void setUpCrearCiudadConcesionario() {
		ciudadDAO.create(new Ciudad(CODIGO_CIUDAD, NOMBRE_CIUDAD));
		concesionarioDAO.create(new Concesionario(CODIGO_CONCESIONARIO, NOMBRE_CONCESIONARIO, CODIGO_CIUDAD));
	}
	
	@AfterEach
	public void setUpBorrarCiudad() {
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
	}
	
	@Test
	public void crearCocheOkTest() throws ErrorConexionJDBCException, DatosObligatoriosNoPresentesException {
		
		boolean isCocheCreado = gestor.crearCoche(new Coche (MATRICULA, MARCA, MODELO, CODIGO_CONCESIONARIO ));
		
		assertTrue(isCocheCreado);
		
	}
	
	@Test
	public void leerCochesOkTest() {
		
		crearCocheUtilitario();
		List<Coche> listaCoches = gestor.leerCoches();
		
		assertTrue(listaCoches.size() >= 1);
		
	}
	
	@Test 
	public void leerCocheOkTest() throws DatosObligatoriosNoPresentesException {
		
		crearCocheUtilitario();
		
		Coche cocheEncontrado = gestor.leerCoche(MATRICULA);
		
		assertNotNull(cocheEncontrado);
	}
	
	@Test
	public void actualizarCocheOkTest() throws CocheNoEncontradoException, DatosObligatoriosNoPresentesException {
		
		crearCocheUtilitario();
		
		final String NUEVO_CODIGO_CONCESIONARIO = "IO789";

		concesionarioDAO.create(new Concesionario(NUEVO_CODIGO_CONCESIONARIO, NOMBRE_CONCESIONARIO, CODIGO_CIUDAD));
		
		boolean isCocheActualizado = gestor.actualizarCoche(MATRICULA, NUEVO_CODIGO_CONCESIONARIO);
		
		Coche cocheActualizado = null;
		
		List<Coche> listaCoches = cocheDAO.readAll();
		
		for(Coche coche: listaCoches) {
			if(coche.getMatricula().equals(MATRICULA)) {
				cocheActualizado = coche;
			}
		}
		
		assertTrue(isCocheActualizado);
		assertEquals(NUEVO_CODIGO_CONCESIONARIO, cocheActualizado.getCodigoConcesionario());
		
	}
	
	@Test
	public void borrarCocheOKTest() throws CocheNoEncontradoException {
		
		crearCocheUtilitario();
		 boolean isCocheBorrado = gestor.borrarCoche(MATRICULA);
		 
		 assertTrue(isCocheBorrado);
		
	}


	//Método utilitario para crear un coche en base de datos
	public void crearCocheUtilitario () {
		cocheDAO.create(new Coche (MATRICULA, MARCA, MODELO, CODIGO_CONCESIONARIO ));
	}
	


}
