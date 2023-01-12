package trua_nay_an_gi.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import trua_nay_an_gi.service.IMerchantService;
import trua_nay_an_gi.service.IRoleService;

@Controller
public class LoginRegisterController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAppUserSevice userSevice;

	@Autowired
	private IMerchantService merchantService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping(value = { "/login" })
	public String showLoginForm() {
		return "login";

	}

	@GetMapping(value = { "/home" })
	public String home(Model model, HttpSession session) {
		String userName = (String) session.getAttribute("username");
		Account account = accountService.findByName(userName);

		String message = "";
		String name = "";
		String avatar = "";
		String role = "";

		if (account == null) {
			message = "chua dang nhap";
		} else {
			if (account.getUser() != null) {
				name = account.getUser().getName();
				avatar = account.getUser().getAvatar();
				role = "user";
			}
			if (isAdmin(session)) {
				name = account.getUser().getName();
				avatar = account.getUser().getAvatar();
				role = "admin";
			}
			if (account.getMerchant() != null) {
				name = account.getMerchant().getName();
				avatar = account.getMerchant().getAvatar();
				role = "merchant";
			}
		}
		model.addAttribute("name", name);
		model.addAttribute("avatar", avatar);
		model.addAttribute("role", role);
		model.addAttribute("message", message);
		return "homepage";
	}

	private boolean isAdmin(HttpSession session) {
		Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) session
				.getAttribute("authorities");
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	@GetMapping("/register")
	public ModelAndView showFormRegister() {
		ModelAndView modelAndView = new ModelAndView("/register");
		String path = "/register";
		String title = "Đăng ký người dùng";
		modelAndView.addObject("register", path);
		modelAndView.addObject("title", title);
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

	@GetMapping("/register/merchant")
	public ModelAndView showFormRegisterMerchant() {
		ModelAndView modelAndView = new ModelAndView("/register");
		String path = "/register/merchant";
		String title = "Đăng ký người bán";
		modelAndView.addObject("register", path);
		modelAndView.addObject("title", title);
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

	@PostMapping("/register")
	public ModelAndView addUser(@ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO) {

		String status = "pending";
		boolean isEnabled = true;
		String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
		Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, accountRegisterDTO.getEmail());
		accountService.save(account);
		Long idAccountAfterCreated = accountService.findIdUserByUserName(account.getUserName());
		roleService.setDefaultRole(idAccountAfterCreated, 1);
		String avatar = "/static/img/images.jpg";

		userSevice.saveUserToRegister(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
				accountRegisterDTO.getPhone(), status, idAccountAfterCreated);

		ModelAndView modelAndView = new ModelAndView("/login");
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

	@PostMapping("/register/merchant")
	public ModelAndView addMerchant(@ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO) {

		String status = "pending";
		boolean isEnabled = true;
		String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
		Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, accountRegisterDTO.getEmail());
		accountService.save(account);
		Long idAccountAfterCreated = accountService.findIdUserByUserName(account.getUserName());
		roleService.setDefaultRole(idAccountAfterCreated, 3);
		String avatar = "/static/img/images.jpg";

		merchantService.saveMerchantToRegister(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
				accountRegisterDTO.getPhone(), status, idAccountAfterCreated);

		ModelAndView modelAndView = new ModelAndView("/login");
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

}
