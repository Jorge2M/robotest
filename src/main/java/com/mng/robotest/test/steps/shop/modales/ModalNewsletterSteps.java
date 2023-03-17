package com.mng.robotest.test.steps.shop.modales;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalNewsletterSteps extends PageBase {

	private static final String XPATH_ASPA_CLOSE = "//button[@data-testid='newsletterSubscriptionModal.nonModal.close']";

	public void closeIfVisible() {
		if (isVisible()) {
			close();
		}
	}
	
	private boolean isVisible() {
		return state(Visible, XPATH_ASPA_CLOSE).check();
	}
	
	@Step (
		description="Cerramos el modal de la Newsletter", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public void close() {
		click(XPATH_ASPA_CLOSE).exec();
	}
}
