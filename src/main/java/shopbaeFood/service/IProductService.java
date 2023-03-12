package shopbaeFood.service;

import java.util.List;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductForm;

public interface IProductService {

	/**
	 * This method is used to find all product by deleteFlag and merchantId
	 * 
	 * @param merchant
	 * @return List<Product>
	 */
	List<Product> findAllProductByMerchantAndDeleteFlag(Merchant merchant);

	/**
	 * This method is used to update Product
	 * 
	 * @param id
	 * @param productForm
	 */
	void updateProduct(Long id, ProductForm productForm);

	/**
	 * This method is used to save Product
	 * 
	 * @param productForm
	 * @param session
	 * @return view merchant-product-manager
	 */
	String saveProduct(ProductForm productForm);

	/**
	 * This method is used to find Product by Id
	 * 
	 * @param id
	 * @return
	 */
	Product findById(Long id);

	/**
	 * This method is used to delete product
	 * 
	 * @param id
	 */
	void delete(Long id);

}
