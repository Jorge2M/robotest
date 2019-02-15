package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;


public class SecTMangoStpV {

    public static void validateIsSectionOk(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el bloque de selección de la forma de pago<br>" +
            "2) Aparece disponible la modalidad de pago:<br>" +
            "   - " + SecTMango.getDescripcionTipoPago(TipoPago.pagoHabitual); 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {

            if (!SecTMango.isVisibleUntil(channel, 0/*maxSecondsToWait*/, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!SecTMango.isModalidadDisponible(dFTest.driver, SecTMango.TipoPago.pagoHabitual, channel)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * @param literalTipoPago contiene uno de los valores de SecTMango.pagoHabitual, SecTMango.tresMeses...
     */
    public static DatosStep clickTipoPago(TipoPago tipoPago, Channel channel, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar la forma de pago \"" + SecTMango.getDescripcionTipoPago(tipoPago) + "\"", 
            "El resultado es correcto");
        try {
            SecTMango.clickModalidad(dFTest.driver, tipoPago, channel);
                                   
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
