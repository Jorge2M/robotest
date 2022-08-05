package com.mng.robotest.test.steps.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.checkout.multibanco.PageMultibanco1rst;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageMultibanco1rstSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String emailUsr, String codPais, Channel channel, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			PageMultibanco1rst.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	   	
	   	State stateVal = State.Warn;
		if (channel.isDevice()) {
			stateVal = State.Info;
		}
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
	   	checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageMultibanco1rst.isPresentCabeceraStep(driver), State.Warn);
	   	
		if (channel==Channel.desktop) {
		   	checks.add(
				"Aparece un campo de introducción de email (informado con <b>" + emailUsr + "</b>)",
				PageMultibanco1rst.isPresentEmailUsr(emailUsr, driver), State.Warn);
		   	checks.add(
				"Figura un botón de pago",
				PageMultibanco1rst.isPresentButtonPagoDesktop(driver), State.Defect);		   	
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de \"En progreso\"")
	public static void continueToNextPage(Channel channel, WebDriver driver) throws Exception {
		PageMultibanco1rst.continueToNextPage(channel, driver);
		PageMultibancoEnProgresoSteps.validateIsPage(driver);
	}
}
