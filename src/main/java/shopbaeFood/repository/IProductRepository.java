package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;

public interface IProductRepository extends IGeneralRepository<Product> {

	List<Product> findAllProductByMerchantAndDeleteFlag(Merchant merchant);

	Long findMerchantIdByProduct(String name);

}
