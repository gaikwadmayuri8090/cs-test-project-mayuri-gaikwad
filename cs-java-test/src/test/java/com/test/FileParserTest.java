package com.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class FileParserTest {

	
	FileParser fileParser = new FileParser("test");

	
	@Test
	public void testFileRead() throws IOException {
		FileDBOperation myObjectMock = Mockito.mock(FileDBOperation.class);
		fileParser.parseAndStoreEventLogs();
		doNothing().when(myObjectMock).storeEventLogsIntoDB(ArgumentMatchers.any());
		assertEquals(3, fileParser.getEventList().size());
	}
}