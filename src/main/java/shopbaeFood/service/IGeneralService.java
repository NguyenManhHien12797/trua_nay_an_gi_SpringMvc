package shopbaeFood.service;

import java.util.List;

public interface IGeneralService<T> {

	T findById(Long id);

	void save(T t);

	void update(T t);

	List<T> findAll();
}
