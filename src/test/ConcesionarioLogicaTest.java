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
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.impl.CiudadDAOimpl;

public class ConcesionarioLogicaTest {
	
	public static GestorConcesionarios gestor;
	
	public static ICiudadDAO ciudadDAO;
	
	private final String CODIGO_CIUDAD = "BB45";
	private final String NOMBRE_CIUDAD = "Santander";
	
	public ConcesionarioLogicaTest() {
		ciudadDAO = new CiudadDAOimpl();
		gestor = new GestorConcesionarios();
	}
	
	@Test
	public void crearConcesionarioOKTest() throws DatosObligatoriosNoPresentesException, CiudadNoEncontradaException, ErrorConexionJDBCException, ConcesionarioNoEncontradoException {
		
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);
		
		assertTrue(concesionarioCreado);
		
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
	}
	
	@Test
	public void crearConcesionarioCiudadNoExistenteErrorTest() throws DatosObligatoriosNoPresentesException {
				
		Concesionario concesionario = new Concesionario(
				"31231",
				"AutosMadrid",
				CODIGO_CIUDAD);
		
		boolean concesionarioCreado = gestor.crearConcesionario(concesionario);

		assertFalse(concesionarioCreado);
	}
	
	@Test
	public void crearConcesionarioParametrosVaciosTest() throws DatosObligatoriosNoPresentesException, ErrorConexionJDBCException, ConcesionarioNoEncontradoException, CiudadNoEncontradaException {
	
		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
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
		
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
	}
	
	@Test 
	public void leerConcesionariosOkTest() throws DatosObligatoriosNoPresentesException, CiudadNoEncontradaException{
		
		final String CODIGO_CONCESIONARIO = "RT124";

		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, "Santander"));
		
		gestor.crearConcesionario( new Concesionario(
				CODIGO_CONCESIONARIO,
				"AutosMadrid",
				CODIGO_CIUDAD));
		
		ArrayList<Concesionario> listaConcesionarios = (ArrayList<Concesionario>) gestor.leerConcesionarios();
		
		assertTrue(listaConcesionarios.size() >= 1);
		
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
	}
	
	@Test
	public void leerConcesionarioOkTest() throws DatosObligatoriosNoPresentesException {

		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		gestor.crearConcesionario(new Concesionario("78J", "Autos Madrid", CODIGO_CIUDAD));
		
		Concesionario concesionario = gestor.leerConcesionario("78J");
		
		assertNotNull(concesionario);
		
	}
	
	@Test
	public void actualizarConcesionariosOKTest() throws DatosObligatoriosNoPresentesException, ConcesionarioNoEncontradoException {
		
		final String CODIGO_CONCESIONARIO = "UIO48";

		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
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
		
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		
	}
	
	@Test
	public void actualizarConcesionarioErrorCodigoVacioTest() throws DatosObligatoriosNoPresentesException {
		
		final String CODIGO_CONCESIONARIO = "872YU";

		gestor.crearCiudad(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));
		gestor.crearConcesionario( new Concesionario(
				CODIGO_CONCESIONARIO,
				"AutosMadrid",
				CODIGO_CIUDAD));
		
		String nuevoNombreConcesionario = "AutosRenovadosMadrid";
		String codigoConcesionarioVacio = "";
				
		assertThrows(DatosObligatoriosNoPresentesException.class, () -> {
			gestor.actualizarConcesionario(codigoConcesionarioVacio, nuevoNombreConcesionario);
		});
		
		//Limpiamos base de datos (Al borrar la ciudad se borrará el concesionario dado que está en ON DETELE CASCADE)
		ciudadDAO.detele(new Ciudad (CODIGO_CIUDAD, NOMBRE_CIUDAD));

	}
	
	
	
	

}
