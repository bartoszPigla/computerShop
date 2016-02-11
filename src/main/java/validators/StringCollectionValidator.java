package validators;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringCollectionValidator implements ConstraintValidator<NotNullStringCollectionElements, Collection<String>>{

	@Override
	public void initialize(NotNullStringCollectionElements arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Collection<String> collection, ConstraintValidatorContext arg1) {
		for(String item:collection)
			if(item==""||item==null)
				return false;
		return true;
	}


}