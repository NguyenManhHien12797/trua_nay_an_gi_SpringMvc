package shopbaeFood.repository;

import shopbaeFood.model.Account;

public interface IAccountRepository extends IGeneralRepository<Account> {

	Account findByName(String name);

	Long findIdUserByUserName(String userName);

	Boolean existsByUserName(String userName);

}
