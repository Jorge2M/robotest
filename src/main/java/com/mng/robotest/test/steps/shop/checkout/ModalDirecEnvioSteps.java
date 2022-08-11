package com.mng.robotest.test.steps.shop.checkout;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;


public class ModalDirecEnvioSteps extends StepBase {
	
	private final ModalDirecEnvio modalDirecEnvio;
	private final Channel channel;
	private final AppEcom app;
	
	public ModalDirecEnvioSteps(Channel channel, AppEcom app) {
		this.modalDirecEnvio = new ModalDirecEnvio();
		this.channel = channel;
		this.app = app;
	}
	
	@Validation
	public ChecksTM validateIsOk() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta #{maxSeconds} seconds)",
			modalDirecEnvio.isVisibleFormUntil(maxSeconds), State.Defect); 
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
			modalDirecEnvio.isVisibleButtonActualizar(), State.Defect); 
	 	return checks;
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
		ChecksTM checks = ChecksTM.getNew();
		Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout(channel, app);
		int maxSeconds = 2; 
		checks.add(
			"Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSeconds + " segundos)",
			page1DktopCheckout.getModalAvisoCambioPais().isVisibleUntil(maxSeconds), State.Warn); 
		checks.add(
			"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
			new PageCheckoutWrapper(channel, app).waitUntilNoDivLoading(maxSeconds), State.Warn);
		return checks;
	}
}
