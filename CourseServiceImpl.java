package com.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.springboot.entity.Course;
import com.springboot.exception.CourseNotFoundException;
import com.springboot.repo.CourseRepository;

import org.springframework.cache.annotation.Cacheable;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
	private CourseRepository courseRepository;

	@Override
	@CacheEvict(value = "courses", allEntries = true)
	public String upsert(Course course) {
		courseRepository.save(course);
		return "Success";
	}
	@Override
	@CachePut(value="course", key="#course.cid")
	public Course Upsert(Course course) {

	    try {
	        if(course.getName() == null){
	            throw new NullPointerException("Course name cannot be null");
	        }

	        if(course.getPrice() <= 0){
	            throw new IllegalArgumentException("Invalid price value");
	        }
	        
	       Course savedCourse = courseRepository.save(course);
 
	        return savedCourse;

	    } catch (Exception e) {
	        throw new RuntimeException("Error while saving course");
	    }
	}
    
	@Cacheable(value = "course", key = "#cid")
	@Override
	public Course getById(Integer cid) {
		Optional<Course> findById = courseRepository.findById(cid);
		return findById.orElse(null);
	}

	
	@Cacheable(value = "courses")
	@Override
	public List<Course> getAllcourses() {
		System.out.println("fetching from db");
		return courseRepository.findAll();
	}

	@Override
	public String deleteById(Integer cid) {
		if(courseRepository.existsById(cid)) {
			courseRepository.deleteById(cid);
		    return "Delete Success";
		} else {
			throw new CourseNotFoundException("No Record Found with id: " + cid);
		}
	}
	@Override
	public Course addCourse(Course course) {
		boolean valid= validateCourseName(course.getName());
       if(valid) {
    	   Course savedCourse = courseRepository.save(course);
  		 return savedCourse;  
       }else {
    	  throw new RuntimeException("Invalid Name of Course");
       }
	}

	@Override
	public Course findCourseById(Integer cid) {
		return courseRepository.findById(cid)
				.orElseThrow(() -> new CourseNotFoundException("No course found with id: " + cid));
	}
	@Override
	public Course findCourseByCid(Integer cid) {

	    try {

	        if(cid == null){
	            throw new NullPointerException("Course id cannot be null");
	        }

	        Optional<Course> course = courseRepository.findById(cid);

	        if(course.isEmpty()){
	            throw new CourseNotFoundException("Course not found with id : " + cid);
	        }

	        return course.get();

	    } catch (CourseNotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new RuntimeException("Error while fetching course");
	    }
	}
	
	
	@Override
	public String deleteByCid(Integer cid) {

	    try {

	        if(!courseRepository.existsById(cid)){
	            throw new CourseNotFoundException("Course not found with id : " + cid);
	        }

	        courseRepository.deleteById(cid);

	        return "Course Deleted Successfully";

	    } catch (CourseNotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new RuntimeException(" error occured when trying to deleting the course: ");
	    }
	}
	
	private boolean validateCourseName(String name) {
		return name != null && !name.isEmpty();
	}
}