package com.idcc.hw5.model;

import com.google.api.client.util.Key;

public class Geometry {
	@Key
	public Location location;
	
	@Override
	public String toString() {
		return location.lat + ", " + location.lng;
	}
	
}
