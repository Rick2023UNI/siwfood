package it.uniroma3.siwfood.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MultipartFile multipartFile = (MultipartFile)target;
		if (multipartFile.isEmpty()) {
			errors.reject("multipartFile.empty");
		}
	}
	
}
