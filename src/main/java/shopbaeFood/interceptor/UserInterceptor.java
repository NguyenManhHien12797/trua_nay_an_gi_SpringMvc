package shopbaeFood.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import shopbaeFood.model.Account;
import shopbaeFood.model.Status;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;


public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	IAccountRepository accountRepository;

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if (isUserLogged()) {
            addToModelUserDetails(request.getSession());
            
            if(isUserBlock()) {
            	SecurityContextHolder.clearContext();
            	request.logout();
            	response.sendRedirect("/shopbaeFood/login?mess=ban-account");
            }
           
        }
        return true;
    }

    /**
     * Used before model is generated, based on session
     */
    private void addToModelUserDetails(HttpSession session) {
       System.out.println("================= addToModelUserDetails ============================");
        String loggedUsername = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        session.setAttribute("username", loggedUsername);
        System.out.println("user(" + loggedUsername + ") session : " + session);
        System.out.println("================= addToModelUserDetails ============================");

    }
    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext()
                .getAuthentication()
                .getName()
                .equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
    
	public boolean isUserBlock() {

        try {
        	Account account = accountService.getAccount();
        	if(account ==null ) {
        		return false;
        	}
        	else {
        	if(account.getUser() != null && Status.BLOCK.equals(account.getUser().getStatus())){
        		return true;
        		}
        	if(account.getMerchant() != null && Status.BLOCK.equals(account.getMerchant().getStatus())){
        		return true;
        		}
        	}
        	return false;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return false;
        }
    }
    
}
