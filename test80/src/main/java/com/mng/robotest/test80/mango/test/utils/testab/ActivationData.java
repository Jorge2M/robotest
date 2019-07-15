package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.List;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class ActivationData {


	private final TestABid testAB;
	private final int vToActive;
	
	private ActivationData(TestABid testAB, int vToActive, List<Channel> supportedChannels, List<AppEcom> supportedApps) {
		this.testAB = testAB;
		this.vToActive = vToActive;
	}
	
	public static ActivationData getNew(TestABid testAB, int vToActivate) {
		return (new ActivationData(testAB, vToActivate, null, null));
	}
	
	public TestABid getTestAB() {
		return testAB;
	}

	public int getvToActive() {
		return vToActive;
	}
}
