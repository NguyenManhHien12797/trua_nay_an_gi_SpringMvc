package shopbaeFood.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import shopbaeFood.model.Account;
import shopbaeFood.service.IAccountService;
import shopbaeFood.util.Constants;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IAccountService accountService;
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_MERCHANT = "ROLE_MERCHANT";
	public static final String HOME_PATH = "/home";
	public static final String ADMIN_PATH = "/admin/merchant-list/ACTIVE/1";
	public static final String MERCHANT_PATH = "/merchant/merchant-dashboard";
	public static final String CHANGE_PASS_PATH = "/home/change-pass";
	
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLoginSuccessHandler() {
        super();
    }


    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
		Account account = accountService.findByName(authentication.getName());
		
		if(account.getFailedAttempt()> 0 ) {
			accountService.resetFailedAttempts(account);
		}
	
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(Constants.SESSION_EXPIRATION);
		session.setAttribute("user", account);
		if (account.getUser() != null) {
			session.setAttribute("userId", account.getUser().getId());
			session.setAttribute("username", account.getUser().getName());
			session.setAttribute("avatar", account.getUser().getAvatar());
			session.setAttribute("address", account.getUser().getAddress());
		}
		if (account.getMerchant() != null) {
			session.setAttribute("userId", account.getMerchant().getId());
			session.setAttribute("username", account.getMerchant().getName());
			session.setAttribute("avatar", account.getMerchant().getAvatar());
			session.setAttribute("address", account.getMerchant().getAddress());

		}
		
		session.setAttribute("authorities", authentication.getAuthorities());
    	
    	handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
       
    	String targetUrl = " ";
		Account account = accountService.findByName(authentication.getName());
		if(account.isFirstLogin()) {
			targetUrl = CHANGE_PASS_PATH;
		}
		else {
			targetUrl = determineTargetUrl(authentication);
		}
		
        if (response.isCommitted()) {
        	System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put(ROLE_USER, HOME_PATH);
        roleTargetUrlMap.put(ROLE_ADMIN, ADMIN_PATH);
        roleTargetUrlMap.put(ROLE_MERCHANT, MERCHANT_PATH);

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {

            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
