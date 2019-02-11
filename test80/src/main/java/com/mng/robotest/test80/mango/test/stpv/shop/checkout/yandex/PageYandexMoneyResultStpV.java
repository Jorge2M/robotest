package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

@SuppressWarnings("javadoc")
public class PageYandexMoneyResultStpV {
    
    public static void validateIsResultOk(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de resultado de Yandex Money<br>" +
            "2) Aparece un mensaje de transferencia con éxito";
        datosStep.setNOKstateByDefault();         
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageYandexMoneyResult.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageYandexMoneyResult.isVisibleMsgTransferOk(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
}
