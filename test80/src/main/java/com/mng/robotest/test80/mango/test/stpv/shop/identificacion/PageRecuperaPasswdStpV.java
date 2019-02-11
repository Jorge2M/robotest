package com.mng.robotest.test80.mango.test.stpv.shop.identificacion;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageRecuperaPasswd;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageRecuperaPasswdStpV {
    
    public static void isPage(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la pantalla de recuperación de la contraseña (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el campo para la introducción del correo";                         
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRecuperaPasswd.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageRecuperaPasswd.isPresentInputCorreo(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
    public static void inputMailAndClickEnviar(String email, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Introducir el email <b>" + email + "</b> y pulsar el botón \"Enviar\"", 
            "Aparece la página de cambio de contraseña");
        try {
            PageRecuperaPasswd.inputEmail(email, dFTest.driver);
            PageRecuperaPasswd.clickEnviar(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece el mensaje de \"Revisa tu email\" (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el botón \"Ir de Shopping\"";                         
        datosStep.setNOKstateByDefault();          
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRecuperaPasswd.isVisibleRevisaTuEmailUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageRecuperaPasswd.isVisibleButtonIrDeShopping(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar
        AllPagesStpV.validacionesEstandar(false/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
}
