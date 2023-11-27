package br.fatec.zl.AgisSpringWeb.persistence;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDao<T> {
	public boolean iudCrud(String acao, T t) throws ClassNotFoundException, SQLException;
	public T buscar(T t) throws ClassNotFoundException, SQLException;
	public List<T> listar() throws ClassNotFoundException, SQLException;
}
