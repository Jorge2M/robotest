package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    public static void validaLinksFooter(Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) throws Exception { 
        //Validaciones
    	List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
        String descripValidac = 
            "1) Aparecen los siguientes links en el footer <b>" + listFooterLinksToValidate + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecFooter.checkFooters(listFooterLinksToValidate, app, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }

    /**
     * @param pageInNewTab indica si el link abrirá la página en una nueva ventana
     * @param closeTabAtEnd indicamos si queremos que finalmente se cierre la ventana o no (porque posteriormente queremos proseguir con la prueba)
     */
    public static DatosStep clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception { 
        //Step
    	PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter, channel);
    	String windowFatherHandle;
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el link del footer <b>" + typeFooter + "</b><br>", 
            "Se redirige a la pantalla de " + pageObject.getName());
        try {
        	windowFatherHandle = 
        		SecFooter.clickLinkAndGetWindowFatherHandle(typeFooter, app, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
                
        //Validaciones
    	String windowActualHandle = dFTest.driver.getWindowHandle();
    	boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
        int maxSecondsToWait = 5;
        String validation2 = "";
        if (typeFooter.pageInNewTab())
        	validation2 = "2) Aparece la página en una ventana aparte";
        
        String descripValidac = 
            "1) Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            validation2;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!pageObject.isPageCorrect(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (typeFooter.pageInNewTab()) {
            	if (!newWindowInNewTab) {
            		listVals.add(2, State.Warn);
            	}
            }
                
            datosStep.setListResultValidations(listVals);
        } 
        finally {
        	listVals.checkAndStoreValidations(descripValidac);
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
    public static void validaPaginaAyuda(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validación
        String telefono = "901 150 543";
        String descripValidac = 
            "1) Aparece \"Preguntas Frecuentes\" en la página <br>" +
            "2) Aparece la sección \"Contáctanos\" con el número de teléfono " + telefono;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAyuda.isPresentCabPreguntasFreq(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageAyuda.isPresentTelefono(dFTest.driver, telefono)) {
                listVals.add(2, State.Warn);            
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
        
    /**
     * Método para probar el formulario de Solicitud de Tarjeta Mango
     */    
     public static void checkSolicitarTarjeta (Channel channel, DataFmwkTest dFTest) throws Exception {
         String ventanaPadre = "";
         DatosStep datosStep = null;
         String descripValidac = "";
         String ventanaOriginal = dFTest.driver.getWindowHandle();

         //Step
         datosStep = new DatosStep       (
             "Seleccionar el botón con fondo negro \"¡La quiero ahora!\"",
             "La página hace scroll hasta el formulario previo de solicitud de la tarjeta");
         try {
             PageMangoCard.clickOnWantMangoCardNow(dFTest.driver, channel);
             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
         }
         finally { fmwkTest.grabStep(datosStep, dFTest); }

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
                 
             datosStep.setNOKstateByDefault();
             ListResultValidation listVals = ListResultValidation.getNew(datosStep);
             try {
                 if (!PageMangoCard.isPresentNameField(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 if (!PageMangoCard.isPresentFirstSurnameField(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 if (!PageMangoCard.isPresentSecondSurnameField(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 if (!PageMangoCard.isPresentMobileField(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 if (!PageMangoCard.isPresentMailField(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 if (!PageMangoCard.isPresentButtonSolMangoCardNow(dFTest.driver)) {
                	 listVals.add(2, State.Warn);
                 }
                 
                datosStep.setListResultValidations(listVals);
             }
             finally { listVals.checkAndStoreValidations(descripValidac); }
    
             
             //Step 
             datosStep = new DatosStep		 (
            		 "Seleccionamos el botón \"¡Lo quiero ahora!\" que aparece debajo del formulario",
            		 "Se abre una nueva pestaña del Banc Sabadell con un modal y texto \"Solicitud de tu MANGO Card\"");
             try {
            	 PageMangoCard.clickToGoSecondMangoCardPage(dFTest.driver);    
                 Thread.sleep(1000);
                 datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);

             } 
             finally { fmwkTest.grabStep(datosStep, dFTest); }
             
             //Validaciones
             int secondsToWait = 3;
             descripValidac = 
		      	 "1) Aparece una nueva ventana<br>" +
		         "2) Aparece un modal de aviso de trámite de la solicitud con un botón \"Continuar\" (la esperamos hasta " + secondsToWait + " segundos)";
                 
             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok); 
             listVals = ListResultValidation.getNew(datosStep);    
             try {
                 ventanaPadre = dFTest.driver.getWindowHandle();
                 WebdrvWrapp.switchToAnotherWindow(dFTest.driver, ventanaPadre);                     
                 //El javascript lanzado por "waitForPageLoaded" rompe la carga de la página -> hemos de aplicar wait explícito previo
                 Thread.sleep(1000); 
                 WebdrvWrapp.waitForPageLoaded(dFTest.driver, 10);
                 if (!PageInputDataSolMangoCard.isPresentBotonContinuarModalUntil(secondsToWait, dFTest.driver)) {
                     listVals.add(2, State.Warn);
                 }
                    
                 datosStep.setListResultValidations(listVals);
             }
             finally { listVals.checkAndStoreValidations(descripValidac); }
        
                 
             //Step
             datosStep = new DatosStep       (
                 "Seleccionar el botón \"Continuar\"", 
                 "Aparece la página del formulario de solicitud de la tarjeta");
             try {
                 PageInputDataSolMangoCard.clickBotonCerrarModal(dFTest.driver);                     
                 datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
             }
             finally { fmwkTest.grabStep(datosStep, dFTest); }
           
             //Validaciones
             descripValidac = 
                 "1) Aparece la página de Solicitud de tu Tarjeta MANGO<br>" +
                 "2) Aparece el apartado \"Datos personales\"<br>" +
                 "3) Aparece el apartado \"Datos bancarios\"<br>" +
                 "4) Aparece el apartado \"Datos de contacto\"<br>" +
                 "5) Aparece el apartado \"Datos socioeconómicos\"<br>"+
                 "6) Aparece el apartado \"Modalidad de pago de tu MANGO Card<br>" +
                 "7) Aparece el botón \"Continuar\"";
             
             datosStep.setNOKstateByDefault(); 
             listVals = ListResultValidation.getNew(datosStep);             
             try {
                 if (!PageInputDataSolMangoCard.isPage2(dFTest.driver)) {
                     listVals.add(1, State.Defect);
                 }
                 //Nos posicionamos en el iframe central para recorrer contenido (datos personales y datos bancarios).
                 PageInputDataSolMangoCard.gotoiFramePage2(dFTest.driver);
                 if (!PageInputDataSolMangoCard.isPresentDatosPersonalesPage2(dFTest.driver)) {
                     listVals.add(2, State.Warn);
                 }
                 if (!PageInputDataSolMangoCard.isPresentDatosBancariosPage2(dFTest.driver)) {
                     listVals.add(3, State.Defect);
                 }
                 if (!PageInputDataSolMangoCard.isPresentDatosContactoPage2(dFTest.driver)) {
                     listVals.add(4, State.Warn);
                 }
                 if (!PageInputDataSolMangoCard.isPresentDatosSocioeconomicosPage2(dFTest.driver)) {
                     listVals.add(5, State.Warn);
                 }
                 if (!PageInputDataSolMangoCard.isPresentModalidadpagoPage2(dFTest.driver)) {
                     listVals.add(6, State.Warn);                 
                 }
                 if (!PageInputDataSolMangoCard.isPresentButtonContinuarPage2(dFTest.driver)) {
                     listVals.add(7, State.Warn);                 
                 }
                 
                 datosStep.setListResultValidations(listVals);
             }
             finally {
            	 listVals.checkAndStoreValidations(descripValidac); 
                 dFTest.driver.close();
                 dFTest.driver.switchTo().window(ventanaOriginal);
             }         
         
         }
     }
     
     public static DatosStep cambioPais(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
         //Step. 
         DatosStep datosStep = new DatosStep(
             "Se selecciona el link para el cambio de país", 
             "Aparece el modal para el cambio de país");
         try {
             SecFooter.clickLinkCambioPais(dFTest.driver, dCtxSh.appE);

             datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
         } 
         finally { fmwkTest.grabStep(datosStep, dFTest);}
         
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
     public static DatosStep validaRGPDFooter(Boolean clickRegister, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
     	DatosStep datosStep = new DatosStep (
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
        finally { fmwkTest.grabStep(datosStep, dFTest);}
             
     	//Validaciones
 		if (dCtxSh.pais.getRgpd().equals("S")) {
 	        String descripValidac = 
 	            "1) El texto de info de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
 	            "2) El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
 	        datosStep.setNOKstateByDefault();
	        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
 	        try {
 	            if (!SecFooter.isTextoRGPDPresent(dFTest.driver)) {
 	                listVals.add(1, State.Defect);
 	            }
 	            if (!SecFooter.isTextoLegalRGPDPresent(dFTest.driver)) {
 	                listVals.add(2, State.Defect);
 	            }
 	            
 	            datosStep.setListResultValidations(listVals);
 	        }
 	        finally { listVals.checkAndStoreValidations(descripValidac); }   
 		}
 		
 		else {
 			String descripValidac = 
 	            "1) El texto de info de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
 	            "2) El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
 	        datosStep.setNOKstateByDefault();
	        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
 	        try {
 	            if (SecFooter.isTextoRGPDPresent(dFTest.driver)) {
 	                listVals.add(1, State.Defect);
 	            }
 	            if (SecFooter.isTextoLegalRGPDPresent(dFTest.driver)) {
 	                listVals.add(2, State.Defect);
 	            }
 	            
 	            datosStep.setListResultValidations(listVals);
 	        }
 	        finally { listVals.checkAndStoreValidations(descripValidac); } 
 		}
 		return datosStep;
     }
}
