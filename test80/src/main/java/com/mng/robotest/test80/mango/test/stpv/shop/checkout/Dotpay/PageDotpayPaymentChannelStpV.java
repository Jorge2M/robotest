package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayPaymentChannel;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageDotpayPaymentChannelStpV {

	@Validation
    public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
      	validations.add(
    		"Aparece la página de Dotpay para la selección del banco",
    		PageDotpayPaymentChannel.isPage(driver), State.Warn);
      	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		return validations;
    }
    
	@Step (
		description="Seleccionar el <b>#{numPayment}o</b> de los Canales de Pago", 
        expected="Se scrolla y se hace visible el bloque de introducción del nombre")
    public static void selectPayment(int numPayment, WebDriver driver) {
		PageDotpayPaymentChannel.clickPayment(numPayment, driver);
		isVisibleBlockInputNombre(1, driver);
    }
	
	@Validation (
		description="Es visible el bloque de introducción del nombre (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean isVisibleBlockInputNombre(int maxSeconds, WebDriver driver) {
		return (PageDotpayPaymentChannel.isVisibleBlockInputDataUntil(maxSeconds, driver));
	}
    
	@Step (
		description="Introducir el nombre <b>#{nameFirst} / #{nameSecond}</b> y seleccionar el botón para Confirmar", 
		expected="Aparece la página de pago")
	public static void inputNameAndConfirm(String nameFirst, String nameSecond, WebDriver driver) {
		PageDotpayPaymentChannel.sendInputNombre(nameFirst, nameSecond, driver);
		PageDotpayPaymentChannel.clickButtonConfirm(driver);
		PageDotpayAcceptSimulationStpV.validateIsPage(5, driver);
	}
}
