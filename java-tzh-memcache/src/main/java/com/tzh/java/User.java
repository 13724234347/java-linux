package com.tzh.java;

import java.io.Serializable;

/**
 *
 * @ClassName:  User   
 * @Description:实体类一定要序列化,不然会报错
 * @date:   2018年11月21日 下午3:03:28   
 *    
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved.
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	private String name;
	private Integer age;
	private String sex;

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

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}

}