package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayPaymentChannel;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageDotpayPaymentChannelStpV {

	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
    public static void selectPayment(int numPayment, WebDriver driver) throws Exception {
		PageDotpayPaymentChannel.clickPayment(numPayment, driver);
		isVisibleBlockInputNombre(1, driver);
    }
	
	@Validation (
		description="Es visible el bloque de introducción del nombre (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean isVisibleBlockInputNombre(int maxSecondsWait, WebDriver driver) {
		return (PageDotpayPaymentChannel.isVisibleBlockInputDataUntil(maxSecondsWait, driver));
	}
    
	@Step (
		description="Introducir el nombre <b>#{nameFirst} / #{nameSecond}</b> y seleccionar el botón para Confirmar", 
        expected="Aparece la página de pago")
    public static void inputNameAndConfirm(String nameFirst, String nameSecond, WebDriver driver) throws Exception {
        PageDotpayPaymentChannel.sendInputNombre(nameFirst, nameSecond, driver);
        PageDotpayPaymentChannel.clickButtonConfirm(driver);
        PageDotpayAcceptSimulationStpV.validateIsPage(driver);
    }    
}
