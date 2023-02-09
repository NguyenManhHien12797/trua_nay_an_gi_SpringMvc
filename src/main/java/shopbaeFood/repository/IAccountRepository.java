package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Account;

public interface IAccountRepository extends IGeneralRepository<Account> {

	@Override
	void save(Account t);

	@Override
	void update(Account t);

	@Override
	List<Account> findAll();

	@Override
	Account findById(Long id);

	Account findByName(String name);

	Long findIdUserByUserName(String userName);

}
