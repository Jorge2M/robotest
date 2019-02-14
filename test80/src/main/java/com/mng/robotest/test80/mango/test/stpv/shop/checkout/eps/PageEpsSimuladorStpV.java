package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;

@SuppressWarnings("javadoc")
public class PageEpsSimuladorStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página de simulación de EPS"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageEpsSimulador.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectDelay(TypeDelay typeDelay, DataFmwkTest dFTest) {
        DatosStep datosStep = new DatosStep       (
            "Seleccionar la opción <b>" + typeDelay + "</b> del apartado \"pending-authorised\"", 
            "La opción se selecciona correctamente");
        try {
            PageEpsSimulador.selectDelayAuthorised(typeDelay, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        return datosStep;
    }
    
    public static DatosStep clickContinueButton(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"pending > autrhorised\"", 
            "El pago se realiza correctamente");
        try {
            //Selecionamos el botón "Continuar"
            PageEpsSimulador.clickButtonContinue(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }
}
