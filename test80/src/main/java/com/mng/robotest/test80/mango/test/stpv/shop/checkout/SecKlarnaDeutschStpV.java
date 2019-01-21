package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarnaDeutsch;

@SuppressWarnings("javadoc")
public class SecKlarnaDeutschStpV {
    
    public static void validateIsSection(datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece el selector de la fecha de nacimiento (lo esperamos hasta un máximo de " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecKlarnaDeutsch.isVisibleSelectDiaNacimientoUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public static datosStep inputData(String fechaNaci, Channel channel, DataFmwkTest dFTest) {
        datosStep datosStep = new datosStep (
            "Introducimos la fecha de nacimiento<b>" + fechaNaci + "</b> y marcamos el radio de <b>\"Acepto\"</b>", 
            "Los datos se informan correctamente");
        try {
            SecKlarnaDeutsch.selectFechaNacimiento(fechaNaci, dFTest.driver);
            SecKlarnaDeutsch.clickAcepto(channel, dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
