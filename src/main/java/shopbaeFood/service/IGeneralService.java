package shopbaeFood.service;

import java.util.List;

public interface IGeneralService<T> {

	/**
	 * This method is used to find T by id
	 * @return T
	 */
	T findById(Long id);

	/**
	 * This method is used to save T
	 */
	void save(T t);

	/**
	 * This method is used to update T
	 */
	void update(T t);

	/**
	 * This method is used to find all T
	 * @return List<T>
	 */
	List<T> findAll();
}
