package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Account;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.MerchantForm;
import shopbaeFood.model.Status;
import shopbaeFood.utils.Page;

public interface IMerchantService extends IGeneralService<Merchant> {

	/**
	 * This method is used to find Merchant by name
	 * 
	 * @param name
	 * @return Merchant
	 */
	Merchant findByName(String name);

	/**
	 * This method is used to update status
	 * 
	 * @param id
	 * @param status
	 * @param navRoute
	 */
	void updateStatus(Long id, Status status, String navRoute);

	/**
	 * This method is used to find Merchants or Users by status
	 * 
	 * @param status
	 * @param route  merchant-list/ user-list
	 * @return List
	 */
	List<?> findMerchantsOrUsersByStatus(Status status, String route);

	/**
	 * This method is used to find Merchants by status
	 * 
	 * @param status
	 * @return List<Merchant>
	 */
	List<Merchant> findMerchantsByStatus(Status status);

	List<Merchant> findMerchantsByStatusAndAddressAndCategory(Status status, String address, String category);
	
	List<Merchant> findMerchantsByStatusAndAddressAndCategoryAndProducName(Status status, String address, String category, String quickSearch);

	List<Merchant> findMerchantsByStatusAndCategoryAndSearch(Status status, String category, String search);

	/**
	 * This method is used to update Merchant info
	 * 
	 * @param merchantForm
	 * @param account
	 * @param session
	 */
	void updateMerchantInfo(MerchantForm merchantForm, Account account, HttpSession session);

	Page<?> page(Status status, String navRoute, int pageNumber);

}
