package com.frontcast.model;



import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.gson.annotations.SerializedName;

/*
public class Frontcast extends GenericJson {
	
	@Key
	public int level;
	
	@Key
	public double longitude;
	
	@Key
	public myTime time;
	
	@Key
	public double latitude;
}
*/

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