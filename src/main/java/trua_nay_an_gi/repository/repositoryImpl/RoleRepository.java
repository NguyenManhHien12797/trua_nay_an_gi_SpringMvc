package trua_nay_an_gi.repository.repositoryImpl;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppRoles;
import trua_nay_an_gi.repository.IRoleRepository;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleRepository implements IRoleRepository<AppRoles>{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void setDefaultRole(Long accountId, Integer roleId) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<AppRoles> query = session.createQuery("insert into account_role(account_id,role_id) values (account_id = :account_id, role_id = :role_id)", AppRoles.class);
		query.setParameter("account_id", accountId);
		query.setParameter("role_id", roleId);
		query.executeUpdate();
	}

	@Override
	public AppRoles findByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<AppRoles> query = session.createQuery("FROM roles a WHERE a.name = :name", AppRoles.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	

}
