//package trua_nay_an_gi.repository.repositoryImpl;
//
//import java.util.List;
//
//import javax.persistence.NoResultException;
//import javax.persistence.Query;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import trua_nay_an_gi.model.CartDetail;
//import trua_nay_an_gi.repository.ICartDetailRepository;
//
//@Repository
//@Transactional(rollbackFor = Exception.class)
//public class CartDetailRepositoryImpl implements ICartDetailRepository{
//	
//	@Autowired
//	private SessionFactory sessionFactory;
//
//	@Override
//	public CartDetail findById(Long id) {
//		Session session = this.sessionFactory.getCurrentSession();
//		return session.get(CartDetail.class, id);
//	}
//
//	@Override
//	public void save(CartDetail cartDetail) {
//		Session session = this.sessionFactory.getCurrentSession();
//		session.save(cartDetail);
//		
//	}
//
//	@Override
//	public void update(CartDetail cartDetail) {
//		Session session = this.sessionFactory.getCurrentSession();
//		session.update(cartDetail);
//		
//	}
//
//	@Override
//	public List<CartDetail> findAll() {
//		Session session = this.sessionFactory.getCurrentSession();
//		List<CartDetail> cartDetails = session.createQuery("FROM CartDetail", CartDetail.class).getResultList();
//		return cartDetails;
//	}
//
//	@Override
//	public CartDetail findCartDetailByProductId(Long product_id) {
//		Session session = this.sessionFactory.getCurrentSession();
//		Query query = session.createQuery("Select c FROM CartDetail c WHERE c.deleteFlag = false and c.product_id ="+product_id, CartDetail.class);
//		try {
//			return (CartDetail) query.getSingleResult();
//		} catch (NoResultException e) {
//			return null;
//		}
//	}
//
//	@Override
//	public List<CartDetail> findAllCartDetailByUserIdAndDeleteFlag(Long userId) {
//		Session session = this.sessionFactory.getCurrentSession();
//		List<CartDetail> cartDetails = session.createQuery("FROM CartDetail c Where c.deleteFlag = false and c.user_id="+ userId, CartDetail.class).getResultList();
//		return cartDetails;
//	}
//
//}
