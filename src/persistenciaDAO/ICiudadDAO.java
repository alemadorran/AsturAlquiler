package persistenciaDAO;

import java.util.List;

import modelo.Ciudad;

public interface ICiudadDAO {

	boolean create(Ciudad ciudad);
	
	Ciudad read();
	
	List<Ciudad> readAll();
	
	boolean update(Ciudad ciudad);
	
	boolean detele(Ciudad ciudad);
	
	
}