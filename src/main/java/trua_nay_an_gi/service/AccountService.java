package trua_nay_an_gi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AccountDetails;
import trua_nay_an_gi.repository.IAccountRepository;

@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService{
	
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

	@Override
	public Account findByName(String name) {
		return accountRepository.findByName(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByName(username);
		return AccountDetails.build(account);
	}

}
