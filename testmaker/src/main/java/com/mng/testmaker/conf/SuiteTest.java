package com.mng.testmaker.conf;

public interface SuiteTest {
	public int getMaxSecondsToWaitStart();
	public SuiteTest getValueOf(String suiteName);
}