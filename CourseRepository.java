package com.springboot.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
