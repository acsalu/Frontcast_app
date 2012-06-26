package com.frontcast.model;



import com.google.gson.annotations.SerializedName;


public class Frontcast {
	
	@SerializedName("user_id")
	public String userID;

	@SerializedName("level")
	public int level;
	
	@SerializedName("longitude")
	public double longitude;
	
	@SerializedName("time")
	public myTime time;
	
	@SerializedName("latitude")
	public double latitude;
	
	@SerializedName("type")
	public String type;
}