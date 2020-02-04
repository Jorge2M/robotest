package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalDirecEnvioStpV {

	@Validation
    public static ChecksResult validateIsOk(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta #{maxSeconds} seconds)",
			ModalDirecEnvio.isVisibleFormUntil(maxSecondsWait, driver), State.Defect); 
	 	validations.add(
			"Es visible el botón \"Actualizar\"",
			ModalDirecEnvio.isVisibleButtonActualizar(driver), State.Defect); 
	 	return validations;
    }
    
    @Step (
    	description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
        expected="Los datos se actualizan correctamente")
    public static void inputDataAndActualizar(DataDireccion dataDirFactura, WebDriver driver) throws Exception {
    	int nTimes = 3;
        ModalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirFactura, nTimes, driver);
        ModalDirecEnvio.moveToAndDoubleClickActualizar(driver);
        checkAfterUpdateData(driver);
    }
    
    @SuppressWarnings("static-access")
    @Validation
    private static ChecksResult checkAfterUpdateData(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 2;
	 	validations.add(
			"Desaparece el modal de introducción de los datos de la dirección (lo esperamos hasta " + maxSecondsWait + " segundos)",
			ModalDirecEnvio.isInvisibleFormUntil(maxSecondsWait, driver), State.Defect); 
	 	validations.add(
			"Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSecondsWait + " segundos)",
			Page1DktopCheckout.modalAvisoCambioPais.isVisibleUntil(maxSecondsWait, driver), State.Warn); 
	 	int maxSeconds = 2;
	 	validations.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
	 		PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSeconds), State.Warn);
    	return validations;
    }
}
