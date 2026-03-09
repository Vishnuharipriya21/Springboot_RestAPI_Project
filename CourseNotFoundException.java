package com.springboot.exception;

public class CourseNotFoundException extends RuntimeException{
	public CourseNotFoundException(String msg) {
		super(msg);
	}
}