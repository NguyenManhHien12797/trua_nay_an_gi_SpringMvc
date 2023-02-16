package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;

public interface IMerchantRepository extends IGeneralRepository<Merchant> {

	List<Merchant> findMerchantsByStatus(Status status);

	Merchant findByName(String name);

}
