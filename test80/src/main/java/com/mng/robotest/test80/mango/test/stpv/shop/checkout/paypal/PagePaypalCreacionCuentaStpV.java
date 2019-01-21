package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;

@SuppressWarnings("javadoc")
public class PagePaypalCreacionCuentaStpV {
    
    public static datosStep clickButtonIniciarSesion(DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep     (
            "Seleccionamos el botón <b>Iniciar Sesión</b>", 
            "Aparece la página de login");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);    
        try {           
            PagePaypalCreacionCuenta.clickButtonIniciarSesion(dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PagePaypalLoginStpV.validateIsPageUntil(10/*maxSecondsToWait*/, datosStep, dFTest);
        
        return datosStep;
    }
}
