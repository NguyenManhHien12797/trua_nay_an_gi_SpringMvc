package trua_nay_an_gi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.service.IAccountService;

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
		Account account = accountService.findByName(username);
		if (userDetails != null) {
			if (account.getUser() != null && "block".equals(account.getUser().getStatus())) {
				throw new BadCredentialsException("Tài khoản của bạn đã bị khóa !!");
			}

			if (account.getMerchant() != null) {
				if ("pending".equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException("Tài khoản đang chờ admin duyệt !!");
				}
				if ("block".equals(account.getMerchant().getStatus())) {
					throw new BadCredentialsException("Tài khoản của bạn đã bị khóa !!");
				}
				if ("refuse".equals(account.getMerchant().getStatus())) {
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
