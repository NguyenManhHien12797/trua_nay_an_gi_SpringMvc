package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;

public interface IMerchantRepository extends IGeneralRepository<Merchant> {

	@Override
	Merchant findById(Long id);

	@Override
	void save(Merchant merchant);

	@Override
	void update(Merchant merchant);

	@Override
	List<Merchant> findAll();

	List<Merchant> findMerchantsByStatus(Status status);

	Merchant findByName(String name);

}
