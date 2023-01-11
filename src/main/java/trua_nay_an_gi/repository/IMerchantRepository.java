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
	void delete(Merchant merchant);

	@Override
	List<Merchant> findAll();

	Merchant findByName(String name);

	void saveMerchantToRegister(String address, String avatar, String name, String phone, String status,
			Long account_id);

}
