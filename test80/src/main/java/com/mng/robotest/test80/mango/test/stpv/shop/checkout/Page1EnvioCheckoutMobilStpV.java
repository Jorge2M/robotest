package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    
    public static void validateIsPage(boolean userLogged, DatosStep datosStep, DataFmwkTest dFTest) {
        
    	String optionalValidac = "3) Aparece seleccionado el método de envío \"Estándar\"<br>";
    	String descripValidac = 
            "1) Aparece la página correspondiente al paso-1<br>" +
            "2) Aparece el botón de introducción del código promocional";

    	if (!userLogged)
    		descripValidac += optionalValidac;
    	
    	datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//h2[@data-toggle='step1']"))) {
                listVals.add(1, State.Warn);
            }
            if (!Page1EnvioCheckoutMobil.isVisibleInputCodigoPromoUntil(0, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            if (!userLogged) {
	            if (!WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]"))) {
	                listVals.add(2, State.Warn);
	            }
            }
                        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    @SuppressWarnings("static-access")
    public static DatosStep selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
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
        finally { fmwkTest.grabStep(datosStep, dFTest); }

        //Validaciones
        validaBlockSelected(tipoTransporte, datosStep, dFTest);
        if (tipoTransporte.isEntregaDomicilio())
            modalDroppoints.validaIsNotVisible(Channel.movil_web, datosStep, dFTest);
        else
            modalDroppoints.validaIsVisible(Channel.movil_web, datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static void validaBlockSelected(TipoTransporte tipoTransporte, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1EnvioCheckoutMobil.isBlockSelectedUntil(tipoTransporte, 3/*maxSecondsToWait*/, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickContinuarToMetodosPago(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"Continuar\"", 
            "Aparece la página de checkout con los métodos de pago");
        try {
            Page1EnvioCheckoutMobil.clickContinuar(dFTest.driver);
                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
                
        PageCheckoutWrapperStpV.validateLoadingDisappears(datosStep, dFTest);
        
        String descripValidac = "1) Aparece la página con los métodos de Pago";
        datosStep.setNOKstateByDefault();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageCheckoutWrapper.isPresentMetodosPago(dCtxSh.pais, dCtxSh.channel, dFTest.driver)) {
                listVals.add(1, State.Defect);  
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
 
        return datosStep;
    }
    
    public static void validaResultImputPromoEmpl(DataBag dataBag, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        Descuento descuento = new Descuento(app, DiscountType.Empleado);
        int maxSecondsWait = 2;
        String descripValidac = 
            "1) Aparece el descuento total aplicado al empleado (en menos de " + maxSecondsWait + " segundos)<br>" +
            "2) Aparece un descuento de empleado mayor que 0";
        datosStep.setNOKstateByDefault();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!Page1EnvioCheckoutMobil.isVisibleDescuentoEmpleadoUntil(dFTest.driver, maxSecondsWait)) {
                listVals.add(1, State.Warn);
            }
            if (!Page1EnvioCheckoutMobil.validateDiscountEmpleadoNotNull(descuento, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                     
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectFranjaHorariaUrgente(int posicion, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionamos la <b>" + posicion + "a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
            "La franja horaria se selecciona correctamente");
        try {
            Page1EnvioCheckoutMobil.selectFranjaHorariaUrgente(posicion, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }
}
