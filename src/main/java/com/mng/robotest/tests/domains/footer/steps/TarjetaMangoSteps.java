package com.mng.robotest.tests.domains.footer.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageMangoCard;
import com.mng.robotest.testslegacy.pageobject.shop.PageInputDataSolMangoCard;

import static com.github.jorge2m.testmaker.conf.State.*;

public class TarjetaMangoSteps extends StepBase {

	private final PageInputDataSolMangoCard pageInputDataSolMangoCard = new PageInputDataSolMangoCard();
	
	public void checkSolicitarTarjeta () {
		selectLoQuieroAhoraButton();
	}
	
	@Step (
		description="Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
		expected="La página hace scroll hasta el formulario previo de solicitud de la tarjeta")
	public void selectLoQuieroAhoraButton() {
		var pageMangoCard = new PageMangoCard();
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
		var pageMangoCard = new PageMangoCard();
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece el campo <b>Nombre</b>",
	 		pageMangoCard.isPresentNameField(), Warn);
		 
		checks.add(
	 		"Aparece el campo <b>Primer Apellido</b>",
	 		pageMangoCard.isPresentFirstSurnameField(), Warn);
		 
		checks.add(
	 		"Aparece el campo <b>Segundo Apellido</b>",
	 		pageMangoCard.isPresentSecondSurnameField(), Warn);
		 
		checks.add(
	 		"Aparece el campo <b>Movil</b>",
	 		pageMangoCard.isPresentMobileField(), Warn);
		 
		checks.add(
	 		"Aparece el campo <b>Mail</b>",
	 		pageMangoCard.isPresentMailField(), Warn);
		 
		checks.add(
	 		"Aparece el botón <b>¡Lo quiero ahora!</b>",
	 		pageMangoCard.isPresentButtonSolMangoCardNow(), Warn);
		 
		return checks;
	}
	 
	 @Step (
		description="Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
		expected="Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"")
	 public void selectLoQuieroAhoraUnderForm() {
		 new PageMangoCard().clickToGoSecondMangoCardPage();
		 waitMillis(1000);
		 checkAfterClickLoQuieroAhoraUnderForm();
	 }
	 
	 @Validation
	 private ChecksTM checkAfterClickLoQuieroAhoraUnderForm() {
  		var checks = ChecksTM.getNew();
		String ventanaPadre = driver.getWindowHandle();
		PageObjTM.switchToAnotherWindow(driver, ventanaPadre);	
		waitMillis(1000); //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
		PageObjTM.waitForPageLoaded(driver, 10);
	 	checks.add(
	 		"Aparece una nueva ventana",
	 		true, Warn);	
	 	
		int seconds = 3;
	 	checks.add(
	 		"Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" " + getLitSecondsWait(seconds),
	 		new PageInputDataSolMangoCard().isPresentBotonContinuarModalUntil(seconds), Warn);
	 	
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
		var checks = ChecksTM.getNew();
	 	checks.add(
	 		"Aparece la página de Solicitud de tu Tarjeta MANGO",
	 		pageInputDataSolMangoCard.isPage2());
	 	
	 	try {
			//Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
			pageInputDataSolMangoCard.gotoiFramePage2();
			checks.add(
		 		"Aparece el apartado \"Datos personales\"",
		 		pageInputDataSolMangoCard.isPresentDatosPersonalesPage2(), Warn);
		 	
			checks.add(
		 		"Aparece el apartado \"Datos bancarios\"",
		 		pageInputDataSolMangoCard.isPresentDatosBancariosPage2());
		 	
			checks.add(
		 		"Aparece el apartado \"Datos de contacto\"",
		 		pageInputDataSolMangoCard.isPresentDatosContactoPage2(), Warn);
		 	
			checks.add(
		 		"Aparece el apartado \"Datos socioeconómicos\"",
		 		pageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(), Warn);
		 	
			checks.add(
		 		"Aparece el apartado \"Modalidad de pago de tu MANGO Card\"",
		 		pageInputDataSolMangoCard.isPresentModalidadpagoPage2(), Warn);
		 	
			checks.add(
		 		"Aparece el botón \"Continuar\"",
		 		pageInputDataSolMangoCard.isPresentButtonContinuarPage2(), Warn);
	 	}
		finally {
			driver.close();
			driver.switchTo().window(ventanaOriginal);
		}   
		 
	 	return checks;
	}
}
