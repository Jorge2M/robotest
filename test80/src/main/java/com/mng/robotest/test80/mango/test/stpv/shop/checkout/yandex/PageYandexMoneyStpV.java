package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoney;

@SuppressWarnings("javadoc")
public class PageYandexMoneyStpV {

    public static DatosStep accessInNewTab(String tabTitle, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Accedemos a la URL de <b>YandexMoney</b>: " + PageYandexMoney.urlAccess, 
            "Aparece la página de YandexMoney");
        try {
            PageYandexMoney.goToPageInNewTab(tabTitle, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validation
        validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el input para el <b>Payment Code</b><br>" +
            "2) Aparece el input para el importe";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageYandexMoney.isVisibleInputPaymentCode(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageYandexMoney.isVisibleInputImport(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputDataAndPay(String paymentCode, String importe, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Introducimos el paymentCode <b>" + paymentCode + "</b>, el importe <b>" + importe + "</b> y pulsamos el botón de Pago", 
            "Aparece la página de resultado del pago a nivel de Yandex");
        try {
            PageYandexMoney.inputPaymentCode(paymentCode, dFTest.driver);
            PageYandexMoney.inputImport(importe, dFTest.driver);
            PageYandexMoney.clickPayButton(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }

        //Validaciones
        PageYandexMoneyResultStpV.validateIsResultOk(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep closeTabByTitle(String tabTitle, String windowHandlePageToSwitch, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Cerramos la actual pestaña con nombre <b>" + tabTitle + "</b>", 
            "Desaparece la pestaña");
        try {
            PageYandexMoney.closeActualTabByTitle(tabTitle, dFTest.driver);
            dFTest.driver.switchTo().window(windowHandlePageToSwitch);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }        
        
        return datosStep;
    }
}
