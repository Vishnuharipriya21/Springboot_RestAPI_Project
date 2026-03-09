package com.springboot.service;

import java.util.List;

import com.springboot.entity.Course;

public interface CourseService {

    public String upsert(Course course);

    public Course getById(Integer cid);

    public List<Course> getAllcourses();

    public String deleteById(Integer cid);

    public Course findCourseById(Integer cid);

    public Course addCourse(Course course);

	Course findCourseByCid(Integer cid);

	Course Upsert(Course course);

	String deleteByCid(Integer cid);
}