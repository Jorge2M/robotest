package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageReembolsos;

@SuppressWarnings("javadoc")
public class PageReembolsosStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Reembolsos<br>" +
            "2) Aparecen los inputs de BANCO, TITULAR e IBAN";
        datosStep.setStateIniValidations();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageReembolsos.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageReembolsos.existsInputBanco(dFTest.driver) ||
                !PageReembolsos.existsInputTitular(dFTest.driver) ||
                !PageReembolsos.existsInputIBAN(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
