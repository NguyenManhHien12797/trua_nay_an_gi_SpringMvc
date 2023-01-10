package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.Merchant;

public interface IMerchantService extends IGeneralService<Merchant>{

	@Override
	void save(Merchant merchant);

	@Override
	void update(Merchant merchant);

	@Override
	void delete(Long id);

	@Override
	List<Merchant> findAll();

	@Override
	Merchant findById(Long id);

	Merchant findByName(String name);

	void saveMerchantToRegister(String address, String avatar, String name, String phone, String status, Long account_id);
}
