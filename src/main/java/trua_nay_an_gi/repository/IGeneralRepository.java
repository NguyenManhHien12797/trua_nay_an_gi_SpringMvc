package trua_nay_an_gi.repository;

import java.util.List;

public interface IGeneralRepository<T> {

	T findById(Long id);

	void save(T t);

	void update(T t);

	void delete(T t);

	List<T> findAll();
}
