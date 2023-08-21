package persistence;

import java.sql.SQLException;
import java.util.List;

public interface interfaceDao<T> {
	public void criar(T t) throws SQLException;
	public T buscar(T t) throws SQLException;
	public void alterar(T t) throws SQLException;
	public void remover(T t) throws SQLException;
	public List<T> buscaAll() throws SQLException;
}