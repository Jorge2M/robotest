package com.mng.robotest.test80.arq.utils.testab;

import com.mng.robotest.test80.arq.utils.conf.AppTest;

public interface TestABGoogleExp extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.GoogleExperiments;
	}
	
	public String getValueCookie(AppTest app);
	
}
