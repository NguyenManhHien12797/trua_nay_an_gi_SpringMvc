package shopbaeFood.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone paramA) {
	}

	@Override
	public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
		
		if (phoneNo == null) {
			return false;
		}
		// validate phone numbers of format "0388888888"
		if (phoneNo.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b"))
			return true;
		// return false if nothing matches the input
		else
			return false;
	}
}
