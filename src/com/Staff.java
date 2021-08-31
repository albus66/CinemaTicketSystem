/**
 * 该类用于定义后台管理员的账号信息
 */
package com;

public class Staff {
	
	private int uid;
	private String username; // 登录用户名
	private String password; // 登录密码 
	private int role; // 权限，具体参考Constant.java文件里的定义

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}

}
