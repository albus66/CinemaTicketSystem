/**
 * 该类用于定义每次放映的相关信息
 */
package com;

public class Show {
	
	private int id;  // 放映编号
	private int mid; //放映的电影编号
	private int hall; //放映厅编号
	private double price; // 票价 （同一电影不同时间放映价格可能不同）
	private String time; // 放映时间
	private String seatsUsed; // 该次放映已经被预订的座位，多个座位间以空格隔开
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getHall() {
		return hall;
	}
	public void setHall(int hall) {
		this.hall = hall;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getSeatsUsed() {
		return seatsUsed;
	}
	public void setSeatsUsed(String seatsUsed) {
		this.seatsUsed = seatsUsed;
	}


}
