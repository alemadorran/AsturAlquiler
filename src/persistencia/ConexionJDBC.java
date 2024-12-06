package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import excepciones.MensajesError;
import logger.LoggerAplicacion;

import java.io.File;

/**
 * Clase para la gestion de la conexión a base de datos con JDBC.
 * Implementa el patrón Singleton para asegurar que solo exista una instancia de la conexión.
 */
public class ConexionJDBC {
    
    /**
     * Objeto de conexión a la base de datos.
     */
    private static Connection conn = null; 
    /**
     * Instancia única de la clase `ConexionJDBC` (patrón Singleton).
     */
    private static ConexionJDBC conexionJDBC; 
    
    /**
     * Método que proporciona acceso a la instancia única de la clase `ConexionJDBC`.
     * Si la instancia no existe, se crea. De lo contrario, se devuelve la existente.
     * 
     * @return La instancia única de `ConexionJDBC`.
     */
    public static ConexionJDBC instace()throws SQLException, NullPointerException{
    	
    	if(conexionJDBC == null || conexionJDBC.getConnection().isClosed()) {
    		conexionJDBC = new ConexionJDBC();
		} 
		return conexionJDBC;
    }
    
    public Connection getConnection() {
    	return conn;
    }
    
    /**
     * Constructor privado para evitar la creación de múltiples instancias de la clase.
     * Establece la conexión inicial con la base de datos.
     */
    private ConexionJDBC() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/asturalquiler",
            		"root", "1234");
        } catch (ClassNotFoundException e) {
			 LoggerAplicacion.logError(e);
		} catch (SQLException e) {
			 LoggerAplicacion.logError(e);
		}
    }
    
    /**
     * Método que crea la base de datos utilizando un script SQL externo.
     * El script debe contener sentencias SQL separadas por punto y coma (';').
     * 
     * @throws java.sql.SQLException Si ocurre un error al ejecutar las sentencias SQL.
     * @throws java.io.FileNotFoundException Si no se encuentra el archivo del script SQL.
     */
    public void crearBD() {
        // Ruta del archivo SQL
        String scriptCreacionDB = "D:/WORK/Clases particulares/Nacho/java_workspace/AsturAlquiler/src/creacionBD.sql"; 
        
        try (Statement stmt = conn.createStatement();
             Scanner scanner = new Scanner(new File(scriptCreacionDB))) {
            // Leer el script SQL línea por línea, separando comandos por ';'
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql); // Ejecutar cada sentencia SQL
                }
            }
        } catch (Exception e) {
            LoggerAplicacion.logError(e);
        }
    }
    /**
     * 
     * Método para cerrar la conexión
     * 
     */
    public void closeConnection() {
		try {
			conn.close();
		} catch (NullPointerException e1) {
			LoggerAplicacion.logError(e1);
			System.out.println(MensajesError.BASE_DATOS_NO_CREADA);
		}catch (Exception e) {
			LoggerAplicacion.logError(e);
			System.out.println(MensajesError.ERROR_INESPERADO);
		}
	}
}
