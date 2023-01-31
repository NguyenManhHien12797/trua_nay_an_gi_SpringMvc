package trua_nay_an_gi.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductCartMap;
import trua_nay_an_gi.repository.ICartRepository;

@Repository(value = "cartRepository")
@Transactional(rollbackFor = Exception.class)
@EnableTransactionManagement
public class CartRepositoryImpl implements ICartRepository{
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Cart> carts = session.createQuery("FROM Cart c Where c.deleteFlag = false and c.user_id="+ userId, Cart.class).getResultList();
		return carts;
	}

	@Override
	public Cart findCartByProductIdAndUserId(Long product_id, Long user_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("Select c FROM Cart c WHERE c.deleteFlag = false and c.product ="+product_id+" and "+ " c.user_id ="+ user_id, Cart.class);
		try {
			return (Cart)query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	


	@Override
	public void setProductCart(ProductCartMap productCartMap) {
	
		Session session = this.sessionFactory.getCurrentSession();
		session.save(productCartMap);
		
	}

	@Override
	public void save(Cart cart) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(cart);
	}



	@Override
	public Cart findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Cart.class, id);
	}



	@Override
	public void update(Cart cart) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(cart);
		
	}


	@Override
	public List<Cart> findAll() {
		return null;
	}







}
