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

		listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.MVPCheckoutDesktop, 0));
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.New_Registry_MLY_Desktop_PRO, 0));
		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.New_Registry_MLY_Mobile_PRO, 0));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.KIRITAKI_LOGIN_DESKTOP_MOBILE_PRE, 1));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.KIRITAKI_LOGIN_SHOP_DESKTOP_PRO, 1));		
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.KIRITAKI_LOGIN_SHOP_MOBILE_PRO, 1));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.KIRITAKI_LOGIN_OUTLET_DESKTOP_PRO, 1));		
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.KIRITAKI_LOGIN_OUTLET_MOBILE_PRO, 1));		
		
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.GPS_NEW_MENU_MOBILE_PRE, 0));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.GPS_NEW_MENU_MOBILE_PRO, 0));

//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PDP_Desktop_Size_Selector_PRE, 1));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PDP_Desktop_Size_Selector_PRO, 1));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PDP_Desktop_Size_Selector_Outlet_PRE, 1));
//		listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PDP_Desktop_Size_Selector_Outlet_PRO, 1));

		TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
	}
}
