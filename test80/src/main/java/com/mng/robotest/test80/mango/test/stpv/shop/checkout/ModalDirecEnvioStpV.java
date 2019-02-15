package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecEnvio;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;


public class ModalDirecEnvioStpV {

    public static void validateIsOk(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSeconds = 5;
        String descripValidac = 
            "1) Es visible el formulario para la introducción de la \"Dirección de envío\" (lo esperamos hasta " + maxSeconds + " seconds)<br>" +
            "2) Es visible el botón \"Actualizar\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDirecEnvio.isVisibleFormUntil(maxSeconds, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ModalDirecEnvio.isVisibleButtonActualizar(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    @SuppressWarnings("static-access")
    public static DatosStep inputDataAndActualizar(DataDireccion dataDirFactura, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducir los datos y pulsar \"Actualizar\"<br>" + dataDirFactura.getFormattedHTMLData(), 
            "Los datos se actualizan correctamente");
        try {
            ModalDirecEnvio.sendDataToInputsNTimesAndWait(dataDirFactura, 2/*nTimes*/, dFTest.driver);
            ModalDirecEnvio.moveToAndDoubleClickActualizar(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Desaparece el modal de introducción de los datos de la dirección (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece un modal de alerta alertando de un posible cambio de precios (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDirecEnvio.isInvisibleFormUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!Page1DktopCheckout.modalAvisoCambioPais.isVisibleUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}
