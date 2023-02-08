package trua_nay_an_gi.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.service.IAccountService;

public class UserNameValidator implements ConstraintValidator<UserNameUnique, String>{
	
	@Autowired
	private IAccountService accountService;

	@Override
	public void initialize(UserNameUnique constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		Account account = accountService.findByName(userName);
		if(account == null) {
			return true;
		}
		return false;
	}

}
