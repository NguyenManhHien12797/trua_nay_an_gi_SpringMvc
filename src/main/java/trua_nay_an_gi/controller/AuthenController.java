package trua_nay_an_gi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.MerchantForm;
import trua_nay_an_gi.model.UserForm;
import trua_nay_an_gi.model.dto.AccountRegisterDTO;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IAuthenService;

@Controller
public class AuthenController {

	@Autowired
	private IAuthenService authenService;
	
	@Autowired
	private IAppUserSevice userSevice;


	@GetMapping(value = { "/login" })
	public String showLoginForm( @RequestParam(required = false) String mess, Model model) {
		model.addAttribute("mess",authenService.showLoginForm(mess));
		return "login";

	}

	@GetMapping(value = { "/home", "/" })
	public String home(Model model, HttpSession session) {
		String path = authenService.home(model, session);
		return path;
	}
	

	@GetMapping(value = { "/home/merchant-detail/{id}"})
	public String merchantDetails(@PathVariable Long id,Model model, HttpSession session ) {
		
		return authenService.merchantDetails(id, model, session);
	}
	
	
	@GetMapping(value = { "/home/{route}"})
	public String userInfo(@PathVariable String route,HttpSession session, Model model) {
		Account account = (Account) session.getAttribute("user");
	
		if(account == null) {
			return "redirect:/login?mess=chua-dang-nhap";
		}
		AppUser user = userSevice.findById( account.getUser().getId());
		UserForm userForm = new UserForm(user.getId(), user.getName(), user.getPhone(),user.getAddress());
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("userForm", userForm);
		model.addAttribute("route", route);
		model.addAttribute("role", "user");
		return "user-info";
	}
	
	
	@PostMapping(value= {"/user/user-info"})
	private String updateAccountMerchant(@ModelAttribute("userForm") UserForm userForm, @ModelAttribute("account") Account account, HttpSession session) {

		userSevice.updateUserInfo(userForm, account, session);
		return "redirect:/home/user-info";
	}
	

	@GetMapping("/register/{role}")
	public ModelAndView showFormRegister(@PathVariable String role) {
		return authenService.showFormRegister(role);
	}

	@PostMapping("/register/{role}")
	public ModelAndView register(@PathVariable String role,
			@ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO) {

		authenService.register(accountRegisterDTO, role);
		ModelAndView modelAndView = new ModelAndView("/login");
		return modelAndView;
	}
	
	
	@RequestMapping(value= {"/home/create-otp"})
	@ResponseBody
	private String createOTP(HttpSession session) {
		authenService.createOtp(session);
		return "create otp ok";
	}
	
	@PostMapping(value= {"/home/checkotp/{account_id}/{otp}"})
	@ResponseBody
	private String checkOTP(@PathVariable Long account_id, @PathVariable String otp) {

		return authenService.checkOtp(account_id,otp);
	}
	
	@PostMapping(value= {"/home/change-pass/{account_id}/{pass}"})
	@ResponseBody
	private String changePass(@PathVariable Long account_id,@PathVariable String pass) {
		authenService.changePass(pass, account_id);
		return "change pass ok";
	}
	

}
