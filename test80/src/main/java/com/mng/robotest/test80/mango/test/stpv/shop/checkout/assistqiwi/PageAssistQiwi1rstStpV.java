package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageAssistQiwi1rst.pasarelasAssist;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAssistQiwi1rstStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el icono de Assist<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece el icono de Qiwi";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAssistQiwi1rst.isPresentIconoAssist(dFTest.driver, channel))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageAssistQiwi1rst.isPresentIconPasarelas(dFTest.driver, channel)) 
                fmwkTest.addValidation(3, State.Warn, listVals);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }            
    }
    
    public static datosStep clickIconPasarelaQiwi(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
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
