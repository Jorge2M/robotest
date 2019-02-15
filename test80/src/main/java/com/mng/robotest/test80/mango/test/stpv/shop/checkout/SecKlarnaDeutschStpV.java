package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarnaDeutsch;


public class SecKlarnaDeutschStpV {
    
    public static void validateIsSection(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece el selector de la fecha de nacimiento (lo esperamos hasta un máximo de " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecKlarnaDeutsch.isVisibleSelectDiaNacimientoUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public static DatosStep inputData(String fechaNaci, Channel channel, DataFmwkTest dFTest) {
        DatosStep datosStep = new DatosStep (
            "Introducimos la fecha de nacimiento<b>" + fechaNaci + "</b> y marcamos el radio de <b>\"Acepto\"</b>", 
            "Los datos se informan correctamente");
        try {
            SecKlarnaDeutsch.selectFechaNacimiento(fechaNaci, dFTest.driver);
            SecKlarnaDeutsch.clickAcepto(channel, dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
