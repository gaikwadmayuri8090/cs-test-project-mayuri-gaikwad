package com.test;
/**
 * 
 * Capture log events to store into DB.
 *
 */
public class AlertLogEvent {

	private String id;
	private long eventDuration;
	private String type;
	private String host;
	private boolean isAlert;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(long eventDuration) {
		this.eventDuration = eventDuration;
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
	public boolean isAlert() {
		return isAlert;
	}
	public void setAlert(boolean isAlert) {
		this.isAlert = isAlert;
	}
	public AlertLogEvent(String id, long eventDuration, String type, String host, boolean isAlert) {
		super();
		this.id = id;
		this.eventDuration = eventDuration;
		this.type = type;
		this.host = host;
		this.isAlert = isAlert;
	}
	@Override
	public String toString() {
		return "AlertLogEvent [id=" + id + ", eventDuration=" + eventDuration + ", type=" + type + ", host=" + host
				+ ", isAlert=" + isAlert + "]";
	}
	
}
