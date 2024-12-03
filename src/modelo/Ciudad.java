package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * Clase modelo Ciudad. Contiene concesionarios.
 * 
 */
public class Ciudad implements Serializable {

    /**
     * Código único que identifica a la ciudad.
     */
    private String codigo;

    /**
     * Nombre de la ciudad.
     */
    private String nombre;

    /**
     * Lista de concesionarios asociados a la ciudad.
     */
    private ArrayList<Concesionario> listaConcesionarios;

    /**
     * Crea una instancia de la clase Ciudad con el código y nombre especificados.
     * Inicializa una lista vacía de concesionarios.
     * 
     * @param codigo el código único de la ciudad.
     * @param nombre el nombre de la ciudad.
     */
    public Ciudad(String codigo, String nombre) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.listaConcesionarios = new ArrayList<>();
    }

    /**
     * Crea una instancia de la clase Ciudad con el código, nombre y lista de concesionarios especificados.
     * 
     * @param codigo el código único de la ciudad.
     * @param nombre el nombre de la ciudad.
     * @param listaConcesionarios la lista de concesionarios asociados a la ciudad.
     */
    public Ciudad(String codigo, String nombre, ArrayList<Concesionario> listaConcesionarios) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.listaConcesionarios = listaConcesionarios;
    }

    /**
     * Obtiene el código de la ciudad.
     * 
     * @return el código de la ciudad.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la ciudad.
     * 
     * @param codigo el nuevo código de la ciudad.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre de la ciudad.
     * 
     * @return el nombre de la ciudad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la ciudad.
     * 
     * @param nombre el nuevo nombre de la ciudad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de concesionarios asociados a la ciudad.
     * 
     * @return la lista de concesionarios.
     */
    public ArrayList<Concesionario> getListaConcesionarios() {
        return listaConcesionarios;
    }

    /**
     * Establece la lista de concesionarios asociados a la ciudad.
     * 
     * @param listaConcesionarios la nueva lista de concesionarios.
     */
    public void setListaConcesionarios(ArrayList<Concesionario> listaConcesionarios) {
        this.listaConcesionarios = listaConcesionarios;
    }

    /**
     * Devuelve une cadena de la ciudad, incluyendo el código, 
     * nombre y la cantidad de concesionarios.
     * 
     * @return una cadena con la representación de la ciudad.
     */
    @Override
    public String toString() {
        return String.format("---------------------------------\n" +
                             "Código de la ciudad: %s\n" +
                             "Nombre: %s\n" +
                             "Número de concesionarios: %d\n", 
                             codigo, nombre, listaConcesionarios.size());
    }

}

