package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalDirecEnvioStpV {

	@Validation
	public static ChecksTM validateIsOk(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	 	validations.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta #{maxSeconds} seconds)",
			ModalDirecEnvio.isVisibleFormUntil(maxSeconds, driver), State.Defect); 
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
	private static ChecksTM checkAfterUpdateData(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
//		validations.add(
//			"Desaparece el modal de introducción de los datos de la dirección (lo esperamos hasta " + maxSeconds + " segundos)",
//			ModalDirecEnvio.isInvisibleFormUntil(maxSeconds, driver), State.Defect); 
		validations.add(
			"Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSeconds + " segundos)",
			Page1DktopCheckout.modalAvisoCambioPais.isVisibleUntil(maxSeconds, driver), State.Warn); 
		validations.add(
			"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
			PageCheckoutWrapper.waitUntilNoDivLoading(driver, maxSeconds), State.Warn);
		return validations;
	}
}
