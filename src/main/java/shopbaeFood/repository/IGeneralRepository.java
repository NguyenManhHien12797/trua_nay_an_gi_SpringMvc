package shopbaeFood.repository;

import java.util.List;

public interface IGeneralRepository<T> {

	T findById(Long id);

	void save(T t);

	void update(T t);

	List<T> findAll();
}
