package com.mng.robotest.tests.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroAddressDataOutlet;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroDirecOutlet;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageRegistroDirecStepsOutlet extends StepBase {
	
	private final PageRegistroAddressDataOutlet pgRegistroAddressData = new PageRegistroAddressDataOutlet();
	private final PageRegistroDirecOutlet pgRegistroDirec = new PageRegistroDirecOutlet();
	
	@Validation
	public ChecksTM isPageFromPais() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparece la página de introducción de datos de la dirección " + getLitSecondsWait(seconds),
			pgRegistroAddressData.isPage(seconds), WARN);
		
		checks.add(
			"Si existe el desplebagle de países, en él aparece el país con código " + dataTest.getCodigoPais() + " (" + dataTest.getPais().getNombrePais() + ")",
			!pgRegistroAddressData.existsDesplegablePaises() || 
			pgRegistroAddressData.isOptionPaisSelected(dataTest.getCodigoPais()), WARN);
		
		return checks;
	}
	
	@Step (
		description="Introducir los datos correctos para el país}", 
		expected="No aparece ningún mensaje de error")
	public void sendDataAccordingCountryToInputs(Map<String,String> dataRegistro) {
		pgRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro);
		validateInputDataOk();
	}

	@Validation(description="No aparece ningún mensaje de error asociado a los campos de entrada")
	public boolean validateInputDataOk() {
		return (pgRegistroDirec.getNumberMsgInputInvalid() <= 0);
	}
	
	@Step (
		description= "Seleccionar el botón \"<b>Finalizar</b>\"", 
		expected="Aparece la página final del proceso de registro",
		saveHtmlPage=IF_PROBLEM)
	public void clickFinalizarButton() {
		pgRegistroDirec.clickFinalizarButton();
		new PageRegistroFinStepsOutlet().isPage(5);
	}
}
