package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;

import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class PageRegistroPersonalizacionShopSteps extends StepBase {

	private final PageRegistroPersonalizacionShop pgRegistroPersonalizacion = new PageRegistroPersonalizacionShop();
	
	public void checkPage(int seconds) {
		checkIsPage(seconds);
		checkPostalCode();
	}
	
	@Validation (
		description="Aparece la página de personalización del registro " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgRegistroPersonalizacion.isPage(seconds);
	}
	
	@Validation
	public ChecksTM checkPostalCode() {
		var checks = ChecksTM.getNew();
		boolean isVisibleCodPostal = pgRegistroPersonalizacion.isPostalCodeVisible();
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
		if (!isCountry(COREA_DEL_SUR)) {
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
			"La introducción de datos es correcta",
		saveHtmlPage=ALWAYS
		)
	private void inputDataWithouBirthDate(DataNewRegister data) {
		pgRegistroPersonalizacion.inputPostalCode(data.getPostalCode());
		pgRegistroPersonalizacion.inputName(data.getName());
		pgRegistroPersonalizacion.selectGender(data.getGender());
		if (data.isCheckPromotions()) {
			pgRegistroPersonalizacion.selectLineas(data.getLineas());
		}
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
		pgRegistroPersonalizacion.inputName(data.getName());
		pgRegistroPersonalizacion.selectGender(data.getGender());
		if (data.isCheckPromotions()) {
			pgRegistroPersonalizacion.selectLineas(data.getLineas());
		}
	}	
	
	@Step (
		description="Introducir la Fecha de nacimiento: <b>#{birthDate}</b><br>",
		expected="La introducción de datos es correcta")
	private void inputBirthDate(String birthDate) {
		pgRegistroPersonalizacion.inputDateOfBirth(birthDate);
	}	
	
	@Step (
		description="Pulsar el botón <b>Guardar</b>",
		expected="Accedemos a la shop como conectados",
		saveImagePage=ALWAYS)
	public void clickGuardar() {
		pgRegistroPersonalizacion.clickGuardar();
		checkWeAreLogged();
	}		
	
	@Step (
		description="Pulsar el botón <b>Guardar</b>",
		expected="",
		saveImagePage=ALWAYS)
	public void clickGuardarNoChecks() {
		pgRegistroPersonalizacion.clickGuardar();
	}		
	
	
	public void checkWeAreLogged() {
		new AccesoSteps().checkIsLogged();
	}	
	
	@Validation(
	    description="Aparece un error de código postal incorrecto " + SECONDS_WAIT)
	public boolean checkCodPostalInvalidMessage(int seconds) {
		return pgRegistroPersonalizacion.checkMessageErrorMovil(seconds);
	}
	
}
