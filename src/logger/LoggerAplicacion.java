package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * Clase de utilidad para registrar mensajes de error en un archivo de log.
 * Los errores se escriben en un archivo llamado "errores.log".
 * 
 */
public class LoggerAplicacion {

    /**
     * Nombre del archivo de log donde se registran los errores.
     */
    private static final String ARCHIVO_LOG = "errores.log";

    /**
     * Formato de fecha y hora para cada entrada de log.
     */
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Bloque estático para inicializar el archivo de log
    static {
        File file = new File(ARCHIVO_LOG);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear archivo: " + ARCHIVO_LOG + " - " + e.getMessage());
            }
        }
    }

    /**
     * Registra un mensaje de error detallado de una excepción en el archivo de log.
     * Incluye el mensaje de la excepción y la traza completa del stack de la excepción.
     * 
     * @param ex la excepción que contiene el mensaje y la traza de stack.
     */
    public static void logError(Exception ex) {
        logError(ex.getMessage());
        for (StackTraceElement elemento : ex.getStackTrace()) {
            logError("\t" + elemento.toString());
        }
    }

    /**
     * Registra un mensaje de error simple en el archivo de log.
     * Añade una marca de tiempo y el mensaje de error en una nueva línea del archivo de log.
     * Si ocurre un error al escribir en el archivo, muestra el mensaje en la consola.
     * 
     * @param mensaje el mensaje de error a registrar.
     */
    private static void logError(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_LOG, true))) {
            String tiempo = LocalDateTime.now().format(FORMATO_FECHA);
            writer.write(tiempo + " - ERROR: " + mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

}