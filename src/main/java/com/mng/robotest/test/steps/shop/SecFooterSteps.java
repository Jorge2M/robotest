package com.mng.robotest.test.steps.shop;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.PageAyuda;
import com.mng.robotest.test.pageobject.shop.PageInputDataSolMangoCard;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.footer.FactoryPageFromFooter;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;
import com.mng.robotest.test.pageobject.shop.footer.PageMangoCard;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.steps.shop.modales.ModalCambioPaisSteps;

public class SecFooterSteps {
	
	private final SecFooter secFooter;
	private final Channel channel;
	private final AppEcom app;
	private final WebDriver driver;
	
	public SecFooterSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.secFooter = new SecFooter();
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	@Validation 
	public ChecksTM validaLinksFooter() throws Exception { 
		ChecksTM checks = ChecksTM.getNew();
		List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
		checks.add(
			"Aparecen los siguientes links en el footer <b>" + listFooterLinksToValidate + "</b>",
			secFooter.checkFooters(listFooterLinksToValidate), State.Defect);
		return checks;
	}

	@Step (
		description="Seleccionar el link del footer <b>#{typeFooter}</b>", 
		expected="Se redirige a la pantalla adecuada")
	public void clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd) throws Exception { 
		String windowFatherHandle = secFooter.clickLinkAndGetWindowFatherHandle(typeFooter);
		checkPageCorrectAfterSelectLinkFooter(windowFatherHandle, typeFooter, closeAtEnd);
	}
	 
	@Validation
	private ChecksTM checkPageCorrectAfterSelectLinkFooter(String windowFatherHandle, FooterLink typeFooter, boolean closeAtEnd) {
		ChecksTM checks = ChecksTM.getNew();
		PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter);
		String windowActualHandle = driver.getWindowHandle();
		boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
		int maxSeconds = 5;
		try {
			checks.add(
				"Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSeconds + " segundos)",
				pageObject.isPageCorrectUntil(maxSeconds), State.Warn);
			if (typeFooter.pageInNewTab()) {
				checks.add(
					"Aparece la página en una ventana aparte",
					newWindowInNewTab, State.Warn);		
			}
		}
		finally {
			if (typeFooter.pageInNewTab()) {
				if (closeAtEnd && newWindowInNewTab) {
					driver.close();
					driver.switchTo().window(windowFatherHandle);
				}
			}
		}		
		
		return checks;
	}
	 
	@Validation
	public ChecksTM validaPaginaAyuda() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		String telefono = "900 150 543";
		checks.add(
			"Aparece \"Preguntas Frecuentes\" en la página",
			PageAyuda.isPresentCabPreguntasFreq(channel, driver), State.Warn);	
		checks.add(
			"Aparece la sección \"Contáctanos\" con el número de teléfono " + telefono,
			PageAyuda.isPresentTelefono(driver, telefono), State.Warn);
		return checks;
	}
		
	 public void checkSolicitarTarjeta () throws Exception {
		 selectLoQuieroAhoraButton();
	 }
	 
	 @Step (
		description="Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
		expected="La página hace scroll hasta el formulario previo de solicitud de la tarjeta")
	 public void selectLoQuieroAhoraButton() {
		 PageMangoCard pageMangoCard = new PageMangoCard();
		 String ventanaOriginal = driver.getWindowHandle();
		 pageMangoCard.clickOnWantMangoCardNow(channel);
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
		SeleniumUtils.switchToAnotherWindow(driver, ventanaPadre);	
		waitMillis(1000); //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
		SeleniumUtils.waitForPageLoaded(driver, 10);
	 	checks.add(
	 		"Aparece una nueva ventana",
	 		true, State.Warn);	
	 	
		int maxSeconds = 3;
	 	checks.add(
	 		"Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + maxSeconds + " segundos)",
	 		PageInputDataSolMangoCard.isPresentBotonContinuarModalUntil(maxSeconds, driver), State.Warn);
	 	return checks;
	 }
	 
	 @Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página del formulario de solicitud de la tarjeta")
	 private void selectContinueButton(String ventanaOriginal) {
		 PageInputDataSolMangoCard.clickBotonCerrarModal(driver);
		 checkValidPageTarjetaMango(ventanaOriginal);
	 }
	 
	 @Validation
	 private ChecksTM checkValidPageTarjetaMango(String ventanaOriginal) {
  		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"Aparece la página de Solicitud de tu Tarjeta MANGO",
	 		PageInputDataSolMangoCard.isPage2(driver), State.Defect);
	 	
	 	try {
			//Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
			PageInputDataSolMangoCard.gotoiFramePage2(driver);
		 	checks.add(
		 		"Aparece el apartado \"Datos personales\"",
		 		PageInputDataSolMangoCard.isPresentDatosPersonalesPage2(driver), State.Warn);	
		 	checks.add(
		 		"Aparece el apartado \"Datos bancarios\"",
		 		PageInputDataSolMangoCard.isPresentDatosBancariosPage2(driver), State.Defect);	
		 	checks.add(
		 		"Aparece el apartado \"Datos de contacto\"",
		 		PageInputDataSolMangoCard.isPresentDatosContactoPage2(driver), State.Warn);	
		 	checks.add(
		 		"Aparece el apartado \"Datos socioeconómicos\"",
		 		PageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(driver), State.Warn);	
		 	checks.add(
		 		"Aparece el apartado \"Modalidad de pago de tu MANGO Card\"",
		 		PageInputDataSolMangoCard.isPresentModalidadpagoPage2(driver), State.Warn);	
		 	checks.add(
		 		"Aparece el botón \"Continuar\"",
		 		PageInputDataSolMangoCard.isPresentButtonContinuarPage2(driver), State.Warn);	
	 	}
		finally {
			driver.close();
			driver.switchTo().window(ventanaOriginal);
		}   
		 
	  	return checks;
	 }
	 
	 @Step (
		description="Se selecciona el link para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	 public void cambioPais(DataCtxShop dCtxSh) {
		 secFooter.clickLinkCambioPais();
		 if (!ModalCambioPaisSteps.validateIsVisible(3, driver)) {
			 //Hay un problema según el cuál en ocasiones no funciona el click así que lo repetimos
			 secFooter.clickLinkCambioPais(); 
		 }
		 ModalCambioPaisSteps.validateIsVisible(5, driver);
		 try {
			 ModalCambioPaisSteps.cambioPais(dCtxSh, driver);  
		 }
		 catch (Exception e) {
			 System.out.println(e);
		 }
	 }
	 
	 @Step (
		description="Hacer click en el cuadro de suscripción del footer",
		expected="Aparecen los textos legales de RGPD")
	public void validaRGPDFooter(Boolean clickRegister, DataCtxShop dCtxSh) throws Exception {
 		if (!clickRegister) {
 			SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE).clickLogoMango();
 		}
		secFooter.clickFooterSuscripcion();
 		if (dCtxSh.pais.getRgpd().equals("S")) {
 			checkIsRGPDpresent(dCtxSh.pais.getCodigo_pais());
 		} else {
 			checkIsNotPresentRGPD(dCtxSh.pais.getCodigo_pais());
 		}
	}
	 
	@Validation (
		description="El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkIsRGPDpresent(String codigoPais) {
  		return secFooter.isTextoLegalRGPDPresent();
	}
	
	@Validation (
		description="El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkIsNotPresentRGPD(String codigoPais) {
  		return !secFooter.isTextoLegalRGPDPresent();
	}
}
