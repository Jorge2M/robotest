package com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureConfirm;

public class PageQiwiConfirmSteps extends StepBase {

	@Step (
		description="Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
		expected="Aparece la página de resultado del pago de Mango")
	public void selectConfirmButton() {
		new PagePaysecureConfirm().clickConfirmar();		 
	}
}
