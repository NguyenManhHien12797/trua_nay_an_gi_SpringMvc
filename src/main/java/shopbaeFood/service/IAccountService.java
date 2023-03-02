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
	
	/**
	 * This method is used to increase failedAttempts
	 * @param account
	 */
	void increaseFailedAttempts(Account account);
	
	/**
	 * This method is used to reset failedAttempts
	 * @param account
	 */
	void resetFailedAttempts(Account account);
	
	/**
	 * This method is used to lock account
	 * @param account
	 */
	void lock(Account account);
	
	/**
	 * This method is used to unlock account when time expired
	 * @param account
	 * @return
	 */
	boolean unlockWhenTimeExpired(Account account);
	
	/**
	 * This method is used to check extis acount by username
	 * @param userName
	 * @return
	 */
	Boolean existsByUserName(String userName);
	
	Account getAccount();

}
