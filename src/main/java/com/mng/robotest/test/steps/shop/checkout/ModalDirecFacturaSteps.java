package com.mng.robotest.test.steps.shop.checkout;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test.pageobject.shop.checkout.ModalDirecFactura;
import com.mng.robotest.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalDirecFacturaSteps extends StepBase {

	private final ModalDirecFactura modalDirecFactura = new ModalDirecFactura();
	
	@Validation
	public ChecksTM validateIsOk() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de facturación\" (lo esperamos hasta " + maxSeconds + " seconds)",
			modalDirecFactura.isVisibleFormUntil(maxSeconds), State.Defect);	
	 	
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
	 		modalDirecFactura.isVisibleButtonActualizar(), State.Defect);
	 	
	 	maxSeconds = 2;
	 	checks.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(maxSeconds), State.Warn);
		return checks;
	}
	
	@Step (
		description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
		expected="Los datos se actualizan correctamente")
	public void inputDataAndActualizar(DataDireccion dataDirFactura) throws Exception {
		modalDirecFactura.sendDataToInputs(dataDirFactura);
		modalDirecFactura.clickActualizar();
		checkAfterChangeDireccion();
	}
	
	@Validation
	private ChecksTM checkAfterChangeDireccion() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Desaparece el modal de introducción de los datos de la dirección",
	 		!modalDirecFactura.isVisibleFormUntil(0), State.Defect);
	 	
	 	checks.add(
			"Queda marcado el radiobutton \"Quiero recibir una factura\"",
			new Page1DktopCheckout().isMarkedQuieroFactura(), State.Defect);
	 	
	 	int maxSeconds = 2;
	 	checks.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + maxSeconds + "segundos", 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(maxSeconds), State.Warn);
	 	
	 	return checks;
	}
}