package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AppUser;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Order;
import shopbaeFood.model.Product;
import shopbaeFood.repository.IAppUserRepository;
import shopbaeFood.repository.IOrderRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderRepositoryImpl implements IOrderRepository {
	
	private final int PAGE_SIZE = 5;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IAppUserRepository userRepository;
	
	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Order findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Order.class, id);
	}

	@Override
	public void save(Order order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(order);

	}

	@Override
	public void update(Order order) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(order);

	}

	@Override
	public List<Order> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Order> orders = session.createQuery("FROM Order", Order.class).getResultList();
		return orders;
	}

	@Override
	public List<Order> findOrdersByUserId(Long user_id) {
		Session session = this.sessionFactory.getCurrentSession();
		AppUser user = userRepository.findById(user_id);
		TypedQuery<Order> query = session
				.createQuery("FROM Order o Where o.userDeleteFlag = false and o.user_id= :user_id", Order.class);
		query.setParameter("user_id", user);
		return query.getResultList();
	}

	@Override
	public List<Order> findOrdersByMerchantId(Long merchant_id, String status) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Order> query = session.createQuery(
				"FROM Order o Where o.deleteFlag = false and o.merchant_id= :merchant_id and o.status = :status order by o.id DESC",
				Order.class);
		query.setParameter("merchant_id", merchant_id);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Order> query = session.createQuery("FROM Order o Where o.deleteFlag = false and o.merchant_id= :merchant_id"
				+ " and o.status = :status or o.status = :status1 order by o.id DESC", Order.class);
		query.setParameter("merchant_id", merchant_id);
		query.setParameter("status", status);
		query.setParameter("status1", status1);
		return query.getResultList();
	}

	@Override
	public List<Order> findAllOrderByMerchantAndDeleteFlag(Long merchant_id, String status, int pageNumber) {
	    
			TypedQuery<Order> query = getSession().createQuery(
				"From Order o where o.deleteFlag = false and o.merchant_id = :merchant_id and o.status = :status", Order.class);
			query.setParameter("merchant_id", merchant_id);
			query.setParameter("status", status);
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			return query.getResultList();
	}

	@Override
	public Long lastPageNumber(Long merchant_id, String status) {
		String countQ = "Select count (o.id) from Order o where o.deleteFlag = false and o.merchant_id = :merchant_id and o.status = :status";
		Query countQuery = getSession().createQuery(countQ);
		countQuery.setParameter("merchant_id", merchant_id);
		countQuery.setParameter("status", status);
		Long countResults = (Long) countQuery.getSingleResult();
		Long lastPageNumber = (countResults / PAGE_SIZE);
		  if (countResults % PAGE_SIZE != 0) {
		      lastPageNumber = (countResults / PAGE_SIZE) + 1;
		    }
		return lastPageNumber;
	}

}
