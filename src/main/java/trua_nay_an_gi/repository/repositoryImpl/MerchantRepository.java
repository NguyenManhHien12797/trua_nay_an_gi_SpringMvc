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

import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.repository.IMerchantRepository;

@Repository(value = "merchantRepository")
@Transactional(rollbackFor = Exception.class)
public class MerchantRepository implements IMerchantRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Merchant findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Merchant.class, id);
	}

	@Override
	public void save(Merchant merchant) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(merchant);
	}

	@Override
	public void update(Merchant merchant) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(merchant);
	}


	@Override
	public List<Merchant> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Merchant> merchants = session.createQuery("FROM Merchant", Merchant.class).getResultList();
		return merchants;
	}

	@Override
	public Merchant findByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		TypedQuery<Merchant> query = session.createQuery("FROM Merchant m WHERE m.name = :name", Merchant.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Merchant> findMerchantsByStatus(String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Merchant m Where m.status= :status");
		query.setParameter("status", status);
		return query.getResultList();
	}


}
