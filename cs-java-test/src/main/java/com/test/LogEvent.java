package com.test;

import java.sql.Timestamp;
/**
 * 
 * This class is used to capture parsed file details.
 *
 */
public class LogEvent {

	private String id;
	private String state;
	private Timestamp timestamp;
	
	private String type;
	private String host;
	private boolean alert;
	
	public LogEvent(String id, String state, Timestamp timestamp, String type, String host, boolean alert) {
		super();
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "LogEvent [id=" + id + ", state=" + state + ", timestamp=" + timestamp + ", type=" + type + ", host="
				+ host + ", alert=" + alert + "]";
	}
	
	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
}
