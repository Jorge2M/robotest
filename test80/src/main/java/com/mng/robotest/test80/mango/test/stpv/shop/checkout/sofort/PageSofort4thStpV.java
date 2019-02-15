package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort4th;

/**
 * Page4: la página de entrada usuario/password
 * @author jorge.munoz
 *
 */

public class PageSofort4thStpV {
    
    public static void validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) { 
        String descripValidac = 
            "1) Aparece la página de introducción del Usuario/Password de \"SOFORT\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSofort4th.isPage(dFTest.driver)) {
                listVals.add(1,State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputCredencialesUsr(String usrSofort, String passSofort, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Introducir el usuario/password de DEMO: " + usrSofort + " / " + passSofort, 
            "Aparece la página de selección de cuenta");
        try {
            PageSofort4th.inputUserPass(dFTest.driver, usrSofort, passSofort);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        String descripValidac = 
            "1) Aparece un formulario para la selección de la cuenta"; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep); 
        try {
            if (!PageSofort4th.isVisibleFormSelCta(dFTest.driver)) {
                listVals.add(1,State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep select1rstCtaAndAccept(DataFmwkTest dFTest) throws Exception { 
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos la 1a cuenta y pulsamos aceptar", 
            "Aparece la página de confirmación de la transacción");
        try {
            //Seleccionamos el radio correspondiente a la 1a cuenta + submit
            PageSofort4th.selectRadioCta(dFTest.driver, 1);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece un campo para la introducción del TAN"; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSofort4th.isVisibleInputTAN(dFTest.driver)) {
                listVals.add(1,State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static DatosStep inputTANandAccept(String TANSofort, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Introducción del TAN: " + TANSofort + " y pulsamos aceptar", 
            "El pago se realiza correctamente");
        try {
            PageSofort4th.inputTAN(dFTest.driver, TANSofort);
            PageSofort4th.clickSubmitButton(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}