package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AppUser;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductCartMap;
import shopbaeFood.repository.IAppUserRepository;
import shopbaeFood.repository.ICartRepository;
import shopbaeFood.repository.IProductRepository;

@Repository(value = "cartRepository")
@Transactional(rollbackFor = Exception.class)
public class CartRepositoryImpl implements ICartRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Autowired
	private IAppUserRepository userRepository;

	@Autowired
	private IProductRepository productRepository;

	@Override
	public List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId) {
		AppUser user = userRepository.findById(userId);
		TypedQuery<Cart> query = getSession()
				.createQuery("FROM Cart c Where c.deleteFlag = false and c.user_id= :user_id", Cart.class);
		query.setParameter("user_id", user);
		return query.getResultList();
	}

	@Override
	public Cart findCartByProductIdAndUserId(Long product_id, Long user_id) {
		AppUser user = userRepository.findById(user_id);
		Product product = productRepository.findById(product_id);
		TypedQuery<Cart> query = getSession().createQuery(
				"Select c FROM Cart c WHERE c.deleteFlag = false and c.product = :product and  c.user_id = :user_id",
				Cart.class);

		query.setParameter("product", product);
		query.setParameter("user_id", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public void setProductCart(ProductCartMap productCartMap) {
		getSession().save(productCartMap);

	}

	@Override
	public void save(Cart cart) {
		getSession().save(cart);
	}

	@Override
	public Cart findById(Long id) {
		return getSession().get(Cart.class, id);
	}

	@Override
	public void update(Cart cart) {
		getSession().update(cart);

	}

	@Override
	public List<Cart> findAll() {
		return null;
	}

}
