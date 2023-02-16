package shopbaeFood.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.dto.AccountRegisterDTO;

public interface IAuthenService {

	/**
	 * This method is used to register by role: User/ Merchant
	 */
	void register(AccountRegisterDTO accountRegisterDTO, String role);

	/**
	 * This method is used to check role Admin
	 */
	boolean isAdmin(HttpSession session);

	/**
	 * This method is used to load list merchant by status: ACTIVE
	 * @return view homepage
	 */
	String home(Model model, HttpSession session);

	/**
	 * This method is used to show message 
	 * @param mess: not-logged-in/ time-out
	 * @return message
	 */
	String showLoginForm(String mess);

	/**
	 * This method is used to show form register by role
	 * @param role user/ merchant
	 * @return view register
	 */
	ModelAndView showFormRegister(String role);

	/**
	 * This method checks if the user is logged in, if logged in, what role?
	 */
	void checkLogin(Model model, HttpSession session);

	/**
	 * This method is used to create otp and send otp mail to the user
	 * @param session
	 */
	void createOtp(HttpSession session);

	/**
	 * This method is used to change password and set otp= null
	 * @param pass password
	 * @param account_id
	 */
	void changePass(String pass, Long account_id);

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
	String merchantDetails(Long id, Model model, HttpSession session);
}
