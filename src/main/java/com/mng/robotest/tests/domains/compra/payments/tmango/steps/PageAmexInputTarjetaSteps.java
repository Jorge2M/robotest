package com.mng.robotest.tests.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.PageAmexInputTarjeta;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAmexInputTarjetaSteps extends StepBase {
	
	private final PageAmexInputTarjeta pageAmexInputTarjeta = new PageAmexInputTarjeta();
	
	@Validation
	public ChecksTM checkIsPageOk(String importeTotal) {
		var checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
		int seconds = 5;
	 	checks.add(
			"Aparece la pasarela de pagos de RedSys " + getLitSecondsWait(seconds),
			pageAmexInputTarjeta.isPasarelaRedSys(seconds)); 
	 	
	 	checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
	 	
	 	checks.add(
			"Aparecen los campos de introducción de tarjeta, fecha caducidad y código de seguridad",
			pageAmexInputTarjeta.isPresentNumTarj() &&
			pageAmexInputTarjeta.isPresentInputMesCad() &&
			pageAmexInputTarjeta.isPresentInputAnyCad() &&
			pageAmexInputTarjeta.isPresentInputCvc(), WARN); 
	 	
	 	checks.add(
			"Figura un botón de Aceptar",
			pageAmexInputTarjeta.isPresentPagarButton()); 
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducimos los datos de la tarjeta: #{numTarj} / #{mesCad}-#{anyCad} / #{Cvc} y pulsamos el botón \"Pagar\"", 
		expected="Aparece la página de simulación del pago RedSys")
	public PageRedsysSimSteps inputTarjetaAndPayButton(String numTarj, String mesCad, String anyCad, String cvc, String importeTotal) {
		pageAmexInputTarjeta.inputDataTarjeta(numTarj, mesCad, anyCad, cvc);
		pageAmexInputTarjeta.clickPagarButton();
		
		var pageRedsysSimSteps = new PageRedsysSimSteps();
		pageRedsysSimSteps.checkPage(1);
		return pageRedsysSimSteps;
	}
}
