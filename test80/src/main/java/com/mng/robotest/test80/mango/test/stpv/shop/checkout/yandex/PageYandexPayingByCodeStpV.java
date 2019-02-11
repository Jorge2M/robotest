package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageYandexPayingByCodeStpV {
    
    public static String validateIsPage(String emailUsr, String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String paymentCodeForReturn = "";
        String descripValidac = 
            "1) Aparece la página de <b>Paying by code</b><br>" +
            "2) Aparece el importe de la compra por pantalla: " + importeTotal + "<br>" +
            "3) Aparece un <b>PaymentCode</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageYandexPayingByCode.isPage(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageYandexPayingByCode.isVisiblePaymentCode(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            else {
                paymentCodeForReturn = PageYandexPayingByCode.getPaymentCode(dFTest.driver);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return paymentCodeForReturn;
    }
    
    public static DatosStep clickBackToMango(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
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
