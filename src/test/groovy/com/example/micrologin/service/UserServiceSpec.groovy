package com.example.micrologin.service

import javax.persistence.EntityNotFoundException;

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource

import com.example.micrologin.exception.UserAlreadyExistsException
import com.example.micrologin.exception.UserValidationException
import com.example.micrologin.model.User
import com.example.micrologin.repository.UserRepository

import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class UserServiceSpec extends Specification {

	@Subject
	UserService userService

	UserRepository userRepository = Mock()
	MessageSource messageSource = Mock()

	def setup() {
		userService = new UserService(userRepository, messageSource)
	}

	def "login a present user should return a user"() {
		given:
		def userEmail = "test@example.com"
		def user = new User()
		user.id = UUID.randomUUID()
		user.email = userEmail
		user.password = "aa11Asdfx"

		userRepository.findByEmail(userEmail) >> Optional.of(user)

		when:
		def result = userService.login(userEmail)

		then:
		result != null
		result.getEmail() == userEmail
	}
	
	def "login a user that is not present should throw a EntityNotFoundException"() {
		given:
		def userEmail = "test@example.com"
		def user = new User()
		user.id = UUID.randomUUID()
		user.email = userEmail
		user.password = "aa11Asdfx"

		userRepository.findByEmail(userEmail) >> Optional.empty()

		when:
		def result = userService.login(userEmail)

		then:
		thrown(EntityNotFoundException)
	}

	def "when user is null and trying to register then an IllegalArgumentException is thrown"() {
		given:
		User user = null

		when:
		userService.register(user)

		then:
		thrown(IllegalArgumentException)
	}

	def "if the user is valid when calling register() it should be persisted"() {
		given:
		def userEmail = "test@example.com"
		def userPassword = "aa11Asdfx"
		def user = new User()
		user.email = userEmail
		user.password = userPassword

		userRepository.findByEmail(userEmail) >> Optional.empty()
		userRepository.save(user) >> user

		when:
		def result = userService.register(user)

		then:
		result != null
		result.email == userEmail
	}
	
	def "if the email is not valid then a UserValidationException should be thrown"() {
		given:
		def userEmail = "test.example.com"
		def userPassword = "aa11Asdfx"
		def user = new User()
		user.email = userEmail
		user.password = userPassword

		userRepository.findByEmail(userEmail) >> Optional.empty()
		userRepository.save(user) >> user

		when:
		def result = userService.register(user)

		then:
		thrown(UserValidationException)
	}
	
	def "if the password is not valid then a UserValidationException should be thrown"() {
		given:
		def userEmail = "test@example.com"
		def userPassword = "aaaa"
		def user = new User()
		user.email = userEmail
		user.password = userPassword

		userRepository.findByEmail(userEmail) >> Optional.empty()
		userRepository.save(user) >> user

		when:
		def result = userService.register(user)

		then:
		thrown(UserValidationException)
	}
	
	def "if the user already exists then a UserAlreadyExistsValidation should be thrown"() {
		given:
		def userEmail = "test@example.com"
		def userPassword = "aa11Asdfg"
		def user = new User()
		user.email = userEmail
		user.password = userPassword

		userRepository.findByEmail(userEmail) >> Optional.of(user)

		when:
		def result = userService.register(user)

		then:
		thrown(UserAlreadyExistsException)
	}
}
