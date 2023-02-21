package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.Account;
import shopbaeFood.repository.IAccountRepository;

@Repository(value = "accountRepository")
@Transactional(rollbackFor = Exception.class)
public class AcountRepository implements IAccountRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public void save(Account account) {
		 getSession().save(account);

	}

	@Override
	public void update(Account account) {
		 getSession().update(account);

	}

	@Override
	public List<Account> findAll() {
		List<Account> accounts = getSession().createQuery("FROM Account", Account.class).getResultList();
		return accounts;
	}

	@Override
	public Account findById(Long id) {
		return  getSession().get(Account.class, id);
	}

	@Override
	public Account findByName(String name) {
		TypedQuery<Account> query =  getSession().createQuery("FROM Account a WHERE a.userName = :userName", Account.class);
		query.setParameter("userName", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long findIdUserByUserName(String userName) {
		Account account = this.findByName(userName);
		if (account != null) {
			return account.getId();
		} else {
			return null;
		}
	}

	@Override
	public Boolean existsByUserName(String userName) {
		Account account = findByName(userName);
		return getSession().contains(account);
	}

}
