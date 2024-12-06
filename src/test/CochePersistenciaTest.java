package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.ICocheDAO;
import persistenciaDAO.IConcesionarioDAO;
import persistenciaDAO.impl.CiudadDAOimpl;
import persistenciaDAO.impl.CocheDAOimpl;
import persistenciaDAO.impl.ConcesionarioDAOimpl;

public class CochePersistenciaTest {
	
	private final ICiudadDAO ciudadDAO; 
	private final IConcesionarioDAO concesionarioDAO;
	private final ICocheDAO cocheDAO;
	
	final String CODIGO_CIUDAD = "BB45";
	final String NOMBRE_CIUDAD = "Santander";
	
	final String CODIGO_CONCESIONARIO = "UIO928";
	final String NOMBRE_CONCESIONARIO = "Santander Autos";
	
	final String MATRICULA = "3482GUL";
	final String MARCA = "Seat";
	final String MODELO = "Ibiza";
	
	CochePersistenciaTest(){
		ciudadDAO = new CiudadDAOimpl();
		concesionarioDAO = new ConcesionarioDAOimpl();
		cocheDAO = new CocheDAOimpl();
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
	public void crearCocheOkTest() {
		
		boolean cocheCreado = cocheDAO.create(new Coche (MATRICULA, MARCA, MODELO, CODIGO_CONCESIONARIO ));
		
		assertTrue(cocheCreado);
	}
	
	@Test
	public void leerCocheOkTest() {
		
		crearCocheUtilitario();
		
		Coche cocheObtenido = cocheDAO.read(MATRICULA);
		
		assertNotNull(cocheObtenido);
		assertEquals(MATRICULA, cocheObtenido.getMatricula());
		assertEquals(MARCA, cocheObtenido.getMarca());

	}
	
	@Test 
	public void leerCochesOkTest() {
		
		crearCocheUtilitario();
		
		List<Coche> coches = cocheDAO.readAll();
		
		assertTrue(coches.size() >= 1);
		
	}
	
	@Test
	public void actualizarCocheOkTest() {
		
		crearCocheUtilitario();
		
		final String NUEVO_CODIGO_CONCESIONARIO = "IO789";

		concesionarioDAO.create(new Concesionario(NUEVO_CODIGO_CONCESIONARIO, NOMBRE_CONCESIONARIO, CODIGO_CIUDAD));
		
		Coche cocheAActualizar = new Coche(MATRICULA, MARCA, MODELO, NUEVO_CODIGO_CONCESIONARIO);
		
		boolean cocheActualizadoCorrectamente = cocheDAO.update(cocheAActualizar);
		Coche cocheActualizado = null; 
		
		assertTrue(cocheActualizadoCorrectamente);
		
		List<Coche> listaCoches = cocheDAO.readAll();
		
		for(Coche coche: listaCoches) {
			if(coche.getMatricula().equals(MATRICULA)) {
				cocheActualizado = coche;
			}
		}
		
		assertEquals(NUEVO_CODIGO_CONCESIONARIO, cocheActualizado.getCodigoConcesionario());
		
	}
	
	@Test
	public void borrarCocheOkTest() {
		crearCocheUtilitario();

		boolean isCocheBorrado = cocheDAO.detele(new Coche (MATRICULA, MARCA, MODELO, CODIGO_CONCESIONARIO ));
		assertTrue(isCocheBorrado);
		
	}
	
	
	//Método utilitario para crear un coche en base de datos
	public void crearCocheUtilitario () {
		cocheDAO.create(new Coche (MATRICULA, MARCA, MODELO, CODIGO_CONCESIONARIO ));
	}
	

}
