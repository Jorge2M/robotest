package com.mng.robotest.test.steps.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.pageobject.shop.checkout.trustpay.PageTrustpaySelectBank;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageTrustpaySelectBankSteps {

	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			PageTrustpaySelectBank.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	 	
	 	State level = State.Warn;
		if (channel.isDevice()) {
			level = State.Info;
		}
	 	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), level); 
	 	checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageTrustpaySelectBank.isPresentCabeceraStep(nombrePago, channel, driver), State.Warn); 
		if (channel==Channel.desktop) {
		 	checks.add(
				"Figura el desplegable de bancos",
				PageTrustpaySelectBank.isPresentSelectBancos(driver), State.Warn); 
		 	checks.add(
				"Figura un botón de pago",
				PageTrustpaySelectBank.isPresentButtonPago(driver), State.Defect); 
		}
		
		return checks;
	}
	
	static final String tagPosibleBanks = "@TagPosibleBanks";
	@Step (
		description="Seleccionamos un banco de test (contiene alguno de los textos " + tagPosibleBanks + ") y pulsamos <b>Pay</b>", 
		expected="Aparece la página de test para la confirmación")
	public static void selectTestBankAndPay(String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ArrayList<String> listOfPosibleValues = new ArrayList<>();
		listOfPosibleValues.addAll(Arrays.asList("TestPay", "Fio banka"));
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagPosibleBanks, String.join(",", listOfPosibleValues));

		PageTrustpaySelectBank.selectBankThatContains(listOfPosibleValues, channel, driver);
		PageTrustpaySelectBank.clickButtonToContinuePay(channel, driver);
		
		//Validation
		//PageTrustpayTestConfirmSteps.validateIsPage(StepTestMaker, dFTest);
		PageTrustPayResultSteps.validateIsPage(importeTotal, codPais, driver);
	}
}
