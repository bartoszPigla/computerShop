package validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = IntegerArrayValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface NotNullIntegerArrayElements {
    String message() default "{range.error.notNull}";
    
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
    
    int min();
}

