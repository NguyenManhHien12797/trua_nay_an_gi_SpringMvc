package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.repository.IOrderDetailRepository;
import shopbaeFood.repository.IOrderRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDetailRepositoryImpl implements IOrderDetailRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}
	
	@Autowired
	private IOrderRepository orderRepository;

	@Override
	public OrderDetail findById(Long id) {
		return getSession().get(OrderDetail.class, id);
	}

	@Override
	public void save(OrderDetail orderDetail) {
		getSession().save(orderDetail);

	}

	@Override
	public void update(OrderDetail orderDetail) {
		getSession().update(orderDetail);

	}

	@Override
	public List<OrderDetail> findAll() {
		List<OrderDetail> orderDetails = getSession().createQuery("FROM OrderDetail", OrderDetail.class).getResultList();
		return orderDetails;
	}
	
	@Override
	public List<OrderDetail> findOrderDetailsByOrderId(Long order_id) {
		Order order = orderRepository.findById(order_id);
		TypedQuery<OrderDetail> query = getSession()
				.createQuery("FROM OrderDetail o Where o.deleteFlag = false and o.order= :order", OrderDetail.class);
		query.setParameter("order", order);
		return query.getResultList();
	}

}
