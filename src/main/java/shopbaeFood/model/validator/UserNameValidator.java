package shopbaeFood.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import shopbaeFood.model.Account;
import shopbaeFood.service.IAccountService;

public class UserNameValidator implements ConstraintValidator<UserNameUnique, String> {

	@Autowired
	private IAccountService accountService;

	@Override
	public void initialize(UserNameUnique constraintAnnotation) {

	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		Account account = accountService.findByName(userName);
		if (account == null) {
			return true;
		}
		return false;
	}

}
