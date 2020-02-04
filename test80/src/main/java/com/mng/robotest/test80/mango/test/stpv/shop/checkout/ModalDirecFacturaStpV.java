package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecFactura;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalDirecFacturaStpV {

	@Validation
    public static ChecksResult validateIsOk(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Es visible el formulario para la introducción de la \"Dirección de facturación\" (lo esperamos hasta " + maxSecondsWait + " seconds)",
			ModalDirecFactura.isVisibleFormUntil(maxSecondsWait, driver), State.Defect);    
	 	validations.add(
			"Es visible el botón \"Actualizar\"",
	 		ModalDirecFactura.isVisibleButtonActualizar(driver), State.Defect);
	 	int maxSeconds = 2;
	 	validations.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
	 		PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSeconds), State.Warn);
    	return validations;
    }
    
	@Step (
		description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
        expected="Los datos se actualizan correctamente")
    public static void inputDataAndActualizar(DataDireccion dataDirFactura, WebDriver driver) throws Exception {
        ModalDirecFactura.sendDataToInputs(dataDirFactura, driver);
        ModalDirecFactura.clickActualizar(driver);
        checkAfterChangeDireccion(driver);
    }
	
	@Validation
	private static ChecksResult checkAfterChangeDireccion(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Desaparece el modal de introducción de los datos de la dirección",
	 		!ModalDirecFactura.isVisibleFormUntil(0, driver), State.Defect);    
	 	validations.add(
			"Queda marcado el radiobutton \"Quiero recibir una factura\"",
			Page1DktopCheckout.isMarkedQuieroFactura(driver), State.Defect);
	 	int maxSeconds = 2;
	 	validations.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
	 		PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSeconds), State.Warn);
	 	
	 	return validations;
	}
}
