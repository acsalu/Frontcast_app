package com.frontcast.model;



import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.gson.annotations.SerializedName;

/*
public class Frontcast extends GenericJson {
<<<<<<< HEAD
=======
	
>>>>>>> ded9186f3a0cf48e9212a9f8fce51daad8afae59
	@Key
	public int level;
	
	@Key
	public double latitude;
		
	@Key
	public double longitude;
	
	//@Key
	//public myTime time;
	
	
<<<<<<< HEAD
=======
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
	
	//@SerializedName("time")
	//public myTime time;
	
	@SerializedName("latitude")
	public double latitude;
	
	@SerializedName("type")
	public String type;
>>>>>>> ded9186f3a0cf48e9212a9f8fce51daad8afae59
}