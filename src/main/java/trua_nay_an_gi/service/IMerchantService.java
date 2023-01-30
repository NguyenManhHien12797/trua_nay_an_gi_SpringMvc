package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.Merchant;

public interface IMerchantService extends IGeneralService<Merchant> {

	@Override
	void save(Merchant merchant);

	@Override
	void update(Merchant merchant);

	@Override
	List<Merchant> findAll();

	@Override
	Merchant findById(Long id);

	Merchant findByName(String name);

	void updateStatus(Long id, String status, String role);

	List<?> findMerchantsOrUsersByStatus(String status, String role);

}
