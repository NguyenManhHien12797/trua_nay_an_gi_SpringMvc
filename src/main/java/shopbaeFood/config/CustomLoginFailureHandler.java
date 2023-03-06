package shopbaeFood.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import shopbaeFood.model.Account;
import shopbaeFood.model.Status;
import shopbaeFood.service.IAccountService;
import shopbaeFood.util.Constants;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	public static final int MAX_FAILED_ATTEMPTS = 3;
	
	@Autowired
	private IAccountService accountService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		Account account = accountService.findByName(request.getParameter("userName"));
		if(account != null) {
			boolean hasError = false;
			if(!hasError) {
				if (account.getUser() != null && Status.BLOCK.equals(account.getUser().getStatus())) {
					exception = new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK);
					hasError = true;
				}
			}
			
			if(!hasError) {
				if (account.getMerchant() != null) {
					if (Status.PENDING.equals(account.getMerchant().getStatus())) {
						exception = new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_PENDING);
						}
					if (Status.BLOCK.equals(account.getMerchant().getStatus())) {
						exception = new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK);
						}
					if (Status.REFUSE.equals(account.getMerchant().getStatus())) {
						exception = new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_REFUSE);
						}
					hasError = true;
					
					}
			}
				
			if(!hasError) {
				
				if(!account.isAccountNonLocked()) {
					if(account.getFailedAttempt() < MAX_FAILED_ATTEMPTS - 1) {
						if(account.getFailedAttempt() == 0) {
							exception = new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK_FIRST_ALERT);
						}
						if(account.getFailedAttempt() == 1) {
							exception = new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK_LAST_ALERT);
						}
						accountService.increaseFailedAttempts(account);
					}
					else {
						accountService.lock(account);
						exception = new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK_ALERT);
					}
				} else if (account.isAccountNonLocked()) {
	                if (accountService.unlockWhenTimeExpired(account)) {
	                    exception = new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_UNBLOCK);
	                }
	            }
				
			}
			
		}
		super.setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
	}
	
	

}
