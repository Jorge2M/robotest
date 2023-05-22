package com.mng.robotest.domains.login.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.login.pageobjects.PageLogin;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageIdentificacionSteps extends StepBase {

	@Validation (description="Aparece el texto de introducción errónea de credenciales")
	public boolean checkTextoCredencialesKO() {
		return new PageLogin().isErrorEmailoPasswordKO();
	}
	
	@Step (
		description="Seleccionar el link \"¿Has olvidado tu contraseña?\"", 
		expected="Aparece la página de cambio de contraseña")
	public void selectHasOlvidadoTuContrasenya() {
		new PageLogin().clickHasOlvidadoContrasenya(); 
		new PageRecuperaPasswdSteps().isPage();
		GenericChecks.checkDefault();
	}
}
