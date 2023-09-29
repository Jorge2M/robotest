package com.mng.robotest.tests.conf.testab;

import static com.mng.robotest.tests.conf.testab.TestABOptimizeImpl.*;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.tests.domains.base.PageBase;

public class TestABactive extends PageBase {

	public void currentTestABsToActivate() throws Exception {
		activateOptimize();
		activateOptimizelly();
	}
	
	private void activateOptimize() throws Exception {
		var testABs = Arrays.asList(
			TestABactData.getNew(NUEVO_GUEST_CHECKOUT_PRE, 1),
			TestABactData.getNew(NUEVO_GUEST_CHECKOUT_PRO, 1),
			TestABactData.getNew(AHORRO_EN_BOLSA_MOBILE_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_SHOP_PRO, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRE, 0),
			TestABactData.getNew(PLP_SELECTOR_LISTADO_MOBILE_1_COLUMNA_OUTLET_PRO, 0),
			TestABactData.getNew(LOGIN_REGISTRI_PAGE_WITHOUT_TABS_SHOP_PRE, 0),
			TestABactData.getNew(LOGIN_REGISTRI_PAGE_WITHOUT_TABS_OUTLET_PRE, 0)
		);
		
		TestABmanager.activateTestsAB(testABs, channel, app, driver);
	}
	
	private void activateOptimizelly() throws Exception {
		//Force TestABs to original variant 
		var cookieClientId = new Cookie("client_id", "robotest");
		driver.manage().addCookie(cookieClientId);
		
		//Si no existe darla de alta con dominio tipo .mango.com
		var pattern = Pattern.compile(".*(\\..+\\..+)");
		var matcher = pattern.matcher(inputParamsSuite.getDnsUrlAcceso());
		if (matcher.find()) {
			var cookieClientId2 = new Cookie(
					"client_id",
					"robotest",
					matcher.group(1),
					"/", null, false, false);
			driver.manage().addCookie(cookieClientId2);
		}
	}
	
}
