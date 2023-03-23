package com.mng.robotest.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroAddressDataOutlet;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroDirecOutlet;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroDirecStepsOutlet extends StepBase {
	
	private final PageRegistroAddressDataOutlet pageRegistroAddressData = new PageRegistroAddressDataOutlet();
	private final PageRegistroDirecOutlet pageRegistroDirec = new PageRegistroDirecOutlet();
	
	@Validation
	public ChecksTM isPageFromPais() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"Aparece la página de introducción de datos de la dirección (la esperamos un máximo de " + seconds + " segundos)",
			pageRegistroAddressData.isPageUntil(seconds), Warn);
		
		checks.add(
			"Si existe el desplebagle de países, en él aparece el país con código " + dataTest.getCodigoPais() + " (" + dataTest.getPais().getNombre_pais() + ")",
			!pageRegistroAddressData.existsDesplegablePaises() || 
			pageRegistroAddressData.isOptionPaisSelected(dataTest.getCodigoPais()), Warn);
		
		return checks;
	}
	
	@Step (
		description="Introducir los datos correctos para el país}", 
		expected="No aparece ningún mensaje de error")
	public void sendDataAccordingCountryToInputs(Map<String,String> dataRegistro) {
		pageRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro);
		validateInputDataOk();
	}

	@Validation(
		description="No aparece ningún mensaje de error asociado a los campos de entrada",
		level=Defect)
	public boolean validateInputDataOk() {
		return (pageRegistroDirec.getNumberMsgInputInvalid() <= 0);
	}
	
	@Step (
		description= "Seleccionar el botón \"<b>Finalizar</b>\"", 
		expected="Aparece la página final del proceso de registro",
		saveHtmlPage=SaveWhen.IfProblem)
	public void clickFinalizarButton() {
		pageRegistroDirec.clickFinalizarButton();
		new PageRegistroFinStepsOutlet().isPageUntil(5);
	}
}
