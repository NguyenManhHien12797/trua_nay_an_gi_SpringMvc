package shopbaeFood.model.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

	String message() default "Số điện thoại có 10 số: 0 +[ 3| 5| 7| 8| 9]+ 8 số [0-9]";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
