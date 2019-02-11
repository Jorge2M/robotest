package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepa1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageSepa1rstStpV {
    
    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "4) Figura el campo de introducción del titular<br>" +
            "5) Figura el campo de introducción del la cuenta<br>" +
            "6) Figura un botón de pago";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageSepa1rst.isPresentIconoSepa(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info_NoHardcopy);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            }
            if (!PageSepa1rst.isPresentCabeceraStep(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentInputTitular(dFTest.driver)) {
                    listVals.add(4, State.Warn);            
                }
            }
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentInputCuenta(dFTest.driver)) {
                    listVals.add(5, State.Warn);
                }
            }
            if (channel==Channel.desktop) {
                if (!PageSepa1rst.isPresentButtonPagoDesktop(dFTest.driver)) {
                    listVals.add(6, State.Defect); 
                }
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputDataAndclickPay(String iban, String titular, String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        String descripStep = "";
        if (channel==Channel.movil_web)
            descripStep = "Seleccionamos el icono de SEPA. ";
        descripStep+=
            "Introducimos los datos:<br>" +
            "  - Titular: <b>" + titular + "</b><br>" +
            "  - Cuenta: <b>" + iban + "</b></br>" +
            "Y pulsamos el botón <b>Pay</b>";                
        DatosStep datosStep = new DatosStep       (
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
