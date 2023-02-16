package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Product;

public interface IProductRepository extends IGeneralRepository<Product> {

	List<Product> findAllProductByDeleteFlag(Long id);

	Long findMerchantIdByProduct(String name);

}
