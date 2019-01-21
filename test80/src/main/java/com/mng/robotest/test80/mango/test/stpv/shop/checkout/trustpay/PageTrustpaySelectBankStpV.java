package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpaySelectBank;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageTrustpaySelectBankStpV {

    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +            
            "4) Figura el desplegable de bancos<br>" +
            "5) Figura un botón de pago";            
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageTrustpaySelectBank.isPresentEntradaPago(nombrePago, channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                if (channel==Channel.movil_web)
                    fmwkTest.addValidation(2, State.Info, listVals);
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);            
            //3)
            if (!PageTrustpaySelectBank.isPresentCabeceraStep(nombrePago, channel, dFTest.driver)) 
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4) 
            if (channel==Channel.desktop) {
                if (!PageTrustpaySelectBank.isPresentSelectBancos(dFTest.driver))
                    fmwkTest.addValidation(4, State.Warn, listVals);
            }
            //5)
            if (channel==Channel.desktop) {
                if (!PageTrustpaySelectBank.isPresentButtonPago(dFTest.driver)) 
                    fmwkTest.addValidation(5, State.Defect, listVals); 
            }
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * Seleccionamos un banco del desplegable de tipo test (contiene "TestPay")
     */
    public static void selectTestBankAndPay(String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        ArrayList<String> listOfPosibleValues = new ArrayList<>();
        listOfPosibleValues.addAll(Arrays.asList("TestPay", "Fio banka"));
        datosStep datosStep = new datosStep       (
            "Seleccionamos un banco de test (contiene alguno de los textos \"" + String.join(",", listOfPosibleValues) + "\") y pulsamos <b>Pay</b>", 
            "Aparece la página de test para la confirmación");
        try {
            PageTrustpaySelectBank.selectBankThatContains(listOfPosibleValues, channel, dFTest.driver);
            PageTrustpaySelectBank.clickButtonToContinuePay(channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validation
        //PageTrustpayTestConfirmStpV.validateIsPage(datosStep, dFTest);
        PageTrustPayResultStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
    }
}
