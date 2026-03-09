package com.springboot.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.springboot.entity.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.exception.CourseNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(CourseNotFoundException.class)
	    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException exception){
	    	ErrorResponse courseNotFound = new ErrorResponse(LocalDateTime.now(),exception.getMessage(),"Course not found");
			return new ResponseEntity<>(courseNotFound,HttpStatus.NOT_FOUND);

	    }
}
