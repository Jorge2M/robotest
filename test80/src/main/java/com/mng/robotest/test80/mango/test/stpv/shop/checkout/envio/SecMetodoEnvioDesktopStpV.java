package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;

@SuppressWarnings("javadoc")
public class SecMetodoEnvioDesktopStpV {

    public static ModalDroppointsStpV modalDroppoints;
    
    @SuppressWarnings("static-access")
    public static DatosStep selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "<b style=\"color:blue;\">" + nombrePago + "</b>:Seleccionamos el método de envío <b>" + tipoTransporte + "</b>", 
            "Se selecciona el método de envío correctamente");
        try {
            SecMetodoEnvioDesktop.selectMetodo(tipoTransporte, dFTest.driver);
            if (!tipoTransporte.isEntregaDomicilio()) {
            	if (ModalDroppoints.isErrorMessageVisibleUntil(dFTest.driver))
            		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), dFTest.driver);
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        validaBlockSelectedDesktop(tipoTransporte, datosStep, dFTest);
        if (tipoTransporte.isEntregaDomicilio())
            modalDroppoints.validaIsNotVisible(Channel.desktop, datosStep, dFTest);
        else
            modalDroppoints.validaIsVisible(Channel.desktop, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validaBlockSelectedDesktop(TipoTransporte tipoTransporte, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Desaparece la capa de Loading  (lo esperamos hasta " + maxSecondsToWait + " segundos) <br>" +
            "2) Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageCheckoutWrapper.waitUntilNoDivLoading(dFTest.driver, maxSecondsToWait)) {
                listVals.add(1, State.Warn);
            }
            if (!SecMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSecondsToWait, dFTest.driver)) {
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
            SecMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
