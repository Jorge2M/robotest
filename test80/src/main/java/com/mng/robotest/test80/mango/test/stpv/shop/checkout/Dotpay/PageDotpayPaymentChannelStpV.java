package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayPaymentChannel;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageDotpayPaymentChannelStpV {

    public static void validateIsPage(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Dotpay para la selección del banco<br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDotpayPaymentChannel.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);            
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }    
    
    public static DatosStep selectPayment(int numPayment, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el <b>" + numPayment + "o</b> de los Canales de Pago", 
            "Se scrolla y se hace visible el bloque de introducción del nombre");
        try {
            PageDotpayPaymentChannel.clickPayment(numPayment, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Es visible el bloque de introducción del nombre (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDotpayPaymentChannel.isVisibleBlockInputDataUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static DatosStep inputNameAndConfirm(String nameFirst, String nameSecond, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Introducir el nombre <b>" + nameFirst + " / " + nameSecond + "</b> y seleccionar el botón para Confirmar", 
            "Aparece la página de pago");
        try {
            PageDotpayPaymentChannel.sendInputNombre(nameFirst, nameSecond, dFTest.driver);
            PageDotpayPaymentChannel.clickButtonConfirm(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageDotpayAcceptSimulationStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }    
}
