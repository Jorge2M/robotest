package com.mng.robotest.test.steps.shop.checkout.assist;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.assist.PageAssistLast;


public class PageAssistLastSteps extends StepBase {

	@Step (
		description="Seleccionar el botón de Submit", 
		expected="Aparece la página de resultado de Mango")
	public void clickSubmit() throws Exception {
		new PageAssistLast().clickButtonSubmit();
	}
}
