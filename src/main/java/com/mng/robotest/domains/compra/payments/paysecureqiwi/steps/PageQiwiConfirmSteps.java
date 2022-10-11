package com.mng.robotest.domains.compra.payments.paysecureqiwi.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureConfirm;
import com.mng.robotest.domains.transversal.StepBase;

public class PageQiwiConfirmSteps extends StepBase {

	@Step (
		description="Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
		expected="Aparece la página de resultado del pago de Mango")
	public void selectConfirmButton() {
		new PagePaysecureConfirm().clickConfirmar();		 
	}
}
