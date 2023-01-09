package trua_nay_an_gi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.service.IAccountService;

@Controller
public class SecurityController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IAccountService accountService;

	@GetMapping(value = { "/login" })
	public String showLoginForm() {
		return "login";

	}

	@GetMapping(value = { "/home" })
	public String home(Model model) {

		String message = "chua dang nhap";
		model.addAttribute("message", message);
		return "homepage";
	}

//	 @PostMapping(value = "/login")
//	    public String login(@RequestParam(name= "userName") String userName, @RequestParam(name= "password") String password, Model model) {
//	            // Tạo ra 1 đối tượng Authentication.
//	            Authentication authentication = authenticationManager.authenticate(
//	                    new UsernamePasswordAuthenticationToken(userName, password));
//
//	            SecurityContextHolder.getContext().setAuthentication(authentication);
////	            AccountDetails userDetails = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////	            List<String> roles = userDetails.getAuthorities().stream()
////	                    .map(GrantedAuthority::getAuthority)
////	                    .collect(Collectors.toList());
////	            Account account = accountService.findByName(loginRequest.getUserName());

//
//	    }

}
