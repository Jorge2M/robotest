package com.mng.robotest.test80.mango.conftestmaker;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;

public enum AppEcom implements AppTest {
	shop,
    outlet,
    votf;

	@Override
	public AppTest getValueOf(String application) {
	    return (AppEcom.valueOf(application));
	}
	
	@Override 
	public List<AppTest> getValues() {
		return Arrays.asList(values());
	}
}
