package modelo;

import java.io.Serializable;

/**
 * 
 * Clase modelo Coche.
 * 
 */
public class Coche implements Serializable {
    
    /**
     * Marca del coche.
     */
    private String marca;

    /**
     * Modelo del coche.
     */
    private String modelo;

    /**
     * Matrícula única del coche. Es la clave primaria.
     */
    private String matricula;

    /**
     * Código del concesionario asociado al coche.
     */
    private String codigoConcesionario;

    /**
     * Crea una instancia de la clase Coche con la marca, modelo, matrícula y 
     * código de concesionario.
     * 
     * @param marca la marca del coche.
     * @param modelo el modelo del coche.
     * @param matricula la matrícula única del coche.
     * @param codigoConcesionario el código del concesionario asociado al coche.
     */
    public Coche(String marca, String modelo, String matricula, String codigoConcesionario) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.codigoConcesionario = codigoConcesionario;
    }

    /**
     * Obtiene la marca del coche.
     * 
     * @return la marca del coche.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del coche.
     * 
     * @param marca la nueva marca del coche.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del coche.
     * 
     * @return el modelo del coche.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del coche.
     * 
     * @param modelo el nuevo modelo del coche.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene la matrícula del coche.
     * 
     * @return la matrícula del coche.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del coche.
     * 
     * @param matricula la nueva matrícula del coche.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el código del concesionario asociado al coche.
     * 
     * @return el código del concesionario.
     */
    public String getCodigoConcesionario() {
        return codigoConcesionario;
    }

    /**
     * Establece el código del concesionario asociado al coche.
     * 
     * @param codigoConcesionario el nuevo código del concesionario.
     */
    public void setCodigoConcesionario(String codigoConcesionario) {
        this.codigoConcesionario = codigoConcesionario;
    }

    /**
     * Devuelve una representación en cadena del coche, incluyendo marca, modelo, 
     * matrícula y el código del concesionario asociado.
     * 
     * @return una cadena con la representación del coche.
     */
    @Override
    public String toString() {
        return "Coche [marca=" + marca + ", modelo=" + modelo + ", matricula=" + matricula + ", codigoConcesionario=" + codigoConcesionario + "]";
    }
    
}

