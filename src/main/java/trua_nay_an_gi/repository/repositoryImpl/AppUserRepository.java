package trua_nay_an_gi.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.repository.IAppUserRepository;

@Repository(value = "appUserRepository")
@Transactional(rollbackFor = Exception.class)
public class AppUserRepository implements IAppUserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(AppUser appUser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(appUser);
	}

	@Override
	public void update(AppUser appUser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(appUser);
	}

	@Override
	public void delete(AppUser appUser) {
		Session session = this.sessionFactory.getCurrentSession();
		session.remove(appUser);

	}

	@Override
	public List<AppUser> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<AppUser> appUsers = session.createQuery("FROM AppUser", AppUser.class).getResultList();
		return appUsers;
	}

	@Override
	public AppUser findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(AppUser.class, id);
	}

	@Override
	public AppUser findByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<AppUser> query = session.createQuery("FROM AppUser a WHERE a.name = :name", AppUser.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void saveUserToRegister(String address, String avatar, String name, String phone, String status,
			Long account_id) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Call INSERT_APPUSER(:address ,:avatar,:name,:phone,:status, :account_id)")
				.addEntity(AppUser.class);
		query.setParameter("address", address);
		query.setParameter("avatar", avatar);
		query.setParameter("name", name);
		query.setParameter("phone", phone);
		query.setParameter("status", status);
		query.setParameter("account_id", account_id);
		query.executeUpdate();

	}


}
