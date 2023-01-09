package trua_nay_an_gi.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Account;

@Repository(value = "accountRepository")
@Transactional(rollbackFor = Exception.class)
public class AcountRepository implements IAccountRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Account account) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(account);

	}

	@Override
	public void update(Account account) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(account);

	}

	@Override
	public void delete(Account account) {
		Session session = this.sessionFactory.getCurrentSession();
		session.remove(account);

	}

	@Override
	public List<Account> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Account> accounts = session.createQuery("FROM account", Account.class).getResultList();
		return accounts;
	}

	@Override
	public Account findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Account.class, id);
	}

	@Override
	public Account findByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Account> query = session.createQuery("FROM account a WHERE a.userName = :userName", Account.class);
		query.setParameter("userName", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
