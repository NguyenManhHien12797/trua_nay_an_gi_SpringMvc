package trua_nay_an_gi.service.seviceImpl;

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
import trua_nay_an_gi.service.IAccountService;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService, UserDetailsService {

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
	public List<Account> findAll() {
		return this.accountRepository.findAll();
//		return null;
	}

	@Override
	public Account findByName(String name) {
		return accountRepository.findByName(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByName(username);
		return AccountDetails.build(account);
	}

	@Override
	public Long findIdUserByUserName(String userName) {
		return accountRepository.findIdUserByUserName(userName);
	}

}
