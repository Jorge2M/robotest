package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKrediKarti;

@SuppressWarnings("javadoc")
public class SecKrediKartiStpV {

    public static datosStep inputNumTarjeta(String numTarjeta, Channel channel, DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep (
            "Introducimos el número de cuenta " + numTarjeta,
            "Aparece la capa correspondiente a las opciones a plazo");
        try {
            SecKrediKarti.inputCardNumberAndTab(dFTest.driver, numTarjeta);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        String descripValidac = 
            "1) Se carga la capa correspondiente al pago a plazos (en menos de 5 segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecKrediKarti.isVisiblePagoAPlazoUntil(dFTest.driver, channel, 5/*seconds*/))
                fmwkTest.addValidation(1, State.Defect, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static datosStep clickOpcionPagoAPlazo(int numOpcion, Channel channel, DataFmwkTest dFTest) {
        datosStep datosStep = new datosStep       (
            "Seleccionamos la " + numOpcion + "a de las opciones de pago a plazo", 
            "La opción se selecciona correctamente");
        try {
            //Seleccionamos la 1a de las opciones de pago a plazo
            SecKrediKarti.clickRadioPlazo(numOpcion, dFTest.driver, channel);
                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
