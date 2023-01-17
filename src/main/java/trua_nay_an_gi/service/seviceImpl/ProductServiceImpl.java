package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.repository.IProductRepository;
import trua_nay_an_gi.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductRepository productRepository;

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
		
	}

	@Override
	public void update(Product product) {
		productRepository.update(product);
		
	}

	@Override
	public void delete(Long id) {
		Product product = new Product();
		productRepository.delete(product);
		
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllProductByDeleteFlag(Long id) {
		return productRepository.findAllProductByDeleteFlag(id);
	}

	@Override
	public Long findMerchantIdByProduct(String name) {
		return productRepository.findMerchantIdByProduct(name);
	}

}
