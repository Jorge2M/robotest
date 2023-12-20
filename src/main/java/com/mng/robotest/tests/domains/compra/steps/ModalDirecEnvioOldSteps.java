package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;
import com.mng.robotest.tests.domains.compra.pageobjects.modals.ModalDirecEnvioOld;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.POBLACION;

public class ModalDirecEnvioOldSteps extends StepBase {
	
	private final ModalDirecEnvioOld modalDirecEnvio = new ModalDirecEnvioOld();
	
	@Validation
	public ChecksTM validateIsOk() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Es visible el formulario para la introducción de la \"Dirección de envío\" " + SECONDS_WAIT,
			modalDirecEnvio.isVisibleFormUntil(seconds));
	 	
	 	checks.add(
			"Es visible el botón \"Actualizar\"",
			modalDirecEnvio.isVisibleButtonActualizar());
	 	
	 	return checks;
	}

	@Step (
		description="Introducir los datos y pulsar \"Guardar\"<br>#{dataDirEnvio.getFormattedHTMLData()}", 
		expected="Los datos se actualizan correctamente")
	public void inputDataAndActualizar(DataDireccion dataDirEnvio) {
		modalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirEnvio, 3);
		modalDirecEnvio.moveToAndDoubleClickActualizar();
		checkAfterUpdateData(dataDirEnvio);
	}
	
	@Validation
	private ChecksTM checkAfterUpdateData(DataDireccion dataDirEnvio) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Desaparece el modal de introducción de los datos de la dirección " + getLitSecondsWait(seconds),
	 		!modalDirecEnvio.isVisibleFormUntil(0));

		checks.add(
			"Desaparece la capa de Loading " + getLitSecondsWait(seconds), 
			new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), WARN);
		
	 	var poblacion = dataDirEnvio.getValue(POBLACION); 
	 	checks.add(
	 		"En la dirección de envío aparece la nueva población <b>" + poblacion + "</b>", 
	 		modalDirecEnvio.getAddress().contains(poblacion));
		
		return checks;
	}
	
}
