package com.mng.robotest.test.utils.testab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.conftestmaker.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

public class TestABactive {

	private TestABactive() {}
	
	public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<TestABactData> listTestABsToActivate = new ArrayList<>();
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.NUEVO_GUEST_CHECKOUT_PRE, 1));
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.NUEVO_GUEST_CHECKOUT_PRO, 1));
		TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
	}
}
