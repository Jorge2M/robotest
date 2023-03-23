package com.mng.robotest.domains.compra.payments.paymaya.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.paymaya.pageobjects.PageInitPaymaya;


public class PageInitPaymayaSteps {

	private PageInitPaymaya pageInitPaymaya = new PageInitPaymaya();
	
	@Validation
	public ChecksTM checkPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página inicial de PayMaya",
			pageInitPaymaya.isPage(), State.Warn); 
	 	checks.add(
			"Aparece la imagen del QR",
			pageInitPaymaya.isQrVisible(), State.Defect);
	 	return checks;
	}
	
	@Step(
		description="Seleccionamos el botón PayMaya para el checkout express",
		expected="Aparece la página de identificación de PayMaya")
	public PageIdentPaymayaSteps clickPaymayaButton() {
		pageInitPaymaya.clickButtonPayMaya();
		var pageIdentPaymayaSteps = new PageIdentPaymayaSteps();
		pageIdentPaymayaSteps.checkPage();
		return pageIdentPaymayaSteps;
	}
	
}
