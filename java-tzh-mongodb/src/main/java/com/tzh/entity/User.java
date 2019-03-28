package com.tzh.entity;

public class User {
	
	private Integer id;
	
	private String name;
	
	private Integer age;
	
	private String sex;
	
	private String addr;
	
	public User(Integer id,String name,Integer age,String sex,String addr) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.addr = addr;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", addr=" + addr + "]";
	}
	
	
	
	
	
}
