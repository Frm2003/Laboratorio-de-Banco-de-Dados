package persistence;

import java.sql.SQLException;
import java.util.List;

public interface interfaceDao<T> {
	public void inserir(T t) throws SQLException;
	public void atualizar(T t) throws SQLException;
	public void excluir(T t) throws SQLException;
	public T busca(T t) throws SQLException;
	public List<T> buscaAll() throws SQLException;
}