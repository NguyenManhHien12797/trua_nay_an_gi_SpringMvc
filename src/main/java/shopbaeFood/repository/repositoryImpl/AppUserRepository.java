package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;
import shopbaeFood.repository.IAppUserRepository;

@Repository(value = "appUserRepository")
@Transactional(rollbackFor = Exception.class)
public class AppUserRepository implements IAppUserRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public void save(AppUser appUser) {
		getSession().save(appUser);
	}

	@Override
	public void update(AppUser appUser) {
		getSession().update(appUser);
	}

	@Override
	public List<AppUser> findAll() {
		List<AppUser> appUsers = getSession().createQuery("FROM AppUser", AppUser.class).getResultList();
		return appUsers;
	}

	@Override
	public AppUser findById(Long id) {
		return getSession().get(AppUser.class, id);
	}

	@Override
	public AppUser findByName(String name) {
		TypedQuery<AppUser> query = getSession().createQuery("FROM AppUser a WHERE a.name = :name", AppUser.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<AppUser> findAppUsersByStatus(Status status) {
		TypedQuery<AppUser> query = getSession().createQuery("FROM AppUser a Where a.status= :status", AppUser.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

}
