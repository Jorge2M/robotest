package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;
import com.mng.robotest.tests.domains.compra.pageobjects.desktop.Page1DktopCheckout;
import com.mng.robotest.tests.domains.compra.pageobjects.modals.ModalDirecFactura;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.*;

public class ModalDirecFacturaSteps extends StepBase {

	private final ModalDirecFactura modalDirecFactura = new ModalDirecFactura();
	
	@Validation
	public ChecksTM validateIsOk() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de facturación\" " + getLitSecondsWait(seconds),
			modalDirecFactura.isVisibleFormUntil(seconds));	
	 	
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
	 		modalDirecFactura.isVisibleButtonActualizar());
	 	
	 	seconds = 2;
	 	checks.add(
	 		"Desaparece la capa de Loading " + getLitSecondsWait(seconds), 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), WARN);
		return checks;
	}
	
	@Step (
		description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
		expected="Los datos se actualizan correctamente")
	public void inputDataAndActualizar(DataDireccion dataDirFactura) {
		modalDirecFactura.sendDataToInputs(dataDirFactura);
		modalDirecFactura.clickActualizar();
		checkAfterChangeDireccion(dataDirFactura);
	}
	
	@Validation
	private ChecksTM checkAfterChangeDireccion(DataDireccion dataDirFactura) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Desaparece el modal de introducción de los datos de la dirección " + getLitSecondsWait(seconds),
	 		!modalDirecFactura.isVisibleFormUntil(0));
	 	
	 	checks.add(
			"Es visible el link \"Descartar factura\"",
			new Page1DktopCheckout().isVisibleDescartarFacturaLink());
	 	
	 	checks.add(
	 		"Desaparece la capa de Loading " + getLitSecondsWait(seconds), 
	 		new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), WARN);
	 	
	 	var poblacion = dataDirFactura.getValue(POBLACION); 
	 	checks.add(
	 		"En la dirección de facturación aparece la nueva población <b>" + poblacion + "</b>", 
	 		modalDirecFactura.getAddress().contains(poblacion));
	 	
	 	return checks;
	}
}
