package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.Account;

public interface IAccountRepository extends IGeneralRepository<Account> {

	@Override
	void save(Account t);

	@Override
	void update(Account t);

	@Override
	void delete(Account t);

	@Override
	List<Account> findAll();

	@Override
	Account findById(Long id);

	Account findByName(String name);

}
