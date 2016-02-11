package validators;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageUrlValidator implements ConstraintValidator<ImageUrl, String> {
	ImageUrl imageUrl;
	
	@Override
	public void initialize(ImageUrl imageUrl) {
		this.imageUrl=imageUrl;
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext arg1) {
		if(url==null)
			return false;
		int separator=url.lastIndexOf('.');
		if(separator!=-1){
			String fileType=url.substring(separator+1, url.length());
			if(Arrays.asList(imageUrl.imageType()).contains(fileType))
				return true;
		}
		return false;
	}

}
