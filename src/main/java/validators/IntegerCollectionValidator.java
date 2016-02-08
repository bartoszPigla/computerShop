package validators;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerCollectionValidator implements ConstraintValidator<NotNullStringCollectionElements, Collection<Integer>>{

	@Override
	public void initialize(NotNullStringCollectionElements arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Collection<Integer> collection, ConstraintValidatorContext arg1) {
		for(Integer item:collection)
			if(item==null || item<0)
				return false;
		return true;
	}


}