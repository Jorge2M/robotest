package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepa1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageSepa1rstStpV {
    
    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "4) Figura el campo de introducción del titular<br>" +
            "5) Figura el campo de introducción del la cuenta<br>" +
            "6) Figura un botón de pago";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageSepa1rst.isPresentIconoSepa(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                if (channel==Channel.movil_web)
                    fmwkTest.addValidation(2, State.Info_NoHardcopy, listVals);
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);            
            //3)
            if (!PageSepa1rst.isPresentCabeceraStep(dFTest.driver)) 
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentInputTitular(dFTest.driver)) 
                    fmwkTest.addValidation(4, State.Warn, listVals);            
            }
            //5)
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentInputCuenta(dFTest.driver)) 
                    fmwkTest.addValidation(5, State.Warn, listVals);
            }
            //6)
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentButtonPagoDesktop(dFTest.driver)) 
                    fmwkTest.addValidation(6, State.Defect, listVals); 
            }
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputDataAndclickPay(String iban, String titular, String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        String descripStep = "";
        if (channel==Channel.movil_web)
            descripStep = "Seleccionamos el icono de SEPA. ";
        descripStep+=
            "Introducimos los datos:<br>" +
            "  - Titular: <b>" + titular + "</b><br>" +
            "  - Cuenta: <b>" + iban + "</b></br>" +
            "Y pulsamos el botón <b>Pay</b>";                
        datosStep datosStep = new datosStep       (
            descripStep, 
            "Aparece la página de resultado de pago OK de Mango");
        try {
            if (channel==Channel.movil_web)
                PageSepa1rst.clickIconoSepa(channel, dFTest.driver);
                
            PageSepa1rst.inputTitular(titular, dFTest.driver);
            PageSepa1rst.inputCuenta(iban, dFTest.driver);
            PageSepa1rst.clickAcepto(dFTest.driver);
            PageSepa1rst.clickButtonContinuePago(channel, dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //En el caso de móvil aparece una página de resultado específica de SEPA
        if (channel==Channel.movil_web)
            PageSepaResultMobilStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
        
        return datosStep;
    }
}
