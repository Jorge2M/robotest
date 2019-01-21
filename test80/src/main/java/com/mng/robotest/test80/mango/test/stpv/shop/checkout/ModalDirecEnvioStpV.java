package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;

@SuppressWarnings("javadoc")
public class ModalDirecEnvioStpV {

    public static void validateIsOk(datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSeconds = 5;
        String descripValidac = 
            "1) Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta " + maxSeconds + " seconds)<br>" +
            "2) Es visible el botón \"Actualizar\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDirecEnvio.isVisibleFormUntil(maxSeconds, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ModalDirecEnvio.isVisibleButtonActualizar(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    @SuppressWarnings("static-access")
    public static datosStep inputDataAndActualizar(DataDireccion dataDirFactura, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Introducir los datos y pulsar \"Actualizar\"<br>" + dataDirFactura.getFormattedHTMLData(), 
            "Los datos se actualizan correctamente");
        try {
            ModalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirFactura, 2/*nTimes*/, dFTest.driver);
            ModalDirecEnvio.moveToAndDoubleClickActualizar(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Desaparece el modal de introducción de los datos de la dirección (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDirecEnvio.isInvisibleFormUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (!Page1DktopCheckout.modalAvisoCambioPais.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}
