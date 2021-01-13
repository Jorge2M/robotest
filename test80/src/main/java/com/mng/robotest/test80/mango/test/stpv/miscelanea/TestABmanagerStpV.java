package com.mng.robotest.test80.mango.test.stpv.miscelanea;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.utils.testab.TestABGoogleExpImpl;


public class TestABmanagerStpV {

	@Step (
		description="Activar el TestAB <b>#{testAB.name()}</b> con la variante <b>#{variante}</b>",
		expected="El TestAB se activa correctamente")
	public static void activateTestAB_GoogleExp(TestABGoogleExpImpl testAB, int variante, Channel channel, AppEcom app, WebDriver driver) 
	throws Exception {
		TestABmanager.activateTestAB(TestABactData.getNew(testAB, variante), channel, app, driver);
	}
}
