package shopbaeFood.service;

import java.util.List;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.model.dto.PasswordDTO;

public interface IAuthenService {

	/**
	 * This method is used to register by role: User/ Merchant
	 */
	void register(AccountRegisterDTO accountRegisterDTO, String role);

	/**
	 * This method is used to show message
	 * 
	 * @param mess: not-logged-in/ time-out
	 * @return message
	 */
	String showMessageLogin(String mess);

	/**
	 * This method is used to create otp and send otp mail to the user
	 */
	void createOtp();

	/**
	 * This method is used to change password and set otp= null
	 * 
	 * @param pass       password
	 * @param account_id
	 */
	void changePass(String pass, Long account_id);

	boolean changePass(PasswordDTO passwordDTO);

	/**
	 * This method is used to check otp
	 * 
	 * @param account_id
	 * @param otp
	 * @return mess: 'ok'
	 * @throws CheckOtpException if check otp false
	 */
	String checkOtp(Long account_id, String otp);
	
	List<Merchant> getMerchants(String address, String category, String quickSearch, HttpSession session);

	List<String> getAddress();

	List<String> getCategories();

	Map<String, String> getListQuickSearch(String category);
}
