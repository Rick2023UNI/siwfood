package it.uniroma3.siwfood.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwfood.model.Credentials;
import it.uniroma3.siwfood.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	@Autowired
	CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials) target;
		if (credentialsService.existsByUsername(credentials.getUsername())) {
			errors.reject("credentials.duplicate");
		}
	}

}
