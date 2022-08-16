package com.mng.robotest.test.steps.shop.identificacion;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class PageIdentificacionSteps {

	@Step (
		description="Seleccionar el link 'Iniciar Sesión' e introducir credenciales incorrectas: <b>#{usrExistente}, #{password}</b>",
		expected="Aparece el correspondiente mensaje de error")
	public static void inicioSesionDatosKO(String usrExistente, String password, Channel channel, AppEcom appE, WebDriver driver) 
			throws Exception {
		new PageIdentificacion().iniciarSesion(usrExistente, password, channel, appE);
		checkTextoCredencialesKO();
		GenericChecks.checkDefault(driver);	
	}
	
	@Validation (
		description="Aparece el texto \"#{PageIdentificacion.getLiteralAvisiCredencialesKO()}\"",
		level=State.Defect)
	private static boolean checkTextoCredencialesKO() {
		return new PageIdentificacion().isErrorEmailoPasswordKO();
	}
	
	@Step (
		description="Seleccionar el link \"¿Has olvidado tu contraseña?\"", 
		expected="Aparece la página de cambio de contraseña")
	public static void selectHasOlvidadoTuContrasenya(WebDriver driver) {
		new PageIdentificacion().clickHasOlvidadoContrasenya(); 
		PageRecuperaPasswdSteps.isPage(driver);
		GenericChecks.checkDefault(driver);
	}
}
