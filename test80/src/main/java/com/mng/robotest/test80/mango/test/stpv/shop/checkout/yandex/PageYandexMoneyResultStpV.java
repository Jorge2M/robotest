package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

@SuppressWarnings("javadoc")
public class PageYandexMoneyResultStpV {
    
    public static void validateIsResultOk(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de resultado de Yandex Money<br>" +
            "2) Aparece un mensaje de transferencia con éxito";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageYandexMoneyResult.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (!PageYandexMoneyResult.isVisibleMsgTransferOk(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
}
