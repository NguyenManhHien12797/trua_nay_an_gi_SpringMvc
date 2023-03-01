package shopbaeFood.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.model.dto.PasswordDTO;

public interface IAuthenService {

	/**
	 * This method is used to register by role: User/ Merchant
	 */
	void register(AccountRegisterDTO accountRegisterDTO, String role);

	/**
	 * This method is used to show message 
	 * @param mess: not-logged-in/ time-out
	 * @return message
	 */
	String showMessageLogin(String mess);

	/**
	 * This method is used to show form register by role
	 * @param role user/ merchant
	 * @return view register
	 */
	ModelAndView showFormRegister(String role);

	/**
	 * This method checks if the user is logged in, if logged in, what role?
	 */
	void checkLogin(Model model);

	/**
	 * This method is used to create otp and send otp mail to the user
	 * @param session
	 */
	void createOtp();

	/**
	 * This method is used to change password and set otp= null
	 * @param pass password
	 * @param account_id
	 */
	void changePass(String pass, Long account_id);
	
	boolean changePass(PasswordDTO passwordDTO);

	/**
	 * This method is used to check otp 
	 * @param account_id
	 * @param otp
	 * @return mess: 'ok'
	 * @throws CheckOtpException if check otp false
	 */
	String checkOtp(Long account_id, String otp);

	/**
	 * This method is used to show merchant-detail page
	 * @param id
	 * @param model
	 * @param session
	 * @return view merchant-details
	 */
	String merchantDetails(Long id, Model model);
	
	List<String>authorities();
}
