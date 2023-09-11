package com.example.micrologin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.micrologin.exception.UserAlreadyExistsException;
import com.example.micrologin.exception.UserValidationException;
import com.example.micrologin.model.User;
import com.example.micrologin.repository.UserRepository;
import com.example.micrologin.utils.EmailAddressValidator;
import com.example.micrologin.utils.Jwt;
import com.example.micrologin.utils.PasswordValidator;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final MessageSource messageSource;

	public UserService(UserRepository userRepository, MessageSource messageSource) {
		this.userRepository = userRepository;
		this.messageSource = messageSource;
	}

	public User register(User user) throws UserValidationException, UserAlreadyExistsException {
		
		if (user == null) {
			throw new IllegalArgumentException("'user' can't be null");
		}

		if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException(messageSource.getMessage("error.user.exists", null, Locale.ENGLISH));
		}

		List<String> errors = this.validateUser(user);

		if (!errors.isEmpty()) {
			throw new UserValidationException(errors);
		}

		user.setCreated(LocalDateTime.now());
		user.setActive(true);
		user.setToken(Jwt.createToken(user.getEmail()));
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

		return this.userRepository.save(user);
	}

	private List<String> validateUser(User user) {

		List<String> errors = new ArrayList<>();

		String emailValidation = EmailAddressValidator.validateEmail(user.getEmail());
		if (!emailValidation.isEmpty()) {
			errors.add(emailValidation);
		}

		List<String> passwordValidation = PasswordValidator.validatePassword(user.getPassword());
		if (!passwordValidation.isEmpty()) {
			errors.addAll(passwordValidation);
		}

		return errors;
	}
	
	public User login(String email) {

		User user = this.userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
		user.setLastLogin(LocalDateTime.now());
		user.setToken(Jwt.createToken(email));
		this.userRepository.save(user);
		
		return user;
	}
	
}
