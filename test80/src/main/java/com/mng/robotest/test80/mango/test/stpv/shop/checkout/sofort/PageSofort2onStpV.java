package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort2on;

/**
 * Page2: la página de selección del país
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PageSofort2onStpV {
    
    public static void validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece la página de selección del país/banco (la esperamos hasta " + maxSecondsToWait + " segundos)"; 
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSofort2on.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectPaisYBanco(String paisSofort, String bankCode, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el país con código <b>" + paisSofort + "</b> y el código de banco <b>" + bankCode + "</b> y pulsamos \"Weiter\"", 
            "Aparece la página de indentificación en SOFORT");
        try {
            PageSofort2on.selectPais(dFTest.driver, paisSofort);
            PageSofort2on.inputBankcode(bankCode, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 5);
            PageSofort2on.clickSubmitButtonPage3(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    
        //Validaciones
        PageSofort4thStpV.validaIsPage(datosStep, dFTest);

        return datosStep;
    }
}
