package shopbaeFood.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
            	System.out.println("khóa");
            	SecurityContextHolder.clearContext();
            	request.logout();
            	response.sendRedirect("/shopbaeFood/login?mess=ban-account");
            }
           
        }
   
    	System.out.println("không khóa");
        return true;
    }

    /**
     * Executed before after handler is executed. If view is a redirect view, we don't need to execute postHandle
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) throws Exception {
//        if (model != null && !isRedirectView(model)) {
//            if (isUserLogged()) {
//                addToModelUserDetails(model);
//            }
//        }
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

    /**
     * Used when model is available
     */
    private void addToModelUserDetails(ModelAndView model) {
    	System.out.println("================= addToModelUserDetails ============================");
        String loggedUsername = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        model.addObject("loggedUsername", loggedUsername);
        System.out.println("session : " + model.getModel());
        System.out.println("================= addToModelUserDetails ============================");

    }

//    public static boolean isRedirectView(ModelAndView mv) {
//
//        String viewName = mv.getViewName();
//        if (viewName.startsWith("redirect:/")) {
//            return true;
//        }
//
//        View view = mv.getView();
//        return (view != null && view instanceof SmartView && ((SmartView) view).isRedirectView());
//    }

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
