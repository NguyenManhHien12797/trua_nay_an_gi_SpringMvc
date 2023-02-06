package trua_nay_an_gi.repository.repositoryImpl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.repository.IOrderRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderRepositoryImpl implements IOrderRepository{

	@Autowired
	private SessionFactory sessionFactory;

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
		List<Order> orders = session.createQuery("FROM Order o Where o.deleteFlag = false and o.user_id="+ user_id, Order.class).getResultList();
		return orders;
	}

	@Override
	public List<Order> findOrdersByMerchantId(Long merchant_id, String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Order o Where o.deleteFlag = false and o.merchant_id="+ merchant_id + " and o.status = :status order by o.id DESC");
		query.setParameter("status", status);
		List<Order> orders = query.getResultList();
		return orders;
	}

	@Override
	public List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Order o Where o.deleteFlag = false and o.merchant_id="+ merchant_id + " and o.status = :status or o.status = :status1 order by o.id DESC");
		query.setParameter("status", status);
		query.setParameter("status1", status1);
		List<Order> orders = query.getResultList();
		return orders;
	}



}
