package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.Product;

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
	
	List<Product> findAllProductByDeleteFlag(Long id);
	
	Long findMerchantIdByProduct(String name);
	
}
