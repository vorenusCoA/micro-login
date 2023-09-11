package com.example.micrologin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class MicroLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroLoginApplication.class, args);
	}

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/messages/error_messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}
