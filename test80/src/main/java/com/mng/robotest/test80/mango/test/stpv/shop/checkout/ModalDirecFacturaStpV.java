package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecFactura;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;

@SuppressWarnings("javadoc")
public class ModalDirecFacturaStpV {

    public static void validateIsOk(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSeconds = 5;
        String descripValidac = 
            "1) Es visible el formulario para la introducción de la \"Dirección de facturación\" (lo esperamos hasta " + maxSeconds + " seconds)<br>" +
            "2) Es visible el botón \"Actualizar\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDirecFactura.isVisibleFormUntil(maxSeconds, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ModalDirecFactura.isVisibleButtonActualizar(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputDataAndActualizar(DataDireccion dataDirFactura, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducir los datos y pulsar \"Actualizar\"<br>" + dataDirFactura.getFormattedHTMLData(), 
            "Los datos se actualizan correctamente");
        try {
            ModalDirecFactura.sendDataToInputs(dataDirFactura, dFTest.driver);
            ModalDirecFactura.clickActualizar(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validaciones
        String descripValidac = 
            "1) Desaparece el modal de introducción de los datos de la dirección <br>" +
            "2) Queda marcado el radiobutton \"Quiero recibir una factura\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (ModalDirecFactura.isVisibleFormUntil(0/*maxSeconds*/, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!Page1DktopCheckout.isMarkedQuieroFactura(dFTest.driver)) { 
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}
