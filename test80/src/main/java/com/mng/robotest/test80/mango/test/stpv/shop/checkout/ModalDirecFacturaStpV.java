package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDirecFactura.isVisibleFormUntil(maxSeconds, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ModalDirecFactura.isVisibleButtonActualizar(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        String descripValidac = 
            "1) Desaparece el modal de introducción de los datos de la dirección <br>" +
            "2) Queda marcado el radiobutton \"Quiero recibir una factura\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (ModalDirecFactura.isVisibleFormUntil(0/*maxSeconds*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!Page1DktopCheckout.isMarkedQuieroFactura(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}
