package com.mng.robotest.domains.identification.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.identification.pageobjects.PageRecuperaPasswd;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageRecuperaPasswdSteps extends StepBase {
	
	private final PageRecuperaPasswd pageRecuperaPasswd = new PageRecuperaPasswd();
	
	@Validation
	public ChecksTM isPage() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Aparece la pantalla de recuperación de la contraseña (la esperamos hasta " + maxSecondsToWait + " segundos)",
			pageRecuperaPasswd.isPageUntil(maxSecondsToWait), State.Defect);
		
		checks.add(
			"Aparece el campo para la introducción del correo",
			pageRecuperaPasswd.isPresentInputCorreo(), State.Defect);
		
		return checks;
	}
	
	@Step (
		description="Introducir el email <b>#{email}</b> y pulsar el botón \"Enviar\"", 
		expected="Aparece la página de cambio de contraseña")
	public void inputMailAndClickEnviar(String email) {
		pageRecuperaPasswd.inputEmail(email);
		pageRecuperaPasswd.clickEnviar();
		isPageCambioPassword();
		GenericChecks.checkDefault(driver);
	}
	
	@Validation
	private ChecksTM isPageCambioPassword() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Aparece el mensaje de \"Revisa tu email\" (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			pageRecuperaPasswd.isVisibleRevisaTuEmailUntil(maxSecondsToWait), State.Defect);
		
		checks.add(
			"Aparece el botón \"Ir de Shopping\"",
			pageRecuperaPasswd.isVisibleButtonIrDeShopping(), State.Defect);
		
		return checks;
	}
}