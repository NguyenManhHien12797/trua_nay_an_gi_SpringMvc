package shopbaeFood.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import shopbaeFood.model.Account;

public interface IAccountService extends IGeneralService<Account> {

	@Override
	Account findById(Long id);

	@Override
	void save(Account t);

	@Override
	void update(Account t);

	@Override
	List<Account> findAll();

	Account findByName(String name);

	Long findIdUserByUserName(String userName);

	UserDetails loadUserByUsername(String username);

}
