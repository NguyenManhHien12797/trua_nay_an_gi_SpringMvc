package trua_nay_an_gi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductForm;

public interface IProductService extends IGeneralService<Product>{

	@Override
	Product findById(Long id);

	@Override
	void save(Product product);

	@Override
	void update(Product product);
	
	@Override
	void delete(Long id);
	
	@Override
	List<Product> findAll();
	
	List<Product> findAllProductByDeleteFlag(HttpSession session);
	
	List<Product> findAllProductByDeleteFlag(Long merchantId);
	
	void updateProduct(Long id, ProductForm productForm);

	String saveProduct(ProductForm productForm, HttpSession session);
	
}
