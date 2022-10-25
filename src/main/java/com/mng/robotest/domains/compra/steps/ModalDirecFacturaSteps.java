package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.pageobjects.DataDireccion;
import com.mng.robotest.domains.compra.pageobjects.ModalDirecFactura;
import com.mng.robotest.domains.compra.pageobjects.Page1DktopCheckout;
import com.mng.robotest.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.domains.transversal.StepBase;

public class ModalDirecFacturaSteps extends StepBase {

	private final ModalDirecFactura modalDirecFactura = new ModalDirecFactura();
	
	@Validation
	public ChecksTM validateIsOk() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de facturación\" (lo esperamos hasta " + seconds + " seconds)",
			modalDirecFactura.isVisibleFormUntil(seconds), State.Defect);	
	 	
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
	 		modalDirecFactura.isVisibleButtonActualizar(), State.Defect);
	 	
	 	seconds = 2;
	 	checks.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + seconds + "segundos", 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), State.Warn);
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
	 	
	 	int seconds = 2;
	 	checks.add(
	 		"Desaparece la capa de Loading (lo esperamos hasta " + seconds + "segundos", 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), State.Warn);
	 	
	 	return checks;
	}
}
