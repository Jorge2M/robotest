package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
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
    public static ChecksResult validaLinksFooter(Channel channel, AppEcom app, WebDriver driver) throws Exception { 
    	ChecksResult validations = ChecksResult.getNew();
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
                
        //Validaciones
    	checkPageCorrectAfterSelectLinkFooter(windowFatherHandle, typeFooter, closeAtEnd, channel, driver);
    }
	 
	@Validation
	private static ChecksResult checkPageCorrectAfterSelectLinkFooter(String windowFatherHandle, FooterLink typeFooter, boolean closeAtEnd, 
																	  Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	    PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter, channel);
		String windowActualHandle = driver.getWindowHandle();
		boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
		int maxSecondsToWait = 5;
		try {
	    	validations.add(
	    		"Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSecondsToWait + " segundos)",
	    		pageObject.isPageCorrect(driver), State.Warn);		
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
    public static ChecksResult validaPaginaAyuda(Channel channel, WebDriver driver) throws Exception {
		ChecksResult validations = ChecksResult.getNew();
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
     public static void selectLoQuieroAhoraButton (Channel channel, WebDriver driver) throws Exception {
         String ventanaOriginal = driver.getWindowHandle();
         PageMangoCard.clickOnWantMangoCardNow(driver, channel);
         if(!driver.getCurrentUrl().contains("shop-ci")) {
        	 checkAfterClickLoQuieroAhoraButton(driver);
        	 selectLoQuieroAhoraUnderForm(driver);
        	 selectContinueButton(ventanaOriginal, driver);
         }
     }
     
     @Validation
     private static ChecksResult checkAfterClickLoQuieroAhoraButton(WebDriver driver) throws Exception {
 		ChecksResult validations = ChecksResult.getNew();
     	validations.add(
     		"Aparece el campo <b>Nombre</b>",
     		PageMangoCard.isPresentNameField(driver), State.Warn);	
     	validations.add(
     		"Aparece el campo <b>Primer Apellido</b>",
     		PageMangoCard.isPresentFirstSurnameField(driver), State.Warn);	
     	validations.add(
     		"Aparece el campo <b>Segundo Apellido</b>",
     		PageMangoCard.isPresentSecondSurnameField(driver), State.Warn);	
     	validations.add(
     		"Aparece el campo <b>Movil</b>",
     		PageMangoCard.isPresentMobileField(driver), State.Warn);
     	validations.add(
     		"Aparece el campo <b>Mail</b>",
     		PageMangoCard.isPresentMailField(driver), State.Warn);
     	validations.add(
     		"Aparece el botón <b>¡Lo quiero ahora!</b>",
     		PageMangoCard.isPresentButtonSolMangoCardNow(driver), State.Warn);
     	return validations;
     }
     
     @Step (
    	description="Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
    	expected="Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"")
     public static void selectLoQuieroAhoraUnderForm(WebDriver driver) throws Exception {
    	 PageMangoCard.clickToGoSecondMangoCardPage(driver);    
         Thread.sleep(1000);
         checkAfterClickLoQuieroAhoraUnderForm(driver);
     }
     
     @Validation
     private static ChecksResult checkAfterClickLoQuieroAhoraUnderForm(WebDriver driver) throws Exception {
  		ChecksResult validations = ChecksResult.getNew();
        String ventanaPadre = driver.getWindowHandle();
        WebdrvWrapp.switchToAnotherWindow(driver, ventanaPadre);    
        Thread.sleep(1000); //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
        WebdrvWrapp.waitForPageLoaded(driver, 10);
     	validations.add(
     		"Aparece una nueva ventana",
     		true, State.Warn);	
     	
        int maxSecondsWait = 3;
     	validations.add(
     		"Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + maxSecondsWait + " segundos)",
     		PageInputDataSolMangoCard.isPresentBotonContinuarModalUntil(maxSecondsWait, driver), State.Warn);
     	return validations;
     }
     
     @Step (
    	description="Seleccionar el botón \"Continuar\"", 
        expected="Aparece la página del formulario de solicitud de la tarjeta")
     private static void selectContinueButton(String ventanaOriginal, WebDriver driver) throws Exception {
         PageInputDataSolMangoCard.clickBotonCerrarModal(driver);                     
         checkValidPageTarjetaMango(ventanaOriginal, driver);
     }
     
     @Validation
     private static ChecksResult checkValidPageTarjetaMango(String ventanaOriginal, WebDriver driver) {
  		ChecksResult validations = ChecksResult.getNew();
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
     public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
         SecFooter.clickLinkCambioPais(driver, dCtxSh.appE);
         
         //Validaciones. 
         int maxSecondsWait = 5;
         ModalCambioPaisStpV.validateIsVisible(maxSecondsWait, driver);
         
         //Step.
         ModalCambioPaisStpV.cambioPais(dCtxSh, driver);
     }
     
     @Step (
    	description="Hacer click en el cuadro de suscripción del footer",
        expected="Aparecen los textos legales de RGPD")
    public static void validaRGPDFooter(Boolean clickRegister, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
 		if (!clickRegister) {
 			SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver)
 				.clickLogoMango();
 		}
        SecFooter.clickFooterSuscripcion(driver);
             
     	//Validaciones
 		if (dCtxSh.pais.getRgpd().equals("S")) {
 			checkIsRGPDpresent(dCtxSh.pais.getCodigo_pais(), driver);
 		}
 		else {
 			checkIsNotPresentRGPD(dCtxSh.pais.getCodigo_pais(), driver);
 		}
    }
     
    @Validation
    private static ChecksResult checkIsRGPDpresent(String codigoPais, WebDriver driver) {
  		ChecksResult validations = ChecksResult.getNew();
     	validations.add(
     		"El texto de info de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + codigoPais,
     		SecFooter.isTextoRGPDPresent(driver), State.Defect);
     	validations.add(
     		"El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + codigoPais,
     		SecFooter.isTextoLegalRGPDPresent(driver), State.Defect);
     	return validations;
    }
    
    @Validation
    private static ChecksResult checkIsNotPresentRGPD(String codigoPais, WebDriver driver) {
  		ChecksResult validations = ChecksResult.getNew();
     	validations.add(
     		"El texto de info de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + codigoPais,
     		!SecFooter.isTextoRGPDPresent(driver), State.Defect);
     	validations.add(
     		"El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + codigoPais,
     		!SecFooter.isTextoLegalRGPDPresent(driver), State.Defect);
     	return validations;
    }
}
