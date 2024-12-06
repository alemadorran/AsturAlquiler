package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import excepciones.CiudadNoEncontradaException;
import excepciones.CocheNoEncontradoException;
import excepciones.ConcesionarioNoEncontradoException;
import excepciones.DatosObligatoriosNoPresentesException;
import excepciones.ErrorConexionJDBCException;
import excepciones.MensajesError;
import logger.LoggerAplicacion;
import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;
import persistencia.GestionArchivoCSV;
import persistencia.GestorJDBC;
import persistenciaDAO.ICiudadDAO;
import persistenciaDAO.ICocheDAO;
import persistenciaDAO.IConcesionarioDAO;
import persistenciaDAO.impl.CiudadDAOimpl;
import persistenciaDAO.impl.CocheDAOimpl;
import persistenciaDAO.impl.ConcesionarioDAOimpl;

/**
 * 
 * Clase con lógica de negocio
 * 
 */
public class GestorConcesionarios {
	
	//Base de datos intermedia (en memoria)
	private List<Ciudad> listaCiudades;
	private List<Concesionario> listaConcesionarios;
	private List<Coche> listaCoches;

	//TODO A BORRAR
	String CSV_CONCESIONARIO = "";
	String CSV_CIUDAD = "";
	String CSV_COCHE = "";
	
	private static ICiudadDAO ciudadDAO;
	private static IConcesionarioDAO concesionarioDAO;
	private static ICocheDAO cocheDAO;
	
    /**
     * 
     * Constructor de la clase GestorConcesionarios
     * 
     */
	public GestorConcesionarios() {
		listaCiudades = new ArrayList<>();
		listaConcesionarios = new ArrayList<>();
		listaCoches = new ArrayList<>();
		
		ciudadDAO = new CiudadDAOimpl();
		concesionarioDAO = new ConcesionarioDAOimpl();
		cocheDAO = new CocheDAOimpl();
	}
	/**
	 * 
	 * Añadimos una nueva ciudad 
	 * 
	 * @param ciudad
	 * @throws DatosObligatoriosNoPresentesException 
	 */
	public boolean crearCiudad(Ciudad ciudad) throws DatosObligatoriosNoPresentesException {
		//Verificamos los parámetros
		if(ciudad != null) {
			veriricaParametro(ciudad.getCodigo(), "codigo ciudad");
			veriricaParametro(ciudad.getNombre(), "nombre ciudad");
		}
		//Actualizamos base de datos
		return ciudadDAO.create(ciudad);
	}
	
	public Ciudad leerCiudad(String codigo) throws DatosObligatoriosNoPresentesException {
		
		veriricaParametro(codigo, "codigo");
		List<Concesionario> listaConcesionarios = concesionarioDAO.readAll();
		
		Ciudad ciudad = ciudadDAO.read(codigo);

		if(ciudad==null) return null;
		
		//Devolvemos la ciudad con todos sus concesionarios
		for (Concesionario concesionario : listaConcesionarios) {
			if(concesionario.getCodigoCiudad().equals(ciudad.getCodigo())) {
				ciudad.getListaConcesionarios().add(concesionario);
			}
		}
		
		return ciudad;
		
	}
	/**
	 * 
	 * Leemos todas las ciudades
	 * 
	 * @return : list
	 */
	public List<Ciudad> leerCiudades() {
		
		List<Ciudad> listaCiudades = ciudadDAO.readAll();
		List <Concesionario> listaConcesionario = concesionarioDAO.readAll();
		
		for(Ciudad ciudad: listaCiudades) {
			for (Concesionario concesionario : listaConcesionario) {
				if(concesionario.getCodigoCiudad().equals(ciudad.getCodigo())){
					ciudad.getListaConcesionarios().add(concesionario);
				}
			}	
		}
		
		return listaCiudades;
		
	}

	/**
	 * 
	 * Borramos una ciudad por su código
	 * 
	 * @param codigo : String
	 * @throws CiudadNoEncontradaException
	 */
	public boolean borrarCiudad(Ciudad ciudad) throws CiudadNoEncontradaException {
		
		boolean ciudadBorrada = true;
		ciudadBorrada = ciudadDAO.detele(ciudad);
		
		if(ciudadBorrada == false) {
			throw new CiudadNoEncontradaException("Ciudad no encontrada con el código: " + ciudad.getCodigo());	
		}
		return true;
		
	}

