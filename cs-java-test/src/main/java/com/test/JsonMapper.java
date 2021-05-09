package com.test;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 
 * This is used to convert json string into Object class.
 *
 */
public class JsonMapper {

	public static LogEvent convertTo(String line) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampDeserializer());
		Gson gson = gsonBuilder.create();
		LogEvent logEvent = gson.fromJson(line, LogEvent.class);

		return logEvent;
	}
}
