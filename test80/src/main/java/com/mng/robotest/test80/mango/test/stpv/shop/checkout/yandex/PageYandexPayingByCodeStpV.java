package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageYandexPayingByCodeStpV {
    
    public static String validateIsPage(String emailUsr, String importeTotal, String codPais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String paymentCodeForReturn = "";
        String descripValidac = 
            "1) Aparece la página de <b>Paying by code</b><br>" +
            "2) Aparece el importe de la compra por pantalla: " + importeTotal + "<br>" +
            "3) Aparece un <b>PaymentCode</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageYandexPayingByCode.isPage(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageYandexPayingByCode.isVisiblePaymentCode(dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
            else
                paymentCodeForReturn = PageYandexPayingByCode.getPaymentCode(dFTest.driver);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return paymentCodeForReturn;
    }
    
    public static datosStep clickBackToMango(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Seleccionamos el botón para volver a Mango", 
            "Aparece la página Mango de resultado OK del pago");
        try {
            PageYandexPayingByCode.clickBackToMango(channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
