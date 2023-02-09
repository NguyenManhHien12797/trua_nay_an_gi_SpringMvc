package shopbaeFood.repository.repositoryImpl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;
import shopbaeFood.repository.IRoleRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
@EnableTransactionManagement
public class RoleRepository implements IRoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void setDefaultRole(AccountRoleMap accountRoleMap) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(accountRoleMap);
	}

	@Override
	public AppRoles findByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<AppRoles> query = session.createQuery("FROM Roles a WHERE a.name = :name", AppRoles.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public AppRoles findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(AppRoles.class, id);
	}

}
