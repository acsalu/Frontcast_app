package com.frontcast.model;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

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