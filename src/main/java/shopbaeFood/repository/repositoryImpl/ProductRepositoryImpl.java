package shopbaeFood.repository.repositoryImpl;

import java.util.List;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.repository.IProductRepository;

@Repository(value = "productRepository")
@Transactional(rollbackFor = Exception.class)
public class ProductRepositoryImpl implements IProductRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}
	

	@Override
	public Product findById(Long id) {
		return getSession().get(Product.class, id);
	}

	@Override
	public void save(Product product) {
		getSession().save(product);

	}

	@Override
	public void update(Product product) {
		getSession().update(product);

	}

	@Override
	public List<Product> findAll() {
		List<Product> products = getSession().createQuery("FROM Product", Product.class).getResultList();
		return products;
	}

	@Override
	public List<Product> findAllProductByMerchantAndDeleteFlag(Merchant merchant) {
		TypedQuery<Product> query = getSession().createQuery(
				"From Product p where p.deleteFlag = false and p.merchant = :merchant", Product.class);
		query.setParameter("merchant", merchant);
		return query.getResultList();

	}

	@Override
	public Long findMerchantIdByProduct(String name) {
		TypedQuery<Long> query = getSession().createQuery("Select p.merchant_id FROM Product p where p.name = :name", Long.class);
		query.setParameter("name", name);
		Long merchantId = query.getSingleResult();
		return merchantId;
	}




}
