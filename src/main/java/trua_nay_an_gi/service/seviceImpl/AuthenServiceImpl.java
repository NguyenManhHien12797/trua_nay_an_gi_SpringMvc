package trua_nay_an_gi.service.seviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.dto.AccountRegisterDTO;
import trua_nay_an_gi.service.IAccountService;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IAuthenService;
import trua_nay_an_gi.service.IMerchantService;
import trua_nay_an_gi.service.IProductService;
import trua_nay_an_gi.service.IRoleService;

@Service
@Transactional
public class AuthenServiceImpl implements IAuthenService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IAppUserSevice userSevice;

	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IProductService productService;

	@Override
	public void register(AccountRegisterDTO accountRegisterDTO, String role) {
		String status = "pending";
		boolean isEnabled = true;
		String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
		Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, accountRegisterDTO.getEmail());
		accountService.save(account);
		
		String avatar = "/static/img/images.jpg";
		Long idAccountAfterCreated = accountService.findIdUserByUserName(account.getUserName());
		
		

		if ("user".equals(role)) {
			roleService.setDefaultRole(idAccountAfterCreated, 1);
			userSevice.saveUserToRegister(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
					accountRegisterDTO.getPhone(), status, idAccountAfterCreated);
		}
		if ("merchant".equals(role)) {
			roleService.setDefaultRole(idAccountAfterCreated, 3);
			merchantService.saveMerchantToRegister(accountRegisterDTO.getAddress(), avatar,
					accountRegisterDTO.getName(), accountRegisterDTO.getPhone(), status, idAccountAfterCreated);
		}

	}

	@Override
	public boolean isAdmin(HttpSession session) {
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

	@Override
	public String home(Model model, HttpSession session) {
		checkLogin(model, session);
		List<?> merchants = merchantService.findMerchantsOrUsersByStatus("active", "merchant-list");
		model.addAttribute("merchants", merchants);
		return "homepage";
	}
	
	@Override
	public void checkLogin(Model model,HttpSession session) {
		Account account = (Account) session.getAttribute("user");
		String message = " ";
		String role = "";
		if (account == null) {
			message = "chua dang nhap";
		} else {
			if (account.getUser() != null) {
				role = "user";
			}
			if (isAdmin(session)) {
				role = "admin";
			}
			if (account.getMerchant() != null) {
				role = "merchant";
			}
			
			model.addAttribute("role", role);
		}
		model.addAttribute("message", message);
	}

	@Override
	public ModelAndView showFormRegister(String role) {
		ModelAndView modelAndView = new ModelAndView("/register");
		String path = "";
		String title = "";
		if ("user".equals(role)) {
			path = "/register/user";
			title = "Đăng ký người dùng";
		}
		if ("merchant".equals(role)) {
			path = "/register/merchant";
			title = "Đăng ký người bán";
		}

		modelAndView.addObject("register", path);
		modelAndView.addObject("title", title);
		modelAndView.addObject("accountRegisterDTO", new AccountRegisterDTO());
		return modelAndView;
	}

	@Override
	public String merchantDetails(Long id, Model model, HttpSession session) {
		checkLogin(model, session);
		Merchant merchant= merchantService.findById(id);
		List<Product> products = productService.findAllProductByDeleteFlag(id);
		model.addAttribute("merchant", merchant);
		model.addAttribute("products", products);
		return "merchant-details";
	}

}
