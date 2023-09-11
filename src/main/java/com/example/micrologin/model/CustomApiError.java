package com.example.micrologin.model;

import java.sql.Timestamp;
import java.time.Instant;

public class CustomApiError {

	private Timestamp timestamp;
	private int codigo;
	private String detail;

	private CustomApiError(int codigo) {
		this.timestamp = Timestamp.from(Instant.now());
		this.codigo = codigo;
	}

	public CustomApiError(int codigo, String detail) {
		this(codigo);
		this.detail = detail;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDetail() {
		return detail;
	}

}
