package com.frontcast.model;

<<<<<<< HEAD
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
=======
import com.google.api.client.json.GenericJson;
import com.google.gson.annotations.SerializedName;

public class myTime {
>>>>>>> ded9186f3a0cf48e9212a9f8fce51daad8afae59

public class myTime {
	@Key public String ctime;
	@Key public int day;
	@Key public int epoch;
	@Key public int hour;
	@Key public List<Integer> isocalendar;
	@Key public DateTime isoformat;
	@Key public int isoweekday;
	@Key public int microsecond;
	@Key public int minute;
	@Key public int month;
	@Key public int second;
	@Key public List<Integer> timetuple;
	@Key public int year;
}