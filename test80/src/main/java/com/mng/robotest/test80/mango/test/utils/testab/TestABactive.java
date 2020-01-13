package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.testab.TestABactData;
import com.mng.testmaker.service.testab.manager.TestABmanager;
import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABactive {

	public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<TestABactData> listTestABsToActivate = new ArrayList<>();
		listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.GaleriaDesktopReact, 0));
		listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.MVPCheckoutDesktop, 0));
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.SHOP126_HeaderNuevosIconosDesktop, 1));
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.SHOP219_PLPMobileSinFavoritos, 0));
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PLP_Desktop_NewFilters, 0));
		
		TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
	}
}
