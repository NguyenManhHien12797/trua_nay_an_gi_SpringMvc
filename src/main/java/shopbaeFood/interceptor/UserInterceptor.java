package shopbaeFood.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import shopbaeFood.model.Account;
import shopbaeFood.model.Status;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.utils.Constants;

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

            if (isUserBlock()) {
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
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.findByName(loggedUsername);

        if (account.getFailedAttempt() > 0) {
            accountService.resetFailedAttempts(account);
        }

        session.setMaxInactiveInterval(Constants.SESSION_EXPIRATION);
        session.setAttribute("user", account);
        if (account.getUser() != null) {
            session.setAttribute("userId", account.getUser().getId());
            session.setAttribute("name", account.getUser().getName());
            session.setAttribute("avatar", account.getUser().getAvatar());
            session.setAttribute("address", account.getUser().getAddress());
        }
        if (account.getMerchant() != null) {
            session.setAttribute("userId", account.getMerchant().getId());
            session.setAttribute("name", account.getMerchant().getName());
            session.setAttribute("avatar", account.getMerchant().getAvatar());
            session.setAttribute("address", account.getMerchant().getAddress());

        }
        List<String> authorities = new ArrayList<String>();
        for (GrantedAuthority a : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            authorities.add(a.getAuthority());
        }
        session.setAttribute("authorities", authorities);
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserBlock() {
        try {
            Account account = accountService.getAccount();
            if (account == null) {
                return false;
            } else {
                if (account.getUser() != null && Status.BLOCK.equals(account.getUser().getStatus())) {
                    return true;
                }
                if (account.getMerchant() != null && Status.BLOCK.equals(account.getMerchant().getStatus())) {
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
