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

import trua_nay_an_gi.exception.HandleException;
import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AccountRoleMap;
import trua_nay_an_gi.model.AppRoles;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Mail;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.dto.AccountRegisterDTO;
import trua_nay_an_gi.repository.IAccountRepository;
import trua_nay_an_gi.service.IAccountService;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IAuthenService;
import trua_nay_an_gi.service.IMailService;
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
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IMailService mailService;
	
	
	

	@Override
	public void register(AccountRegisterDTO accountRegisterDTO, String role) {
		String status = "pending";
		boolean isEnabled = true;
		String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
		Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, accountRegisterDTO.getEmail());
		accountService.save(account);
		
		String avatar = "images.jpg";
		
		if ("user".equals(role)) {
			AppRoles appRole = roleService.findById(1L);
			
			roleService.setDefaultRole(new AccountRoleMap(account,appRole));
			userSevice.save(new AppUser(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
					accountRegisterDTO.getPhone(), status, account));
		}
		if ("merchant".equals(role)) {
			AppRoles appRole = roleService.findById(3L);
			roleService.setDefaultRole(new AccountRoleMap(account,appRole));
			
			merchantService.save(new Merchant(accountRegisterDTO.getAddress(), avatar,
					accountRegisterDTO.getName(), accountRegisterDTO.getPhone(), status, account));
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

	@Override
	public void createOtp(HttpSession session) {
		Account account = (Account) session.getAttribute("user");
		double randomDouble = Math.random();
	    randomDouble = randomDouble * 1000000+1 ;
	    int OTP= (int) randomDouble;
	    account.setOtp(String.valueOf(OTP));
	    
	    accountRepository.update(account);
        Mail mail= new Mail();
        mail.setMailTo(account.getEmail());
        mail.setMailFrom("nguyenhuuquyet07092001@gmail.com");
        mail.setMailSubject("Mã xác nhận OTP");
        mail.setMailContent("Mã OTP của bạn là:"+OTP+"\nVui lòng không chia sẻ với ai\nMời nhấp link bên dưới để đến trang xác nhận OTP\nhttp://localhost:4200/forgotpass/otp");
        mailService.sendEmail(mail);
		
	}

	@Override
	public String checkOtp(Long account_id,String otp) {
		Account account = accountRepository.findById(account_id);
		if(otp.equals(account.getOtp())) {
			return "ok";
		}
		throw new HandleException(500,"Sai OTP");
	}

	@Override
	public void changePass(String pass, Long account_id) {
		Account account = accountRepository.findById(account_id);
		
		account.setPassword(passwordEncoder.encode(pass));
		account.setOtp(null);
		accountRepository.update(account);
	}

	@Override
	public String showLoginForm(String mess) {
		String message= " ";
		if("chua-dang-nhap".equals(mess)) {
			message = "Vui lòng đăng nhập để tiếp tục!";
		}
		if("timeout".equals(mess)) {
			message = "Hết thời gian. Vui lòng đăng nhập để tiếp tục!";
		}
		
		return message;
	}

}
