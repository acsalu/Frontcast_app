package com.frontcast.model;



import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Frontcast extends GenericJson {
	@Key
	public int user_id;
	
	@Key
	public int level;
	
	@Key
	public double longitude;
	
	@Key
	public myTime time;
	
	@Key
	public double latitude;
}