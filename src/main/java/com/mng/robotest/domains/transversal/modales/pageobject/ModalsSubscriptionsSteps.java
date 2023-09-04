package com.mng.robotest.domains.transversal.modales.pageobject;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalsSubscriptions;
import com.mng.robotest.test.pageobject.shop.modales.ModalsSubscriptions.InitialModal;

public class ModalsSubscriptionsSteps extends StepBase {

	private final ModalsSubscriptions modalsSubscriptions = new ModalsSubscriptions(); 
	
	public void closeIfVisible() {
		for (InitialModal modal : modalsSubscriptions.getModalsCollection()) { 
			if (modalsSubscriptions.isVisible(modal)) {
				close(modal);
			}
		}
	}
	
	@Step (
		description="Cerramos el modal <b>#{modal.name()}</b>", 
		expected="Desaparece el modal")
	public void close(InitialModal modal) {
		modalsSubscriptions.close(modal);
	}
}
