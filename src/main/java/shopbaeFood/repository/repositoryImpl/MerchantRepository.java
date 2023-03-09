package shopbaeFood.repository.repositoryImpl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;
import shopbaeFood.repository.IMerchantRepository;

@Repository(value = "merchantRepository")
@Transactional(rollbackFor = Exception.class)
public class MerchantRepository implements IMerchantRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Merchant findById(Long id) {
		return getSession().get(Merchant.class, id);
	}

	@Override
	public void save(Merchant merchant) {
		getSession().save(merchant);
	}

	@Override
	public void update(Merchant merchant) {
		getSession().update(merchant);
	}

	@Override
	public List<Merchant> findAll() {
		List<Merchant> merchants = getSession().createQuery("FROM Merchant", Merchant.class).getResultList();
		return merchants;
	}

	@Override
	public Merchant findByName(String name) {
		TypedQuery<Merchant> query = getSession().createQuery("FROM Merchant m WHERE m.name = :name", Merchant.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Merchant> findMerchantsByStatus(Status status) {
		TypedQuery<Merchant> query = getSession().createQuery("FROM Merchant m Where m.status= :status", Merchant.class);
		query.setParameter("status", status);
		try {
		return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	
	}

	@Override
	public List<Merchant> findMerchantsByStatusAndAddress(Status status, String address) {
		TypedQuery<Merchant> query = getSession().createQuery("FROM Merchant m Where m.status= :status and m.address = :address", Merchant.class);
		query.setParameter("status", status);
		query.setParameter("address", address);
		try {
		return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Merchant> findMerchantsByStatusAndSearch(Status status, String search) {
		TypedQuery<Merchant> query = getSession().createQuery("FROM Merchant m Where m.status= :status and m.address like :search or m.name like :search " , Merchant.class);
		query.setParameter("status", status);
		query.setParameter("address", search +"%");
		try {
		return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
