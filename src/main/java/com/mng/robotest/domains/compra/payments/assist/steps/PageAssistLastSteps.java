package com.mng.robotest.domains.compra.payments.assist.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.assist.pageobjects.PageAssistLast;


public class PageAssistLastSteps extends StepBase {

	@Step (
		description="Seleccionar el botón de Submit", 
		expected="Aparece la página de resultado de Mango")
	public void clickSubmit() {
		new PageAssistLast().clickButtonSubmit();
	}
}
