package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputCip;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAmexInputCipStpV {
    
    public static void validateIsPageOk(String importeTotal, String codigoPais, DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la página de introducción del CIP (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) Aparece el importe de la operación " + importeTotal;
        datosStep.setExcepExists(true);
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAmexInputCip.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep inputCipAndAcceptButton(String CIP, String importeTotal, String codigoPais, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducimos el CIP " + CIP + " y pulsamos el botón \"Aceptar\"", 
            "Aparece una página de la pasarela de resultado OK");
        try {
            PageAmexInputCip.inputCIP(CIP, dFTest.driver);
            PageAmexInputCip.clickAceptarButton(dFTest.driver);
                               
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                    
        //Validaciones
        PageAmexResultStpV.validateIsPageOk(datosStep, importeTotal, codigoPais, dFTest);
        
        return datosStep;
    }
}
