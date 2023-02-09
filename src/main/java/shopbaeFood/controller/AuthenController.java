package shopbaeFood.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.UserForm;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;

@Controller
public class AuthenController {

	@Autowired
	private IAuthenService authenService;

	@Autowired
	private IAppUserSevice userSevice;
	
	public static final String USER = "user";
	public static final String MECHANT = "merchant";

	// Show form login
	@GetMapping(value = { "/login" })
	public String showLoginForm(@RequestParam(required = false) String mess, Model model) {
		model.addAttribute("mess", authenService.showLoginForm(mess));
		return "login";

	}

	// Show trang homepage
	@GetMapping(value = { "/home", "/" })
	public String home(Model model, HttpSession session) {
		String homePage = authenService.home(model, session);
		return homePage;
	}

	// Show trang merchant details
	@GetMapping(value = { "/home/merchant-detail/{id}" })
	public String merchantDetails(@PathVariable Long id, Model model, HttpSession session) {

		return authenService.merchantDetails(id, model, session);
	}

	// Show trang user info/ user update info
	@GetMapping(value = { "/home/{route}" })
	public String userInfo(@PathVariable String route, HttpSession session, Model model) {
		Account account = (Account) session.getAttribute("user");

		if (account == null) {
			return "redirect:/login?mess=not-logged-in";
		}
		AppUser user = userSevice.findById(account.getUser().getId());
		UserForm userForm = new UserForm(user.getId(), user.getName(), user.getPhone(), user.getAddress());
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("userForm", userForm);
		model.addAttribute("route", route);
		model.addAttribute("role", "user");
		return "user-info";
	}

	// Update info
	@PostMapping(value = { "/user/user-info" })
	private String updateAccountUser(@ModelAttribute("userForm") UserForm userForm,
			@ModelAttribute("account") Account account, HttpSession session) {

		userSevice.updateUserInfo(userForm, account, session);
		return "redirect:/home/user-info";
	}

	// Show trang đăng ký theo role: user/ merchant
	@GetMapping("/register/{role}")
	public ModelAndView showFormRegister(@PathVariable String role) {
		return authenService.showFormRegister(role);
	}

	// Đăng ký theo role: user/ merchant
	@PostMapping("/register/{role}")
	public String register(@PathVariable String role,
			@Valid @ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			String title = "";
			if (USER.equals(role)) {
				title = "Đăng ký người dùng";
			}
			if (MECHANT.equals(role)) {
				title = "Đăng ký người bán";
			}

			model.addAttribute("title", title);
			return "register";
		}
		authenService.register(accountRegisterDTO, role);

		return "login";
	}

	// Create otp
	@RequestMapping(value = { "/home/create-otp" })
	@ResponseBody
	private String createOTP(HttpSession session) {
		authenService.createOtp(session);
		return "create otp ok";
	}

	// Check otp
	@PostMapping(value = { "/home/checkotp/{account_id}/{otp}" })
	@ResponseBody
	private String checkOTP(@PathVariable Long account_id, @PathVariable String otp) {

		return authenService.checkOtp(account_id, otp);
	}

	// Đổi password
	@PostMapping(value = { "/home/change-pass/{account_id}/{pass}" })
	@ResponseBody
	private String changePass(@PathVariable Long account_id, @PathVariable String pass) {
		authenService.changePass(pass, account_id);
		return "change pass ok";
	}

}
