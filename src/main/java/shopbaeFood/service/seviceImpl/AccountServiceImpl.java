package shopbaeFood.service.seviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.Account;
import shopbaeFood.model.AccountDetails;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService, UserDetailsService {
	
    
    
    private static final long LOCK_TIME_DURATION = 10 * 1000;

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
	}

	@Override
	public Account getAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}else {
			Account account = accountRepository.findByName(authentication.getName());
			return account;
		}
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


	@Override
	public Boolean existsByUserName(String userName) {
		return accountRepository.existsByUserName(userName);
	}


	@Override
	public void increaseFailedAttempts(Account account) {
		  int newFailAttempts = account.getFailedAttempt() + 1;
		  account.setFailedAttempt(newFailAttempts);
		  accountRepository.update(account);
		
	}


	@Override
	public void resetFailedAttempts(Account account) {
		 account.setFailedAttempt(0);
		 accountRepository.update(account);
		
	}


	@Override
	public void lock(Account account) {
		account.setAccountNonLocked(true);
		account.setLockTime(new Date());
	         
		accountRepository.update(account);
		
	}


	@Override
	public boolean unlockWhenTimeExpired(Account account) {
	     long lockTimeInMillis = account.getLockTime().getTime();
	     long currentTimeInMillis = System.currentTimeMillis();
	         
	     if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
	        account.setAccountNonLocked(false);
	        account.setLockTime(null);
	        account.setFailedAttempt(0);
	             
	        accountRepository.update(account);
	             
	        return true;
	      }
	         
	      return false;
	}

}
