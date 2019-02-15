package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.Arrays;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpaySelectBank;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageTrustpaySelectBankStpV {

    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +            
            "4) Figura el desplegable de bancos<br>" +
            "5) Figura un botón de pago";            
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageTrustpaySelectBank.isPresentEntradaPago(nombrePago, channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            //3)
            if (!PageTrustpaySelectBank.isPresentCabeceraStep(nombrePago, channel, dFTest.driver)) 
                listVals.add(3, State.Warn);
            //4) 
            if (channel==Channel.desktop) {
                if (!PageTrustpaySelectBank.isPresentSelectBancos(dFTest.driver))
                    listVals.add(4, State.Warn);
            }
            //5)
            if (channel==Channel.desktop) {
                if (!PageTrustpaySelectBank.isPresentButtonPago(dFTest.driver)) 
                    listVals.add(5, State.Defect); 
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Seleccionamos un banco del desplegable de tipo test (contiene "TestPay")
     */
    public static void selectTestBankAndPay(String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        ArrayList<String> listOfPosibleValues = new ArrayList<>();
        listOfPosibleValues.addAll(Arrays.asList("TestPay", "Fio banka"));
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos un banco de test (contiene alguno de los textos \"" + String.join(",", listOfPosibleValues) + "\") y pulsamos <b>Pay</b>", 
            "Aparece la página de test para la confirmación");
        try {
            PageTrustpaySelectBank.selectBankThatContains(listOfPosibleValues, channel, dFTest.driver);
            PageTrustpaySelectBank.clickButtonToContinuePay(channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validation
        //PageTrustpayTestConfirmStpV.validateIsPage(datosStep, dFTest);
        PageTrustPayResultStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
    }
}
