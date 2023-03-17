package com.mng.robotest.test.steps.miscelanea;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.utils.testab.TestABGoogleExpImpl;

public class TestABmanagerSteps extends StepBase {

	@Step (
		description="Activar el TestAB <b>#{testAB.name()}</b> con la variante <b>#{variante}</b>",
		expected="El TestAB se activa correctamente")
	public void activateTestAB_GoogleExp(TestABGoogleExpImpl testAB, int variante) throws Exception {
		TestABmanager.activateTestAB(TestABactData.getNew(testAB, variante), channel, app, driver);
	}
}
