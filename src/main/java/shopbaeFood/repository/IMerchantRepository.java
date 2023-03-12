package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;

public interface IMerchantRepository extends IGeneralRepository<Merchant> {

	List<Merchant> findMerchantsByStatus(Status status);

	List<Merchant> findMerchantsByStatusAndAddressAndCategory(Status status, String address, String category);

	List<Merchant> findMerchantsByStatusAndCategoryAndSearch(Status status, String category, String search);

	Merchant findByName(String name);

}
