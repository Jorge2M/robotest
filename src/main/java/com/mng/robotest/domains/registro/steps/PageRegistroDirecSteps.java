package com.mng.robotest.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroAddressData;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroDirec;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;


public class PageRegistroDirecSteps {
	
	private final PageRegistroAddressData pageRegistroAddressData = new PageRegistroAddressData();
	private final PageRegistroDirec pageRegistroDirec = new PageRegistroDirec();
	
	@Validation
	public ChecksTM isPageFromPais(Pais pais) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
		checks.add(
			"Aparece la página de introducción de datos de la dirección (la esperamos un máximo de " + maxSeconds + " segundos)",
			pageRegistroAddressData.isPageUntil(maxSeconds), State.Warn);
		checks.add(
			"Si existe el desplebagle de países, en él aparece el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
			!pageRegistroAddressData.existsDesplegablePaises() || 
			pageRegistroAddressData.isOptionPaisSelected(pais.getCodigo_pais()), State.Warn);
		return checks;
	}
	
	@Step (
		description="Introducir los datos correctos para el país #{pais.getNombre_pais()}", 
		expected="No aparece ningún mensaje de error")
	public void sendDataAccordingCountryToInputs(Map<String,String> dataRegistro, Pais pais, Channel channel) 
			throws Exception {
		pageRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro, pais, channel);
		validateInputDataOk();
	}

	@Validation(
		description="No aparece ningún mensaje de error asociado a los campos de entrada",
		level=State.Defect)
	public boolean validateInputDataOk() {
		return (pageRegistroDirec.getNumberMsgInputInvalid() <= 0);
	}
	
	@Step (
		description= "Seleccionar el botón \"<b>Finalizar</b>\"", 
		expected="Aparece la página final del proceso de registro")
	public void clickFinalizarButton(DataCtxShop dataTest) {
		pageRegistroDirec.clickFinalizarButton();
		new PageRegistroFinSteps(dataTest, pageRegistroDirec.driver).isPageUntil(5);
	}
}
