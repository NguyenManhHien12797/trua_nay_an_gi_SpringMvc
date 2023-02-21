package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductForm;

public interface IProductService extends IGeneralService<Product> {

	/**
	 * This method is used to find all product by deleteFlag = false
	 * @param session
	 * @return List<Product>
	 */
	List<Product> findAllProductByDeleteFlag(HttpSession session);

	/**
	 * This method is used to find all product by deleteFlag and merchantId
	 * @param merchant
	 * @return List<Product>
	 */
	List<Product> findAllProductByDeleteFlag(Merchant merchant);
	
	List<Product> findAllProductByDeleteFlag(Merchant merchant, int pageNumber);
	
	Long lastPageNumber(Merchant merchant);

	/**
	 * This method is used to update Product
	 * @param id
	 * @param productForm
	 */
	void updateProduct(Long id, ProductForm productForm);

	/**
	 * This method is used to save Product
	 * @param productForm
	 * @param session
	 * @return view merchant-product-manager
	 */
	String saveProduct(ProductForm productForm, HttpSession session);
	
	/**
	 * This method is used to delete product
	 * @param id
	 */
	void delete(Long id);

}
