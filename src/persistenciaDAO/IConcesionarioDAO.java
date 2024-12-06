package persistenciaDAO;

import java.util.List;

import modelo.Ciudad;
import modelo.Concesionario;

public interface IConcesionarioDAO {
	
	boolean create(Concesionario concesionario);
	
	Concesionario read();
	
	List<Concesionario> readAll();
	
	boolean update(Concesionario concesionario);
	
	boolean detele(Concesionario concesionario);
	
	
}
