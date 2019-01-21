package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.Descuento.DiscountType;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.ModalDroppointsStpV;

/**
 * @author jorge.munoz
/* Pasos/Validaciones correspondientes a la página-1 del checkout (1. Envío) en móvil-web
 */
@SuppressWarnings("javadoc")
public class Page1EnvioCheckoutMobilStpV {

    public static ModalDroppointsStpV modalDroppoints;
    
    public static void validateIsPage(boolean userLogged, datosStep datosStep, DataFmwkTest dFTest) {
        
    	String optionalValidac = "3) Aparece seleccionado el método de envío \"Estándar\"<br>";
    	String descripValidac = 
            "1) Aparece la página correspondiente al paso-1<br>" +
            "2) Aparece el botón de introducción del código promocional";

    	if (!userLogged)
    		descripValidac += optionalValidac;
    	
    	datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//h2[@data-toggle='step1']"))) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            
            //2)
            if (!Page1EnvioCheckoutMobil.isVisibleInputCodigoPromoUntil(0, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
            
            //3)
            if (!userLogged)
	            if (!WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]")))
	                fmwkTest.addValidation(2, State.Warn, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    @SuppressWarnings("static-access")
    public static datosStep selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "<b style=\"color:blue;\">" + nombrePago + "</b>:Seleccionamos el método de envío <b>" + tipoTransporte + "</b> (previamente, si no lo estamos, nos posicionamos en el apartado \"1. Envio\")", 
            "Se selecciona el método de envío correctamente");
        try {
            Page1EnvioCheckoutMobil.selectMetodoAfterPositioningIn1Envio(tipoTransporte, dFTest.driver);
            if (!tipoTransporte.isEntregaDomicilio()) {
            	if (ModalDroppoints.isErrorMessageVisibleUntil(dFTest.driver))
            		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), dFTest.driver);
            }
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        validaBlockSelected(tipoTransporte, datosStep, dFTest);
        if (tipoTransporte.isEntregaDomicilio())
            modalDroppoints.validaIsNotVisible(Channel.movil_web, datosStep, dFTest);
        else
            modalDroppoints.validaIsVisible(Channel.movil_web, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static void validaBlockSelected(TipoTransporte tipoTransporte, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page1EnvioCheckoutMobil.isBlockSelectedUntil(tipoTransporte, 3/*maxSecondsToWait*/, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep clickContinuarToMetodosPago(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón \"Continuar\"", 
            "Aparece la página de checkout con los métodos de pago");
        try {
            Page1EnvioCheckoutMobil.clickContinuar(dFTest.driver);
                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 10;
        String descripValidac = "1) Acaba desapareciendo la capa de \"Cargando...\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            Thread.sleep(200); //Damos tiempo a que aparezca la capa de "Cargando"
            if (!PageCheckoutWrapper.isNoDivLoadingUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);     
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        descripValidac = "1) Aparece la página con los métodos de Pago";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.isPresentMetodosPago(dCtxSh.pais, dCtxSh.channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);  
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
 
        return datosStep;
    }
    
    public static void validaResultImputPromoEmpl(DataBag dataBag, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        Descuento descuento = new Descuento(app, DiscountType.Empleado);
        String descripValidac = 
            "1) Aparece el descuento total aplicado al empleado (en menos de 2 segundos)<br>" +
            "2) Aparece un descuento de empleado mayor que 0";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page1EnvioCheckoutMobil.isVisibleDescuentoEmpleadoUntil(dFTest.driver, 2/*secondsToWait*/))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!Page1EnvioCheckoutMobil.validateDiscountEmpleadoNotNull(descuento, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
                     
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
//    public static void inputCodPromoFalso(DataCtxPago dCtxPago, DataFmwkTest dFTest) throws Exception {
//        String codigoFalso = "SOYUNFAKE";
//        datosStep datosStep = new datosStep       (
//            "Introducir un código promocional falso (" + codigoFalso + ") y pulsar \"Aplicar código\"", 
//            "Aparece el correspondiente mensaje de error");
//        try {
//            dCtxPago.getDataPedido().setDireccionEnvio(Page1EnvioCheckoutMobil.getDireccionEntrega(dFTest.driver));
//            Page1EnvioCheckoutMobil.inputCodigoPromoAndAccept(codigoFalso, dFTest.driver);
//                                
//            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
//        }
//        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
//                
//        int maxSecondsToWait = 3;
//        String descripValidac = 
//            "1) Se hace visible un mensaje de error referente al código promocional (lo esperamos hasta " + maxSecondsToWait + " segundos)";
//        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
//        try {
//            List<SimpleValidation> listVals = new ArrayList<>();
//            //1)
//            if (!Page1EnvioCheckoutMobil.isVisibleErrorPromoUntil(maxSecondsToWait, dFTest.driver))
//                fmwkTest.addValidation(1, State.Defect, listVals);
//                        
//            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
//        }
//        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }    
//    }
    
    public static datosStep selectFranjaHorariaUrgente(int posicion, DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep     (
            "Seleccionamos la <b>" + posicion + "a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
            "La franja horaria se selecciona correctamente");
        try {
            Page1EnvioCheckoutMobil.selectFranjaHorariaUrgente(posicion, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
