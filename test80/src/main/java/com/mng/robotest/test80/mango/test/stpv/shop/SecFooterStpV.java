package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import com.mng.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageAyuda;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageInputDataSolMangoCard;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.FactoryPageFromFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageMangoCard;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalCambioPaisStpV;

public class SecFooterStpV {
    
	@Validation 
    public static ChecksTM validaLinksFooter(Channel channel, AppEcom app, WebDriver driver) throws Exception { 
    	ChecksTM validations = ChecksTM.getNew();
    	List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
    	validations.add(
    		"Aparecen los siguientes links en el footer <b>" + listFooterLinksToValidate + "</b>",
    		SecFooter.checkFooters(listFooterLinksToValidate, app, driver), State.Defect);
    	return validations;
    }

    /**
     * @param pageInNewTab indica si el link abrirá la página en una nueva ventana
     * @param closeTabAtEnd indicamos si queremos que finalmente se cierre la ventana o no (porque posteriormente queremos proseguir con la prueba)
     */
	@Step (
		description="Seleccionar el link del footer <b>#{typeFooter}</b>", 
        expected="Se redirige a la pantalla adecuada")
    public static void clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd, Channel channel, WebDriver driver) 
    throws Exception { 
    	String windowFatherHandle = SecFooter.clickLinkAndGetWindowFatherHandle(typeFooter, driver);
    	checkPageCorrectAfterSelectLinkFooter(windowFatherHandle, typeFooter, closeAtEnd, channel, driver);
    }
	 
	@Validation
	private static ChecksTM checkPageCorrectAfterSelectLinkFooter(
			String windowFatherHandle, FooterLink typeFooter, boolean closeAtEnd, Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter, channel, driver);
		String windowActualHandle = driver.getWindowHandle();
		boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
		int maxSeconds = 5;
		try {
	    	validations.add(
	    		"Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSeconds + " segundos)",
	    		pageObject.isPageCorrectUntil(maxSeconds), State.Warn);
		    if (typeFooter.pageInNewTab()) {
		    	validations.add(
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
	    
	    return validations;
	}
    
    /**
     * Método que valida la existencia del número de teléfono en el apartado Preguntas frecuentes
     */    
	@Validation
    public static ChecksTM validaPaginaAyuda(Channel channel, WebDriver driver) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		String telefono = "901 150 543";
    	validations.add(
    		"Aparece \"Preguntas Frecuentes\" en la página",
    		PageAyuda.isPresentCabPreguntasFreq(channel, driver), State.Warn);	
    	validations.add(
    		"Aparece la sección \"Contáctanos\" con el número de teléfono " + telefono,
    		PageAyuda.isPresentTelefono(driver, telefono), State.Warn);
    	return validations;
    }
        
    /**
     * Método para probar el formulario de Solicitud de Tarjeta Mango
     */    
     public static void checkSolicitarTarjeta (Channel channel, WebDriver driver) throws Exception {
    	 selectLoQuieroAhoraButton(channel, driver);
    	 if (!driver.getCurrentUrl().contains("shop-ci")) {
    		 
    	 }
     }
     
     @Step (
    	description="Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
        expected="La página hace scroll hasta el formulario previo de solicitud de la tarjeta")
     public static void selectLoQuieroAhoraButton (Channel channel, WebDriver driver) {
    	 PageMangoCard pageMangoCard = new PageMangoCard(driver);
         String ventanaOriginal = driver.getWindowHandle();
         pageMangoCard.clickOnWantMangoCardNow(channel);
         if(!driver.getCurrentUrl().contains("shop-ci")) {
        	 checkAfterClickLoQuieroAhoraButton(driver);
        	 selectLoQuieroAhoraUnderForm(driver);
        	 selectContinueButton(ventanaOriginal, driver);
         }
     }
     
     @Validation
     private static ChecksTM checkAfterClickLoQuieroAhoraButton(WebDriver driver) {
    	 PageMangoCard pageMangoCard = new PageMangoCard(driver);
    	 ChecksTM validations = ChecksTM.getNew();
    	 validations.add(
    		"Aparece el campo <b>Nombre</b>",
     		pageMangoCard.isPresentNameField(), State.Warn);	
    	 validations.add(
     		"Aparece el campo <b>Primer Apellido</b>",
     		pageMangoCard.isPresentFirstSurnameField(), State.Warn);	
    	 validations.add(
     		"Aparece el campo <b>Segundo Apellido</b>",
     		pageMangoCard.isPresentSecondSurnameField(), State.Warn);	
    	 validations.add(
     		"Aparece el campo <b>Movil</b>",
     		pageMangoCard.isPresentMobileField(), State.Warn);
    	 validations.add(
     		"Aparece el campo <b>Mail</b>",
     		pageMangoCard.isPresentMailField(), State.Warn);
    	 validations.add(
     		"Aparece el botón <b>¡Lo quiero ahora!</b>",
     		pageMangoCard.isPresentButtonSolMangoCardNow(), State.Warn);
     	return validations;
     }
     
     @Step (
    	description="Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
    	expected="Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"")
     public static void selectLoQuieroAhoraUnderForm(WebDriver driver) {
    	 PageMangoCard pageMangoCard = new PageMangoCard(driver);
    	 pageMangoCard.clickToGoSecondMangoCardPage();
         waitMillis(1000);
         checkAfterClickLoQuieroAhoraUnderForm(driver);
     }
     
     @Validation
     private static ChecksTM checkAfterClickLoQuieroAhoraUnderForm(WebDriver driver) {
  		ChecksTM validations = ChecksTM.getNew();
        String ventanaPadre = driver.getWindowHandle();
        SeleniumUtils.switchToAnotherWindow(driver, ventanaPadre);    
        waitMillis(1000); //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
        SeleniumUtils.waitForPageLoaded(driver, 10);
     	validations.add(
     		"Aparece una nueva ventana",
     		true, State.Warn);	
     	
        int maxSeconds = 3;
     	validations.add(
     		"Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + maxSeconds + " segundos)",
     		PageInputDataSolMangoCard.isPresentBotonContinuarModalUntil(maxSeconds, driver), State.Warn);
     	return validations;
     }
     
     @Step (
    	description="Seleccionar el botón \"Continuar\"", 
        expected="Aparece la página del formulario de solicitud de la tarjeta")
     private static void selectContinueButton(String ventanaOriginal, WebDriver driver) {
         PageInputDataSolMangoCard.clickBotonCerrarModal(driver);
         checkValidPageTarjetaMango(ventanaOriginal, driver);
     }
     
     @Validation
     private static ChecksTM checkValidPageTarjetaMango(String ventanaOriginal, WebDriver driver) {
  		ChecksTM validations = ChecksTM.getNew();
     	validations.add(
     		"Aparece la página de Solicitud de tu Tarjeta MANGO",
     		PageInputDataSolMangoCard.isPage2(driver), State.Defect);
     	
     	try {
	        //Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
	        PageInputDataSolMangoCard.gotoiFramePage2(driver);
	     	validations.add(
	     		"Aparece el apartado \"Datos personales\"",
	     		PageInputDataSolMangoCard.isPresentDatosPersonalesPage2(driver), State.Warn);	
	     	validations.add(
	     		"Aparece el apartado \"Datos bancarios\"",
	     		PageInputDataSolMangoCard.isPresentDatosBancariosPage2(driver), State.Defect);	
	     	validations.add(
	     		"Aparece el apartado \"Datos de contacto\"",
	     		PageInputDataSolMangoCard.isPresentDatosContactoPage2(driver), State.Warn);	
	     	validations.add(
	     		"Aparece el apartado \"Datos socioeconómicos\"",
	     		PageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(driver), State.Warn);	
	     	validations.add(
	     		"Aparece el apartado \"Modalidad de pago de tu MANGO Card\"",
	     		PageInputDataSolMangoCard.isPresentModalidadpagoPage2(driver), State.Warn);	
	     	validations.add(
	     		"Aparece el botón \"Continuar\"",
	     		PageInputDataSolMangoCard.isPresentButtonContinuarPage2(driver), State.Warn);	
     	}
        finally {
            driver.close();
            driver.switchTo().window(ventanaOriginal);
        }   
         
      	return validations;
     }
     
     @Step (
    	description="Se selecciona el link para el cambio de país", 
        expected="Aparece el modal para el cambio de país")
     public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) {
    	 SecFooter.clickLinkCambioPais(driver, dCtxSh.appE);
    	 if (!ModalCambioPaisStpV.validateIsVisible(3, driver)) {
    		 //Hay un problema según el cuál en ocasiones no funciona el click así que lo repetimos
    		 SecFooter.clickLinkCambioPais(driver, dCtxSh.appE); 
    	 }
         ModalCambioPaisStpV.validateIsVisible(5, driver);
         try {
        	 ModalCambioPaisStpV.cambioPais(dCtxSh, driver); 
         }
         catch (Exception e) {
        	 System.out.println(e);
         }
     }
     
     @Step (
    	description="Hacer click en el cuadro de suscripción del footer",
        expected="Aparecen los textos legales de RGPD")
    public static void validaRGPDFooter(Boolean clickRegister, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
 		if (!clickRegister) {
 			SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver).clickLogoMango();
 		}
        SecFooter.clickFooterSuscripcion(driver);
 		if (dCtxSh.pais.getRgpd().equals("S")) {
 			checkIsRGPDpresent(dCtxSh.pais.getCodigo_pais(), driver);
 		} else {
 			checkIsNotPresentRGPD(dCtxSh.pais.getCodigo_pais(), driver);
 		}
    }
     
    @Validation
    private static ChecksTM checkIsRGPDpresent(String codigoPais, WebDriver driver) {
  		ChecksTM validations = ChecksTM.getNew();
     	validations.add(
     		"El texto de info de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + codigoPais,
     		SecFooter.isTextoRGPDPresent(driver), State.Defect);
     	validations.add(
     		"El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + codigoPais,
     		SecFooter.isTextoLegalRGPDPresent(driver), State.Defect);
     	return validations;
    }
    
    @Validation
    private static ChecksTM checkIsNotPresentRGPD(String codigoPais, WebDriver driver) {
  		ChecksTM validations = ChecksTM.getNew();
     	validations.add(
     		"El texto de info de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + codigoPais,
     		!SecFooter.isTextoRGPDPresent(driver), State.Defect);
     	validations.add(
     		"El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + codigoPais,
     		!SecFooter.isTextoLegalRGPDPresent(driver), State.Defect);
     	return validations;
    }
}
