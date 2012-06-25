package com.idcc.hw5.model;

import java.util.List;

import com.google.api.client.util.Key;

public class RestaurantList {

	@Key
	public String status;

	@Key
	public List<Restaurant> results;

}