	/**
	 * 
	 * Añadimos un nuevo concesionario.
	 * 
	 * @param nuevoConcesionario
	 * @throws DatosObligatoriosNoPresentesException 
	 */
	public boolean crearConcesionario(Concesionario nuevoConcesionario) throws DatosObligatoriosNoPresentesException {
		
		if(nuevoConcesionario != null) {
			veriricaParametro(nuevoConcesionario.getCodigoConcesionario(), "codigo concesionario");
			veriricaParametro(nuevoConcesionario.getNombre(), "nombre concesionario");
		}
		
		return concesionarioDAO.create(nuevoConcesionario);
		
	}

	/**
	 * 
	 * Recuperamos todos los concesionarios.
	 * 
	 * @return
	 */
	public List<Concesionario> leerConcesionarios() {

		
		 List<Concesionario> listaconcesionario = concesionarioDAO.readAll();
		 List<Coche> listaCoches = cocheDAO.readAll();
		 
		 for(Concesionario concesionario : listaconcesionario) {

			 for (Coche coche : listaCoches) {
				if(coche.getCodigoConcesionario().equals(concesionario.getCodigoConcesionario())) {
					concesionario.getListaCoche().add(coche);
				}
			}
			 
		 }
		return listaconcesionario;

	}
	
	public Concesionario leerConcesionario(String codigo_concesionario) throws DatosObligatoriosNoPresentesException {
		
		veriricaParametro(codigo_concesionario, "codigo concesionario");
		
		Concesionario concesionario = concesionarioDAO.read(codigo_concesionario);
		List<Coche> listaCoches = cocheDAO.readAll();
		
		if(concesionario==null)return null;
		
		for(Coche coche : listaCoches) {
			if(coche.getCodigoConcesionario().equals(concesionario.getCodigoConcesionario())) {
				concesionario.getListaCoche().add(coche);
			}
		}
		
		return concesionario;
	}
	
	/**
	 * 
	 * Actualizamos el nombre del concesionario
	 * 
	 * @param codigoC
	 * @param nombreConcecionario
	 * @throws ConcesionarioNoEncontradoException 
	 * @throws DatosObligatoriosNoPresentesException 
	 * @throws ErrorConexionJDBCException 
	 */
	public boolean actualizarConcesionario(String codigoC, String nombreConcecionario) throws ConcesionarioNoEncontradoException, DatosObligatoriosNoPresentesException {
	
		veriricaParametro(codigoC, "código concesionario");
	
		Concesionario concesionarioAActualizar = null; 
		
		ArrayList<Concesionario> listaConcesionarios = (ArrayList<Concesionario>) concesionarioDAO.readAll(); 
		
		//Buscamos el concesionario con el mismo código
		for(Concesionario concesionario : listaConcesionarios) {
			if(concesionario.getCodigoConcesionario().equals(codigoC)) {
				concesionarioAActualizar = concesionario;
			}
		}
		
		//Verificamos si se ha encontrado el concesionario 
		if(concesionarioAActualizar == null) {
			throw new ConcesionarioNoEncontradoException("El concecionario con el código " + codigoC + " no ha sido registrado");
		}else {
			//Modificamos nombre del concesionario
			concesionarioAActualizar.setNombre(nombreConcecionario);
			
			return concesionarioDAO.update(concesionarioAActualizar);
			
		}
		
	}

	/**
	 * 
	 * Borramos concesionario por su código
	 * 
	 * @param codigo
	 * @return 
	 * @throws ConcesionarioNoEncontradoException 
	 * @throws ErrorConexionJDBCException 
	 */
	public boolean borrarConcesionario(String codigo) throws ConcesionarioNoEncontradoException {
		
		Concesionario concesionarioABorrar = null;
		
		ArrayList<Concesionario> listaConcesionarios = (ArrayList<Concesionario>) concesionarioDAO.readAll();
		
		for(Concesionario concesionario: listaConcesionarios) {
			
			if(concesionario.getCodigoConcesionario().equals(codigo)) {
				concesionarioABorrar = concesionario;
			}
			
		}
		
		if(concesionarioABorrar == null) {
			throw new ConcesionarioNoEncontradoException(MensajesError.CONCESIONARIO_NO_ENCONTRADO + codigo);
		}
		return concesionarioDAO.detele(concesionarioABorrar);
		
	}

