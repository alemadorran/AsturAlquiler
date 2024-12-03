package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * Clase modelo Concesionario. Contiene coches. 
 * 
 */
public class Concesionario implements Serializable {
    
    /**
     * Código único que identifica al concesionario.
     */
    private String codigoConcesionario;

    /**
     * Nombre del concesionario.
     */
    private String nombre;

    /**
     * Lista de coches asociados al concesionario.
     */
    private ArrayList<Coche> listaCoche;

    /**
     * Código de la ciudad a la que pertenece el concesionario.
     */
    private String codigoCiudad;

    /**
     * Crea una instancia de la clase Concesionario con el código, nombre y código de ciudad especificados.
     * Inicializa una lista vacía de coches.
     * 
     * @param codigoConcesionario el código único del concesionario.
     * @param nombre el nombre del concesionario.
     * @param codigoCiudad el código de la ciudad a la que pertenece el concesionario.
     */
    public Concesionario(String codigoConcesionario, String nombre, String codigoCiudad) {
        this.codigoConcesionario = codigoConcesionario;
        this.nombre = nombre;
        this.codigoCiudad = codigoCiudad;
        this.listaCoche = new ArrayList<Coche>();
    }

    /**
     * Crea una instancia de la clase Concesionario con el código, nombre, lista de coches y código de ciudad especificados.
     * 
     * @param codigoConcesionario el código único del concesionario.
     * @param nombre el nombre del concesionario.
     * @param listaCoche la lista de coches asociados al concesionario.
     * @param codigoCiudad el código de la ciudad a la que pertenece el concesionario.
     */
    public Concesionario(String codigoConcesionario, String nombre, ArrayList<Coche> listaCoche, String codigoCiudad) {
        this.codigoConcesionario = codigoConcesionario;
        this.nombre = nombre;
        this.listaCoche = listaCoche;
        this.codigoCiudad = codigoCiudad;
    }

    /**
     * Obtiene el código del concesionario.
     * 
     * @return el código del concesionario.
     */
    public String getCodigoConcesionario() {
        return codigoConcesionario;
    }

    /**
     * Establece el código del concesionario.
     * 
     * @param codigoConcesionario el nuevo código del concesionario.
     */
    public void setCodigoConcesionario(String codigoConcesionario) {
        this.codigoConcesionario = codigoConcesionario;
    }

    /**
     * Obtiene el nombre del concesionario.
     * 
     * @return el nombre del concesionario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del concesionario.
     * 
     * @param nombre el nuevo nombre del concesionario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de coches asociados al concesionario.
     * 
     * @return la lista de coches.
     */
    public ArrayList<Coche> getListaCoche() {
        return listaCoche;
    }

    /**
     * Establece la lista de coches asociados al concesionario.
     * 
     * @param listaCoche la nueva lista de coches.
     */
    public void setListaCoche(ArrayList<Coche> listaCoche) {
        this.listaCoche = listaCoche;
    }

    /**
     * Obtiene el código de la ciudad a la que pertenece el concesionario.
     * 
     * @return el código de la ciudad.
     */
    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    /**
     * Establece el código de la ciudad a la que pertenece el concesionario.
     * 
     * @param codigoCiudad el nuevo código de la ciudad.
     */
    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    /**
     * Devuelve una representación en cadena del concesionario, incluyendo el código, 
     * nombre, cantidad de coches asociados, y el código de la ciudad.
     * 
     * @return una cadena con la representación del concesionario.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("Código Concesionario: ").append(codigoConcesionario).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Número de Coches: ").append(listaCoche.size()).append("\n");
        sb.append("Código Ciudad: ").append(codigoCiudad).append("\n");
        sb.append("=====================================");
        return sb.toString();
    }

}

