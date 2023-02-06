package trua_nay_an_gi.repository.repositoryImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.OrderDetail;
import trua_nay_an_gi.repository.IOrderDetailRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDetailRepositoryImpl implements IOrderDetailRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public OrderDetail findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(OrderDetail.class, id);
	}

	@Override
	public void save(OrderDetail orderDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(orderDetail);
		
	}

	@Override
	public void update(OrderDetail orderDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(orderDetail);
		
	}

	@Override
	public List<OrderDetail> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrderDetail> orderDetails = session.createQuery("FROM OrderDetail", OrderDetail.class).getResultList();
		return orderDetails;
	}

//	@Override
//	public List<OrderDetail> findOrderDetailsByMerchantId(Long merchant_id, String status) {
//		Session session = this.sessionFactory.getCurrentSession();
//		Query query = session.createQuery("FROM OrderDetail o Where o.deleteFlag = false and o.merchant_id="+ merchant_id + " and o.status = :status");
//		query.setParameter("status", status);
//		List<OrderDetail> orderDetails = query.getResultList();
//		return orderDetails;
//	}
//
	@Override
	public List<OrderDetail> findOrderDetailsByOrderId(Long order_id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrderDetail> orderDetails = session.createQuery("FROM OrderDetail o Where o.deleteFlag = false and o.order="+ order_id, OrderDetail.class).getResultList();
		return orderDetails;
	}



}
