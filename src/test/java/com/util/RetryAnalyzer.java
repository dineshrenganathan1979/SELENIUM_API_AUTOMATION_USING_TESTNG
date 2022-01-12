package com.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private static int retryFailedTestLimit = 3;
	private static int counter = 0;

	public boolean retry(ITestResult result) {
		if(counter < retryFailedTestLimit ) {
			counter++;
			return true;
		}
		return false;
	}

}
