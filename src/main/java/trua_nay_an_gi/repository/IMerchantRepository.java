package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.Merchant;

public interface IMerchantRepository extends IGeneralRepository<Merchant> {

	@Override
	Merchant findById(Long id);

	@Override
	void save(Merchant merchant);

	@Override
	void update(Merchant merchant);


	@Override
	List<Merchant> findAll();
	
	List<Merchant> findMerchantsByStatus(String status);

	Merchant findByName(String name);

}
