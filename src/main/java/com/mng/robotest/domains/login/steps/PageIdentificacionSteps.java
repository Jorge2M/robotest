package com.mng.robotest.domains.login.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.login.pageobjects.PageIdentificacion;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageIdentificacionSteps extends StepBase {

	@Step (
		description="Seleccionar el link 'Iniciar Sesión' e introducir credenciales incorrectas: <b>#{usrExistente}, #{password}</b>",
		expected="Aparece el correspondiente mensaje de error")
	public void inicioSesionDatosKO(String usrExistente, String password) {
		new PageIdentificacion().iniciarSesion(usrExistente, password);
		checkTextoCredencialesKO();
		GenericChecks.checkDefault();	
	}
	
	@Validation (
		description="Aparece el texto de introducción errónea de credenciales",
		level=Defect)
	private boolean checkTextoCredencialesKO() {
		return new PageIdentificacion().isErrorEmailoPasswordKO();
	}
	
	@Step (
		description="Seleccionar el link \"¿Has olvidado tu contraseña?\"", 
		expected="Aparece la página de cambio de contraseña")
	public void selectHasOlvidadoTuContrasenya() {
		new PageIdentificacion().clickHasOlvidadoContrasenya(); 
		new PageRecuperaPasswdSteps().isPage();
		GenericChecks.checkDefault();
	}
}
