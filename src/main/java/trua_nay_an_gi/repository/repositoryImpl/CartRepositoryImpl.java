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
import trua_nay_an_gi.model.ProductCartMap;
import trua_nay_an_gi.repository.ICartRepository;

@Repository(value = "cartRepository")
@Transactional(rollbackFor = Exception.class)
@EnableTransactionManagement
public class CartRepositoryImpl implements ICartRepository<Cart>{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Cart> findAllByUserId(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Cart> carts = session.createQuery("FROM Cart c Where c.user_id="+ userId, Cart.class).getResultList();
		return carts;
	}

	@Override
	public void saveCart(int quantity, double price, Long userID, Long productId, double totalPrice) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("insert into Cart c (c.quantity,c.price,c.user_id,c.product_id,c.totalPrice) values ("+ quantity +","+price+","+userID+","+productId+","+totalPrice+")")
				.addEntity(Cart.class);
		query.executeUpdate();
		
	}


	@Override
	public Cart findCartById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Cart.class, id);
	}

	@Override
	public Cart findCartByProductIdAndUserId(Long product_id, Long user_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("Select c FROM Cart c WHERE c.product_id ="+product_id+" and "+ " c.user_id ="+ user_id, Cart.class);
		try {
			return (Cart)query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}


	@Override
	public void setProductCart(Long cart_id, Long product_id) {
	
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("insert into product_cart(cart_id,product_id) values (?1,?2)")
				.addEntity(ProductCartMap.class);
		query.setParameter(1, cart_id);
		query.setParameter(2, product_id);
		query.executeUpdate();
	}

	@Override
	public void updateQuantityCart(int quantity, Long cart_id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("update Cart set quantity ="+ quantity+" where id=" +cart_id)
				.addEntity(Cart.class);
		query.executeUpdate();
		
	}

	@Override
	public void save(Cart cart) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.save(cart);
	}



}
