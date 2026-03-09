package com.springboot.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name="Course_details")
public class Course implements Serializable{
	 private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;

	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private Double price;

	public Course() {
	}

	public Course(Integer cid, String name, Double price) {
		this.cid = cid;
		this.name = name;
		this.price = price;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Course [cid=" + cid + ", name=" + name + ", price=" + price + "]";
	}
}