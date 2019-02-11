package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst.pasarelasAssist;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAssistQiwi1rstStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el icono de Assist<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece el icono de Qiwi";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAssistQiwi1rst.isPresentIconoAssist(dFTest.driver, channel)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageAssistQiwi1rst.isPresentIconPasarelas(dFTest.driver, channel)) {
                listVals.add(3, State.Warn);
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }            
    }
    
    public static DatosStep clickIconPasarelaQiwi(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar la opción de Qiwi Kошелек", 
            "Aparece la página de introducción del número de teléfono");
        try {
            PageAssistQiwi1rst.clickIconPasarela(dFTest.driver, channel, pasarelasAssist.qiwikошелек);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        PageQiwiInputTlfnStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
