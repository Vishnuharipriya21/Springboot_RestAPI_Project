package com.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springboot.repo.CourseRepository;
import com.springboot.service.CourseServiceImpl;

@SpringBootTest
class SpringbootRestApiProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	
	@Autowired
	private CourseServiceImpl serviceImpl;
	@MockBean
	private CourseRepository repository;

}
