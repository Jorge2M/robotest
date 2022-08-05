package com.mng.robotest.test.steps.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.PageAmexInputTarjeta;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageAmexInputTarjetaSteps {
	
	private final PageAmexInputTarjeta pageAmexInputTarjeta;
	
	public PageAmexInputTarjetaSteps(WebDriver driver) {
		pageAmexInputTarjeta = new PageAmexInputTarjeta(driver); 
	}

	@Validation
	public ChecksTM validateIsPageOk(String importeTotal, String codPais) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	 	validations.add(
			"Aparece la pasarela de pagos de RedSys (la esperamos hasta " + maxSeconds + " segundos)",
			pageAmexInputTarjeta.isPasarelaRedSysUntil(maxSeconds), State.Defect); 
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageAmexInputTarjeta.driver), State.Warn); 
	 	validations.add(
			"Aparecen los campos de introducción de tarjeta, fecha caducidad y código de seguridad",
			pageAmexInputTarjeta.isPresentNumTarj() &&
			pageAmexInputTarjeta.isPresentInputMesCad() &&
			pageAmexInputTarjeta.isPresentInputAnyCad() &&
			pageAmexInputTarjeta.isPresentInputCvc(), State.Warn); 
	 	validations.add(
			"Figura un botón de Aceptar",
			pageAmexInputTarjeta.isPresentPagarButton(), State.Defect); 
	 	return validations;
	}
	
	@Step (
		description="Introducimos los datos de la tarjeta: #{numTarj} / #{mesCad}-#{anyCad} / #{Cvc} y pulsamos el botón \"Pagar\"", 
		expected="Aparece la página de simulación del pago RedSys")
	public PageRedsysSimSteps inputTarjetaAndPayButton(
			String numTarj, String mesCad, String anyCad, String Cvc, String importeTotal, String codigoPais) 
					throws Exception {
		pageAmexInputTarjeta.inputDataTarjeta(numTarj, mesCad, anyCad, Cvc);
		pageAmexInputTarjeta.clickPagarButton();
		
		PageRedsysSimSteps pageRedsysSimSteps = new PageRedsysSimSteps(pageAmexInputTarjeta.driver);
		pageRedsysSimSteps.checkPage();
		return pageRedsysSimSteps;
	}
}
