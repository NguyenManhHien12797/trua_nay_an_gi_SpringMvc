package shopbaeFood.repository.repositoryImpl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;
import shopbaeFood.repository.IRoleRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleRepository implements IRoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public void setDefaultRole(AccountRoleMap accountRoleMap) {
		getSession().save(accountRoleMap);
	}

	@Override
	public AppRoles findByName(String name) {
		TypedQuery<AppRoles> query = getSession().createQuery("FROM Roles a WHERE a.name = :name", AppRoles.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public AppRoles findById(Long id) {
		return getSession().get(AppRoles.class, id);
	}

}
