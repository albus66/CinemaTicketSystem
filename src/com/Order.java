/**
 * 该类用于定义订单的基本变量和方法
 */
package com;

public class Order {
	private int id;
	private String name; // 订票人姓名
	private String phone; // 订票人电话
	private String data; // 订单数据（包含多张票的信息，票之间用分号隔开，每张票的信息包括放映场次id, 电影名称，放映时间以及价格座位等）
	private String datetime; // 订单生成时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
