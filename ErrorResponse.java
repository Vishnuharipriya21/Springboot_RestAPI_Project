package com.springboot.entity;

import java.time.LocalDateTime;

public class ErrorResponse {

	private LocalDateTime timeStamp;
	private String errorMessage;
	private String errorDetails;

	public ErrorResponse(LocalDateTime timeStamp, String errorMessage, String errorDetails) {
		this.timeStamp = timeStamp;
		this.errorMessage = errorMessage;
		this.errorDetails = errorDetails;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
}