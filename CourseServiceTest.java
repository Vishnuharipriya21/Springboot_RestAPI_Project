package com.springboot.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.entity.Course;
import com.springboot.exception.CourseNotFoundException;
import com.springboot.repo.CourseRepository;
import com.springboot.service.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

	@Mock
	CourseRepository courseRepo;
	@InjectMocks 
	CourseServiceImpl courseServiceImpl;
    private static Course course=null;
    
	@BeforeAll
	public static void init() {
		System.out.println("Before All");
		course = new Course();
		course.setCid(1);
		course.setName("Python") ;
		course.setPrice(6788.0);
	}
	@BeforeEach
	public static void initEachTest() {
		System.out.println("Before All");
	}
	
	@Test
	void addCourseShouldAddCourseSuccessfully() {
		Course course = new Course();
		course.setCid(1);
		course.setName("Python") ;
		course.setPrice(6788.0);
		
		courseServiceImpl.addCourse(course);
		 RuntimeException runtimeException = assertThrows(RuntimeException.class, ()-> { 
			courseServiceImpl.addCourse(course);
		});
		 assertEquals("Invalid name of course", runtimeException.getMessage());
		 verify(courseRepo,times(0)).save(course);
		
		Mockito.when(courseRepo.save(course)).thenReturn(course);
		Course addedCourse=   courseServiceImpl.addCourse(course);
		assertNotNull(addedCourse);
		assertEquals(course.getCid(),addedCourse.getCid());
        assertEquals(course.getName(), addedCourse.getName());
        assertTrue(course.getCid()==1);
		System.out.println("my first unit test");
	}
	@Test
	public void deleteCourseShouldDeleteCourseSuccesfully() {
		doNothing().when(courseRepo).deleteById(1);
		courseServiceImpl.deleteByCid(1);
		verify(courseRepo,times(1)).deleteById(1);
	}
	@Test
	void testPrivateMethod_validateCourseName() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//by using reflections we did this;
		Method validateCourseName =    CourseServiceImpl.class.getDeclaredMethod("ValidateCourseName",String.class);
	   validateCourseName.setAccessible(true);
		Boolean book =(Boolean) validateCourseName.invoke(courseServiceImpl, "Book");
	   assertTrue(book);
	}
	
	@AfterAll
	public static void destroy() {
		System.out.println("After All");
	}
	@AfterEach
	public void cleanup() {
		System.out.println("After Each");
	}
	

}
