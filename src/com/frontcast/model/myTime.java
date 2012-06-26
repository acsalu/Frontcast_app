package com.frontcast.model;


import com.google.gson.annotations.SerializedName;

public class myTime {

	@SerializedName("ctime")
	public String ctime;
	
	@SerializedName("year")
	public int year;
	
	@SerializedName("month")
	public int month;
	
	@SerializedName("day")
	public int day;
	
	@SerializedName("hour")
	public int hour;
	
	@SerializedName("minute")
	public int minute;
	
	@SerializedName("second")
	public int second;
	
	@SerializedName("epoch")
	public int epoch;

}