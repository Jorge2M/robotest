package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.checkout.Page2IdentCheckout;


public class Page2IdentCheckoutSteps {
	
	private final Page2IdentCheckout page2IdentCheckout;
	private final Channel channel;
	private final WebDriver driver;
	
	public Page2IdentCheckoutSteps(Channel channel, Pais pais, WebDriver driver) {
		this.page2IdentCheckout = new Page2IdentCheckout(pais, driver);
		this.channel = channel;
		this.driver = driver;
	}
	
	public Page2IdentCheckoutSteps(Channel channel, Pais pais, EgyptCity egyptCity, WebDriver driver) {
		this.page2IdentCheckout = new Page2IdentCheckout(pais, egyptCity, driver);
		this.channel = channel;
		this.driver = driver;
	}
	
	@Validation
	public ChecksTM validateIsPage(boolean emailYetExists, int maxSeconds) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página-2 de introducción de datos de la dirección del cliente (la esperamos hasta " + maxSeconds + " segundos)",
			page2IdentCheckout.isPageUntil(maxSeconds), State.Defect);
	 	validations.add(
			"Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña",
			page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists), State.Warn);
	 	return validations;
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
		description="Se hace clickable el botón \"Continuar\" (lo esperamos hasta #{maxSeconds})",
		level=State.Defect)
	private boolean checkIsVisibleContiueButton(int maxSeconds) {
		return (page2IdentCheckout.isContinuarClickableUntil(maxSeconds));
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\"",
		expected="Aparece la página de Checkout",
		saveImagePage=SaveWhen.Always)
	public void clickContinuar(boolean userRegistered, AppEcom app, DataBag dataBag)
	throws Exception {
		int maxSecondsToWait = 20;
		page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait);   
		new PageCheckoutWrapperSteps(channel, app, driver).validateIsFirstPage(userRegistered, dataBag);
	}
	
	@Step (
		description="Seleccionamos el botón \"Continuar\" (hay carácteres no-latinos introducidos en la dirección)",
		expected="Aparece un aviso indicando que en la dirección no pueden figurar carácteres no-latinos",
		saveImagePage=SaveWhen.Always)
	public void clickContinuarAndExpectAvisoDirecWithNoLatinCharacters() throws Exception {
		int maxSecondsToWait = 2;
		page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait);	  
		checkAvisoDireccionWithNoLatinCharacters();
	}
			
	@Validation (
		description="Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos",
		level=State.Defect)
	private boolean checkAvisoDireccionWithNoLatinCharacters() {
		return (page2IdentCheckout.isDisplayedAvisoAduanas());
	}
}
