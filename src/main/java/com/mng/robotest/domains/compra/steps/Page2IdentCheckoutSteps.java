package com.mng.robotest.domains.compra.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.pageobjects.Page2IdentCheckout;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.generic.UtilsMangoTest;

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
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página-2 de introducción de datos de la dirección del cliente (la esperamos hasta " + seconds + " segundos)",
			page2IdentCheckout.isPageUntil(seconds), State.Defect);
	 	checks.add(
			"Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña",
			page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists), State.Warn);
	 	return checks;
	}
	
	@Validation (
		description="Figura el email <b>#{email}</b>",
		level=State.Warn)
	public boolean checkEmail(String email) {
		return page2IdentCheckout.checkEmail(email);
	}
	
	@Step (
		description="Introducimos los datos del cliente según el país", 
		expected="Se hace clickable el botón \"Continuar\"",
		saveImagePage=SaveWhen.Always)
	public Map<String, String> inputDataPorDefecto(String emailUsr, boolean inputDireccCharNoLatinos) 
			throws Exception {
		Map<String, String> datosRegistro = 
			page2IdentCheckout.inputDataPorDefectoSegunPais(emailUsr, inputDireccCharNoLatinos, false, channel);
		
		TestMaker.getCurrentStepInExecution().addDescriptionText(". Utilizando los datos: "+ UtilsMangoTest.listaCamposHTML(datosRegistro)); 
		checkIsVisibleContiueButton(5);
		return datosRegistro;
	}
	
	@Validation (
		description="Se hace clickable el botón \"Continuar\" (lo esperamos hasta #{seconds})",
		level=State.Defect)
	private boolean checkIsVisibleContiueButton(int seconds) {
		return (page2IdentCheckout.isContinuarClickableUntil(seconds));
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"",
		expected="Aparece la página de Checkout",
		saveImagePage=SaveWhen.Always)
	public void clickContinuar(boolean userRegistered)
	throws Exception {
		page2IdentCheckout.clickBotonContinuarAndWait(20);   
		new CheckoutSteps().validateIsFirstPage(userRegistered);
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\" (hay carácteres no-latinos introducidos en la dirección)",
		expected="Aparece un aviso indicando que en la dirección no pueden figurar carácteres no-latinos",
		saveImagePage=SaveWhen.Always)
	public void clickContinuarAndExpectAvisoDirecWithNoLatinCharacters() throws Exception {
		page2IdentCheckout.clickBotonContinuarAndWait(2);	  
		checkAvisoDireccionWithNoLatinCharacters();
	}
			
	@Validation (
		description="Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos",
		level=State.Defect)
	private boolean checkAvisoDireccionWithNoLatinCharacters() {
		return (page2IdentCheckout.isDisplayedAvisoAduanas());
	}
}
