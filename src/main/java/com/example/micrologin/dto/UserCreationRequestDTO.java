package com.example.micrologin.dto;

import java.util.List;

import com.example.micrologin.model.Phone;

public class UserCreationRequestDTO {

	private String name;
	private String email;
	private String password;
	private List<Phone> phones;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

}
