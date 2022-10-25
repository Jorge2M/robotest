package com.mng.robotest.domains.footer.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.domains.footer.pageobjects.PageMangoCard;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageInputDataSolMangoCard;

public class TarjetaMangoSteps extends StepBase {

	private final PageInputDataSolMangoCard pageInputDataSolMangoCard = new PageInputDataSolMangoCard();
	
	public void checkSolicitarTarjeta () {
		selectLoQuieroAhoraButton();
	}
	 
	 @Step (
		description="Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
		expected="La página hace scroll hasta el formulario previo de solicitud de la tarjeta")
	 public void selectLoQuieroAhoraButton() {
		 PageMangoCard pageMangoCard = new PageMangoCard();
		 String ventanaOriginal = driver.getWindowHandle();
		 pageMangoCard.clickOnWantMangoCardNow();
		 if(!driver.getCurrentUrl().contains("shop-ci")) {
			 checkAfterClickLoQuieroAhoraButton();
			 selectLoQuieroAhoraUnderForm();
			 selectContinueButton(ventanaOriginal);
		 }
	 }
	 
	 @Validation
	 private ChecksTM checkAfterClickLoQuieroAhoraButton() {
		 PageMangoCard pageMangoCard = new PageMangoCard();
		 ChecksTM checks = ChecksTM.getNew();
		 checks.add(
			"Aparece el campo <b>Nombre</b>",
	 		pageMangoCard.isPresentNameField(), State.Warn);
		 
		 checks.add(
	 		"Aparece el campo <b>Primer Apellido</b>",
	 		pageMangoCard.isPresentFirstSurnameField(), State.Warn);
		 
		 checks.add(
	 		"Aparece el campo <b>Segundo Apellido</b>",
	 		pageMangoCard.isPresentSecondSurnameField(), State.Warn);
		 
		 checks.add(
	 		"Aparece el campo <b>Movil</b>",
	 		pageMangoCard.isPresentMobileField(), State.Warn);
		 
		 checks.add(
	 		"Aparece el campo <b>Mail</b>",
	 		pageMangoCard.isPresentMailField(), State.Warn);
		 
		 checks.add(
	 		"Aparece el botón <b>¡Lo quiero ahora!</b>",
	 		pageMangoCard.isPresentButtonSolMangoCardNow(), State.Warn);
		 
	 	return checks;
	 }
	 
	 @Step (
		description="Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
		expected="Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"")
	 public void selectLoQuieroAhoraUnderForm() {
		 PageMangoCard pageMangoCard = new PageMangoCard();
		 pageMangoCard.clickToGoSecondMangoCardPage();
		 waitMillis(1000);
		 checkAfterClickLoQuieroAhoraUnderForm();
	 }
	 
	 @Validation
	 private ChecksTM checkAfterClickLoQuieroAhoraUnderForm() {
  		ChecksTM checks = ChecksTM.getNew();
		String ventanaPadre = driver.getWindowHandle();
		PageObjTM.switchToAnotherWindow(driver, ventanaPadre);	
		waitMillis(1000); //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
		PageObjTM.waitForPageLoaded(driver, 10);
	 	checks.add(
	 		"Aparece una nueva ventana",
	 		true, State.Warn);	
	 	
		int seconds = 3;
	 	checks.add(
	 		"Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + seconds + " segundos)",
	 		new PageInputDataSolMangoCard().isPresentBotonContinuarModalUntil(seconds), State.Warn);
	 	
	 	return checks;
	 }
	 
	 @Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página del formulario de solicitud de la tarjeta")
	 private void selectContinueButton(String ventanaOriginal) {
		 pageInputDataSolMangoCard.clickBotonCerrarModal();
		 checkValidPageTarjetaMango(ventanaOriginal);
	 }
	 
	 @Validation
	 private ChecksTM checkValidPageTarjetaMango(String ventanaOriginal) {
  		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"Aparece la página de Solicitud de tu Tarjeta MANGO",
	 		pageInputDataSolMangoCard.isPage2(), State.Defect);
	 	
	 	try {
			//Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
			pageInputDataSolMangoCard.gotoiFramePage2();
		 	checks.add(
		 		"Aparece el apartado \"Datos personales\"",
		 		pageInputDataSolMangoCard.isPresentDatosPersonalesPage2(), State.Warn);
		 	
		 	checks.add(
		 		"Aparece el apartado \"Datos bancarios\"",
		 		pageInputDataSolMangoCard.isPresentDatosBancariosPage2(), State.Defect);
		 	
		 	checks.add(
		 		"Aparece el apartado \"Datos de contacto\"",
		 		pageInputDataSolMangoCard.isPresentDatosContactoPage2(), State.Warn);
		 	
		 	checks.add(
		 		"Aparece el apartado \"Datos socioeconómicos\"",
		 		pageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(), State.Warn);
		 	
		 	checks.add(
		 		"Aparece el apartado \"Modalidad de pago de tu MANGO Card\"",
		 		pageInputDataSolMangoCard.isPresentModalidadpagoPage2(), State.Warn);
		 	
		 	checks.add(
		 		"Aparece el botón \"Continuar\"",
		 		pageInputDataSolMangoCard.isPresentButtonContinuarPage2(), State.Warn);
	 	}
		finally {
			driver.close();
			driver.switchTo().window(ventanaOriginal);
		}   
		 
	  	return checks;
	 }
}
