package com.mng.robotest.test80.arq.utils.testab;

import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class TestABactData {

	private final TestAB testAB;
	private final int vToActive;
	
	private TestABactData(TestAB testAB, int vToActive, List<Channel> supportedChannels, List<AppTest> supportedApps) {
		this.testAB = testAB;
		this.vToActive = vToActive;
	}
	
	public static TestABactData getNew(TestAB testAB, int vToActivate) {
		return (new TestABactData(testAB, vToActivate, null, null));
	}
	
	public TestAB getTestAB() {
		return testAB;
	}

	public int getvToActive() {
		return vToActive;
	}
}
