//package trua_nay_an_gi.repository;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import trua_nay_an_gi.model.Roles;
//
//@Repository
//@Transactional(rollbackFor = Exception.class)
//public class RoleRepository implements IRoleRepository{
//
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	@Override
//	public Roles findById(Long id) {
//		Session session = this.sessionFactory.getCurrentSession();
//		return session.get(Roles.class, id);
//	}
//
//	@Override
//	public void save(Roles role) {
//		Session session = this.sessionFactory.getCurrentSession();
//		session.save(role);
//	}
//
//	@Override
//	public void update(Roles role) {
//		Session session = this.sessionFactory.getCurrentSession();
//		session.update(role);
//	}
//
//	@Override
//	public void delete(Roles role) {
//		Session session = this.sessionFactory.getCurrentSession();
//		session.remove(role);
//		
//	}
//
//	@Override
//	public List<Roles> findAll() {
//		Session session = this.sessionFactory.getCurrentSession();
//		List<Roles> roles = session.createQuery("FROM roles", Roles.class).getResultList();
//		return roles;
//	}
//
//}
