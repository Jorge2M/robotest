package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepaResultMobil;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageSepaResultMobilStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de resultado de SEPA para móvil<br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSepaResultMobil.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);            
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep clickButtonPagar(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el botón para Pagar", 
            "Aparece la página de resultado OK del pago en Mango");
        try {
            PageSepaResultMobil.clickButtonPay(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
