package com.mng.robotest.tests.domains.login.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.login.pageobjects.PageRecuperaPasswd;

public class PageRecuperaPasswdSteps extends StepBase {
	
	private final PageRecuperaPasswd pageRecuperaPasswd = new PageRecuperaPasswd();
	
	@Validation
	public ChecksTM isPage() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece la pantalla de recuperación de la contraseña " + getLitSecondsWait(seconds),
			pageRecuperaPasswd.isPageUntil(seconds));
		
		checks.add(
			"Aparece el campo para la introducción del correo",
			pageRecuperaPasswd.isPresentInputCorreo());
		
		return checks;
	}
	
	@Step (
		description="Introducir el email <b>#{email}</b> y pulsar el botón \"Enviar\"", 
		expected="Aparece la página de cambio de contraseña")
	public void inputMailAndClickEnviar(String email) {
		pageRecuperaPasswd.inputEmail(email);
		pageRecuperaPasswd.clickEnviar();
		isPageCambioPassword();
		checksDefault();
	}
	
	@Validation
	private ChecksTM isPageCambioPassword() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece el mensaje de \"Revisa tu email\" " + getLitSecondsWait(seconds),
			pageRecuperaPasswd.isVisibleRevisaTuEmailUntil(seconds));
		
		checks.add(
			"Aparece el botón \"Ir de Shopping\"",
			pageRecuperaPasswd.isVisibleButtonIrDeShopping());
		
		return checks;
	}
}
