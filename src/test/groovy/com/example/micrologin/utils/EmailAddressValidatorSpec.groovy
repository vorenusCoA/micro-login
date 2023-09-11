package com.example.micrologin.utils

import com.example.micrologin.model.User
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

class EmailAddressValidatorSpec extends Specification {

	def "Given a list of invalid emails none should be present in the result list"() {
		given:
		def emails = [
			"@",
			"asd@gmail",
			"    ",
			".com",
			"asd.com"
		]

		when:
		List<String> valid = emails.findAll { !EmailAddressValidator.validateEmail(it) }
		
		then:
		valid.empty == true
	}
	
	def "Given a list of valid emails none should be present in the result list"() {
		given:
		def emails = [
			"test@gmail.com",
			"test.test@gmail.com.ar"
		]

		when:
		List<String> invalid = emails.findAll { EmailAddressValidator.validateEmail(it) }
		
		then:
		invalid.empty == true
	}
	
}
