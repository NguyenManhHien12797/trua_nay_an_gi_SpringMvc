package shopbaeFood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import shopbaeFood.model.Account;
import shopbaeFood.model.Status;
import shopbaeFood.service.IAccountService;
import shopbaeFood.util.Constants;

@Component
public class CustomIdentityAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private IAccountService accountService;

	/**
	 * This method is used to check user is valid
	 * 
	 * @param username
	 * @param password
	 * @return user
	 */
	private UserDetails isValidUser(String username, String password) {
		Account account = accountService.findByName(username);
		if (account != null && username.equalsIgnoreCase(account.getUserName())
				&& passwordEncoder.matches(password, account.getPassword())) {

			UserDetails user = accountService.loadUserByUsername(username);

			return user;
		}
		return null;
	}

	/**
	 * This method is used to authenticate and check the login status: PENDING/
	 * ACTIVE/ BLOCK/ REFUSE
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails userDetails = isValidUser(username, password);

		if (userDetails != null) {
			if (userDetails.isAccountNonLocked()) {
				throw new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK);
			}
			Account account = accountService.findByName(username);
			if (account.getUser() != null && Status.BLOCK.equals(account.getUser().getStatus())) {
				throw new LockedException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK);
			}
			if (account.getMerchant() != null) {
				if (Status.PENDING.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_PENDING);
				}
				if (Status.BLOCK.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK);
				}
				if (Status.REFUSE.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_REFUSE);
				}
			}
			return new UsernamePasswordAuthenticationToken(userDetails, username, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException(Constants.RESPONSE_MESSAGE.LOGIN_FAILE);
		}
	}

	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
