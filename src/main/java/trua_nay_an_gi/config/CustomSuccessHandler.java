package trua_nay_an_gi.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.service.IAccountService;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    private IAccountService accountService;
	
	  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	    @Override
	    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	            throws IOException {
	        String targetUrl = determineTargetUrl(authentication);

	        if (response.isCommitted()) {
	            System.out.println("Can't redirect");
	            return;
	        }
	        
	        redirectStrategy.sendRedirect(request, response, targetUrl);
	    }
	    
	    

	    /*
	     * This method extracts the roles of currently logged-in user and returns
	     * appropriate URL according to his/her role.
	     */
	    protected String determineTargetUrl(Authentication authentication) {

	    	
	        String url = "";

	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	        List<String> roles = new ArrayList<String>();

	        for (GrantedAuthority a : authorities) {
	            roles.add(a.getAuthority());
	        }
	        if (isAdmin(roles)) {
	        	
	  		  Account account = accountService.findByName(authentication.getName());
			  if(account.getUser() != null && "pending".equals(account.getUser().getStatus())) {
				  
				   url = "/admin";
			
			  }else {
				  url = "/khongcoquyen";
			  }
	        	
	         
	        } else if (isUser(roles)) {
	            url = "/user/create-account";
	        } else {
	            url = "/khongcoquyen";
	        }

	        return url;
	    }

	    private boolean isUser(List<String> roles) {
	        if (roles.contains("ROLE_USER")) {
	            return true;
	        }
	        return false;
	    }

	    private boolean isAdmin(List<String> roles) {
	        if (roles.contains("ROLE_ADMIN")) {
	            return true;
	        }
	        return false;
	    }

	    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
	        this.redirectStrategy = redirectStrategy;
	    }

	    protected RedirectStrategy getRedirectStrategy() {
	        return redirectStrategy;
	    }

	
}
