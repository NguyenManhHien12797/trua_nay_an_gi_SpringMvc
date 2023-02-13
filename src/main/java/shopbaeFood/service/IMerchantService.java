package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Account;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.MerchantForm;
import shopbaeFood.model.Status;

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

	void updateStatus(Long id, Status status, String role);

	List<?> findMerchantsOrUsersByStatus(Status status, String route);
	
	List<Merchant> findMerchantsByStatus(Status status);

	void updateMerchantInfo(MerchantForm merchantForm, Account account, HttpSession session);

}
