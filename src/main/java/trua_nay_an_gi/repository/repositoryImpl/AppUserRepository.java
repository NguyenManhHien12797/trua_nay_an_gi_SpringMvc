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
import trua_nay_an_gi.model.Merchant;
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
	public List<AppUser> findAppUsersByStatus(String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM AppUser a Where a.status= :status");
		query.setParameter("status", status);
		return query.getResultList();
	}



}
