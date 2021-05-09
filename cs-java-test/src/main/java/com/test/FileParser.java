package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class is used to parse and read event log details from file. It will
 * flag as true when difference between start and finish log greater than 4ms.
 *
 */
public class FileParser implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);
	LineNumberReader lineNumberReader = null;
	FileReader reader = null;
	List<LogEvent> eventList = new ArrayList<>();
	List<AlertLogEvent> alertList = new ArrayList<>();
	

	public List<LogEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<LogEvent> eventList) {
		this.eventList = eventList;
	}

	public List<AlertLogEvent> getAlertList() {
		return alertList;
	}

	public void setAlertList(List<AlertLogEvent> alertList) {
		this.alertList = alertList;
	}

	public FileParser(String filename) {
		super();
		LOGGER.info("Intialize filereader and linerreader");
		try {
			File file = new File(filename+".txt");
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		lineNumberReader = new LineNumberReader(reader);

	}

	void parseAndStoreEventLogs() throws IOException {
		String line = null;
		LOGGER.info("Started reading file");
		synchronized (this) {
			while ((line = lineNumberReader.readLine()) != null) {
				eventList.add(JsonMapper.convertTo(line));
			}
		}
		createCollectionOFLogIdIntoMap();
		writeEventLogintoDB();
	}

	void createCollectionOFLogIdIntoMap() throws IOException {
		Map<String, List<LogEvent>> map = eventList.stream().collect(Collectors.groupingBy(LogEvent::getId));
		Set<Entry<String, List<LogEvent>>> entrySet = map.entrySet();
		Iterator<Entry<String, List<LogEvent>>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<LogEvent>> entry = (Map.Entry<String, List<LogEvent>>) iterator.next();
			List<LogEvent> value = entry.getValue();
			AlertLogEvent alertLogEvent = null;
			long start = 0l;
			long end = 0l;
			for (LogEvent event : value) {
				if ("STARTED".equalsIgnoreCase(event.getState())) {
					start = event.getTimestamp().getTime();
				} else if ("FINISHED".equalsIgnoreCase(event.getState())) {
					end = event.getTimestamp().getTime();
				}
			}
			long eventDuration = end - start;
			if (eventDuration > 4) {
				alertLogEvent = new AlertLogEvent(value.get(0).getId(), eventDuration, value.get(0).getType(),
						value.get(0).getHost(), true);
			} else {
				alertLogEvent = new AlertLogEvent(value.get(0).getId(), eventDuration, value.get(0).getType(),
						value.get(0).getHost(), false);
			}
			alertList.add(alertLogEvent);
			LOGGER.info("Collected log id information to store into DB {}",alertList.size());
		}
	}

	void writeEventLogintoDB() {
		LOGGER.info("Started performing DB operation");
		FileDBOperation dbOperation = new FileDBOperation();
		for (AlertLogEvent alertLogEvent : getAlertList()) {
			dbOperation.storeEventLogsIntoDB(alertLogEvent);
		}
		LOGGER.info("Finished DB operation.");

	}

	@Override
	public void run() {
		try {
			LOGGER.info("Started Parse and store event operations");
			parseAndStoreEventLogs();
			LOGGER.info("Finished Parse and store event operations");
		} catch (IOException e) {
			LOGGER.debug("Exception occured while performing  parsing file {}",e.getMessage());
		}finally {
			try {
				if(null!=reader) {
				reader.close();
				}
			} catch (IOException e) {
				LOGGER.debug("Exception occured while closing reader object {}",e.getMessage());
			}
			try {
				if(null!=lineNumberReader) {
				lineNumberReader.close();
				}
			} catch (IOException e) {
				LOGGER.debug("Exception occured while closing reader object {}",e.getMessage()); 
			}
		}
		
	}

}