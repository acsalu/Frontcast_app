package  com.idcc.hw5.model;

import com.google.api.client.util.Key;
import com.google.api.client.json.GenericJson;

public class Restaurant extends GenericJson {
	@Key
	public Geometry geometry;
	
	@Key
	public String[] types;
}
