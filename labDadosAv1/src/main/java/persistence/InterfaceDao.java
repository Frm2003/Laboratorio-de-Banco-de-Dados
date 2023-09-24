package persistence;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDao<T> {
	public void iudCrud(String acao, T t) throws ClassNotFoundException, SQLException;
	public T buscar(T t) throws ClassNotFoundException, SQLException;
	public List<T> listar() throws ClassNotFoundException, SQLException;
}
