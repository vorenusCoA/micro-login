package com.example.micrologin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.micrologin.model.User;

public class UserResponseDTOMapper {

	public static UserResponseDTO getUserResponseDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setPassword(user.getPassword());
		dto.setActive(user.isActive());
		dto.setPhones(user.getPhones());
		dto.setToken(user.getToken());
		dto.setCreated(formatDate(user.getCreated()));
		dto.setLastLogin(formatDate(user.getLastLogin()));

		return dto;
	}

	private static String formatDate(LocalDateTime date) {
		
		if (date == null) return "";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm:ss a", Locale.US);
		return date.format(formatter);
	}

}
