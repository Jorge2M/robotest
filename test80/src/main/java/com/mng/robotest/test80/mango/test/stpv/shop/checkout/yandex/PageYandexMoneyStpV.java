package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoney;

@SuppressWarnings("javadoc")
public class PageYandexMoneyStpV {

    public static datosStep accessInNewTab(String tabTitle, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Accedemos a la URL de <b>YandexMoney</b>: " + PageYandexMoney.urlAccess, 
            "Aparece la página de YandexMoney");
        try {
            PageYandexMoney.goToPageInNewTab(tabTitle, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validation
        validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el input para el <b>Payment Code</b><br>" +
            "2) Aparece el input para el importe";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageYandexMoney.isVisibleInputPaymentCode(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2) 
            if (!PageYandexMoney.isVisibleInputImport(dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputDataAndPay(String paymentCode, String importe, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep       (
            "Introducimos el paymentCode <b>" + paymentCode + "</b>, el importe <b>" + importe + "</b> y pulsamos el botón de Pago", 
            "Aparece la página de resultado del pago a nivel de Yandex");
        try {
            PageYandexMoney.inputPaymentCode(paymentCode, dFTest.driver);
            PageYandexMoney.inputImport(importe, dFTest.driver);
            PageYandexMoney.clickPayButton(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        PageYandexMoneyResultStpV.validateIsResultOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static datosStep closeTabByTitle(String tabTitle, String windowHandlePageToSwitch, DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep       (
            "Cerramos la actual pestaña con nombre <b>" + tabTitle + "</b>", 
            "Desaparece la pestaña");
        try {
            PageYandexMoney.closeActualTabByTitle(tabTitle, dFTest.driver);
            dFTest.driver.switchTo().window(windowHandlePageToSwitch);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        return datosStep;
    }
}
