package shopbaeFood.service;

import shopbaeFood.model.Mail;

public interface IMailService {
	
	/**
	 * This method is used to send mail
	 * @param mail
	 */
	void sendEmail(Mail mail);

}
