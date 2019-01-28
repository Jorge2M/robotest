package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageCheckoutWrapper.waitUntilNoDivLoading(dFTest.driver, maxSecondsToWait)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!SecMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
