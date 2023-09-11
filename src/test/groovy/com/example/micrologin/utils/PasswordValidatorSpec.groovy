package com.example.micrologin.utils

import com.example.micrologin.model.User
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {

	def "Given a list of invalid passwords none should be present in the result list"() {
		given:
		def passwords = [
			"     ",
			"11As", // too short
			"11Aasdfghjhyt" //too long
		]

		when:
		List<String> valid = passwords.findAll { !PasswordValidator.validatePassword(it) }
		
		then:
		valid.empty == true
	}
	
	def "Given a list of valid passwords none should be present in the result list"() {
		given:
		def passwords = [
			"5asxx8As",
			"12Xasdfghjhy",
			"1asdFwdw3",
			"F4asdfx6",
			"Ssdawsss33"
		]

		when:
		List<String> invalid = passwords.findAll { PasswordValidator.validatePassword(it) }
		
		then:
		invalid.empty == true
	}
	
}
