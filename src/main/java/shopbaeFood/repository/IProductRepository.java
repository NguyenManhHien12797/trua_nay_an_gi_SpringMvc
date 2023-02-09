package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Product;

public interface IProductRepository extends IGeneralRepository<Product> {

	@Override
	Product findById(Long id);

	@Override
	void save(Product product);

	@Override
	void update(Product product);

	@Override
	List<Product> findAll();

	List<Product> findAllProductByDeleteFlag(Long id);

	Long findMerchantIdByProduct(String name);

}
