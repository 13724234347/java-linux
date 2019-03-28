package com.tzh.spring.entities;

import java.io.Serializable;

/**
* @Title:  User.java   
* @Package cn.yr.entities
* @Description:    TODO
* @date:   2018年11月22日 上午9:01:18   
*/
public class User implements Serializable{

	/**
	 *  @Fields serialVersionUID : TODO  
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String account;
	private String passwords;
	private String tel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", passwords=" + passwords + ", tel=" + tel + "]";
	}
}
