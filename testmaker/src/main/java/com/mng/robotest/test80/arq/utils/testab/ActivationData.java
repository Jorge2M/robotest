package com.mng.robotest.test80.arq.utils.testab;

import java.util.List;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;

public class ActivationData {

	private final TestAB testAB;
	private final int vToActive;
	
	private ActivationData(TestAB testAB, int vToActive, List<Channel> supportedChannels, List<AppTest> supportedApps) {
		this.testAB = testAB;
		this.vToActive = vToActive;
	}
	
	public static ActivationData getNew(TestAB testAB, int vToActivate) {
		return (new ActivationData(testAB, vToActivate, null, null));
	}
	
	public TestAB getTestAB() {
		return testAB;
	}

	public int getvToActive() {
		return vToActive;
	}
}
