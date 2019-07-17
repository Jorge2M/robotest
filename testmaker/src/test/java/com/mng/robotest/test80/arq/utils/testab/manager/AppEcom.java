package com.mng.robotest.test80.arq.utils.testab.manager;

import com.mng.robotest.test80.arq.utils.conf.AppTest;

public enum AppEcom implements AppTest {
	shop,
    outlet,
    votf;

	@Override
	public AppTest getValueOf(String application) {
	    return (AppEcom.valueOf(application));
	}
}
