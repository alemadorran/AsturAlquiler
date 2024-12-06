package persistenciaDAO;

import java.util.List;

import modelo.Coche;
import modelo.Concesionario;

public interface CocheDAO {
	
	boolean create(Coche coche);
	
	Concesionario read();
	
	List<Coche> readAll();
	
	boolean update(Coche coche);
	
	boolean detele(Coche coche);

}
