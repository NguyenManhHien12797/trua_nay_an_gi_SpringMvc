package trua_nay_an_gi.service;

import java.util.List;


import trua_nay_an_gi.model.Account;


public interface IAccountService extends IGeneralService<Account> {

	@Override
	Account findById(Long id);

	@Override
	void save(Account t);

	@Override
	void update(Account t);

	@Override
	void delete(Long id);

	@Override
	List<Account> findAll();
	
	Account findByName(String name);

	
}
