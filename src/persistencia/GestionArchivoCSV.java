package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modelo.Ciudad;
import modelo.Coche;
import modelo.Concesionario;

/**
 * 
 * Clase de persistencia en archivos CSV
 * 
 */
public class GestionArchivoCSV {
	
	private static final String DELIMITADOR = ",";
	
	/**
	 * 
	 * Sobreescribimos un lista de ciudades
	 * 
	 * @param listaCiudades
	 * @param archivo
	 * @throws IOException
	 */
	public static void crearCiudad(List<Ciudad>listaCiudades, String archivo) throws IOException {
		
		FileWriter fileWriter = new FileWriter(archivo);
		
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		for(Ciudad ciudad: listaCiudades) {
			writer.write(ciudad.getCodigo()+ DELIMITADOR + ciudad.getNombre());
			writer.newLine();
		}
		writer.close();
		fileWriter.close();
	}

    /**
     * 
     * Recuperamos todas las ciudades del archivo CSV
     * 
     * @param archivoCiudad
     * @param archivoConcesionario
     * @param archivoCoche
     * @return
     * @throws IOException
     */
	public static List<Ciudad> leerCiudades(String archivoCiudad, String archivoConcesionario, String archivoCoche) throws IOException {
		
		List<Ciudad> listaCiudades = new ArrayList<Ciudad>();
		
		BufferedReader reader = new BufferedReader(new FileReader(archivoCiudad));
		
		String linea; 
		while((linea = reader.readLine()) != null) {
			String[] elementos = linea.split(DELIMITADOR);
			String codigoCiudad = elementos[0];
			String nombre = elementos[1];
			
			Ciudad nuevaCiudad = new Ciudad(codigoCiudad, nombre);
			
			ArrayList<Concesionario> concesionarios = new ArrayList<Concesionario>();
			
			concesionarios = leerConcesionarioPorCiudad(codigoCiudad, archivoConcesionario, archivoCoche);
			
			nuevaCiudad.setListaConcesionarios(concesionarios);
			
			listaCiudades.add(nuevaCiudad);
		}
		reader.close();
		return listaCiudades;
	}
	/**
	 * 
	 * Recuperamos concesionarios por ciudad
	 * 
	 * @param codigoCiudad
	 * @param archivoConcesionario
	 * @param archivoCoche
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<Concesionario> leerConcesionarioPorCiudad(String codigoCiudad, String archivoConcesionario, String archivoCoche) throws IOException{
		
		List<Concesionario> concecionarios = leerConcesionarios(archivoConcesionario, archivoCoche);
		List<Concesionario> concesionariosPorCiudad = new ArrayList<Concesionario>();
		
		for(Concesionario concesionario : concecionarios) {
			if(concesionario.getCodigoCiudad().equals(codigoCiudad)) {
				concesionariosPorCiudad.add(concesionario);
			}
		}
		return (ArrayList<Concesionario>) concesionariosPorCiudad;
	}

	/**
	 * 
	 * Sobreescribimos lista de concesionarios
	 * 
	 * @param listaConcesionarios
	 * @param archivo
	 * @throws IOException
	 */
	public static void crearConcesionario(List<Concesionario> listaConcesionarios, String archivo) throws IOException {

		FileWriter fileWriter = new FileWriter(archivo);
				
		BufferedWriter writer = new BufferedWriter(fileWriter);
				
		for(Concesionario concesionario: listaConcesionarios) {
			writer.write(concesionario.getCodigoConcesionario()+ DELIMITADOR + concesionario.getNombre() + DELIMITADOR + concesionario.getCodigoCiudad());
			writer.newLine();
		}
		writer.close();
		fileWriter.close();
	}

	/**
	 * 
	 * Recuperamos todos los concesionarios
	 * 
	 * @param archivoConcesionario
	 * @param archivoCoche
	 * @return
	 * @throws IOException
	 */
	public static List<Concesionario> leerConcesionarios(String archivoConcesionario, String archivoCoche) throws IOException {
		
		List<Concesionario> listaConcesionario = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader(archivoConcesionario));
		
		String linea; 
		while((linea = reader.readLine()) != null) {
			String[] elementos = linea.split(DELIMITADOR);
			String codigoConcesionario = elementos[0];
			String nombre = elementos[1];
			String codigoCiudad = elementos[2];
			
			Concesionario nuevoConcesionario = new Concesionario(codigoConcesionario, nombre, codigoCiudad);
			
			List<Coche> coches = new ArrayList<Coche>();
			coches = leerCochesPorConcesionario(codigoConcesionario, archivoCoche );
			nuevoConcesionario.setListaCoche((ArrayList<Coche>) coches);
			listaConcesionario.add(nuevoConcesionario);
		}
		reader.close();
		return listaConcesionario;
		
	}
	
	/**
	 * 
	 * Devolvemos los coches que pertenecen a un determinado concesionario
	 * 
	 * @param codigoConcesionario
	 * @param archivoCoche
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<Coche> leerCochesPorConcesionario(String codigoConcesionario, String archivoCoche) throws IOException {
		
		List<Coche> coches = leerCoches(archivoCoche);
		List<Coche> cochesPorConcesionario = new ArrayList<Coche>();
		
		for(Coche coche: coches) {
			if(coche.getCodigoConcesionario().equals(codigoConcesionario)) {
				cochesPorConcesionario.add(coche);
			}
		}
		return (ArrayList<Coche>) cochesPorConcesionario; 
	}

	/**
	 * 
	 * Sobreescribimos lista de coches
	 * 
	 * @param listaCoches
	 * @param archivo
	 * @throws IOException
	 */
	public static void crearCoche(List<Coche> listaCoches, String archivo) throws IOException {
		
		FileWriter fileWriter = new FileWriter(archivo);
		
		BufferedWriter writer = new BufferedWriter(fileWriter);
				
		for(Coche coche: listaCoches) {
			writer.write(coche.getMarca()+ DELIMITADOR + coche.getModelo() + DELIMITADOR 
					      + coche.getMatricula() + DELIMITADOR + coche.getCodigoConcesionario());
			writer.newLine();
		}
		writer.close();
		fileWriter.close();
		
	}

	/**
	 * 
	 * Recuperamos todos los coches
	 * 
	 * @param archivo
	 * @return
	 * @throws IOException
	 */
	public static List<Coche> leerCoches(String archivo) throws IOException{
		
		List<Coche> listaCoches = new ArrayList<>();
		
		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		
		String linea; 
		while((linea = reader.readLine()) != null) {
			
			String[] elementos = linea.split(DELIMITADOR);
			
			String marca = elementos[0];
			String modelo = elementos[1];
			String matricula = elementos[2];
			String codigoConcesionario = elementos[3];
			
			Coche nuevoCoche = new Coche(marca, modelo, matricula, codigoConcesionario);
			
			listaCoches.add(nuevoCoche);
		}
		reader.close();
		return listaCoches;		
	}
	
	
	

}
