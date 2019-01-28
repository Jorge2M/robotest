package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

@SuppressWarnings("javadoc")
public class SecTMangoStpV {

    public static void validateIsSectionOk(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el bloque de selección de la forma de pago<br>" +
            "2) Aparece disponible la modalidad de pago:<br>" +
            "   - " + SecTMango.getDescripcionTipoPago(TipoPago.pagoHabitual); 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecTMango.isVisibleUntil(channel, 0/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!SecTMango.isModalidadDisponible(dFTest.driver, SecTMango.TipoPago.pagoHabitual, channel)) 
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
