package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
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
        datosStep.setStateIniValidations();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAssist1rst.isPresentLogoAssist(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (PageCheckoutWrapper.isPresentMetodosPago(pais, channel, dFTest.driver)) {
                listVals.add(3, State.Defect);            
            }
            if (!PageAssist1rst.isPresentInputsForTrjData(channel, dFTest.driver)) {
                listVals.add(4, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputDataTarjAndPay(Pago pago, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
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
        datosStep.setStateIniValidations(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAssist1rst.invisibilityBotonPagoUntil(maxSecondsWait, channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageAssistLast.isPage(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}