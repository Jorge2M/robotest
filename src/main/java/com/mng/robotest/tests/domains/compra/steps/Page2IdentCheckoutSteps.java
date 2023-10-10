package com.mng.robotest.tests.domains.compra.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.conf.factories.entities.EgyptCity;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.Page2IdentCheckout;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class Page2IdentCheckoutSteps extends StepBase {
	
	private final Page2IdentCheckout page2IdentCheckout;
	
	public Page2IdentCheckoutSteps() {
		this.page2IdentCheckout = new Page2IdentCheckout();
	}
	
	public Page2IdentCheckoutSteps(EgyptCity egyptCity) {
		this.page2IdentCheckout = new Page2IdentCheckout(egyptCity);
	}
	
	@Validation
	public ChecksTM validateIsPage(boolean emailYetExists, int seconds) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página-2 de introducción de datos de la dirección del cliente " + getLitSecondsWait(seconds),
			page2IdentCheckout.isPageUntil(seconds));
	 	checks.add(
			"Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña",
			page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists), Warn);
	 	return checks;
	}
	
	@Validation (
		description="Figura el email <b>#{email}</b>",
		level=Warn)
	public boolean checkEmail(String email) {
		return page2IdentCheckout.checkEmail(email);
	}
	
	@Step (
		description="Introducimos los datos del cliente según el país", 
		expected="Se hace clickable el botón \"Continuar\"",
		saveImagePage=SaveWhen.Always)
	public Map<String, String> inputDataPorDefecto(String emailUsr, boolean inputDireccCharNoLatinos) {
		var datosRegistro = 
			page2IdentCheckout.inputDataPorDefectoSegunPais(emailUsr, inputDireccCharNoLatinos, false, channel);
		
		setStepDescription(getStepDescription() + ". Utilizando los datos: "+ UtilsMangoTest.listaCamposHTML(datosRegistro)); 
		checkIsVisibleContiueButton(5);
		return datosRegistro;
	}
	
	@Validation (
		description="Se hace clickable el botón \"Continuar\" " + SECONDS_WAIT)
	private boolean checkIsVisibleContiueButton(int seconds) {
		return (page2IdentCheckout.isContinuarClickableUntil(seconds));
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"",
		expected="Aparece la página de Checkout",
		saveImagePage=SaveWhen.Always)
	public void clickContinuar(boolean userRegistered) {
		page2IdentCheckout.clickBotonContinuarAndWait(20);   
		new CheckoutSteps().validateIsFirstPage(userRegistered);
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\" (hay carácteres no-latinos introducidos en la dirección)",
		expected="Aparece un aviso indicando que en la dirección no pueden figurar carácteres no-latinos",
		saveImagePage=SaveWhen.Always)
	public void clickContinuarAndExpectAvisoDirecWithNoLatinCharacters() {
		page2IdentCheckout.clickBotonContinuarAndWait(2);	  
		checkAvisoDireccionWithNoLatinCharacters();
	}
			
	@Validation (
		description="Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos")
	private boolean checkAvisoDireccionWithNoLatinCharacters() {
		return (page2IdentCheckout.isDisplayedAvisoAduanas());
	}

	@Step (
		description="Seleccionar el link <b>Política de privacidad</b>",
		expected="Se despliega la política de privacidad")	
	public void clickPoliticaPrivacidad() {
		page2IdentCheckout.clickPoliticaPrivacidad();
	}
}