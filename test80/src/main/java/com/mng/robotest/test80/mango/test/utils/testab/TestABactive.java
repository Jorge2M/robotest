package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABactive {

	public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<TestABactData> listTestABsToActivate = new ArrayList<>();

		listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.MVPCheckoutDesktop, 0));
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.Test_New_Menu_Desktop_PRE, 0));
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.Test_New_Menu_Desktop_PRO, 0));
		
		TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
	}
}
