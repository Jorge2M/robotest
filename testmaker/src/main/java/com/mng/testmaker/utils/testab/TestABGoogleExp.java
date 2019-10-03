package com.mng.testmaker.utils.testab;

public interface TestABGoogleExp extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.GoogleExperiments;
	}
	
	public String getValueCookie(Enum<?> app);
}
