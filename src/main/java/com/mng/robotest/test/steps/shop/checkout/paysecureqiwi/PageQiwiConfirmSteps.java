package com.mng.robotest.test.steps.shop.checkout.paysecureqiwi;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureConfirm;

public class PageQiwiConfirmSteps extends StepBase {

	@Step (
		description="Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
		expected="Aparece la página de resultado del pago de Mango")
	public void selectConfirmButton() throws Exception {
		new PagePaysecureConfirm().clickConfirmar();		 
	}
}
