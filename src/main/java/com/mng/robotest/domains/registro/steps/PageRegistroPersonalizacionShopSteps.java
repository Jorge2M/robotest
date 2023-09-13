package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.test.data.PaisShop;

public class PageRegistroPersonalizacionShopSteps extends StepBase {

	private final PageRegistroPersonalizacionShop pageRegistroPersonalizacion = new PageRegistroPersonalizacionShop();
	
	public void checkPage(int seconds) {
		checkIsPage(seconds);
		checkPostalCode();
	}
	
	@Validation (
		description="Aparece la página de personalización del registro (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsPage(int seconds) {
		return pageRegistroPersonalizacion.isPageUntil(seconds);
	}
	
	@Validation
	public ChecksTM checkPostalCode() {
		var checks = ChecksTM.getNew();
		boolean isVisibleCodPostal = pageRegistroPersonalizacion.isPostalCodeVisible();
		if (dataTest.getPais().getRegister().isCpost()) {
		  	checks.add(
				"Sí es visible el input del código postal",
				isVisibleCodPostal);
		} else {
		  	checks.add(
				"No es visible el input del código postal",
				!isVisibleCodPostal);
		}
		return checks;
	}
	
	public void inputData(DataNewRegister data) {
		var pais = dataTest.getPais();
		if (pais.getRegister().isCpost()) {
			inputDataWithouBirthDate(data);
		}
		if (PaisShop.getPais(pais)!=PaisShop.COREA_DEL_SUR) {
			inputBirthDate(data.getDateOfBirth());
		}
	}
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Nombre: <b>#{data.getName()}</b><br>" +
			"  - Código postal: <b>#{data.getPostalCode()}</br>" + 
			"  - Género: <b>#{data.getGender()}</b><br>" +
			"  - Líneas: <b>#{data.getLineas()}</b>",
		expected=
			"La introducción de datos es correcta")
	private void inputDataWithouBirthDate(DataNewRegister data) {
		pageRegistroPersonalizacion.inputPostalCode(data.getPostalCode());
		pageRegistroPersonalizacion.inputName(data.getName());
		pageRegistroPersonalizacion.selectGender(data.getGender());
		pageRegistroPersonalizacion.selectLineas(data.getLineas());
	}	
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Nombre: <b>#{data.getName()}</b><br>" +
			"  - Género: <b>#{data.getGender()}</b><br>" +
			"  - Líneas: <b>#{data.getLineas()}</b>",
		expected=
			"La introducción de datos es correcta")
	private void inputDataWithouBirthDateAndPostalCode(DataNewRegister data) {
		pageRegistroPersonalizacion.inputName(data.getName());
		pageRegistroPersonalizacion.selectGender(data.getGender());
		pageRegistroPersonalizacion.selectLineas(data.getLineas());
	}	
	
	@Step (
		description="Introducir la Fecha de nacimiento: <b>#{birthDate}</b><br>",
		expected="La introducción de datos es correcta")
	private void inputBirthDate(String birthDate) {
		pageRegistroPersonalizacion.inputDateOfBirth(birthDate);
	}	
	
	@Step (
		description="Pulsar el botón <b>Guardar</b>",
		expected="Accedemos a la shop como conectados",
		saveImagePage=SaveWhen.Always)
	public void clickGuardar() {
		pageRegistroPersonalizacion.clickGuardar();
		checkWeAreLogged();
	}		
	
	public void checkWeAreLogged() {
		new AccesoSteps().checkIsLogged(7);
	}	
	
}
