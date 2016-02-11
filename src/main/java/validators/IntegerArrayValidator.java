package validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerArrayValidator implements ConstraintValidator<NotNullIntegerArrayElements, Integer[]>{

	@Override
	public void initialize(NotNullIntegerArrayElements arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Integer[] collection, ConstraintValidatorContext arg1) {
		for(Integer item:collection)
			if(item==null || item<0)
				return false;
		return true;
	}


}