package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.testab.TestABactData;
import com.mng.robotest.test80.arq.utils.testab.manager.TestABmanager;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABactive {

    public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	List<TestABactData> listTestABsToActivate = new ArrayList<>();
    	listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.GaleriaDesktopReact, 0));
    	//listTestABsToActivate.add(ActivationData.getNew(TestABid.SHOP191_BuscadorDesktop, 1));
    	listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.MVPCheckoutDesktop, 0));
    	listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.SHOP126_HeaderNuevosIconosDesktop, 1));
    	TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
    }
}
