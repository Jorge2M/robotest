package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputTarjeta;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageAmexInputTarjetaStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    public static void validateIsPageOk(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones 
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la pasarela de pagos de Banco Sabadell (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparecen los campos de introducción de tarjeta, fecha caducidad y código de seguridad<br>" +
            "4) Figura un botón de Aceptar";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageAmexInputTarjeta.isPasarelaBancoSabadellUntil(maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageAmexInputTarjeta.isPresentNumTarj(dFTest.driver) ||
                !PageAmexInputTarjeta.isPresentInputMesCad(dFTest.driver) ||
                !PageAmexInputTarjeta.isPresentInputAnyCad(dFTest.driver) ||
                !PageAmexInputTarjeta.isPresentInputCvc(dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
            //4)
            if (!PageAmexInputTarjeta.isPresentPagarButton(dFTest.driver)) 
                fmwkTest.addValidation(4, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem validating Page Amex for input tarjeta in country {}", codPais, e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep inputTarjetaAndPayButton(String numTarj, String mesCad, String anyCad, String Cvc, String importeTotal, String codigoPais, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Introducimos los datos de la tarjeta: " + numTarj + " / " + mesCad + "-" + anyCad + " / " + Cvc + " y pulsamos el botón \"Pagar\""/*Descripción Test*/, 
            "Aparece la página de introducción del CIP");
        try {
            PageAmexInputTarjeta.inputDataTarjeta(numTarj, mesCad, anyCad, Cvc, dFTest.driver);
            PageAmexInputTarjeta.clickPagarButton(dFTest.driver);
                               
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                    
        //Validaciones
        PageAmexInputCipStpV.validateIsPageOk(importeTotal, codigoPais, datosStep, dFTest);
        
        return datosStep;
    }
}
