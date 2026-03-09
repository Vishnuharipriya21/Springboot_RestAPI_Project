package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.entity.Course;
import com.springboot.entity.ErrorResponse;
import com.springboot.exception.CourseNotFoundException;
import com.springboot.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/course")
    public String course(@RequestParam String name, @RequestParam Double price){

        log.info("Starting course price for user: {} with course name: {}", price, name);

        if(price <= 0) {
            log.warn("Invalid price: {}", price);
            return "price must be greater than 0";
        }

        if(name == null || name.isEmpty()) {
            throw new RuntimeException("name is required..");
        }

        log.info("course successfully purchased by user: {}", name);
        return "course successfully purchased by " + name + " with price " + price;
    }

    
    @GetMapping("/courseGet/{cid}")
    public ResponseEntity<?> getCourse(@PathVariable Integer cid){
        try{
            if(cid == null){
                throw new NullPointerException("Id is null");
            }

            Course course = courseService.findCourseById(cid);
            return new ResponseEntity<>(course,HttpStatus.OK);

        }catch(NullPointerException e){
            return new ResponseEntity<>("Id cannot be null",HttpStatus.BAD_REQUEST);

        }catch(CourseNotFoundException e){
            return new ResponseEntity<>("Course not found",HttpStatus.NOT_FOUND);
        }
    }
    
    
    @PostMapping("/course")
    public ResponseEntity<?> createNewCourse(@RequestBody Course course){
        try{

            if(course.getName() == null){
                throw new NullPointerException("Name is null");
            }

            if(course.getPrice() <= 0){
                throw new IllegalArgumentException("Invalid price");
            }

            String status = courseService.upsert(course);
            return new ResponseEntity<>(status,HttpStatus.CREATED);

        }catch(NullPointerException e){
            return new ResponseEntity<>("Name cannot be null",HttpStatus.BAD_REQUEST);

        }catch(IllegalArgumentException e){
            return new ResponseEntity<>("Price must be greater than 0",HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    @PostMapping("/course/new")
    public ResponseEntity<String> createCourse(@RequestBody Course course){
        log.info("Request received for creating a course: {}", course.getName());
        String status = courseService.upsert(course);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/course/{cid}")
    public ResponseEntity<Course> getcourse(@PathVariable Integer cid){
        Course course = courseService.getById(cid);
        if(course == null){
            throw new CourseNotFoundException("Course not found with id: " + cid);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> allCourses = courseService.getAllcourses();
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @PutMapping("/course")
    public ResponseEntity<String> updateCourse(@RequestBody Course course){
        String status = courseService.upsert(course);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/course/{cid}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer cid){
        String status = courseService.deleteById(cid);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Course> addCourse(@RequestBody Course course){
        Course createdCourse = courseService.addCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException exception){
        ErrorResponse courseNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Course not found");
        return new ResponseEntity<>(courseNotFound, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getCourse")
    public Course getCourseDataById(@RequestParam Integer cid) {
        return courseService.findCourseById(cid);
    }
}