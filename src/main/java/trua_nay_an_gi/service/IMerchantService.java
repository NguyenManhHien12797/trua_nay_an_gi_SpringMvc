package trua_nay_an_gi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.MerchantForm;

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
	
	void updateMerchantInfo(MerchantForm merchantForm, Account account, HttpSession session);

}
