package shopbaeFood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import shopbaeFood.model.Account;
import shopbaeFood.model.Status;
import shopbaeFood.model.payload.MessageResponse;
import shopbaeFood.service.IAccountService;

@Component
public class CustomIdentityAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private IAccountService accountService;

	UserDetails isValidUser(String username, String password) {
		Account account = accountService.findByName(username);
		if (account != null && username.equalsIgnoreCase(account.getUserName())
				&& passwordEncoder.matches(password, account.getPassword())) {

			UserDetails user = accountService.loadUserByUsername(username);

			return user;
		}
		return null;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails userDetails = isValidUser(username, password);

		if (userDetails != null) {
			Account account = accountService.findByName(username);
			if (account.getUser() != null && Status.BLOCK.equals(account.getUser().getStatus())) {
				throw new BadCredentialsException("Tài khoản của bạn đã bị khóa !!");
			}

			if (account.getMerchant() != null) {
				if (Status.PENDING.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException("Tài khoản đang chờ admin duyệt !!");
				}
				if (Status.BLOCK.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException("Tài khoản của bạn đã bị khóa !!");
				}
				if (Status.REFUSE.equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException("Admin từ chối đăng ký Merchant !!");
				}
			}

			return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Tài khoản hoặc mật khẩu không đúng !!");
		}
	}

	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
