package shopbaeFood.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Status;
import shopbaeFood.model.UserForm;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.model.dto.PasswordDTO;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;
import shopbaeFood.service.IMerchantService;

@Controller
public class AuthenController {

	@Autowired
	private IAuthenService authenService;

	@Autowired
	private IAppUserSevice userSevice;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IAccountService accountService;
	
	public static final String USER = "user";
	public static final String MECHANT = "merchant";
	public static final String TITLE_MERCHANT = "Đăng ký người bán";
	public static final String TITLE_USER = "Đăng ký người dùng";
	
	/**
	 * This method returns login page or message when login error
	 * @param message 
	 * @param model
	 * @return view login
	 */
	@GetMapping(value = { "/login" })
	public String showLoginForm(@RequestParam(required = false) String mess, Model model, HttpServletResponse response) {
		
		Account account = accountService.getAccount();
	    if (account == null) {
	    	model.addAttribute("mess", authenService.showMessageLogin(mess));
	    	  response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
	          response.setHeader("Pragma","no-cache");
	          response.setDateHeader("Expires", 0);
	    	return "login";
	    }
 
        return "redirect:/";

	}

	/**
	 * This method returns homePage page
	 * @param model
	 * @param session
	 * @return view homePage
	 */
	@GetMapping(value = { "/home", "/" })
	public String home(@RequestParam(required = false) String address, @RequestParam(required = false) String category, Model model, HttpSession session) {
		authenService.checkLogin(model);
		Account account = accountService.getAccount();
		List<String> listAddress = new ArrayList<String>();
		List<String> categories = new ArrayList<String>();
		listAddress.add("Hà Nội");
		listAddress.add("Tp.HCM");
		listAddress.add("Đà Nẵng");
		categories.add("Đồ ăn");
		categories.add("Thực phẩm");
		categories.add("Bia");
		categories.add("Hoa");
		categories.add("Thuốc");
		if( address ==null) {
			if(session.getAttribute("address") != null) {
				address =(String) session.getAttribute("address");
			}else {
				if(account != null) {
					if(account.getUser() != null) {
						address = accountService.getAccount().getUser().getAddress();
					}
					
					if(account.getMerchant() != null) {
						address = accountService.getAccount().getMerchant().getAddress();
					}
				}
			}
			
		}else {
			session.setAttribute("address", address);
			listAddress.remove(address);
		}
		
		if(category ==null) {
			category = "Đồ ăn";
		}
		List<Merchant> merchants = merchantService.findMerchantsByStatusAndAddressAndCategory(Status.ACTIVE,address, category);
		getListQuickSearch(category, model);
		model.addAttribute("merchants", merchants);
		model.addAttribute("listAddress", listAddress);
		session.setAttribute("categories", categories);
		model.addAttribute("categories", categories);
		model.addAttribute("category", category);
		System.out.println(session.getAttribute("address"));
		
	
		return "homepage";
	}
	
	private List<String> getListQuickSearch(String category, Model model) {
		List<String>quickSearchs1 = new ArrayList<String>();
		List<String>quickSearchs2 = new ArrayList<String>();
		quickSearchs1.add("All");
		quickSearchs1.add("Bún");
		quickSearchs1.add("Phở");
		quickSearchs1.add("Hamburger");
		quickSearchs1.add("Đồ chay");
		quickSearchs1.add("Đồ uống");
		quickSearchs1.add("Tráng miệng");
		quickSearchs2.add("Thịt");
		quickSearchs2.add("Thịt bò");
		quickSearchs2.add("Thịt lợn");
		quickSearchs2.add("Thịt gà");
		if("Đồ ăn".equals(category)) {
			model.addAttribute("quickSearchs", quickSearchs1);
			return quickSearchs1;
		}
		if("Thực phẩm".equals(category)) {
			model.addAttribute("quickSearchs", quickSearchs2);
			return quickSearchs2;
		}
		return null;
	}

	/**
	 * This method returns merchant details page
	 * @param id : merchant_id
	 * @param model
	 * @return view merchant-detail
	 */
	@GetMapping(value = { "/home/merchant-detail/{id}" })
	public String merchantDetails(@PathVariable Long id, Model model) {
		return authenService.merchantDetails(id, model);
	}
	
	/**
	 * This method returns fragments: user_info/ user_update_info by route
	 * @param route : user_info/ user_update_info
	 * @param model
	 * @return view user_info page
	 */
	@GetMapping(value = { "/home/{route}" })
	public String userInfo(@PathVariable String route, Model model) {
		Account account = accountService.getAccount();
		List<String> roleList = authenService.authorities();
		String role = "user";
		if (account == null) {
			return "redirect:/login?mess=not-logged-in";
		}
		if(roleList.contains("ROLE_ADMIN")) {
			role = "admin";
		}
		System.out.println(account.getAccountRoleMapSet().size());
		System.out.println(account.getAccountRoleMapSet());
		AppUser user = userSevice.findById(account.getUser().getId());
		UserForm userForm = new UserForm(user.getId(), user.getName(), user.getPhone(), user.getAddress());
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("userForm", userForm);
		model.addAttribute("route", route);
		model.addAttribute("role", role);
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
				title = TITLE_USER;
			}
			if (MECHANT.equals(role)) {
				title = TITLE_MERCHANT;
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
	public String createOTP() {
		authenService.createOtp();
		return "create otp ok";
	}

	/**
	 * This method is used to check otp
	 * @param account_id
	 * @param otp
	 * @return message: 'ok'
	 * @throws CheckOtpException if check otp false
	 */
	@PostMapping(value = { "/home/checkotp/{account_id}/{otp}" })
	@ResponseBody
	public String checkOTP(@PathVariable Long account_id, @PathVariable String otp) {

		return authenService.checkOtp(account_id, otp);
	}
	
	@GetMapping("/home/change-pass")
	public String showChangePassPage(Model model) {
		Account account = accountService.getAccount();
		if(account == null) {
			return "redirect:/login?mess=not-logged-in";
		}
		model.addAttribute("passwordDTO", new PasswordDTO());
		return "change_pass";
	}
	
	@PostMapping(value = { "/home/change-pass" })
	public String changePass(@Valid @ModelAttribute("passwordDTO") PasswordDTO passwordDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "change_pass";
		}
		if(authenService.changePass(passwordDTO)) {
			List<String>authorities = authenService.authorities();
			if(authorities == null) {
				return "redirect:/login?mess=not-logged-in";
			}else {
				if(authorities.contains("ROLE_ADMIN")) {
					return "redirect:/admin";	
				}
				if(authorities.contains("ROLE_MERCHANT")) {
					return "redirect:/merchant";	
				}
				if(authorities.contains("ROLE_USER")) {
					return "redirect:/";	
				}
			}
	
		}
		model.addAttribute("error", "Mật khẩu không đúng");
		return "change_pass";
	}
	/**
	 * This method is used to change pass
	 * @param account_id
	 * @param pass
	 * @return message: 'change pass ok'
	 */
	@PostMapping(value = { "/home/change-pass/{account_id}/{pass}" })
	@ResponseBody
	public String changePass(@PathVariable Long account_id, @PathVariable String pass) {
		authenService.changePass(pass, account_id);
		return "change pass ok";
	}
	
	@PostMapping(value = { "/home/search/{category}" })
	@ResponseBody
	public List<Merchant> searchMerchant(@RequestBody(required = false) String search, @PathVariable(required = false) String category) {
		System.out.println("category: "+ category);
		
		List<Merchant> merchants = merchantService.findMerchantsByStatusAndCategoryAndSearch(Status.ACTIVE,category, search);
		return merchants;
	}

}
