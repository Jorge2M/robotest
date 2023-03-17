package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.DataDireccion;
import com.mng.robotest.domains.compra.pageobjects.ModalDirecEnvioOld;
import com.mng.robotest.domains.compra.pageobjects.Page1DktopCheckout;
import com.mng.robotest.domains.compra.pageobjects.PageCheckoutWrapper;

public class ModalDirecEnvioOldSteps extends StepBase {
	
	private final ModalDirecEnvioOld modalDirecEnvio = new ModalDirecEnvioOld();
	
	@Validation
	public ChecksTM validateIsOk() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta #{seconds} seconds)",
			modalDirecEnvio.isVisibleFormUntil(seconds), State.Defect);
	 	
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
			modalDirecEnvio.isVisibleButtonActualizar(), State.Defect);
	 	
	 	return checks;
	}

	@Step (
		description="Introducir los datos y pulsar \"Guardar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
		expected="Los datos se actualizan correctamente")
	public void inputDataAndActualizar(DataDireccion dataDirFactura) {
		modalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirFactura, 3);
		modalDirecEnvio.moveToAndDoubleClickActualizar();
		checkAfterUpdateData();
	}
	
	@Validation
	private ChecksTM checkAfterUpdateData() {
		var checks = ChecksTM.getNew();
		Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout();
		int seconds = 2; 
		checks.add(
			"Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + seconds + " segundos)",
			page1DktopCheckout.getModalAvisoCambioPais().isVisibleUntil(seconds), State.Warn);
		
		checks.add(
			"Desaparece la capa de Loading (lo esperamos hasta " + seconds + "segundos", 
			new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), State.Warn);
		
		return checks;
	}
}
