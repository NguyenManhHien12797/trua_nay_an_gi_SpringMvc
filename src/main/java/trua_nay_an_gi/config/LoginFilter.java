//package trua_nay_an_gi.config;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//public class LoginFilter extends OncePerRequestFilter{
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		HttpSession session = request.getSession(false);
//		if(!session.isNew()) {
//			request.getRequestDispatcher("/login").forward(request, response);
//		}else {
//			filterChain.doFilter(request, response);
//		}
//		
//	}
//
//}
