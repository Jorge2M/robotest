package com.mng.robotest.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.checkout.dotpay.PageDotpay1rst;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageDotpay1rstStpV {
	
	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	  	validations.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			PageDotpay1rst.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	  	
	  	State stateVal = State.Warn;
	  	StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = State.Info;
			store = StoreType.None;
		}
	  	validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal, store);
	  	validations.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageDotpay1rst.isPresentCabeceraStep(nombrePago, channel, driver), State.Warn);
	  	if (channel==Channel.desktop) {
		  	validations.add(
				"Figura un botón de pago",
				PageDotpay1rst.isPresentButtonPago(driver), State.Defect);
	  	}
	  	
	  	return validations;
	}
	
	@Step (
		description="Seleccionar el link hacia el Pago", 
		expected="Aparece la página de selección del canal de pago")
	public static void clickToPay(String importeTotal, String codPais, Channel channel, WebDriver driver) throws Exception {
		PageDotpay1rst.clickToPay(channel, driver);
		PageDotpayPaymentChannelStpV.validateIsPage(importeTotal, codPais, driver);
	}
}
