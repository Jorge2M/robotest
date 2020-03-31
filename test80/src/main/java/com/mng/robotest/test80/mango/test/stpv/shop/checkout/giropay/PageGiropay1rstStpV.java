package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropay1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageGiropay1rstStpV {

	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b>",
			PageGiropay1rst.isPresentIconoGiropay(channel, driver), State.Warn);	

		State stateVal = State.Warn;
		boolean avoidEvidences = false;
		if (channel==Channel.movil_web) {
			stateVal = State.Info;
			avoidEvidences = true;
		}
		validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal, avoidEvidences);
		validations.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageGiropay1rst.isPresentCabeceraStep(driver), State.Warn);	

		if (channel==Channel.desktop) {
//			validations.add(
//				"Aparece un input para la introducción del Banco (lo esperamos hasta " + maxSeconds + " segundos)",
//				PageGiropay1rst.isVisibleInputBankUntil(maxSeconds, driver), State.Warn);
			int maxSeconds = 2;
			validations.add(
				"Figura un botón de pago (lo esperamos hasta " + maxSeconds + " segundos)",
				PageGiropay1rst.isPresentButtonPagoDesktopUntil(maxSeconds, driver), State.Defect);
		}
		
		return validations;
	}

	@Step (
		description="Pulsamos el botón para continuar con el Pago", 
		expected="Aparece la página de Test de introducción de datos de Giropay")
	public static void clickButtonContinuePay(Channel channel, WebDriver driver) throws Exception {
		PageGiropay1rst.clickButtonContinuePay(channel, driver);
		PageGiropayInputBankStpV.checkIsPage(driver);
	}
}
