package com.example.micrologin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.micrologin.dto.UserCreationRequestDTO;
import com.example.micrologin.dto.UserCreationRequestDTOMapper;
import com.example.micrologin.dto.UserResponseDTO;
import com.example.micrologin.dto.UserResponseDTOMapper;
import com.example.micrologin.exception.UserAlreadyExistsException;
import com.example.micrologin.exception.UserValidationException;
import com.example.micrologin.model.CustomApiError;
import com.example.micrologin.model.TokenRequest;
import com.example.micrologin.model.User;
import com.example.micrologin.service.UserService;
import com.example.micrologin.utils.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("sign-up")
	public ResponseEntity<UserResponseDTO> register(@RequestBody UserCreationRequestDTO userDTO)
			throws UserValidationException, UserAlreadyExistsException {

		User user = UserCreationRequestDTOMapper.getUser(userDTO);
		user = this.userService.register(user);

		return ResponseEntity.ok().body(UserResponseDTOMapper.getUserResponseDTO(user));
	}

	@PostMapping("login")
	public ResponseEntity<Object> login(@RequestBody TokenRequest token) {
		
		if (token == null || token.getToken() == null || token.getToken().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomApiError(400, "Bad Request"));
		}

		Jws<Claims> parseJwt;
		try {
			parseJwt = Jwt.parseJwt(token.getToken());
		} catch (JwtException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomApiError(401, "Invalid token"));
		}
		
		User user = this.userService.login(parseJwt.getBody().getSubject());

		return ResponseEntity.ok().body(UserResponseDTOMapper.getUserResponseDTO(user));
	}

}
