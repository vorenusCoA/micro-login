package com.example.micrologin.dto;

import com.example.micrologin.model.User;

public class UserCreationRequestDTOMapper {

	public static User getUser(UserCreationRequestDTO dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setName(dto.getName());
		user.setPhones(dto.getPhones());

		return user;
	}

}
