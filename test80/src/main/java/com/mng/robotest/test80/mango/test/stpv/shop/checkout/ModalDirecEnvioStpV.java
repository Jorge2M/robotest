package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalDirecEnvioStpV {
	
	private final ModalDirecEnvio modalDirecEnvio;
	private final WebDriver driver;
	private final Channel channel;
	
	public ModalDirecEnvioStpV(Channel channel, WebDriver driver) {
		this.modalDirecEnvio = new ModalDirecEnvio(driver);
		this.driver = driver;
		this.channel = channel;
	}
	
	@Validation
	public ChecksTM validateIsOk() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	 	validations.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta #{maxSeconds} seconds)",
			modalDirecEnvio.isVisibleFormUntil(maxSeconds), State.Defect); 
	 	validations.add(
			"Es visible el botón \"Actualizar\"",
			modalDirecEnvio.isVisibleButtonActualizar(), State.Defect); 
	 	return validations;
	}

	@Step (
		description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
		expected="Los datos se actualizan correctamente")
	public void inputDataAndActualizar(DataDireccion dataDirFactura) throws Exception {
		int nTimes = 3;
		modalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirFactura, nTimes);
		modalDirecEnvio.moveToAndDoubleClickActualizar();
		checkAfterUpdateData();
	}

	@SuppressWarnings("static-access")
	@Validation
	private ChecksTM checkAfterUpdateData() {
		ChecksTM validations = ChecksTM.getNew();
		Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout(channel, driver);
		int maxSeconds = 2; 
		validations.add(
			"Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSeconds + " segundos)",
			page1DktopCheckout.getModalAvisoCambioPais().isVisibleUntil(maxSeconds), State.Warn); 
		validations.add(
			"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
			(new PageCheckoutWrapper(channel, driver)).waitUntilNoDivLoading(maxSeconds), State.Warn);
		return validations;
	}
}
