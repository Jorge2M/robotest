package com.mng.robotest.tests.domains.compra.payments.mercadopago.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoLogin;
import com.mng.robotest.testslegacy.beans.Pago;

public class PageMercpagoLoginSteps extends StepBase {

	private final PageMercpagoLogin pageMercpagoLogin = new PageMercpagoLogin();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de identificación de Mercadopago",
			pageMercpagoLogin.isPage());
		
		checks.add(
			"En la página figuran los campos de identificación (email + password)",
			pageMercpagoLogin.isInputUserVisible() &&
			pageMercpagoLogin.isInputPasswordVisible());
		
		return checks;
	}
	
	@Step (
		description="Introducir el usuario/password de mercadopago (#{pago.getUseremail()} / #{pago.getPasswordemail()}) + click botón \"Ingresar\"",
		expected=
			"Aparece alguna de las páginas:<br>" +
			" - Elección medio pago<br>" +
			" - Introducción CVC")
	public void loginMercadopago(Pago pago) {
		pageMercpagoLogin.sendInputUser(pago.getUseremail());
		pageMercpagoLogin.sendInputPassword(pago.getPasswordemail());
		pageMercpagoLogin.clickBotonContinuar();
		new PageMercpagoDatosTrjSteps().validaIsPage(0);
	}
}
