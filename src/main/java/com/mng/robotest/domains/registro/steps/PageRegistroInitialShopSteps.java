package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroInitialShop;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.transversal.StepBase;

public class PageRegistroInitialShopSteps extends StepBase {

	private final PageRegistroInitialShop pageRegistroInitial = new PageRegistroInitialShop();
	
	@Validation (
		description="Aparece la página inicial del proceso de registro (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPageUntil(int maxSeconds) {
		return pageRegistroInitial.isPageUntil(maxSeconds);
	}

	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Email: <b>#{data.getEmail()}</b><br>" +
			"  - Contraseña: <b>*****</br>" + 
			"  - Mobil: <b>#{data.getMovil()}</b><br>" +
			"  - Check promociones: <b>#{data.isCheckPromotions()}</b>",
		expected=
			"La introducción de datos es correcta")
	public void inputData(DataNewRegister data) {
		pageRegistroInitial.inputMovil(data.getMovil());
		pageRegistroInitial.inputPassword(data.getPassword());
		pageRegistroInitial.inputEmail(data.getEmail());
		if (data.isCheckPromotions()) {
			pageRegistroInitial.clickRadioGivePromotions();
		}
	}
	
	@Step (
		description="Pulsar el botón <b>Crear cuenta</b>",
		expected="Aparece la página de personalización del registro",
		saveImagePage=SaveWhen.Always)
	public void clickCreateAccountButton() {
		pageRegistroInitial.clickCreateAccountButton();
		new PageRegistroPersonalizacionShopSteps().checkIsPageUntil(5);
	}	
	
}
