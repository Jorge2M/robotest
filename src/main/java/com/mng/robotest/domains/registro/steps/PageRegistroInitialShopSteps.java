package com.mng.robotest.domains.registro.steps;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroInitialShop;

public class PageRegistroInitialShopSteps extends StepBase {

	private final PageRegistroInitialShop pageRegistroInitial = new PageRegistroInitialShop();
	
	public void checkPage(int seconds) {
		checkIsPage(seconds);
		checkRadioPubli();
	}
	
	@Validation (
		description="Aparece la página inicial del proceso de registro (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsPage(int seconds) {
		return pageRegistroInitial.isPage(seconds);
	}

	@Validation
	private ChecksTM checkRadioPubli() {
		var checks = ChecksTM.getNew();
		boolean isSelectedPubli = pageRegistroInitial.isSelectedCheckboxGivePromotions();
		if (dataTest.getPais().getRegister().ispubli()) {
		  	checks.add(
				"Sí está seleccionado el radiobox de publicidad",
				isSelectedPubli);
		} else {
		  	checks.add(
				"No está seleccionado el radiobox de publicidad",
				!isSelectedPubli);
		}
		return checks;
	}		
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Email: <b>#{data.getEmail()}</b><br>" +
			"  - Contraseña: <b>*****</br>" + 
			"  - Mobil: <b>#{data.getMovil()}</b><br>" +
			"  - Check promociones: <b>#{data.isCheckPromotions()}</b>",
		expected=
			"La introducción de datos es correcta")
	public void inputData(DataNewRegister data) {
		pageRegistroInitial.inputMovil(data.getMovil());
		pageRegistroInitial.inputPassword(data.getPassword());
		pageRegistroInitial.inputEmail(data.getEmail());
		if (data.isCheckPromotions()) {
			pageRegistroInitial.enableCheckBoxGivePromotions();
		} else {
			pageRegistroInitial.disableCheckBoxGivePromotions();
		}
	}
	
	@Step (
		description="Pulsar el botón <b>Crear cuenta</b>",
		expected="Aparece la página de personalización del registro")
	public void clickCreateAccountButton() {
		pageRegistroInitial.clickCreateAccountButton();
		new PageRegistroPersonalizacionShopSteps().checkPage(10);
		checksDefault();
	}	

	@Step(
		description="Introducir la fecha de nacimiento <b>#{birthdate}</b>",
		expected="El dato se introduce correctamente")	
	public void inputBirthDate(String birthdate) {
		pageRegistroInitial.inputBirthDate(birthdate);
	}
	
	@Step(
		description="Seleccionar el link <b>Collection and use of optional personal information</b>",
		expected="Aparece el apartado de collection and use")	
	public void clickLinkGivePromotions() {
		pageRegistroInitial.clickLinkGivePromotions();
	}	
		
	@Step(
		description="Seleccionar el link <b>Collection and use</b>",
		expected="Aparece el apartado de collection and use")	
	public void clickConsentPersonalInformationLink() {
		pageRegistroInitial.clickConsentPersonalInformationLink();
		checkPersonalInformationInfoVisible();
	}	
	@Validation(
	    description="Aparece el apartado de \"Collection and use of your personal information\"")	
	public boolean checkPersonalInformationInfoVisible() {
		return pageRegistroInitial.checkPersonalInformationInfoVisible();
	}
	
	@Step(
		description="Seleccionar el checkbox <b>Collection and use</b>",
		expected="Se selecciona el checkbox")	
	public void clickConsentPersonalInformationRadio() {
		pageRegistroInitial.clickConsentPersonalInformationRadio();
	}

	@Step (
		description="Pulsar el link <b>Política de privacidad</b>",
		expected="Aparece el modal de \"Cómo protegemos y tratamos tus datos?\"")	
	public void clickPoliticaPrivacidad() {
		pageRegistroInitial.clickPoliticaPrivacidad();
		//if (dataTest.getPais().loyalty()) {
			pageRegistroInitial.keyDown(5);
			if (!pageRegistroInitial.isModalPoliticaPrivacidadVisible(1)) {
				pageRegistroInitial.clickPoliticaPrivacidad();
			}
			pageRegistroInitial.keyDown(5);
			checkIsModalPoliticaPrivacidadVisible(1);
		//}
	}
	@Validation (description="Aparece el modal de \"Cómo protegemos y tratamos tus datos?\" (lo esperamos hasta #{seconds} segundos)")
	public boolean checkIsModalPoliticaPrivacidadVisible(int seconds) {
		return pageRegistroInitial.isModalPoliticaPrivacidadVisible(seconds);
	}	
	
	public void clickPoliticaPrivacidadModal() {
		Pair<String, String> pair = clickPoliticaPrivacidadModalStep();
		switchToParent(pair.getLeft(), pair.getRight());
	}
	
	public void clickCondicionesVenta() {
		Pair<String, String> pair = clickCondicionesVentaStep();
		switchToParent(pair.getLeft(), pair.getRight());
	}
	
	@Step (
		description="Pulsar el link <b>Política de privacidad</b> del modal",
		expected="Aparece una nueva página con la política de privacidad y cookies")
	private synchronized Pair<String, String> clickPoliticaPrivacidadModalStep() {
		String parentWindow = driver.getWindowHandle();
		pageRegistroInitial.clickPoliticaPrivacidadModal();
		String childWindow = switchToAnotherWindow(driver, parentWindow);
		new PagePoliticaPrivacidadSteps().checkIsPageUntil(4);
		return Pair.of(parentWindow, childWindow);
	}
	
	@Step (
		description="Pulsar el link <b>Condiciones de venta</b> del modal",
		expected="Aparece una nueva página con la política de privacidad y cookies")
	private Pair<String, String> clickCondicionesVentaStep() {
		String parentWindow = driver.getWindowHandle();
		pageRegistroInitial.clickCondicionesVenta();
		String childWindow = switchToAnotherWindow(driver, parentWindow);
		new PageCondicionesVentaSteps().checkIsPageUntil(4);
		return Pair.of(parentWindow, childWindow);
	}	
	
	private void switchToParent(String parentWindow, String childWindow) {
		if (childWindow.compareTo(parentWindow)!=0) {
			driver.switchTo().window(childWindow);
			driver.close();
			driver.switchTo().window(parentWindow);
		}
	}
	
	@Validation (description="Desaparece el modal de \"Cómo protegemos y tratamos tus datos?\"")
	public boolean checkIsModalPoliticaPrivacidadInvisible() {
		return pageRegistroInitial.isModalPoliticaPrivacidadInvisible(2);
	}
	
}