	/**
	 * 
	 * Añadimos un nuevo coche
	 * 
	 * @param nuevoCoche
	 * @throws DatosObligatoriosNoPresentesException 
	 * @throws ErrorConexionJDBCException 
	 */
	public boolean crearCoche(Coche nuevoCoche) throws DatosObligatoriosNoPresentesException{
		
		veriricaParametro(nuevoCoche.getMatricula(), "matrícula");
		veriricaParametro(nuevoCoche.getCodigoConcesionario(), "código concesionario");
		
		return cocheDAO.create(nuevoCoche);
		
	}

	/**
	 * 
	 * Recuperamos todos los coches
	 * 
	 * @return
	 */
	public List<Coche> leerCoches() {
		
		List<Coche> listaCoches = null;
		
		listaCoches = cocheDAO.readAll();
		
	    return listaCoches;
	}
	
	/**
	 * 
	 * Recuperamos un coche por su matrícula
	 * 
	 * @return
	 * @throws DatosObligatoriosNoPresentesException 
	 */
	public Coche leerCoche(String matricula) throws DatosObligatoriosNoPresentesException {
		
		veriricaParametro(matricula, "matrícula");
	
		Coche coche = cocheDAO.read(matricula);
		
		return coche;
	}

	/**
	 * 
	 * Este método actualiza el código del consionario asignado al coche
	 * 
	 * @param matriculaCoche
	 * @param codigoConces
	 * @throws CocheNoEncontradoException 
	 * @throws DatosObligatoriosNoPresentesException 
	 */
	public boolean actualizarCoche(String matriculaCoche, String codigoConces) throws CocheNoEncontradoException, DatosObligatoriosNoPresentesException {
		
		veriricaParametro(matriculaCoche, "matricula");
		veriricaParametro(codigoConces, "codigo concesionario");

		
		//Sincronizar lista en memoria con la persistencia
		List <Coche> listaCoches = cocheDAO.readAll();
		
		Coche cocheAActualizar = null; 
		
		//Vamos a encontrar el coche a actualizar
		for(Coche coche: listaCoches) {
			if(coche.getMatricula().equals(matriculaCoche)) {
				cocheAActualizar = coche;
			}
		}
		
		//Con este if comprobamos que se haya encontrado un coche con esa matrícula
		if(cocheAActualizar == null) {
			
			throw new CocheNoEncontradoException("Matricula " + matriculaCoche + " no encontrada.");
			
		}else {
			//Actualizar con el nuevo código concesionario
			cocheAActualizar.setCodigoConcesionario(codigoConces);
			
			return cocheDAO.update(cocheAActualizar);
			
		}
		
	}

	/**
	 * 
	 * Borramos coche por su matrícula
	 * 
	 * @param matricula
	 * @throws CocheNoEncontradoException 
	 */
	public boolean borrarCoche(String matricula) throws CocheNoEncontradoException {
		
		//Sincronizar lista en memoria con la persistencia
		List<Coche> listaCoches = cocheDAO.readAll();
				
		Coche cocheABorrar = null; 
				
		//Vamos a encontrar el coche a actualizar
		for(Coche coche: listaCoches) {
			if(coche.getMatricula().equals(matricula)) {
				cocheABorrar = coche;
			}
		}
		
		if(cocheABorrar == null) {
			throw new CocheNoEncontradoException("Matricula " + matricula + " no encontrada.");
		}else {
			//Borramos el objeto en la lista en memoria
			return cocheDAO.detele(cocheABorrar);
		}
		
	}
	

	private void veriricaParametro(String parametro, String tipoParametro) throws DatosObligatoriosNoPresentesException {
	
		if (parametro == null || parametro.isEmpty()) {
			throw new DatosObligatoriosNoPresentesException(MensajesError.PARAMETROS_OBLIGATORIOS_NO_PRESENTES + tipoParametro);
		}
		
	}

	
}
