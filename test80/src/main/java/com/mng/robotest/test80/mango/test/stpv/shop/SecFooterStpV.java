package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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

@SuppressWarnings("javadoc")
public class SecFooterStpV {
    
    /**
     * Valida que existe el footer con todas sus componentes
     */
    public static void validaLinksFooter(Channel channel, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) throws Exception { 
        //Validaciones
    	List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
        String descripValidac = 
            "1) Aparecen los siguientes links en el footer <b>" + listFooterLinksToValidate + "</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecFooter.checkFooters(listFooterLinksToValidate, app, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally {fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);}        
    }

    /**
     * @param pageInNewTab indica si el link abrirá la página en una nueva ventana
     * @param closeTabAtEnd indicamos si queremos que finalmente se cierre la ventana o no (porque posteriormente queremos proseguir con la prueba)
     */
    public static datosStep clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception { 
        //Step
    	PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter, channel);
    	String windowFatherHandle;
        datosStep datosStep = new datosStep       (
            "Seleccionar el link del footer <b>" + typeFooter + "</b><br>", 
            "Se redirige a la pantalla de " + pageObject.getName());
        try {
        	windowFatherHandle = 
        		SecFooter.clickLinkAndGetWindowFatherHandle(typeFooter, app, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
    	String windowActualHandle = dFTest.driver.getWindowHandle();
    	boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
        int maxSecondsToWait = 5;
        String validation2 = "";
        if (typeFooter.pageInNewTab())
        	validation2 = "2) Aparece la página en una ventana aparte";
        
        String validation = 
            "1) Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            validation2;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageObject.isPageCorrect(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (typeFooter.pageInNewTab()) {
            	if (!newWindowInNewTab)
            		fmwkTest.addValidation(2, State.Warn, listVals);
            }
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally {
            fmwkTest.grabStepValidation(datosStep, validation, dFTest);
            if (typeFooter.pageInNewTab()) {
                if (closeAtEnd && newWindowInNewTab) {
                    dFTest.driver.close();
                    dFTest.driver.switchTo().window(windowFatherHandle);
                }
            }
        }         
        
        return datosStep;
     }
    
