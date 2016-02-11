package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

	@Override
	public void initialize(PhoneNumber arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext arg1) {
		if (phoneField == null || phoneField=="") {
			return false;
		}
		return phoneField.matches("[0-9()-\\.]*");
	}

}
