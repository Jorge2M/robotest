package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssist1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssistLast;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAssist1rstStpV {
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Está presente el logo de Assist<br>" +
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" + 
            "3) No se trata de la página de precompra (no aparece los logos de formas de pago)<br>";
        if (channel==Channel.movil_web)
            descripValidac+=
            "4) Figuran 5 campos de input para los datos de la tarjeta: 1 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC";
        else
            descripValidac+=
            "4) Figuran 5 campos de input para los datos de la tarjeta: 4 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAssist1rst.isPresentLogoAssist(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (PageCheckoutWrapper.isPresentMetodosPago(pais, channel, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);            
            //4)
            if (!PageAssist1rst.isPresentInputsForTrjData(channel, dFTest.driver))
                fmwkTest.addValidation(4, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputDataTarjAndPay(Pago pago, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Introducimos los datos de la tarjeta y pulsamos el botón de pago", 
            "Aparece la página de resultado de Mango");
        try {
            PageAssist1rst.inputDataPagoAndWaitSubmitAvailable(pago, channel, dFTest.driver);
            PageAssist1rst.clickBotonPago(channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        int maxSecondsWait = 10;
        String descripValidac = 
            "1) Desaparece la página con el botón de pago (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) Aparece una página intermedia con un botón de submit"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAssist1rst.invisibilityBotonPagoUntil(maxSecondsWait, channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageAssistLast.isPage(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}