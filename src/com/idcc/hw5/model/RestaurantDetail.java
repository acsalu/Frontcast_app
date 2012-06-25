package com.idcc.hw5.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class RestaurantDetail extends GenericJson {
	@Key
	public DetailResult result;
	
	public DetailResult getResult() { return result; }
}