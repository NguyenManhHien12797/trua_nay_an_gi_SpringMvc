package trua_nay_an_gi.repository.repositoryImpl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.repository.IProductRepository;

@Repository(value = "productRepository")
@Transactional(rollbackFor = Exception.class)
public class ProductRepositoryImpl implements IProductRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Product findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Product.class, id);
	}

	@Override
	public void save(Product product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(product);
		
	}

	@Override
	public void update(Product product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(product);
		
	}

	@Override
	public void delete(Product product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.remove(product);
		
	}

	@Override
	public List<Product> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Product> products = session.createQuery("FROM Product", Product.class).getResultList();
		return products;
	}

	@Override
	public List<Product> findAllProductByDeleteFlag(Long id) {
		Session session= sessionFactory.getCurrentSession();
		return session.createQuery("From Product p where p.deleteFlag = false and p.merchant ="+id,Product.class).getResultList();

	}

	@Override
	public Long findMerchantIdByProduct(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Long> query = session.createQuery("Select p.merchant_id FROM Product p where p.name = "+ name);
		Long merchantId = query.getSingleResult();
		return merchantId;
	}

}
