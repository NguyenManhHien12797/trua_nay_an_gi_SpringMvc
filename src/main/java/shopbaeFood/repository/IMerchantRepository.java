package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;

public interface IMerchantRepository extends IGeneralRepository<Merchant> {

	List<Merchant> findMerchantsByStatus(Status status);
	
	List<Merchant> findMerchantsByStatusAndAddress(Status status ,String address);
	
	List<Merchant> findMerchantsByStatusAndSearch(Status status ,String search);
	

	Merchant findByName(String name);

}
