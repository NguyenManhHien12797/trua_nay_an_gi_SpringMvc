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

	/**
	 * This method returns login page or message when login error
	 * @param mess 
	 * @param model
	 * @return view login
	 */
	@GetMapping(value = { "/login" })
	public String showLoginForm(@RequestParam(required = false) String mess, Model model) {
		model.addAttribute("mess", authenService.showLoginForm(mess));
		return "login";

	}

	/**
	 * This method returns homePage page
	 * @param model
	 * @param session
	 * @return view homePage
	 */
	@GetMapping(value = { "/home", "/" })
	public String home(Model model, HttpSession session) {
		String homePage = authenService.home(model, session);
		return homePage;
	}

	/**
	 * This method returns merchant details page
	 * @param id : merchant_id
	 * @param model
	 * @param session
	 * @return view merchant-detail
	 */
	@GetMapping(value = { "/home/merchant-detail/{id}" })
	public String merchantDetails(@PathVariable Long id, Model model, HttpSession session) {

		return authenService.merchantDetails(id, model, session);
	}
	
	/**
	 * This method returns fragments: user_info/ user_update_info by route
	 * @param route : user_info/ user_update_info
	 * @param session
	 * @param model
	 * @return view user_info page
	 */
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

	/**
	 * This method is used to update user and return user-info
	 * @param userForm
	 * @param account
	 * @param session
	 * @return view user-info
	 */
	@PostMapping(value = { "/user/user-info" })
	private String updateAccountUser(@ModelAttribute("userForm") UserForm userForm,
			@ModelAttribute("account") Account account, HttpSession session) {

		userSevice.updateUserInfo(userForm, account, session);
		return "redirect:/home/user-info";
	}

	/**
	 * This method returns register page by role
	 * @param role : user/ merchant
	 * @return view register
	 */
	@GetMapping("/register/{role}")
	public ModelAndView showFormRegister(@PathVariable String role) {
		return authenService.showFormRegister(role);
	}

	/**
	 * This method is used to register user/ merchant
	 * @param role : user/ merchant
	 * @param accountRegisterDTO
	 * @param bindingResult
	 * @param model
	 * @return view login
	 */
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

	/**
	 * This method is used to create OTP
	 * @param session
	 * @return mess: 'create otp ok'
	 */
	@RequestMapping(value = { "/home/create-otp" })
	@ResponseBody
	private String createOTP(HttpSession session) {
		authenService.createOtp(session);
		return "create otp ok";
	}

	/**
	 * This method is used to check otp
	 * @param account_id
	 * @param otp
	 * @return message: 'ok' or throw new HandleException
	 */
	@PostMapping(value = { "/home/checkotp/{account_id}/{otp}" })
	@ResponseBody
	private String checkOTP(@PathVariable Long account_id, @PathVariable String otp) {

		return authenService.checkOtp(account_id, otp);
	}

	/**
	 * This method is used to change pass
	 * @param account_id
	 * @param pass
	 * @return message: 'change pass ok'
	 */
	@PostMapping(value = { "/home/change-pass/{account_id}/{pass}" })
	@ResponseBody
	private String changePass(@PathVariable Long account_id, @PathVariable String pass) {
		authenService.changePass(pass, account_id);
		return "change pass ok";
	}

}
