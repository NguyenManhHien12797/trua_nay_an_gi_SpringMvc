package shopbaeFood.service.seviceImpl;

import java.text.MessageFormat;
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

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.Account;
import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Mail;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.Status;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;
import shopbaeFood.service.IMailService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IProductService;
import shopbaeFood.service.IRoleService;
import shopbaeFood.util.Contants;

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

	public static final String USER = "user";
	public static final String MECHANT = "merchant";
	public static final String MAIL_SUBJECT = "Mã xác nhận OTP";
	public static final String MAIL_FROM = "nguyenhuuquyet07092001@gmail.com";


	@Override
	public void register(AccountRegisterDTO accountRegisterDTO, String role) {
		Status status = Status.PENDING;
		boolean isEnabled = true;
		String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
		Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, accountRegisterDTO.getEmail());
		accountService.save(account);

		String avatar = "images.jpg";

		if (USER.equals(role)) {
			AppRoles appRole = roleService.findById(1L);

			roleService.setDefaultRole(new AccountRoleMap(account, appRole));
			userSevice.save(new AppUser(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
					accountRegisterDTO.getPhone(), status, account));
		}
		if (MECHANT.equals(role)) {
			AppRoles appRole = roleService.findById(3L);
			roleService.setDefaultRole(new AccountRoleMap(account, appRole));

			merchantService.save(new Merchant(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
					accountRegisterDTO.getPhone(), status, account));
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean isAdmin(HttpSession session) {
		Collection<? extends GrantedAuthority> attribute = (Collection<? extends GrantedAuthority>) session
				.getAttribute("authorities");
		
		Collection<? extends GrantedAuthority> authorities = attribute;
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
		List<Merchant> merchants = merchantService.findMerchantsByStatus(Status.ACTIVE);
		model.addAttribute("merchants", merchants);
		return "homepage";
	}

	
	@Override
	public void checkLogin(Model model, HttpSession session) {
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
		Merchant merchant = merchantService.findById(id);
		List<Product> products = productService.findAllProductByDeleteFlag(id);
		model.addAttribute("merchant", merchant);
		model.addAttribute("products", products);
		return "merchant-details";
	}

	@Override
	public void createOtp(HttpSession session) {
		Account account = (Account) session.getAttribute("user");
		double randomDouble = Math.random();
		randomDouble = randomDouble * 1000000 + 1;
		int OTP = (int) randomDouble;
		account.setOtp(String.valueOf(OTP));

		accountRepository.update(account);
		Mail mail = new Mail();
		mail.setMailTo(account.getEmail());
		mail.setMailFrom(MAIL_FROM);
		mail.setMailSubject(MAIL_SUBJECT);
		String content = MessageFormat.format("Mã OTP của bạn là: {0} \nVui lòng không chia sẻ với ai",
				String.valueOf(OTP));
		mail.setMailContent(content);
		mailService.sendEmail(mail);

	}

	@Override
	public String checkOtp(Long account_id, String otp) {
		Account account = accountRepository.findById(account_id);
		if (otp.equals(account.getOtp())) {
			return "ok";
		}
		throw new CheckOtpException(500, Contants.RESPONSE_MESSAGE.WRONG_OTP);
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
		String message = " ";
		if (Contants.LOGIN_STATE.NOT_LOGIN.equals(mess)) {
			message = Contants.RESPONSE_MESSAGE.NOT_LOGIN;
		}
		if (Contants.LOGIN_STATE.TIME_OUT.equals(mess)) {
			message = Contants.RESPONSE_MESSAGE.TIME_OUT;
		}

		return message;
	}

}
