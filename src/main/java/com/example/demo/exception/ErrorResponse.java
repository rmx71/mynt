package com.example.demo.exception;

import java.util.List;
import java.util.Map;

public class ErrorResponse {
	public ErrorResponse(String message, Map<String, List<String>> details) {
		super();
		this.message = message;
		this.details = details;
	}

	private String message;
	private Map<String, List<String>> details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, List<String>> getDetails() {
		return details;
	}

	public void setDetails(Map<String, List<String>> details) {
		this.details = details;
	}
}
