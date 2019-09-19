package com.mng.robotest.test80.arq.utils.testab;

public interface TestABGoogleExp extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.GoogleExperiments;
	}
	
	public String getValueCookie(Enum<?> app);
}
