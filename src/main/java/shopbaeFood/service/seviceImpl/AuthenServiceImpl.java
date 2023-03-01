package shopbaeFood.service.seviceImpl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import shopbaeFood.model.dto.PasswordDTO;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;
import shopbaeFood.service.IMailService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IProductService;
import shopbaeFood.service.IRoleService;
import shopbaeFood.util.Constants;

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
	
	@Override
	public List<String>authorities(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority a : authentication.getAuthorities()) {
			authorities.add(a.getAuthority());
		}
		return authorities;
	}
	
	@Override
	public void checkLogin(Model model) {
		String message = " ";
		String role = "";
	    if (authorities()==null) {
	    	message = "chua dang nhap";
	    }else {
	    	if (authorities().contains("ROLE_USER")) {
				role = "user";
			}
			if (authorities().contains("ROLE_ADMIN")) {
				role = "admin";
			}
			if (authorities().contains("ROLE_MERCHANT")) {
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
	public String merchantDetails(Long id, Model model) {
		checkLogin(model);
		Merchant merchant = merchantService.findById(id);
		List<Product> products = productService.findAllProductByMerchantAndDeleteFlag(merchant);
		model.addAttribute("merchant", merchant);
		model.addAttribute("products", products);
		return "merchant-details";
	}

	@Override
	public void createOtp() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepository.findByName(authentication.getName());
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
		throw new CheckOtpException(500, Constants.RESPONSE_MESSAGE.WRONG_OTP);
	}

	@Override
	public void changePass(String pass, Long account_id) {
		Account account = accountRepository.findById(account_id);

		account.setPassword(passwordEncoder.encode(pass));
		account.setOtp(null);
		accountRepository.update(account);
	}

	@Override
	public String showMessageLogin(String mess) {
		String message = " ";
		if (Constants.LOGIN_STATE.NOT_LOGIN.equals(mess)) {
			message = Constants.RESPONSE_MESSAGE.NOT_LOGIN;
		}
		if (Constants.LOGIN_STATE.TIME_OUT.equals(mess)) {
			message = Constants.RESPONSE_MESSAGE.TIME_OUT;
		}

		return message;
	}

	@Override
	public boolean changePass(PasswordDTO passwordDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepository.findByName(authentication.getName());
		if(passwordEncoder.matches(passwordDTO.getCurrentPassword(), account.getPassword())) {
			if(passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
				account.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
				account.setOtp(null);
				account.setFirstLogin(false);
				accountRepository.update(account);
				return true;
			}
		}
	
		return false;
		
	}

}
