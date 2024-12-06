package persistenciaDAO;

import java.util.List;

import modelo.Coche;
import modelo.Concesionario;

public interface ICocheDAO {
	
	boolean create(Coche coche);
	
	Coche read(String matricula);
	
	List<Coche> readAll();
	
	boolean update(Coche coche);
	
	boolean detele(Coche coche);

}
