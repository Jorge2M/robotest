package com.mng.robotest.test80.arq.utils.testab;

public interface TestABOptimize extends TestAB {

	@Override
	default TypeTestAB getType() {
		return TypeTestAB.Optimize;
	}
}
