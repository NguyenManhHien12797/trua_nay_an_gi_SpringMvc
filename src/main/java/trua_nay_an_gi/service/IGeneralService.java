package trua_nay_an_gi.service;

import java.util.List;

public interface IGeneralService<T> {

	T findById(Long id);
	void save(T t);
	void update(T t);
	void delete(Long id);
	List<T> findAll();
}
