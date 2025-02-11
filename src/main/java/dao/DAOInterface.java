package dao;

import java.util.List;

public interface DAOInterface<T, TypeData> {
	public List<T> findAll();

	public T findById(TypeData id);

	public void create(T t);

	public boolean deleteById(TypeData id);

	public void update(T t);
}
