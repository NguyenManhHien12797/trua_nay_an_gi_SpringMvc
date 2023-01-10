package trua_nay_an_gi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.dto.AccountRegisterDTO;
import trua_nay_an_gi.service.IAccountService;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IRoleService;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAppUserSevice userSevice;
	
	@Autowired
	private IRoleService roleService;
	

	@GetMapping(value = { "/login" })
	public String showLoginForm() {
		return "login";

	}

	@GetMapping(value = { "/home" })
	public String home(Model model, HttpSession session) {
		String userName = (String) session.getAttribute("username");
		String message = "";
		if (userName == null) {
			message = "chua dang nhap";
		}

		model.addAttribute("message", message);
		return "homepage";
	}

	@GetMapping("/register")
	public ModelAndView showFormRegister() {
		ModelAndView modelAndView = new ModelAndView("/register");
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

	@PostMapping("/register")
	public ModelAndView addUser(@ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO) {

		String status = "active";
		boolean isEnabled = true;
		Account account = new Account(accountRegisterDTO.getUserName(), accountRegisterDTO.getPassword(), isEnabled,
				accountRegisterDTO.getEmail());
		accountService.save(account);
		Long idAccountAfterCreated = accountService.findIdUserByUserName(account.getUserName());
		roleService.setDefaultRole(idAccountAfterCreated, 2);
		String avatar = "https://scr.vn/wp-content/uploads/2020/07/Avatar-Facebook-tr%E1%BA%AFng.jpg";

		userSevice.saveUserToRegister(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
				accountRegisterDTO.getPhone(), status,idAccountAfterCreated);
		

		ModelAndView modelAndView = new ModelAndView("/login");
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

}
