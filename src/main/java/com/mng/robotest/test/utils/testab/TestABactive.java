package com.mng.robotest.test.utils.testab;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.conftestmaker.AppEcom;
import com.github.jorge2m.testmaker.conf.Channel;

import static com.mng.robotest.test.utils.testab.TestABOptimizeImpl.*;

public class TestABactive {

	private TestABactive() {}
	
	public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) 
			throws Exception {
		var testABs = Arrays.asList(
			TestABactData.getNew(NUEVO_GUEST_CHECKOUT_PRE, 1),
			TestABactData.getNew(NUEVO_GUEST_CHECKOUT_PRO, 1),
			TestABactData.getNew(AHORRO_EN_BOLSA_MOBILE_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRO, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRO, 0)
		);
		
		TestABmanager.activateTestsAB(testABs, channel, app, driver);
	}
}
