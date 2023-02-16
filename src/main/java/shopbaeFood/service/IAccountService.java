package shopbaeFood.service;


import org.springframework.security.core.userdetails.UserDetails;

import shopbaeFood.model.Account;

public interface IAccountService extends IGeneralService<Account> {

	/**
	 * This method is used to find Account by name
	 * @return Account
	 */
	Account findByName(String name);

	/**
	 * This method is used to find id Account by userName
	 * @return Long Account_id
	 */
	Long findIdUserByUserName(String userName);

	/**
	 * This method is used to build UserDetails by username
	 * @return AccountDetails
	 */
	UserDetails loadUserByUsername(String username);

}
