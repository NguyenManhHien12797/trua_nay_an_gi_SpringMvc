package trua_nay_an_gi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.repository.AcountRepository;
import trua_nay_an_gi.repository.IAccountRepository;

@Service
@Transactional
public class AccountService implements IAccountService{
	
	@Autowired
	private IAccountRepository accountRepository;


	@Override
	public Account findById(Long id) {
		return this.accountRepository.findById(id);
	}

	@Override
	public void save(Account account) {
		this.accountRepository.save(account);
		
	}

	@Override
	public void update(Account account) {
		this.accountRepository.update(account);
	}

	@Override
	public void delete(Long id) {
		Account account = this.findById(id);
		this.accountRepository.delete(account);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
//		return null;
	}

}
