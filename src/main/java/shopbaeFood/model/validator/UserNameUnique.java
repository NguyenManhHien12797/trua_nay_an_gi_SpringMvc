package shopbaeFood.model.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import shopbaeFood.util.Constants;


@Documented
@Constraint(validatedBy = UserNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameUnique {

	String message() default Constants.VALIDATOR_MESSAGE.USERNAME_EXISTS;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
