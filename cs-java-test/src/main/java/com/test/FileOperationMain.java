package com.test;
/**
 * 
 * Main class is used to perform parse operation.
 *
 */
public class FileOperationMain {

	public static void main(String[] args) {
		FileParser fileParser = new FileParser(args[0]);
		Thread thread = new Thread(fileParser,"File-Read-Thread");
		thread.start();
	}
}