    /**
     * Método que valida la existencia del número de teléfono en el apartado Preguntas frecuentes
     */    
    public static void validaPaginaAyuda(Channel channel, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validación
        String telefono = "901 150 543";
        String descripValidac = 
            "1) Aparece \"Preguntas Frecuentes\" en la página <br>" +
            "2) Aparece la sección \"Contáctanos\" con el número de teléfono " + telefono;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAyuda.isPresentCabPreguntasFreq(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageAyuda.isPresentTelefono(dFTest.driver, telefono))
                fmwkTest.addValidation(2, State.Warn, listVals);            
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
        
    /**
     * Método para probar el formulario de Solicitud de Tarjeta Mango
     */    
     public static void checkSolicitarTarjeta (Channel channel, DataFmwkTest dFTest) throws Exception {
         String ventanaPadre = "";
         datosStep datosStep = null;
         String descripValidac = "";
         String ventanaOriginal = dFTest.driver.getWindowHandle();

         //Step
         datosStep = new datosStep       (
             "Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
             "La página hace scroll hasta el formulario previo de solicitud de la tarjeta");
         try {
             PageMangoCard.clickOnWantMangoCardNow(dFTest.driver, channel);
             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
         }
         finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

         //Si no estamos en el entorno de CI
         if(!dFTest.driver.getCurrentUrl().contains("shop-ci")) {
             //Validaciones
             descripValidac = 
           		 "1) Aparece el campo <b>Nombre</b><br>" + 
           		 "2) Aparece el campo <b>Primer Apellido</b><br>" + 
           		 "3) Aparece el campo <b>Segundo Apellido</b><br>" + 
           		 "4) Aparece el campo <b>Movil</b><br>" + 
           		 "5) Aparece el campo <b>Mail</b><br>" + 
           		 "6) Aparece el botón <b>¡Lo quiero ahora!</b>";
                 
             datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
             try {
                 List<SimpleValidation> listVals = new ArrayList<>();
                 //1)
                 if (!PageMangoCard.isPresentNameField(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 //2)
                 if (!PageMangoCard.isPresentFirstSurnameField(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 //3)
                 if (!PageMangoCard.isPresentSecondSurnameField(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 //4)
                 if (!PageMangoCard.isPresentMobileField(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 //5)
                 if (!PageMangoCard.isPresentMailField(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 //6)
                 if (!PageMangoCard.isPresentButtonSolMangoCardNow(dFTest.driver))
                	 fmwkTest.addValidation(2, State.Warn, listVals);
                 
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
             }
             finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    
             
             //Step 
             datosStep = new datosStep		 (
            		 "Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
            		 "Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"");
             try {
            	 PageMangoCard.clickToGoSecondMangoCardPage(dFTest.driver);    
                 Thread.sleep(1000);
                 datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);

             } 
             finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
             
             //Validaciones
             int secondsToWait = 3;
             descripValidac = 
		      	 "1) Aparece una nueva ventana<br>" +
		         "2) Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + secondsToWait + " segundos)";
                 
             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok); 
                 
             try {
                 List<SimpleValidation> listVals = new ArrayList<>();
                 //1)
                 ventanaPadre = dFTest.driver.getWindowHandle();
                 WebdrvWrapp.switchToAnotherWindow(dFTest.driver, ventanaPadre);                     
                 //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
                 Thread.sleep(1000); 
                 WebdrvWrapp.waitForPageLoaded(dFTest.driver, 10);
                     
                 //2)
                 if (!PageInputDataSolMangoCard.isPresentBotonContinuarModalUntil(secondsToWait, dFTest.driver))
                     fmwkTest.addValidation(2, State.Warn, listVals);
                    
                 datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
             }
             finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
                 
             //Step
             datosStep = new datosStep       (
                 "Seleccionar el botón \"Continuar\"", 
                 "Aparece la página del formulario de solicitud de la tarjeta");
             try {
                 PageInputDataSolMangoCard.clickBotonCerrarModal(dFTest.driver);                     
                 datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
             }
             finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
           
             //Validaciones
             descripValidac = 
                 "1) Aparece la página de Solicitud de tu Tarjeta MANGO<br>" +
                 "2) Aparece el apartado \"Datos personales\"<br>" +
                 "3) Aparece el apartado \"Datos bancarios\"<br>" +
                 "4) Aparece el apartado \"Datos de contacto\"<br>" +
                 "5) Aparece el apartado \"Datos socioeconómicos\"<br>"+
                 "6) Aparece el apartado \"Modalidad de pago de tu MANGO Card<br>" +
                 "7) Aparece el botón \"Continuar\"";
             
             datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok); 
             
             try {
                 List<SimpleValidation> listVals = new ArrayList<>();
                 //1)
                 if (!PageInputDataSolMangoCard.isPage2(dFTest.driver))
                     fmwkTest.addValidation(1, State.Defect, listVals);
                 //Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
                 PageInputDataSolMangoCard.gotoiFramePage2(dFTest.driver);
                 //2)
                 if (!PageInputDataSolMangoCard.isPresentDatosPersonalesPage2(dFTest.driver))
                     fmwkTest.addValidation(2, State.Warn, listVals);
                 //3)
                 if (!PageInputDataSolMangoCard.isPresentDatosBancariosPage2(dFTest.driver))
                     fmwkTest.addValidation(3, State.Defect, listVals);
                 //4)
                 if (!PageInputDataSolMangoCard.isPresentDatosContactoPage2(dFTest.driver))
                     fmwkTest.addValidation(4, State.Warn, listVals);
                 //5)
                 if (!PageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(dFTest.driver))
                     fmwkTest.addValidation(5, State.Warn, listVals);
                 //6)
                 if (!PageInputDataSolMangoCard.isPresentModalidadpagoPage2(dFTest.driver))
                     fmwkTest.addValidation(6, State.Warn, listVals);                 
                 //7)
                 if (!PageInputDataSolMangoCard.isPresentButtonContinuarPage2(dFTest.driver))
                     fmwkTest.addValidation(7, State.Warn, listVals);                 
                 
                 datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
             }
             finally {
                 fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); 
                 dFTest.driver.close();
                 dFTest.driver.switchTo().window(ventanaOriginal);
             }         
         
         }
     }
     
     public static datosStep cambioPais(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
         //Step. 
         datosStep datosStep = new datosStep(
             "Se selecciona el link para el cambio de país", 
             "Aparece el modal para el cambio de país");
         try {
             SecFooter.clickLinkCambioPais(dFTest.driver, dCtxSh.appE);

             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
         } 
         finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}
         
         //Validaciones. 
         ModalCambioPaisStpV.validateIsVisible(datosStep, dFTest);
         
         //Step.
         ModalCambioPaisStpV.cambioPais(dCtxSh, dFTest);
         
         return datosStep;
     }
     
     /**
      * Validaciones del código fuente de la página para ver si están presentes o no los textos legales de RGPD
     * @throws Exception 
      */
     public static datosStep validaRGPDFooter(Boolean clickRegister, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
     	datosStep datosStep = new datosStep (
                 "Hacer click en el cuadro de suscripción del footer<br>",
                 "Aparecen los textos legales de RGPD");
     	try {
     		if (!clickRegister) {
     			SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver).
     				clickLogoMango();
     		}
     		
            SecFooter.clickFooterSuscripcion(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}
             
     	//Validaciones
 		if (dCtxSh.pais.getRgpd().equals("S")) {
 	        String descripValidac = 
 	            "1) El texto de info de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
 	            "2) El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
 	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
 	        try {
 	            List<SimpleValidation> listVals = new ArrayList<>();
 	            //1)
 	            if (!SecFooter.isTextoRGPDPresent(dFTest.driver))
 	                fmwkTest.addValidation(1, State.Defect, listVals);
 	            //2)
 	            if (!SecFooter.isTextoLegalRGPDPresent(dFTest.driver))
 	                fmwkTest.addValidation(2, State.Defect, listVals);
 	            
 	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
 	        }
 	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }   
 		}
 		
 		else {
 			String descripValidac = 
 	            "1) El texto de info de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
 	            "2) El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
 	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                             
 	        try {
 	            List<SimpleValidation> listVals = new ArrayList<>();
 	            //1)
 	            if (SecFooter.isTextoRGPDPresent(dFTest.driver))
 	                fmwkTest.addValidation(1, State.Defect, listVals);
 	            //2)
 	            if (SecFooter.isTextoLegalRGPDPresent(dFTest.driver))
 	                fmwkTest.addValidation(2, State.Defect, listVals);
 	            
 	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
 	        }
 	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 
 		}
 		return datosStep;
     }
}
