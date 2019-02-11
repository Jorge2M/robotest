package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKrediKarti;

@SuppressWarnings("javadoc")
public class SecKrediKartiStpV {

    public static DatosStep inputNumTarjeta(String numTarjeta, Channel channel, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducimos el número de cuenta " + numTarjeta,
            "Aparece la capa correspondiente a las opciones a plazo");
        try {
            SecKrediKarti.inputCardNumberAndTab(dFTest.driver, numTarjeta);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsWait = 5;
        String descripValidac = 
            "1) Se carga la capa correspondiente al pago a plazos (en menos de " + maxSecondsWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecKrediKarti.isVisiblePagoAPlazoUntil(dFTest.driver, channel, maxSecondsWait)) {
                listVals.add(1, State.Defect);
            }
                        
            datosStep.setListResultValidations(listVals); 
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep clickOpcionPagoAPlazo(int numOpcion, Channel channel, DataFmwkTest dFTest) {
        DatosStep datosStep = new DatosStep       (
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
