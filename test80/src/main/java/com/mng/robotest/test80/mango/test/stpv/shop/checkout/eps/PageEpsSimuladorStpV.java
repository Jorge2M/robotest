package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;

@SuppressWarnings("javadoc")
public class PageEpsSimuladorStpV {
    
    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página de simulación de EPS"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);  
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageEpsSimulador.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep selectDelay(TypeDelay typeDelay, DataFmwkTest dFTest) {
        datosStep datosStep = new datosStep       (
            "Seleccionar la opción <b>" + typeDelay + "</b> del apartado \"pending-authorised\"", 
            "La opción se selecciona correctamente");
        try {
            PageEpsSimulador.selectDelayAuthorised(typeDelay, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        return datosStep;
    }
    
    public static datosStep clickContinueButton(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep       (
            "Seleccionar el botón \"pending > autrhorised\"", 
            "El pago se realiza correctamente");
        try {
            //Selecionamos el botón "Continuar"
            PageEpsSimulador.clickButtonContinue(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
