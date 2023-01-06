package trua_nay_an_gi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AccountDetails;
import trua_nay_an_gi.service.AccountService;
import trua_nay_an_gi.service.IAccountService;

@Component
public class CustomIdentityAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private IAccountService accountService;
	
	private AccountService accountService2 = new AccountService();
	
	  UserDetails isValidUser(String username, String password) {
		  Account account = accountService.findByName(username);
		  
		    if (username.equalsIgnoreCase(account.getUserName())
		        && password.equals(account.getPassword())) {
		    	
		    	 AccountDetails userDetails = (AccountDetails)accountService2.loadUserByUsername(username);
//		      UserDetails user = User
//		          .withUsername(username)
//		          .password("NOT_DISCLOSED")
//		          .roles("ADMIN")
//		          .build();

		      return userDetails;
//		      return user;
		    }
		    return null;
		  }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getName();
	    String password = authentication.getCredentials().toString();

	    UserDetails userDetails = isValidUser(username, password);

	    if (userDetails != null) {
	      return new UsernamePasswordAuthenticationToken(
	          username,
	          password,
	          userDetails.getAuthorities());
	    } else {
	      throw new BadCredentialsException("Incorrect user credentials !!");
	    }
	}

	 @Override
	  public boolean supports(Class<?> authenticationType) {
	    return authenticationType
	        .equals(UsernamePasswordAuthenticationToken.class);
	  }

}
